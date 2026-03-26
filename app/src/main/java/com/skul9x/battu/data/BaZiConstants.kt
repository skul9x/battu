package com.skul9x.battu.data

/**
 * BaZi Constants - Lookup tables for Nạp Âm (60 Giáp Tý) and Tàng Can (Hidden Stems)
 * Based on traditional Bát Tự Tử Bình methodology
 */
object BaZiConstants {

    // ============================================================
    // 0. CẤU TRÚC CƠ BẢN (STREMS & BRANCHES)
    // ============================================================
    
    val THIEN_CAN = listOf("Giáp", "Ất", "Bính", "Đinh", "Mậu", "Kỷ", "Canh", "Tân", "Nhâm", "Quý")
    val DIA_CHI = listOf("Tý", "Sửu", "Dần", "Mão", "Thìn", "Tỵ", "Ngọ", "Mùi", "Thân", "Dậu", "Tuất", "Hợi")

    // ============================================================
    // 1. NẠP ÂM (60 GIÁP TÝ) - Lục Thập Hoa Giáp
    // ============================================================
    
    /**
     * Lookup table for Nạp Âm based on Heavenly Stem + Earthly Branch pair
     * Each pair maps to a specific Nạp Âm with element and description
     */
    val NAP_AM_TABLE: Map<Pair<String, String>, NapAm> = mapOf(
        // 1-2: Hải Trung Kim (Gold in the Sea)
        ("Giáp" to "Tý") to NapAm("Hải Trung Kim", Element.KIM, "Vàng trong biển"),
        ("Ất" to "Sửu") to NapAm("Hải Trung Kim", Element.KIM, "Vàng trong biển"),
        
        // 3-4: Lô Trung Hỏa (Fire in the Furnace)
        ("Bính" to "Dần") to NapAm("Lô Trung Hỏa", Element.HOA, "Lửa trong lò"),
        ("Đinh" to "Mão") to NapAm("Lô Trung Hỏa", Element.HOA, "Lửa trong lò"),
        
        // 5-6: Đại Lâm Mộc (Great Forest Wood)
        ("Mậu" to "Thìn") to NapAm("Đại Lâm Mộc", Element.MOC, "Gỗ rừng lớn"),
        ("Kỷ" to "Tỵ") to NapAm("Đại Lâm Mộc", Element.MOC, "Gỗ rừng lớn"),
        
        // 7-8: Lộ Bàng Thổ (Roadside Earth)
        ("Canh" to "Ngọ") to NapAm("Lộ Bàng Thổ", Element.THO, "Đất ven đường"),
        ("Tân" to "Mùi") to NapAm("Lộ Bàng Thổ", Element.THO, "Đất ven đường"),
        
        // 9-10: Kiếm Phong Kim (Sword Edge Gold)
        ("Nhâm" to "Thân") to NapAm("Kiếm Phong Kim", Element.KIM, "Vàng đầu kiếm"),
        ("Quý" to "Dậu") to NapAm("Kiếm Phong Kim", Element.KIM, "Vàng đầu kiếm"),
        
        // 11-12: Sơn Đầu Hỏa (Mountain Top Fire)
        ("Giáp" to "Tuất") to NapAm("Sơn Đầu Hỏa", Element.HOA, "Lửa trên núi"),
        ("Ất" to "Hợi") to NapAm("Sơn Đầu Hỏa", Element.HOA, "Lửa trên núi"),
        
        // 13-14: Giản Hạ Thủy (Stream Water)
        ("Bính" to "Tý") to NapAm("Giản Hạ Thủy", Element.THUY, "Nước suối"),
        ("Đinh" to "Sửu") to NapAm("Giản Hạ Thủy", Element.THUY, "Nước suối"),
        
        // 15-16: Thành Đầu Thổ (City Wall Earth)
        ("Mậu" to "Dần") to NapAm("Thành Đầu Thổ", Element.THO, "Đất thành trì"),
        ("Kỷ" to "Mão") to NapAm("Thành Đầu Thổ", Element.THO, "Đất thành trì"),
        
        // 17-18: Bạch Lạp Kim (White Wax Gold)
        ("Canh" to "Thìn") to NapAm("Bạch Lạp Kim", Element.KIM, "Vàng sáp trắng"),
        ("Tân" to "Tỵ") to NapAm("Bạch Lạp Kim", Element.KIM, "Vàng sáp trắng"),
        
        // 19-20: Dương Liễu Mộc (Willow Wood)
        ("Nhâm" to "Ngọ") to NapAm("Dương Liễu Mộc", Element.MOC, "Gỗ liễu"),
        ("Quý" to "Mùi") to NapAm("Dương Liễu Mộc", Element.MOC, "Gỗ liễu"),
        
        // 21-22: Tuyền Trung Thủy (Spring Water)
        ("Giáp" to "Thân") to NapAm("Tuyền Trung Thủy", Element.THUY, "Nước giếng"),
        ("Ất" to "Dậu") to NapAm("Tuyền Trung Thủy", Element.THUY, "Nước giếng"),
        
        // 23-24: Ốc Thượng Thổ (Roof Top Earth)
        ("Bính" to "Tuất") to NapAm("Ốc Thượng Thổ", Element.THO, "Đất trên mái"),
        ("Đinh" to "Hợi") to NapAm("Ốc Thượng Thổ", Element.THO, "Đất trên mái"),
        
        // 25-26: Tích Lịch Hỏa (Thunderbolt Fire)
        ("Mậu" to "Tý") to NapAm("Tích Lịch Hỏa", Element.HOA, "Lửa sét"),
        ("Kỷ" to "Sửu") to NapAm("Tích Lịch Hỏa", Element.HOA, "Lửa sét"),
        
        // 27-28: Tùng Bách Mộc (Pine Wood)
        ("Canh" to "Dần") to NapAm("Tùng Bách Mộc", Element.MOC, "Gỗ tùng bách"),
        ("Tân" to "Mão") to NapAm("Tùng Bách Mộc", Element.MOC, "Gỗ tùng bách"),
        
        // 29-30: Trường Lưu Thủy (Flowing Stream Water)
        ("Nhâm" to "Thìn") to NapAm("Trường Lưu Thủy", Element.THUY, "Nước chảy dài"),
        ("Quý" to "Tỵ") to NapAm("Trường Lưu Thủy", Element.THUY, "Nước chảy dài"),
        
        // 31-32: Sa Trung Kim (Sand Gold)
        ("Giáp" to "Ngọ") to NapAm("Sa Trung Kim", Element.KIM, "Vàng trong cát"),
        ("Ất" to "Mùi") to NapAm("Sa Trung Kim", Element.KIM, "Vàng trong cát"),
        
        // 33-34: Sơn Hạ Hỏa (Mountain Foot Fire)
        ("Bính" to "Thân") to NapAm("Sơn Hạ Hỏa", Element.HOA, "Lửa chân núi"),
        ("Đinh" to "Dậu") to NapAm("Sơn Hạ Hỏa", Element.HOA, "Lửa chân núi"),
        
        // 35-36: Bình Địa Mộc (Flat Land Wood)
        ("Mậu" to "Tuất") to NapAm("Bình Địa Mộc", Element.MOC, "Gỗ đất bằng"),
        ("Kỷ" to "Hợi") to NapAm("Bình Địa Mộc", Element.MOC, "Gỗ đất bằng"),
        
        // 37-38: Bích Thượng Thổ (Wall Earth)
        ("Canh" to "Tý") to NapAm("Bích Thượng Thổ", Element.THO, "Đất tường"),
        ("Tân" to "Sửu") to NapAm("Bích Thượng Thổ", Element.THO, "Đất tường"),
        
        // 39-40: Kim Bạch Kim (Gold Foil Gold)
        ("Nhâm" to "Dần") to NapAm("Kim Bạch Kim", Element.KIM, "Vàng lá mỏng"),
        ("Quý" to "Mão") to NapAm("Kim Bạch Kim", Element.KIM, "Vàng lá mỏng"),
        
        // 41-42: Phúc Đăng Hỏa (Lamp Fire)
        ("Giáp" to "Thìn") to NapAm("Phúc Đăng Hỏa", Element.HOA, "Lửa đèn"),
        ("Ất" to "Tỵ") to NapAm("Phúc Đăng Hỏa", Element.HOA, "Lửa đèn"),
        
        // 43-44: Thiên Hà Thủy (Milky Way Water)
        ("Bính" to "Ngọ") to NapAm("Thiên Hà Thủy", Element.THUY, "Nước ngân hà"),
        ("Đinh" to "Mùi") to NapAm("Thiên Hà Thủy", Element.THUY, "Nước ngân hà"),
        
        // 45-46: Đại Dịch Thổ (Great Post Earth)
        ("Mậu" to "Thân") to NapAm("Đại Dịch Thổ", Element.THO, "Đất trạm lớn"),
        ("Kỷ" to "Dậu") to NapAm("Đại Dịch Thổ", Element.THO, "Đất trạm lớn"),
        
        // 47-48: Thoa Xuyến Kim (Hairpin Gold)
        ("Canh" to "Tuất") to NapAm("Thoa Xuyến Kim", Element.KIM, "Vàng trâm"),
        ("Tân" to "Hợi") to NapAm("Thoa Xuyến Kim", Element.KIM, "Vàng trâm"),
        
        // 49-50: Tang Đố Mộc (Mulberry Wood)
        ("Nhâm" to "Tý") to NapAm("Tang Đố Mộc", Element.MOC, "Gỗ dâu tằm"),
        ("Quý" to "Sửu") to NapAm("Tang Đố Mộc", Element.MOC, "Gỗ dâu tằm"),
        
        // 51-52: Đại Khê Thủy (Great Stream Water)
        ("Giáp" to "Dần") to NapAm("Đại Khê Thủy", Element.THUY, "Nước suối lớn"),
        ("Ất" to "Mão") to NapAm("Đại Khê Thủy", Element.THUY, "Nước suối lớn"),
        
        // 53-54: Sa Trung Thổ (Sand Earth)
        ("Bính" to "Thìn") to NapAm("Sa Trung Thổ", Element.THO, "Đất cát"),
        ("Đinh" to "Tỵ") to NapAm("Sa Trung Thổ", Element.THO, "Đất cát"),
        
        // 55-56: Thiên Thượng Hỏa (Sky Fire)
        ("Mậu" to "Ngọ") to NapAm("Thiên Thượng Hỏa", Element.HOA, "Lửa trên trời"),
        ("Kỷ" to "Mùi") to NapAm("Thiên Thượng Hỏa", Element.HOA, "Lửa trên trời"),
        
        // 57-58: Thạch Lựu Mộc (Pomegranate Wood)
        ("Canh" to "Thân") to NapAm("Thạch Lựu Mộc", Element.MOC, "Gỗ thạch lựu"),
        ("Tân" to "Dậu") to NapAm("Thạch Lựu Mộc", Element.MOC, "Gỗ thạch lựu"),
        
        // 59-60: Đại Hải Thủy (Great Ocean Water)
        ("Nhâm" to "Tuất") to NapAm("Đại Hải Thủy", Element.THUY, "Nước biển lớn"),
        ("Quý" to "Hợi") to NapAm("Đại Hải Thủy", Element.THUY, "Nước biển lớn")
    )

