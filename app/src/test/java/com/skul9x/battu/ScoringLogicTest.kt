package com.skul9x.battu

import com.skul9x.battu.core.BaZiLogic
import com.skul9x.battu.data.Gender
import com.skul9x.battu.data.UserInput
import org.junit.Assert.assertEquals
import org.junit.Test
import java.io.File

/**
 * ScoringLogicTest - Verification for Improved Element Balance Scoring (v2.3.0)
 * 
 * Validates:
 * 1. MONTH_MULTIPLIER (2.0x) for Month Pillar
 * 2. XUN_KONG_PENALTY (0.5x) for Void Pillars
 * 3. Combined effects (2.0 * 0.5 = 1.0)
 */
class ScoringLogicTest {

    private val solarTermsJson = AdvancedFeaturesTest.getFileContent()
    private val baZiLogic = BaZiLogic(solarTermsJson)

    @Test
    fun testMonthPillarMultiplication() {
        // Sample: 1990-05-01 12:00 (Male)
        // Year: Canh Ngọ (Thân/Dậu void)
        // Day: Bính Dần (Tuất/Hợi void)
        // Month: Canh Thìn (Dần/Mão void - wait, Month depends on solar term)
        // In 1990, May 1st is month Canh Thìn.
        // Branches: Ngọ, Thìn, Dần, Ngọ. 
        // Void: Thân, Dậu, Tuất, Hợi.
        // Month Thìn is NOT void. Multiplier = 2.0.
        
        val input = UserInput(
            name = "Test Non-Void Month",
            solarDay = 1,
            solarMonth = 5,
            solarYear = 1990,
            hour = 12,
            gender = Gender.NAM
        )
        
        val data = baZiLogic.calculateBaZi(input)
        val scores = data.elementBalance
        
        // Month is Thìn (Mậu 60%, Ất 30%, Quý 10%)
        // Multiplier = 2.0
        // Thổ (Earth) from Month: 60% * 60 * 2.0 = 72
        // Mộc (Wood) from Month: 30% * 60 * 2.0 = 36
        // Thủy (Water) from Month: 10% * 60 * 2.0 = 12
        
        // Thổ points calculation:
        // Stems: Canh(0), Canh(0), Bính(0), Giáp(0) [Hour 12:00 is Giáp Ngọ] -> 0
        // Year Ngọ: Kỷ(30% * 60 * 1.0 = 18) -> +18
        // Month Thìn: Mậu(72) -> +72
        // Day Dần: Mậu(10% * 60 * 1.0 = 6) -> +6
        // Hour Ngọ: Kỷ(30% * 60 * 1.0 = 18) -> +18
        // NapAm: Canh Ngọ (Lộ Bàng Thổ), Canh Thìn (Bạch Lạp Kim), Bính Dần (Lô Trung Hỏa), Giáp Ngọ (Sa Trung Kim)
        // Thổ NapAm: Canh Ngọ -> 10 * 1.0 = 10
        // Total Thổ = 18 + 72 + 6 + 18 + 10 = 124
        
        assertEquals(124, scores["Thổ"])
    }

    @Test
    fun testXunKongPenalty() {
        // 1993-01-01 12:00 (Male)
        // Year: Nhâm Thân
        // Day: Quý Dậu
        // Xun Kong of Nhâm Thân (Year): Tuất, Hợi.
        // Xun Kong of Quý Dậu (Day): Tuất, Hợi.
        // Is any pillar Tuất or Hợi? 
        // 1993-01-01 is month Nhâm Tý. Hour 12:00 is Mậu Ngọ. 
        // No void here.
        
        // Let's find a case with Void.
        // Year Nhâm Thân, if Month was Tuất or Hợi.
        // 1992-10-23 (Ngọc case): Month is Canh Tuất. 
        // Year is Nhâm Thân -> Void: Tuất, Hợi.
        // So Month Tuất is VOID!
        
        val input = UserInput(
            name = "Nguyễn Thị Ngọc",
            solarDay = 23,
            solarMonth = 10,
            solarYear = 1992,
            hour = 17,
            gender = Gender.NU
        )
        
        val data = baZiLogic.calculateBaZi(input)
        val scores = data.elementBalance
        
        // Month Pillar: Multiplier = 2.0 (Month) * 0.5 (Void) = 1.0
        // Month Tuất (Mậu 60%, Tân 30%, Đinh 10%)
        // Earth (Mậu) from Month: 60% * 60 * 1.0 = 36
        // Metal (Tân) from Month: 30% * 60 * 1.0 = 18
        // Fire (Đinh) from Month: 10% * 60 * 1.0 = 6
        
        // Total Thổ (Earth):
        // Stems: Nhâm(0), Canh(0), Nhâm(0), Kỷ(40) -> 40
        // Year: Mậu(6)
        // Month: Mậu(36)
        // Day: Mậu(6)
        // NapAm: Hour(10)
        // Total = 40 + 6 + 36 + 6 + 10 = 98
        
        assertEquals(98, scores["Thổ"])
        
        // Total Hỏa (Fire):
        // Stems: 0
        // Month: Đinh(10% * 60 * 1.0 = 6)
        // Day/Year/Hour: 0
        // NapAm: 0 (Kiếm Phong Kim, Thoa Xuyến Kim, Kiếm Phong Kim, Đại Dịch Thổ)
        // Total = 6
        assertEquals(6, scores["Hỏa"])
    }
}
