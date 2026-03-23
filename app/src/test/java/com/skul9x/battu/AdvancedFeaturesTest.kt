package com.skul9x.battu

import com.skul9x.battu.core.BaZiLogic
import com.skul9x.battu.data.BaZiConstants
import com.skul9x.battu.data.Gender
import com.skul9x.battu.data.UserInput
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Assert.assertFalse
import org.junit.Test
import java.io.File

/**
 * AdvancedFeaturesTest - Comprehensive verification for BatTu Engine v2.2.0
 * 
 * This test suite validates all features implemented in the "Advanced Bazi Features" plan:
 * 1. Phục Ngâm (Branch duplication)
 * 2. Tuần Không (Void Branches - Year and Day based)
 * 3. Thai Nguyên (Fetal Origin)
 * 4. Mệnh Cung (Life Palace via Ngũ Hổ Độn)
 * 5. Expanded Shen Sha (Đào Hoa, Thiên Y, Hồng Loan, Thiên Hỉ)
 * 6. Ten Gods for all Hidden Stems (Bản/Trung/Dư khí)
 * 7. Annual Luck (Lưu Niên) calculation accuracy
 * 
 * Reference Case: Nguyễn Thị Ngọc (23/10/1992, 17:30, Female)
 * Source of truth: hocvienlyso.org
 */
class AdvancedFeaturesTest {

    private val solarTermsJson = getFileContent()

    companion object {
        fun getFileContent(): String {
            val paths = listOf(
                "app/src/main/assets/solar-term.json",
                "src/main/assets/solar-term.json",
                "../app/src/main/assets/solar-term.json",
                "../../app/src/main/assets/solar-term.json"
            )
            for (p in paths) {
                val file = File(p)
                if (file.exists()) return file.readText()
            }
            throw java.io.FileNotFoundException("Could not find solar-term.json. CWD is " + File(".").absolutePath)
        }
    }

    private val baZiLogic = BaZiLogic(solarTermsJson)

    private val ngocInput = UserInput(
        name = "Nguyễn Thị Ngọc",
        solarDay = 23,
        solarMonth = 10,
        solarYear = 1992,
        hour = 17, // 17:30 → giờ Dậu
        gender = Gender.NU,
        longitude = 105.8,
        dayBoundaryAt23 = true
    )

    @Test
    fun testXunKong_NhamThan() {
        val (v1, v2) = BaZiConstants.getXunKong("Nhâm", "Thân")
        assertEquals("Tuất", v1)
        assertEquals("Hợi", v2)
    }

    @Test
    fun testFetalOrigin_Ngoc() {
        // Ngọc sinh tháng Canh Tuất (idx: Canh=6, Tuất=10)
        // Thai Nguyên: (6+1=7 -> Tân, 10+3=13 -> 1 -> Sửu)
        val data = baZiLogic.calculateBaZi(ngocInput)
        assertEquals("Tân", data.fetalOrigin?.stem)
        assertEquals("Sửu", data.fetalOrigin?.branch)
    }

    @Test
    fun testLifePalace_Ngoc() {
        // Đối chiếu ảnh: Mệnh Cung = Canh Tuất
        val data = baZiLogic.calculateBaZi(ngocInput)
        assertEquals("Canh", data.lifePalace?.stem)
        assertEquals("Tuất", data.lifePalace?.branch)
    }

    @Test
    fun testShenSha_Ngoc_DaoHoa_ThienY() {
        val data = baZiLogic.calculateBaZi(ngocInput)
        
        // Ngày Thân → Đào Hoa = Dậu → Giờ Dậu → match
        assertTrue("Giờ Dậu nên có Đào Hoa", data.shenShaList.any { it.name == "Đào Hoa" && it.pillar == "Giờ" })
        
        // Tháng Tuất → Thiên Y = Dậu → Giờ Dậu → match
        assertTrue("Giờ Dậu nên có Thiên Y", data.shenShaList.any { it.name == "Thiên Y" && it.pillar == "Giờ" })
    }

    @Test
    fun testShenSha_Ngoc_HongLoan_ThienHi() {
        val data = baZiLogic.calculateBaZi(ngocInput)
        
        // Năm Thân (1992) -> Hồng Loan = Mùi (Sửu đối diện là đối xung, Thân-8 -> 2)
        // Theo map: Thân -> Mùi (Hồng Loan)
        // Hồng Loan tại Mùi. Thiên Hỉ đối diện Hồng Loan -> Sửu.
        // Kiểm tra xem có Hồng Loan/Thiên Hỉ không (Ngọc không có Mùi/Sửu ở 4 trụ, nhưng test logic map)
        
        assertEquals("Mùi", BaZiConstants.HONG_LOAN["Thân"])
        assertEquals("Sửu", BaZiConstants.LUC_XUNG[BaZiConstants.HONG_LOAN["Thân"]])
    }

    @Test
    fun testHiddenStem_TenGod_Ngoc_Tuat() {
        // Nhật chủ Nhâm, Chi Tuất: Mậu=Thất Sát, Tân=Chính Ấn, Đinh=Chính Tài
        val data = baZiLogic.calculateBaZi(ngocInput)
        val monthHS = data.month.hiddenStems
        
        assertEquals("Thất Sát", monthHS[0].tenGod) // Mậu
        assertEquals("Chính Ấn", monthHS[1].tenGod) // Tân
        assertEquals("Chính Tài", monthHS[2].tenGod) // Đinh
    }

    @Test
    fun testPhucNgam_Ngoc() {
        // Ngọc sinh năm Nhâm Thân, ngày Nhâm Thân -> Phục Ngâm Năm-Ngày
        val data = baZiLogic.calculateBaZi(ngocInput)
        assertTrue("Nên có Phục Ngâm Năm-Ngày", data.interactions.any { 
            it.typeName == "Phục Ngâm" && it.pillars.containsAll(listOf("Năm", "Ngày")) 
        })
    }

    @Test
    fun testAnnualPillar_2026_BinhNgo() {
        val data = baZiLogic.calculateBaZi(ngocInput)
        var found2026 = false
        data.luckPillars.forEach { lp ->
            lp.annualPillars.forEach { ap ->
                if (ap.year == 2026) {
                    assertEquals("Bính", ap.stem)
                    assertEquals("Ngọ", ap.branch)
                    found2026 = true
                }
            }
        }
        assertTrue("Nên tìm thấy Lưu niên 2026", found2026)
    }

    @Test
    fun testPrintSampleJson() {
        val data = baZiLogic.calculateBaZi(ngocInput)
        val json = com.skul9x.battu.ai.PromptBuilder.buildJsonPrompt("Nguyễn Thị Ngọc", Gender.NU, data)
        println("SAMPLE JSON PROMPT:")
        println(json)
    }
}
