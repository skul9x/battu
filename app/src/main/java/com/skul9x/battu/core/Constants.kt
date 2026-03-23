package com.skul9x.battu.core

/**
 * Constants for Bát Tự (Four Pillars) calculation
 * Filtered from original TuViAndroid-BatTu to include only Bát Tự related constants
 * Removed all Tử Vi specific constants (stars, palaces, etc.)
 */
object Constants {
    
    // ============================================
    // THIÊN CAN & ĐỊA CHI (Heavenly Stems & Earthly Branches)
    // ============================================
    
    /**
     * 10 Thiên Can (Heavenly Stems)
     */
    val THIEN_CAN = listOf(
        "Giáp", "Ất", "Bính", "Đinh", "Mậu", 
        "Kỷ", "Canh", "Tân", "Nhâm", "Quý"
    )
    
    /**
     * 12 Địa Chi (Earthly Branches)
     */
    val DIA_CHI = listOf(
        "Tý", "Sửu", "Dần", "Mão", "Thìn", "Tỵ", 
        "Ngọ", "Mùi", "Thân", "Dậu", "Tuất", "Hợi"
    )
    
    // ============================================
    // NGŨ HÀNH (FIVE ELEMENTS)
    // ============================================
    
    /**
     * Ngũ Hành của Thiên Can
     */
    val NGU_HANH_CAN = mapOf(
        "Giáp" to "Mộc", "Ất" to "Mộc", 
        "Bính" to "Hỏa", "Đinh" to "Hỏa",
        "Mậu" to "Thổ", "Kỷ" to "Thổ", 
        "Canh" to "Kim", "Tân" to "Kim",
        "Nhâm" to "Thủy", "Quý" to "Thủy"
    )
    
    /**
     * Ngũ Hành của 12 Địa Chi
     */
    val NGU_HANH_CUNG = mapOf(
        "Tý" to "Thủy", "Sửu" to "Thổ", "Dần" to "Mộc", "Mão" to "Mộc",
        "Thìn" to "Thổ", "Tỵ" to "Hỏa", "Ngọ" to "Hỏa", "Mùi" to "Thổ",
        "Thân" to "Kim", "Dậu" to "Kim", "Tuất" to "Thổ", "Hợi" to "Thủy"
    )
    
    // ============================================
    // ÂM DƯƠNG (YIN YANG)
    // ============================================
    
    /**
     * Âm Dương của Thiên Can
     */
    val CAN_YIN_YANG = mapOf(
        "Giáp" to "Dương", "Ất" to "Âm", 
        "Bính" to "Dương", "Đinh" to "Âm",
        "Mậu" to "Dương", "Kỷ" to "Âm", 
        "Canh" to "Dương", "Tân" to "Âm",
        "Nhâm" to "Dương", "Quý" to "Âm"
    )
    
    /**
     * Âm Dương của Địa Chi
     */
    val CHI_YIN_YANG = mapOf(
        "Tý" to "Dương", "Sửu" to "Âm", "Dần" to "Dương", "Mão" to "Âm",
        "Thìn" to "Dương", "Tỵ" to "Âm", "Ngọ" to "Dương", "Mùi" to "Âm",
        "Thân" to "Dương", "Dậu" to "Âm", "Tuất" to "Dương", "Hợi" to "Âm"
    )
    
    // ============================================
    // TÀNG CAN (HIDDEN STEMS)
    // ============================================
    
    /**
     * Tàng Can - Hidden Stems within each Earthly Branch
     */
    val TANG_CAN = mapOf(
        "Tý" to listOf("Quý"),
        "Sửu" to listOf("Kỷ", "Quý", "Tân"),
        "Dần" to listOf("Giáp", "Bính", "Mậu"),
        "Mão" to listOf("Ất"),
        "Thìn" to listOf("Mậu", "Ất", "Quý"),
        "Tỵ" to listOf("Bính", "Canh", "Mậu"),
        "Ngọ" to listOf("Đinh", "Kỷ"),
        "Mùi" to listOf("Kỷ", "Đinh", "Ất"),
        "Thân" to listOf("Canh", "Nhâm", "Mậu"),
        "Dậu" to listOf("Tân"),
        "Tuất" to listOf("Mậu", "Tân", "Đinh"),
        "Hợi" to listOf("Nhâm", "Giáp")
    )
    
