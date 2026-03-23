package com.skul9x.battu.core

import com.skul9x.battu.data.*
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.*

/**
 * BaZiLogic - Core calculation engine for Four Pillars (Bát Tự)
 * Cloned from TuViAndroid-BatTu with package name changes only
 * DO NOT modify the calculation algorithms
 */
class BaZiLogic(private val solarTermsJson: String) {

    private val solarTermsData = JSONObject(solarTermsJson)
    private val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US).apply {
        timeZone = TimeZone.getTimeZone("UTC")
    }

    fun calculateBaZi(input: UserInput): BaZiData {
        val longitude = input.longitude ?: 105.8
        
        // 1. Calculate True Solar Time (TST)
        // TST = LocalTime + (Longitude - TimezoneMeridian) * 4 minutes
        // For UTC+7, TimezoneMeridian = 7 * 15 = 105
        val longitudeCorrectionMinutes = ((longitude - 105.0) * 4).toInt()
        
        val calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT+7")).apply {
            set(input.solarYear, input.solarMonth - 1, input.solarDay, input.hour, 0)
            add(Calendar.MINUTE, longitudeCorrectionMinutes)
        }
        
        val tstYear = calendar.get(Calendar.YEAR)
        val tstMonth = calendar.get(Calendar.MONTH) + 1
        val tstDay = calendar.get(Calendar.DAY_OF_MONTH)
        val tstHour = calendar.get(Calendar.HOUR_OF_DAY)
        val tstMinute = calendar.get(Calendar.MINUTE)

        // 2. Day Boundary (23:00)
        var calcJdn = calculateJDN(tstYear, tstMonth, tstDay)
        if (input.dayBoundaryAt23 && tstHour >= 23) {
            calcJdn += 1
        }
        
        // 3. Year Pillar
        // Based on Lập Xuân (start_of_spring)
        val birthTimeUtc = calendar.timeInMillis
        val lapXuanThisYear = getSolarTerm(tstYear, "start_of_spring")
        
        val yearCalc = if (birthTimeUtc < lapXuanThisYear) tstYear - 1 else tstYear
        val yearCanIdx = (yearCalc - 4 + 100) % 10
        val yearChiIdx = (yearCalc - 4 + 120) % 12
        val dayCanIdx = ((calcJdn + 9) % 10).toInt()
        val dayMaster = BaZiConstants.THIEN_CAN[dayCanIdx]
        
        val yearPillar = createPillar(BaZiConstants.THIEN_CAN[yearCanIdx], BaZiConstants.DIA_CHI[yearChiIdx], dayMaster)

        // 4. Month Pillar
        val monthChiIdx = calculateMonthChiIndex(tstYear, birthTimeUtc)
        val monthCanIdx = calculateMonthCanIndex(yearCanIdx, monthChiIdx)
        val monthPillar = createPillar(BaZiConstants.THIEN_CAN[monthCanIdx], BaZiConstants.DIA_CHI[monthChiIdx], dayMaster)

        // 5. Day Pillar
        val dayChiIdx = ((calcJdn + 1) % 12).toInt()
        val dayPillar = createPillar(BaZiConstants.THIEN_CAN[dayCanIdx], BaZiConstants.DIA_CHI[dayChiIdx], dayMaster)

        // 6. Hour Pillar
        val hourChiIdx = (((tstHour + 1) / 2) % 12) 
        val hourCanIdx = calculateHourCanIndex(dayCanIdx, hourChiIdx)
        val hourPillar = createPillar(BaZiConstants.THIEN_CAN[hourCanIdx], BaZiConstants.DIA_CHI[hourChiIdx], dayMaster)

        // 7. Ten Gods
        val tenGods = TenGods(
            dayMaster = dayMaster,
            yearStemGod = BaZiConstants.calculateTenGod(dayMaster, yearPillar.stem),
            yearBranchGod = BaZiConstants.calculateTenGod(dayMaster, BaZiConstants.getMainHiddenStem(yearPillar.branch)),
            monthStemGod = BaZiConstants.calculateTenGod(dayMaster, monthPillar.stem),
            monthBranchGod = BaZiConstants.calculateTenGod(dayMaster, BaZiConstants.getMainHiddenStem(monthPillar.branch)),
            dayStemGod = "Nhật Chủ",
            dayBranchGod = BaZiConstants.calculateTenGod(dayMaster, BaZiConstants.getMainHiddenStem(dayPillar.branch)),
            hourStemGod = BaZiConstants.calculateTenGod(dayMaster, hourPillar.stem),
            hourBranchGod = BaZiConstants.calculateTenGod(dayMaster, BaZiConstants.getMainHiddenStem(hourPillar.branch))
        )

        // 8. Element Balance
        val elementBalance = calculateElementBalance(listOf(yearPillar, monthPillar, dayPillar, hourPillar))

        // 9. Season & Strength
        val birthSeason = BaZiConstants.getSeason(monthPillar.branch)
        val dmStrength = BaZiConstants.getDayMasterStrength(birthSeason, dayMaster)

        val currentTermInfo = getCurrentAndNextTerm(tstYear, birthTimeUtc)

        val pillarsMap = mapOf("Năm" to yearPillar, "Tháng" to monthPillar, "Ngày" to dayPillar, "Giờ" to hourPillar)
        val shenSha = calculateShenSha(yearPillar, dayPillar, pillarsMap)
        val interactions = calculateInteractions(pillarsMap)
        val luckPillars = calculateLuckPillars(yearPillar.stem, monthCanIdx, monthChiIdx, input.gender, birthTimeUtc, tstYear)

        return BaZiData(
            birthInfo = "TST: $tstYear-$tstMonth-$tstDay $tstHour:$tstMinute (Long: $longitude)",
            year = yearPillar,
            month = monthPillar,
            day = dayPillar,
            hour = hourPillar,
            tenGods = tenGods,
            currentTerm = currentTermInfo.first,
            nextTerm = currentTermInfo.second,
            nextTermTime = currentTermInfo.third,
            elementBalance = elementBalance,
            season = birthSeason.label,
            dayMasterStrength = dmStrength,
            interactions = interactions,
            shenShaList = shenSha,
            luckPillars = luckPillars
        )
    }

    private fun createPillar(can: String, chi: String): Pillar {
        return createPillar(can, chi, "")
    }

    private fun createPillar(can: String, chi: String, dayMaster: String): Pillar {
        val stemElement = BaZiConstants.getElementOfStem(can)
        val branchElement = BaZiConstants.getElementOfBranch(chi)
        
        return Pillar(
            stem = can,
            stemYinYang = BaZiConstants.getYinYangOfStem(can),
            stemElement = BaZiConstants.getElementNameVi(stemElement),
            branch = chi,
            branchYinYang = BaZiConstants.getYinYangOfBranch(chi),
            branchElement = BaZiConstants.getElementNameVi(branchElement),
            napAm = BaZiConstants.getNapAm(can, chi),
            hiddenStems = BaZiConstants.getHiddenStems(chi),
            lifeStage = if (dayMaster.isNotEmpty()) BaZiConstants.getLifeStage(dayMaster, chi) else null
        )
    }

    private val solarTermNames = mapOf(
            "start_of_spring" to "Lập Xuân",
            "spring_showers" to "Vũ Thủy",
            "awakening_of_insects" to "Kinh Trập",
            "spring_equinox" to "Xuân Phân",
            "pure_brightness" to "Thanh Minh",
            "grain_rain" to "Cốc Vũ",
            "start_of_summer" to "Lập Hạ",
            "grain_buds" to "Tiểu Mãn",
            "grain_in_ear" to "Mang Chủng",
            "summer_solstice" to "Hạ Chí",
            "minor_heat" to "Tiểu Thử",
            "major_heat" to "Đại Thử",
            "start_of_autumn" to "Lập Thu",
            "end_of_heat" to "Xử Thử",
            "white_dew" to "Bạch Lộ",
            "autumn_equinox" to "Thu Phân",
            "cold_dew" to "Hàn Lộ",
            "frost" to "Sương Giáng",
            "start_of_winter" to "Lập Đông",
            "minor_snow" to "Tiểu Tuyết",
            "major_snow" to "Đại Tuyết",
            "winter_solstice" to "Đông Chí",
            "minor_cold" to "Tiểu Hàn",
            "major_cold" to "Đại Hàn"
    )

    private fun calculateMonthChiIndex(year: Int, birthTimeUtc: Long): Int {
        val monthStarts = listOf(
            "minor_cold" to 1,          // Jan (~Jan 5)
            "start_of_spring" to 2,     // Feb (Lập Xuân ~Feb 4)
            "awakening_of_insects" to 3, // Mar (Kinh Trập ~Mar 5)
            "pure_brightness" to 4,     // Apr (Thanh Minh ~Apr 5)
            "start_of_summer" to 5,     // May (Lập Hạ ~May 5)
            "grain_in_ear" to 6,       // Jun (Mang Chủng ~Jun 6)
            "minor_heat" to 7,          // Jul (Tiểu Thử ~Jul 7)
            "start_of_autumn" to 8,     // Aug (Lập Thu ~Aug 8)
            "white_dew" to 9,           // Sep (Bạch Lộ ~Sep 8)
            "cold_dew" to 10,           // Oct (Hàn Lộ ~Oct 8)
            "start_of_winter" to 11,    // Nov (Lập Đông ~Nov 7)
            "major_snow" to 0           // Dec (Đại Tuyết ~Dec 7)
        )
        
        var lastChi = 1
        val checkYears = listOf(year - 1, year)
        for (y in checkYears) {
            for ((term, chi) in monthStarts) {
                if (birthTimeUtc >= getSolarTerm(y, term)) {
                    lastChi = chi
                }
            }
        }
        return lastChi
    }

    private fun calculateMonthCanIndex(yearCanIdx: Int, monthChiIdx: Int): Int {
        val startCan = when (yearCanIdx % 5) {
            0 -> 2 // Giáp/Kỷ -> Bính Dần (2)
            1 -> 4 // Ất/Canh -> Mậu Dần (4)
            2 -> 6 // Bính/Tân -> Canh Dần (6)
            3 -> 8 // Đinh/Nhâm -> Nhâm Dần (8)
            4 -> 0 // Mậu/Quý -> Giáp Dần (0)
            else -> 0
        }
        val diff = (monthChiIdx - 2 + 12) % 12
        return (startCan + diff) % 10
    }

    private fun calculateHourCanIndex(dayCanIdx: Int, hourChiIdx: Int): Int {
        val startCan = when (dayCanIdx % 5) {
            0 -> 0 // Giáp/Kỷ -> Giáp Tý (0)
            1 -> 2 // Ất/Canh -> Bính Tý (2)
            2 -> 4 // Bính/Tân -> Mậu Tý (4)
            3 -> 6 // Đinh/Nhâm -> Canh Tý (6)
            4 -> 8 // Mậu/Quý -> Nhâm Tý (8)
            else -> 0
        }
        return (startCan + hourChiIdx) % 10
    }

    private fun getSolarTerm(year: Int, termKey: String): Long {
        return try {
            val yearStr = year.toString()
            if (solarTermsData.has(yearStr)) {
                val yearData = solarTermsData.getJSONObject(yearStr).getJSONObject("data")
                val dateStr = yearData.getString(termKey).replace("T", " ").take(19)
                sdf.parse(dateStr)?.time ?: 0L
            } else 0L
        } catch (e: Exception) {
            0L
        }
    }

    private fun getCurrentAndNextTerm(year: Int, birthTimeUtc: Long): Triple<String, String, String> {
        val allTerms = mutableListOf<Pair<Long, String>>()
        for (y in (year - 1)..(year + 1)) {
            solarTermNames.keys.forEach { key ->
                allTerms.add(getSolarTerm(y, key) to key)
            }
        }
        allTerms.sortBy { it.first }
        
        var current = "N/A"
        var next = "N/A"
        var nextTime = "N/A"
        
        for (i in 0 until allTerms.size - 1) {
            if (birthTimeUtc >= allTerms[i].first && birthTimeUtc < allTerms[i+1].first) {
                current = solarTermNames[allTerms[i].second] ?: "N/A"
                next = solarTermNames[allTerms[i+1].second] ?: "N/A"
                nextTime = sdf.format(Date(allTerms[i+1].first))
                break
            }
        }
        
        return Triple(current, next, nextTime)
    }

    private fun calculateElementBalance(pillars: List<Pillar>): Map<String, Int> {
        val scores = mutableMapOf("Kim" to 0, "Mộc" to 0, "Thủy" to 0, "Hỏa" to 0, "Thổ" to 0)
        for (p in pillars) {
            // 1. Thiên Can lộ: 40 điểm
            val stemElement = BaZiConstants.getElementOfStem(p.stem)
            val stemName = BaZiConstants.getElementNameVi(stemElement)
            if (stemName.isNotEmpty()) {
                scores[stemName] = (scores[stemName] ?: 0) + 40
            }
            
            // 2. Địa Chi (Tàng Can): 60 điểm chia theo tỷ lệ
            for (hs in p.hiddenStems) {
                val hsElement = BaZiConstants.getElementOfStem(hs.stem)
                val hsName = BaZiConstants.getElementNameVi(hsElement)
                if (hsName.isNotEmpty()) {
                    val contribution = (hs.percentage * 60) / 100
                    scores[hsName] = (scores[hsName] ?: 0) + contribution
                }
            }
            
            // 3. Nạp Âm bonus: +10 điểm
            p.napAm?.let { na ->
                val naName = BaZiConstants.getElementNameVi(na.element)
                if (naName.isNotEmpty()) {
                    scores[naName] = (scores[naName] ?: 0) + 10
                }
            }
        }
        return scores
    }

    private fun calculateJDN(year: Int, month: Int, day: Int): Long {
        var yLong = year.toLong()
        var mLong = month.toLong()
        val dLong = day.toLong()
        val a = (14 - mLong) / 12
        yLong = yLong + 4800 - a
        mLong = mLong + 12 * a - 3
        return dLong + (153 * mLong + 2) / 5 + 365 * yLong + yLong / 4 - yLong / 100 + yLong / 400 - 32045
    }

    private fun calculateShenSha(yearPillar: Pillar, dayPillar: Pillar, pillars: Map<String, Pillar>): List<ShenSha> {
        val result = mutableListOf<ShenSha>()
        val yearStem = yearPillar.stem
        val dayStem = dayPillar.stem
        val yearBranch = yearPillar.branch
        val dayBranch = dayPillar.branch
        val thienAtRefs = listOf(dayStem, yearStem)
        
        pillars.forEach { (name, p) ->
            val cb = p.branch
            
            // 1. Thiên Ất Quý Nhân
            var addedTA = false
            for (ref in thienAtRefs) {
                val validTA = BaZiConstants.THIEN_AT_QUY_NHAN[ref] ?: emptyList()
                if (!addedTA && validTA.contains(cb)) {
                    result.add(ShenSha("Thiên Ất Quý Nhân", name, "Cát", "Gặp dữ hóa lành, quý nhân phù trợ"))
                    addedTA = true
                }
            }
            
            // 2. Lộc Tồn
            if (BaZiConstants.LOC_TON[dayStem] == cb) {
                result.add(ShenSha("Lộc Tồn", name, "Cát", "Tài lộc, phú quý"))
            }
            
            // 3. Kình Dương
            if (BaZiConstants.KINH_DUONG[dayStem] == cb) {
                result.add(ShenSha("Kình Dương", name, "Hung", "Nóng tính, cản trở, tai họa"))
            }
            
            // 4. Dịch Mã
            val dichMaBranches = listOf(BaZiConstants.DICH_MA[dayBranch], BaZiConstants.DICH_MA[yearBranch])
            if (cb in dichMaBranches) {
                if (result.none { it.name == "Dịch Mã" && it.pillar == name }) {
                    result.add(ShenSha("Dịch Mã", name, "Trung", "Biến động, di chuyển, thay đổi"))
                }
            }
            
            // 5. Hoa Cái
            val hoaCaiBranches = listOf(BaZiConstants.HOA_CAI[dayBranch], BaZiConstants.HOA_CAI[yearBranch])
            if (cb in hoaCaiBranches) {
                if (result.none { it.name == "Hoa Cái" && it.pillar == name }) {
                    result.add(ShenSha("Hoa Cái", name, "Trung", "Nghệ thuật, cô độc, huyền học"))
                }
            }

            // 6. Văn Xương
            val vanXuongBranches = listOf(BaZiConstants.VAN_XUONG[dayStem], BaZiConstants.VAN_XUONG[yearStem])
            if (cb in vanXuongBranches) {
                if (result.none { it.name == "Văn Xương" && it.pillar == name }) {
                    result.add(ShenSha("Văn Xương", name, "Cát", "Thông minh, học vấn, công danh"))
                }
            }

            // 7. Kiếp Sát
            val kiepSatBranches = listOf(BaZiConstants.KIEP_SAT[dayBranch], BaZiConstants.KIEP_SAT[yearBranch])
            if (cb in kiepSatBranches) {
                if (result.none { it.name == "Kiếp Sát" && it.pillar == name }) {
                    result.add(ShenSha("Kiếp Sát", name, "Hung", "Tai nạn, thị phi, hao tốn"))
                }
            }
        }

        // 8. Thập Ác Đại Bại (Chỉ xét trụ ngày)
        if (BaZiConstants.THAP_AC_DAI_BAI.contains(dayStem to dayBranch)) {
            result.add(ShenSha("Thập Ác Đại Bại", "Ngày", "Hung", "Hao tốn tiền bạc, khó tụ tài"))
        }

        return result
    }

    private fun calculateInteractions(pillarsMap: Map<String, Pillar>): List<Interaction> {
        val result = mutableListOf<Interaction>()
        val list = pillarsMap.toList()
        
        for (i in 0 until list.size) {
            val (name1, p1) = list[i]
            for (j in i+1 until list.size) {
                val (name2, p2) = list[j]
                if (BaZiConstants.LUC_HOP[p1.branch] == p2.branch) {
                    result.add(Interaction(InteractionType.LUC_HOP, "Lục Hợp", listOf(name1, name2), "${p1.branch} hợp ${p2.branch}"))
                }
                if (BaZiConstants.LUC_XUNG[p1.branch] == p2.branch) {
                    result.add(Interaction(InteractionType.LUC_XUNG, "Lục Xung", listOf(name1, name2), "${p1.branch} xung ${p2.branch}"))
                }
                if (BaZiConstants.LUC_HAI[p1.branch] == p2.branch) {
                    result.add(Interaction(InteractionType.LUC_HAI, "Lục Hại", listOf(name1, name2), "${p1.branch} hại ${p2.branch}"))
                }
                if (p1.branch == p2.branch && BaZiConstants.TU_HINH.contains(p1.branch)) {
                    result.add(Interaction(InteractionType.TU_HINH, "Tự Hình", listOf(name1, name2), "${p1.branch} tự hình"))
                }
            }
        }
        
        val allBranches = list.map { it.second.branch }.toSet()
        BaZiConstants.TAM_HOP.forEach { group ->
            if (allBranches.containsAll(group)) {
                val involved = list.filter { group.contains(it.second.branch) }.map { it.first }
                result.add(Interaction(InteractionType.TAM_HOP, "Tam Hợp", involved, group.joinToString("-")))
            }
        }
        BaZiConstants.TAM_HINH.forEach { group ->
            if (allBranches.containsAll(group)) {
                val involved = list.filter { group.contains(it.second.branch) }.map { it.first }
                if (involved.size >= 3) {
                    result.add(Interaction(InteractionType.TAM_HINH, "Tam Hình", involved, group.joinToString("-")))
                }
            }
        }
        return result
    }

    private fun calculateLuckPillars(yearStem: String, monthCanIdx: Int, monthChiIdx: Int, gender: Gender, birthTimeUtc: Long, year: Int): List<LuckPillar> {
        val results = mutableListOf<LuckPillar>()
        val isYearYang = BaZiConstants.getYinYangOfStem(yearStem) == "Dương"
        val isMale = gender == Gender.NAM
        val isThuan = (isMale && isYearYang) || (!isMale && !isYearYang)
        
        val terms = mutableListOf<Long>()
        // Tiết khí đại diện cho sự chuyển giao các tháng
        val ts = listOf("minor_cold", "start_of_spring", "awakening_of_insects", "pure_brightness", 
                        "start_of_summer", "grain_in_ear", "minor_heat", "start_of_autumn", 
                        "white_dew", "cold_dew", "start_of_winter", "major_snow")
        for (y in (year - 1)..(year + 1)) {
            ts.forEach { term -> terms.add(getSolarTerm(y, term)) }
        }
        terms.sort()
        
        val targetTermTime = if (isThuan) {
            terms.firstOrNull { it > birthTimeUtc } ?: birthTimeUtc
        } else {
            terms.lastOrNull { it <= birthTimeUtc } ?: birthTimeUtc
        }
        
        val diffMillis = Math.abs(targetTermTime - birthTimeUtc)
        val diffDays = diffMillis / (1000 * 60 * 60 * 24)
        var startAge = (diffDays / 3).toInt()
        if (startAge == 0) startAge = 1
        
        var currentCanIdx = monthCanIdx
        var currentChiIdx = monthChiIdx
        
        for (i in 0 until 10) {
            if (isThuan) {
                currentCanIdx = (currentCanIdx + 1) % 10
                currentChiIdx = (currentChiIdx + 1) % 12
            } else {
                currentCanIdx = (currentCanIdx - 1 + 10) % 10
                currentChiIdx = (currentChiIdx - 1 + 12) % 12
            }
            results.add(LuckPillar(
                startAge = startAge + (i * 10),
                endAge = startAge + (i * 10) + 9,
                stem = BaZiConstants.THIEN_CAN[currentCanIdx],
                branch = BaZiConstants.DIA_CHI[currentChiIdx]
            ))
        }
        return results
    }
}