    // ============================================================
    // 2. TÀNG CAN (HIDDEN STEMS) - 12 Địa Chi
    // ============================================================
    
    /**
     * Hidden Stems table for 12 Earthly Branches
     * Each branch contains 1-3 hidden stems with percentage weights
     * Bản Khí (Main): 60-100%, Trung Khí (Middle): 30%, Dư Khí (Residual): 10%
     */
    val HIDDEN_STEMS_TABLE: Map<String, List<HiddenStem>> = mapOf(
        "Tý" to listOf(
            HiddenStem("Quý", 100, HiddenStemType.BAN_KHI)
        ),
        "Sửu" to listOf(
            HiddenStem("Kỷ", 60, HiddenStemType.BAN_KHI),
            HiddenStem("Quý", 30, HiddenStemType.TRUNG_KHI),
            HiddenStem("Tân", 10, HiddenStemType.DU_KHI)
        ),
        "Dần" to listOf(
            HiddenStem("Giáp", 60, HiddenStemType.BAN_KHI),
            HiddenStem("Bính", 30, HiddenStemType.TRUNG_KHI),
            HiddenStem("Mậu", 10, HiddenStemType.DU_KHI)
        ),
        "Mão" to listOf(
            HiddenStem("Ất", 100, HiddenStemType.BAN_KHI)
        ),
        "Thìn" to listOf(
            HiddenStem("Mậu", 60, HiddenStemType.BAN_KHI),
            HiddenStem("Ất", 30, HiddenStemType.TRUNG_KHI),
            HiddenStem("Quý", 10, HiddenStemType.DU_KHI)
        ),
        "Tỵ" to listOf(
            HiddenStem("Bính", 60, HiddenStemType.BAN_KHI),
            HiddenStem("Canh", 30, HiddenStemType.TRUNG_KHI),
            HiddenStem("Mậu", 10, HiddenStemType.DU_KHI)
        ),
        "Ngọ" to listOf(
            HiddenStem("Đinh", 70, HiddenStemType.BAN_KHI),
            HiddenStem("Kỷ", 30, HiddenStemType.TRUNG_KHI)
        ),
        "Mùi" to listOf(
            HiddenStem("Kỷ", 60, HiddenStemType.BAN_KHI),
            HiddenStem("Đinh", 30, HiddenStemType.TRUNG_KHI),
            HiddenStem("Ất", 10, HiddenStemType.DU_KHI)
        ),
        "Thân" to listOf(
            HiddenStem("Canh", 60, HiddenStemType.BAN_KHI),
            HiddenStem("Nhâm", 30, HiddenStemType.TRUNG_KHI),
            HiddenStem("Mậu", 10, HiddenStemType.DU_KHI)
        ),
        "Dậu" to listOf(
            HiddenStem("Tân", 100, HiddenStemType.BAN_KHI)
        ),
        "Tuất" to listOf(
            HiddenStem("Mậu", 60, HiddenStemType.BAN_KHI),
            HiddenStem("Tân", 30, HiddenStemType.TRUNG_KHI),
            HiddenStem("Đinh", 10, HiddenStemType.DU_KHI)
        ),
        "Hợi" to listOf(
            HiddenStem("Nhâm", 70, HiddenStemType.BAN_KHI),
            HiddenStem("Giáp", 30, HiddenStemType.TRUNG_KHI)
        )
    )