    /**
     * Trọng số Tàng Can - Weight percentage of hidden stems
     * Used for calculating element balance
     */
    val TANG_CAN_WEIGHT = mapOf(
        "Tý" to mapOf("Quý" to 100),
        "Sửu" to mapOf("Kỷ" to 60, "Quý" to 30, "Tân" to 10),
        "Dần" to mapOf("Giáp" to 60, "Bính" to 30, "Mậu" to 10),
        "Mão" to mapOf("Ất" to 100),
        "Thìn" to mapOf("Mậu" to 60, "Ất" to 30, "Quý" to 10),
        "Tỵ" to mapOf("Bính" to 60, "Canh" to 30, "Mậu" to 10),
        "Ngọ" to mapOf("Đinh" to 70, "Kỷ" to 30),
        "Mùi" to mapOf("Kỷ" to 60, "Đinh" to 30, "Ất" to 10),
        "Thân" to mapOf("Canh" to 60, "Nhâm" to 30, "Mậu" to 10),
        "Dậu" to mapOf("Tân" to 100),
        "Tuất" to mapOf("Mậu" to 60, "Tân" to 30, "Đinh" to 10),
        "Hợi" to mapOf("Nhâm" to 70, "Giáp" to 30)
    )
    
    // ============================================
    // NẠP ÂM NGŨ HÀNH (60 JIAZI CYCLE)
    // ============================================
    
    /**
     * Nạp Âm Ngũ Hành - 60 Jiazi cycle element names
     * Maps Can-Chi combination to element type name
     */
    val NAP_AM_MAP = mapOf(
        "Giáp Tý" to "Hải Trung Kim", "Ất Sửu" to "Hải Trung Kim",
        "Bính Dần" to "Lư Trung Hỏa", "Đinh Mão" to "Lư Trung Hỏa",
        "Mậu Thìn" to "Đại Lâm Mộc", "Kỷ Tỵ" to "Đại Lâm Mộc",
        "Canh Ngọ" to "Lộ Bàng Thổ", "Tân Mùi" to "Lộ Bàng Thổ",
        "Nhâm Thân" to "Kiếm Phong Kim", "Quý Dậu" to "Kiếm Phong Kim",
        "Giáp Tuất" to "Sơn Đầu Hỏa", "Ất Hợi" to "Sơn Đầu Hỏa",
        "Bính Tý" to "Giản Hạ Thủy", "Đinh Sửu" to "Giản Hạ Thủy",
        "Mậu Dần" to "Thành Đầu Thổ", "Kỷ Mão" to "Thành Đầu Thổ",
        "Canh Thìn" to "Bạch Lạp Kim", "Tân Tỵ" to "Bạch Lạp Kim",
        "Nhâm Ngọ" to "Dương Liễu Mộc", "Quý Mùi" to "Dương Liễu Mộc",
        "Giáp Thân" to "Tuyền Trung Thủy", "Ất Dậu" to "Tuyền Trung Thủy",
        "Bính Tuất" to "Ốc Thượng Thổ", "Đinh Hợi" to "Ốc Thượng Thổ",
        "Mậu Tý" to "Tích Lịch Hỏa", "Kỷ Sửu" to "Tích Lịch Hỏa",
        "Canh Dần" to "Tùng Bách Mộc", "Tân Mão" to "Tùng Bách Mộc",
        "Nhâm Thìn" to "Trường Lưu Thủy", "Quý Tỵ" to "Trường Lưu Thủy",
        "Giáp Ngọ" to "Sa Trung Kim", "Ất Mùi" to "Sa Trung Kim",
        "Bính Thân" to "Sơn Hạ Hỏa", "Đinh Dậu" to "Sơn Hạ Hỏa",
        "Mậu Tuất" to "Bình Địa Mộc", "Kỷ Hợi" to "Bình Địa Mộc",
        "Canh Tý" to "Bích Thượng Thổ", "Tân Sửu" to "Bích Thượng Thổ",
        "Nhâm Dần" to "Kim Bạc Kim", "Quý Mão" to "Kim Bạc Kim",
        "Giáp Thìn" to "Phú Đăng Hỏa", "Ất Tỵ" to "Phú Đăng Hỏa",
        "Bính Ngọ" to "Thiên Hà Thủy", "Đinh Mùi" to "Thiên Hà Thủy",
        "Mậu Thân" to "Đại Trạch Thổ", "Kỷ Dậu" to "Đại Trạch Thổ",
        "Canh Tuất" to "Thoa Xuyến Kim", "Tân Hợi" to "Thoa Xuyến Kim",
        "Nhâm Tý" to "Tang Đố Mộc", "Quý Sửu" to "Tang Đố Mộc",
        "Giáp Dần" to "Đại Khê Thủy", "Ất Mão" to "Đại Khê Thủy",
        "Bính Thìn" to "Sa Trung Thổ", "Đinh Tỵ" to "Sa Trung Thổ",
        "Mậu Ngọ" to "Thiên Thượng Hỏa", "Kỷ Mùi" to "Thiên Thượng Hỏa",
        "Canh Thân" to "Thạch Lựu Mộc", "Tân Dậu" to "Thạch Lựu Mộc",
        "Nhâm Tuất" to "Đại Hải Thủy", "Quý Hợi" to "Đại Hải Thủy"
    )
    
