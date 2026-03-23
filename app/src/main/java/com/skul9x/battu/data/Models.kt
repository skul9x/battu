package com.skul9x.battu.data

import kotlinx.serialization.Serializable

/**
 * Gender enum for Bát Tự calculation
 */
enum class Gender {
    NAM, NU
}

/**
 * Five Elements (Ngũ Hành)
 */
@Serializable
enum class Element {
    KIM, MOC, THUY, HOA, THO, NONE
}

/**
 * Nạp Âm data structure for 60 Giáp Tý
 */
@Serializable
data class NapAm(
    val name: String,
    val element: Element,
    val description: String
)

/**
 * Type of Hidden Stem (Tàng Can)
 */
@Serializable
enum class HiddenStemType {
    BAN_KHI,    // Chính khí (Main) - 60-100%
    TRUNG_KHI,  // Trung khí (Middle) - 30%
    DU_KHI      // Dư khí (Residual) - 10-30%
}

/**
 * Detailed Hidden Stem with weight
 */
@Serializable
data class HiddenStem(
    val stem: String,
    val percentage: Int,
    val type: HiddenStemType
)

/**
 * User input data for Bát Tự chart calculation
 * Simplified version - removed Tử Vi specific fields (viewingYear, readingStyle, viewingMode)
 */
@Serializable
data class UserInput(
    val name: String,
    val solarDay: Int,
    val solarMonth: Int,
    val solarYear: Int,
    val hour: Int, // 0-23
    val gender: Gender,
    val isLunar: Boolean = false,
    val longitude: Double? = 105.8, // Default: Hanoi
    val dayBoundaryAt23: Boolean = true // Default: day changes at 23:00
)

/**
 * Represents one pillar (Trụ) in the Four Pillars (Tứ Trụ)
 * Contains Heavenly Stem (Thiên Can), Earthly Branch (Địa Chi), and Hidden Stems (Tàng Can)
 */
@Serializable
data class Pillar(
    val stem: String,          // Thiên Can (Heavenly Stem)
    val stemYinYang: String,   // Âm/Dương of stem
    val stemElement: String,   // Ngũ Hành of stem (Five Elements)
    val branch: String,        // Địa Chi (Earthly Branch)
    val branchYinYang: String, // Âm/Dương of branch
    val branchElement: String, // Ngũ Hành của branch
    val napAm: NapAm? = null,  // Nạp Âm of this pillar
    val hiddenStems: List<HiddenStem> = emptyList(), // Tàng Can (Hidden Stems) with weights
    val tenGodOfStem: String? = null,   // Thập Thần của Thiên Can (compared to Day Master)
    val tenGodOfBranch: String? = null, // Thập Thần của Địa Chi (based on Main Hidden Stem)
    val lifeStage: String? = null       // Trạng thái Trường sinh so với Nhật chủ
)

/**
 * Ten Gods (Thập Thần) relationships in Bát Tự
 */
@Serializable
data class TenGods(
    val dayMaster: String = "",      // Day Master element
    val yearStemGod: String = "",    // Ten God of Year Stem
    val yearBranchGod: String = "",  // Ten God of Year Branch
    val monthStemGod: String = "",   // Ten God of Month Stem
    val monthBranchGod: String = "", // Ten God of Month Branch
    val dayStemGod: String = "Nhật Chủ", // Ten God of Day Stem (Always Day Master)
    val dayBranchGod: String = "",   // Ten God of Day Branch
    val hourStemGod: String = "",    // Ten God of Hour Stem
    val hourBranchGod: String = ""   // Ten God of Hour Branch
)

/**
 * Types of relationships (Interactions)
 */
@Serializable
enum class InteractionType {
    LUC_HOP, TAM_HOP, BAN_TAM_HOP, TAM_HOI, LUC_XUNG, LUC_HAI, TAM_HINH, TU_HINH, CAN_HOP, CAN_XUNG
}

/**
 * Interactions between stems/branches
 */
@Serializable
data class Interaction(
    val type: InteractionType,
    val typeName: String,     // Ví dụ: "Lục Xung"
    val pillars: List<String>,// Ví dụ: ["Năm", "Ngày"]
    val description: String   // Mô tả chi tiết
)

/**
 * Shen Sha (Thần Sát)
 */
@Serializable
data class ShenSha(
    val name: String,         // Ví dụ: "Thiên Ất Quý Nhân"
    val pillar: String,       // Ví dụ: "Ngày"
    val type: String,         // "Cát" (Tốt) hoặc "Hung" (Xấu)
    val description: String   // Ý nghĩa
)

/**
 * Luck Pillar (Đại Vận)
 */
@Serializable
data class LuckPillar(
    val startAge: Int,        // Tuổi nguyên (Years)
    val endAge: Int,          // Tuổi kết thúc (Years)
    val startMonths: Int,     // Tháng lẻ
    val startDays: Int,       // Ngày lẻ
    val displayAge: String,   // Chuỗi hiển thị (e.g., "6 tuổi 4 tháng")
    val stem: String,         // Thiên Can
    val branch: String        // Địa Chi
)

/**
 * Complete Bát Tự (Four Pillars) chart data
 */
@Serializable
data class BaZiData(
    val birthInfo: String = "",              // Birth details (TST, Longitude info)
    val year: Pillar,                   // Year Pillar (Trụ Năm)
    val month: Pillar,                  // Month Pillar (Trụ Tháng)
    val day: Pillar,                    // Day Pillar (Trụ Ngày)
    val hour: Pillar,                   // Hour Pillar (Trụ Giờ)
    val tenGods: TenGods = TenGods(),               // Ten Gods relationships
    val currentTerm: String = "",            // Current solar term
    val nextTerm: String = "",               // Next solar term
    val nextTermTime: String = "",           // Time of next solar term
    val elementBalance: Map<String, Int> = emptyMap(), // Five Elements balance count
    val season: String = "",            // Mùa sinh (Xuân, Hạ, Thu, Đông)
    val dayMasterStrength: String = "", // Trạng thái Nhật Chủ (Vượng, Tướng, Hưu, Tù, Tử)
    val interactions: List<Interaction> = emptyList(), // Hợp, Xung, Hình, Hại
    val shenShaList: List<ShenSha> = emptyList(),      // Thần Sát
    val luckPillars: List<LuckPillar> = emptyList()    // Đại Vận
)