    // ============================================================
    // 3. TRƯỜNG SINH (LIFE STAGES) - 10 Thiên Can x 12 Địa Chi
    // ============================================================

    private val LIFE_STAGES = listOf(
        "Trường Sinh", "Mộc Dục", "Quan Đới", "Lâm Quan", 
        "Đế Vượng", "Suy", "Bệnh", "Tử", "Mộ", "Tuyệt", "Thai", "Dưỡng"
    )

    private val CAN_TRUONG_SINH_START = mapOf(
        "Giáp" to "Hợi", "Bính" to "Dần", "Mậu" to "Dần", "Canh" to "Tỵ", "Nhâm" to "Thân", // Dương thuận
        "Ất" to "Ngọ", "Đinh" to "Dậu", "Kỷ" to "Dậu", "Tân" to "Tý", "Quý" to "Mão"       // Âm nghịch
    )

    /**
     * Get Life Stage of a Stem at a given Branch
     */
    fun getLifeStage(stem: String, branch: String): String {
        val startBranch = CAN_TRUONG_SINH_START[stem] ?: return ""
        val startIdx = DIA_CHI.indexOf(startBranch)
        val targetIdx = DIA_CHI.indexOf(branch)
        
        val isDuong = stem in listOf("Giáp", "Bính", "Mậu", "Canh", "Nhâm")
        val steps = if (isDuong) {
            (targetIdx - startIdx + 12) % 12
        } else {
            (startIdx - targetIdx + 12) % 12
        }
        
        return LIFE_STAGES[steps]
    }