    // ============================================
    // HELPER FUNCTIONS
    // ============================================
    
    /**
     * Convert Nạp Âm name to basic Five Element
     * Example: "Hải Trung Kim" -> "Kim"
     */
    fun napAmToNguHanh(napAm: String): String {
        return when {
            napAm.contains("Kim") -> "Kim"
            napAm.contains("Mộc") -> "Mộc"
            napAm.contains("Thủy") -> "Thủy"
            napAm.contains("Hỏa") -> "Hỏa"
            napAm.contains("Thổ") -> "Thổ"
            else -> "Không xác định"
        }
    }
    
    /**
     * Calculate relationship between two Five Elements
     * Returns: "đồng hành", "sinh", "được sinh", "khắc", "bị khắc"
     */
    fun sinhKhac(hanh1: String, hanh2: String): String {
        val sinh = mapOf(
            "Kim" to "Thủy", "Thủy" to "Mộc", "Mộc" to "Hỏa",
            "Hỏa" to "Thổ", "Thổ" to "Kim"
        )
        val khac = mapOf(
            "Kim" to "Mộc", "Mộc" to "Thổ", "Thổ" to "Thủy",
            "Thủy" to "Hỏa", "Hỏa" to "Kim"
        )
        return when {
            hanh1 == hanh2 -> "đồng hành"
            sinh[hanh1] == hanh2 -> "sinh"
            sinh[hanh2] == hanh1 -> "được sinh"
            khac[hanh1] == hanh2 -> "khắc"
            khac[hanh2] == hanh1 -> "bị khắc"
            else -> "không xác định"
        }
    }
    
    /**
     * Calculate Ten God (Thập Thần) relationship
     * @param dayMaster Day Master stem (Nhật Can)
     * @param targetStem Target stem to analyze
     * @return Ten God name in Vietnamese
     */
    fun calculateTenGod(dayMaster: String, targetStem: String): String {
        val dmHanh = NGU_HANH_CAN[dayMaster] ?: return ""
        val targetHanh = NGU_HANH_CAN[targetStem] ?: return ""
        val dmYinYang = CAN_YIN_YANG[dayMaster] ?: ""
        val targetYinYang = CAN_YIN_YANG[targetStem] ?: ""
        
        val relate = sinhKhac(dmHanh, targetHanh)
        val sameSign = dmYinYang == targetYinYang

        return when (relate) {
            "đồng hành" -> if (sameSign) "Tỷ Kiên" else "Kiếp Tài"
            "sinh" -> if (sameSign) "Thực Thần" else "Thương Quan"
            "được sinh" -> if (sameSign) "Thiên Ấn" else "Chính Ấn"
            "khắc" -> if (sameSign) "Thiên Tài" else "Chính Tài"
            "bị khắc" -> if (sameSign) "Thất Sát" else "Chính Quan"
            else -> ""
        }
    }
}