    // ============================================================
    // 4. MÙA SINH (SEASON STRENGTH)
    // ============================================================

    enum class Season(val label: String) {
        XUAN("Xuân"), HA("Hạ"), THU("Thu"), DONG("Đông"), TU_QUY("Tứ Quý")
    }

    fun getSeason(branch: String): Season {
        return when (branch) {
            "Dần", "Mão" -> Season.XUAN
            "Tỵ", "Ngọ" -> Season.HA
            "Thân", "Dậu" -> Season.THU
            "Hợi", "Tý" -> Season.DONG
            else -> Season.TU_QUY // Thìn, Tuất, Sửu, Mùi
        }
    }

    /**
     * Get Day Master Strength based on 12 Life Stages relative to birth month (Lệnh tháng)
     * Replaces the old seasonal element balance rules with more accurate Bazi methodology
     */
    fun getDayMasterStrength(monthBranch: String, stem: String): String {
        return getLifeStage(stem, monthBranch)
    }

    // ============================================================
    // 5. TUẦN KHÔNG (XUN KONG / VOID BRANCHES)
    // ============================================================

    /**
     * Calculate Void Branches (Không Vong) for a given Stem-Branch pair.
     * Each 10-day cycle (Tuần) in the 60-year cycle has 2 missing branches.
     * @return Pair of branch names that are Void
     */
    fun getXunKong(stem: String, branch: String): Pair<String, String> {
        val canIdx = THIEN_CAN.indexOf(stem)
        val chiIdx = DIA_CHI.indexOf(branch)
        if (canIdx == -1 || chiIdx == -1) return "" to ""

        // Find the start branch of the 10-day cycle (Tuần) by counting back canIdx steps
        val startChiIdx = (chiIdx - canIdx + 12) % 12
        
        // The 2 void branches are the 11th and 12th positions after the start branch
        val void1Idx = (startChiIdx + 10) % 12
        val void2Idx = (startChiIdx + 11) % 12
        
        return DIA_CHI[void1Idx] to DIA_CHI[void2Idx]
    }

    // ============================================================
    // 6. HELPER FUNCTIONS
    // ============================================================
    
    /**
     * Get Nạp Âm for a given Stem-Branch pair
     * @return NapAm object or null if not found
     */
    fun getNapAm(stem: String, branch: String): NapAm? {
        return NAP_AM_TABLE[stem to branch]
    }
    
    /**
     * Get all hidden stems for a given Earthly Branch
     * @return List of HiddenStem with percentages, or empty list if not found
     */
    fun getHiddenStems(branch: String): List<HiddenStem> {
        return HIDDEN_STEMS_TABLE[branch] ?: emptyList()
    }
    
    /**
     * Get the main hidden stem (Bản Khí) for a given Earthly Branch
     * This is used for calculating Ten Gods of branches
     * @return Main stem string, or the branch itself if not found
     */
    fun getMainHiddenStem(branch: String): String {
        return HIDDEN_STEMS_TABLE[branch]
            ?.firstOrNull { it.type == HiddenStemType.BAN_KHI }
            ?.stem ?: branch
    }
    
    /**
     * Calculate Ten God relationship between Day Master and target stem
     * Based on Five Elements relationship (Sinh/Khắc) and Yin/Yang polarity
     * 
     * @param dayStem Day Master (Nhật Chủ) - the reference point
     * @param targetStem Target stem to compare
     * @return Ten God name in Vietnamese
     */
    fun calculateTenGod(dayStem: String, targetStem: String): String {
        // Same stem = Tỷ Kiên (same polarity) or Kiếp Tài (different polarity)
        if (dayStem == targetStem) {
            return "Tỷ Kiên"
        }
        
        val dayElement = getElementOfStem(dayStem)
        val dayYinYang = getYinYangOfStem(dayStem)
        val targetElement = getElementOfStem(targetStem)
        val targetYinYang = getYinYangOfStem(targetStem)
        
        val relation = getElementRelation(dayElement, targetElement)
        val sameYinYang = (dayYinYang == targetYinYang)
        
        return when (relation) {
            ElementRelation.SAME -> if (sameYinYang) "Tỷ Kiên" else "Kiếp Tài"
            ElementRelation.I_PRODUCE -> if (sameYinYang) "Thực Thần" else "Thương Quan"
            ElementRelation.PRODUCE_ME -> if (sameYinYang) "Thiên Ấn" else "Chính Ấn"
            ElementRelation.I_CONTROL -> if (sameYinYang) "Thiên Tài" else "Chính Tài"
            ElementRelation.CONTROL_ME -> if (sameYinYang) "Thất Sát" else "Chính Quan"
        }
    }
    
    // ============================================================
    // 4. INTERNAL HELPER FUNCTIONS
    // ============================================================
    
    fun getElementOfStem(stem: String): Element {
        return when (stem) {
            "Giáp", "Ất" -> Element.MOC
            "Bính", "Đinh" -> Element.HOA
            "Mậu", "Kỷ" -> Element.THO
            "Canh", "Tân" -> Element.KIM
            "Nhâm", "Quý" -> Element.THUY
            else -> Element.NONE
        }
    }
    
    fun getElementOfBranch(branch: String): Element {
        return when (branch) {
            "Dần", "Mão" -> Element.MOC
            "Tỵ", "Ngọ" -> Element.HOA
            "Thân", "Dậu" -> Element.KIM
            "Hợi", "Tý" -> Element.THUY
            "Sửu", "Thìn", "Mùi", "Tuất" -> Element.THO
            else -> Element.NONE
        }
    }
    
    fun getYinYangOfStem(stem: String): String {
        return when (stem) {
            "Giáp", "Bính", "Mậu", "Canh", "Nhâm" -> "Dương"
            "Ất", "Đinh", "Kỷ", "Tân", "Quý" -> "Âm"
            else -> ""
        }
    }

    fun getYinYangOfBranch(branch: String): String {
        return when (branch) {
            "Tý", "Dần", "Thìn", "Ngọ", "Thân", "Tuất" -> "Dương"
            "Sửu", "Mão", "Tỵ", "Mùi", "Dậu", "Hợi" -> "Âm"
            else -> ""
        }
    }
    
    private fun getElementOfStemInternal(stem: String): Element = getElementOfStem(stem)
    
    private fun getYinYangOfStemInternal(stem: String): YinYang {
        return if (getYinYangOfStem(stem) == "Dương") YinYang.DUONG else YinYang.AM
    }
    
    private fun getElementRelation(from: Element, to: Element): ElementRelation {
        if (from == to) return ElementRelation.SAME
        
        // I produce (Ta sinh)
        val iProduceMap = mapOf(
            Element.MOC to Element.HOA,
            Element.HOA to Element.THO,
            Element.THO to Element.KIM,
            Element.KIM to Element.THUY,
            Element.THUY to Element.MOC
        )
        if (iProduceMap[from] == to) return ElementRelation.I_PRODUCE
        
        // Produce me (Sinh ta)
        if (iProduceMap[to] == from) return ElementRelation.PRODUCE_ME
        
        // I control (Ta khắc)
        val iControlMap = mapOf(
            Element.MOC to Element.THO,
            Element.THO to Element.THUY,
            Element.THUY to Element.HOA,
            Element.HOA to Element.KIM,
            Element.KIM to Element.MOC
        )
        if (iControlMap[from] == to) return ElementRelation.I_CONTROL
        
        // Control me (Khắc ta)
        return ElementRelation.CONTROL_ME
    }

    /**
     * Helper to get element name in Vietnamese
     */
    fun getElementNameVi(element: Element): String {
        return when (element) {
            Element.KIM -> "Kim"
            Element.MOC -> "Mộc"
            Element.THUY -> "Thủy"
            Element.HOA -> "Hỏa"
            Element.THO -> "Thổ"
            Element.NONE -> ""
        }
    }
    
    // ============================================================
    // 5. ENUMS
    // ============================================================
    
    private enum class YinYang {
        AM, DUONG
    }
    
    private enum class ElementRelation {
        SAME,        // Ngang vai
        I_PRODUCE,   // Ta sinh
        PRODUCE_ME,  // Sinh ta
        I_CONTROL,   // Ta khắc
        CONTROL_ME   // Khắc ta
    }

    // ============================================================
    // 6. THẦN SÁT (SHEN SHA) - Bảng Tra Cứu
    // ============================================================
    
    // Thiên Ất Quý Nhân (Dựa vào Can Ngày/Năm)
    val THIEN_AT_QUY_NHAN = mapOf(
        "Giáp" to listOf("Sửu", "Mùi"), "Mậu" to listOf("Sửu", "Mùi"),
        "Ất" to listOf("Tý", "Thân"), "Kỷ" to listOf("Tý", "Thân"),
        "Bính" to listOf("Hợi", "Dậu"), "Đinh" to listOf("Hợi", "Dậu"),
        "Canh" to listOf("Dần", "Ngọ"), "Tân" to listOf("Dần", "Ngọ"),
        "Nhâm" to listOf("Mão", "Tỵ"), "Quý" to listOf("Mão", "Tỵ")
    )

    // Lộc Tồn (Dựa vào Can Ngày)
    val LOC_TON = mapOf(
        "Giáp" to "Dần", "Ất" to "Mão", "Bính" to "Tỵ", "Đinh" to "Ngọ", "Mậu" to "Tỵ",
        "Kỷ" to "Ngọ", "Canh" to "Thân", "Tân" to "Dậu", "Nhâm" to "Hợi", "Quý" to "Tý"
    )

    // Kình Dương (Dương Nhẫn) - Trước Lộc Tồn 1 cung
    val KINH_DUONG = mapOf(
        "Giáp" to "Mão", "Ất" to "Dần", "Bính" to "Ngọ", "Đinh" to "Tỵ", "Mậu" to "Ngọ",
        "Kỷ" to "Tỵ", "Canh" to "Dậu", "Tân" to "Thân", "Nhâm" to "Tý", "Quý" to "Hợi"
    )

    // Dịch Mã (Dựa vào Chi Năm/Ngày)
    val DICH_MA = mapOf(
        "Dần" to "Thân", "Ngọ" to "Thân", "Tuất" to "Thân",
        "Thân" to "Dần", "Tý" to "Dần", "Thìn" to "Dần",
        "Tỵ" to "Hợi", "Dậu" to "Hợi", "Sửu" to "Hợi",
        "Hợi" to "Tỵ", "Mão" to "Tỵ", "Mùi" to "Tỵ"
    )

    // Hoa Cái (Dựa vào Chi Năm/Ngày)
    val HOA_CAI = mapOf(
        "Dần" to "Tuất", "Ngọ" to "Tuất", "Tuất" to "Tuất",
        "Thân" to "Thìn", "Tý" to "Thìn", "Thìn" to "Thìn",
        "Hợi" to "Mùi", "Mão" to "Mùi", "Mùi" to "Mùi",
        "Tỵ" to "Sửu", "Dậu" to "Sửu", "Sửu" to "Sửu"
    )

    // Văn Xương (Dựa vào Can Năm/Ngày)
    val VAN_XUONG = mapOf(
        "Giáp" to "Tỵ", "Ất" to "Ngọ", "Bính" to "Thân", "Đinh" to "Dậu", "Mậu" to "Thân",
        "Kỷ" to "Dậu", "Canh" to "Hợi", "Tân" to "Tý", "Nhâm" to "Dần", "Quý" to "Mão"
    )

    // Kiếp Sát (Dựa vào Chi Năm/Ngày)
    val KIEP_SAT = mapOf(
        "Hợi" to "Thân", "Mão" to "Thân", "Mùi" to "Thân",
        "Dần" to "Hợi", "Ngọ" to "Hợi", "Tuất" to "Hợi",
        "Tỵ" to "Dần", "Dậu" to "Dần", "Sửu" to "Dần",
        "Thân" to "Tỵ", "Tý" to "Tỵ", "Thìn" to "Tỵ"
    )

    // Thập Ác Đại Bại (Dựa vào Trụ Ngày)
    val THAP_AC_DAI_BAI = setOf(
        "Giáp" to "Thìn", "Ất" to "Tỵ", "Bính" to "Thân", "Đinh" to "Hợi", "Mậu" to "Tuất",
        "Kỷ" to "Sửu", "Canh" to "Thìn", "Tân" to "Tỵ", "Nhâm" to "Thân", "Quý" to "Hợi"
    )

    // Đào Hoa (Hàm Trì) - Dựa vào Chi Năm/Ngày
    val DAO_HOA = mapOf(
        "Thân" to "Dậu", "Tý" to "Dậu", "Thìn" to "Dậu",
        "Dần" to "Mão", "Ngọ" to "Mão", "Tuất" to "Mão",
        "Tỵ" to "Ngọ", "Dậu" to "Ngọ", "Sửu" to "Ngọ",
        "Hợi" to "Tý", "Mão" to "Tý", "Mùi" to "Tý"
    )

    // Thiên Y - Dựa vào Chi Tháng
    val THIEN_Y = mapOf(
        "Tý" to "Hợi", "Sửu" to "Tý", "Dần" to "Sửu",
        "Mão" to "Dần", "Thìn" to "Mão", "Tỵ" to "Thìn",
        "Ngọ" to "Tỵ", "Mùi" to "Ngọ", "Thân" to "Mùi",
        "Dậu" to "Thân", "Tuất" to "Dậu", "Hợi" to "Tuất"
    )

    // Hồng Loan - Dựa vào Chi Năm
    val HONG_LOAN = mapOf(
        "Tý" to "Mão", "Sửu" to "Dần", "Dần" to "Sửu",
        "Mão" to "Tý", "Thìn" to "Hợi", "Tỵ" to "Tuất",
        "Ngọ" to "Dậu", "Mùi" to "Thân", "Thân" to "Mùi",
        "Dậu" to "Ngọ", "Tuất" to "Tỵ", "Hợi" to "Thìn"
    )

    // ============================================================
    // 7. QUAN HỆ ĐỊA CHI (INTERACTIONS)
    // ============================================================
    
    val LUC_HOP = mapOf(
        "Tý" to "Sửu", "Sửu" to "Tý",
        "Dần" to "Hợi", "Hợi" to "Dần",
        "Mão" to "Tuất", "Tuất" to "Mão",
        "Thìn" to "Dậu", "Dậu" to "Thìn",
        "Tỵ" to "Thân", "Thân" to "Tỵ",
        "Ngọ" to "Mùi", "Mùi" to "Ngọ"
    )

    val LUC_XUNG = mapOf(
        "Tý" to "Ngọ", "Ngọ" to "Tý",
        "Sửu" to "Mùi", "Mùi" to "Sửu",
        "Dần" to "Thân", "Thân" to "Dần",
        "Mão" to "Dậu", "Dậu" to "Mão",
        "Thìn" to "Tuất", "Tuất" to "Thìn",
        "Tỵ" to "Hợi", "Hợi" to "Tỵ"
    )

    val LUC_HAI = mapOf(
        "Tý" to "Mùi", "Mùi" to "Tý",
        "Sửu" to "Ngọ", "Ngọ" to "Sửu",
        "Dần" to "Tỵ", "Tỵ" to "Dần",
        "Mão" to "Thìn", "Thìn" to "Mão",
        "Thân" to "Hợi", "Hợi" to "Thân",
        "Dậu" to "Tuất", "Tuất" to "Dậu"
    )

    val TAM_HOP = mapOf(
        setOf("Thân", "Tý", "Thìn") to "Thủy",
        setOf("Dần", "Ngọ", "Tuất") to "Hỏa",
        setOf("Hợi", "Mão", "Mùi") to "Mộc",
        setOf("Tỵ", "Dậu", "Sửu") to "Kim"
    )

    val TAM_HINH = listOf(
        setOf("Dần", "Tỵ", "Thân"),
        setOf("Sửu", "Mùi", "Tuất")
    )

    val TU_HINH = listOf("Thìn", "Ngọ", "Dậu", "Hợi")
}
