package com.skul9x.battu

import com.skul9x.battu.core.BaZiLogic
import com.skul9x.battu.data.Gender
import com.skul9x.battu.data.UserInput
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Test

class AuxiliaryPillarTest {

    private val mockSolarTerms = """
        {
            "1992": {
                "year": "1992",
                "data": {
                    "minor_cold": "1992-01-05 00:00:00+00:00",
                    "start_of_spring": "1992-02-04 13:00:00+00:00",
                    "awakening_of_insects": "1992-03-05 00:00:00+00:00",
                    "pure_brightness": "1992-04-05 00:00:00+00:00",
                    "start_of_summer": "1992-05-05 00:00:00+00:00",
                    "grain_in_ear": "1992-06-06 00:00:00+00:00",
                    "minor_heat": "1992-07-07 00:00:00+00:00",
                    "start_of_autumn": "1992-08-08 00:00:00+00:00",
                    "white_dew": "1992-09-08 00:00:00+00:00",
                    "cold_dew": "1992-10-08 00:00:00+00:00",
                    "start_of_winter": "1992-11-07 00:00:00+00:00",
                    "major_snow": "1992-12-07 00:00:00+00:00"
                }
            }
        }
    """.trimIndent()

    @Test
    fun testAuxiliaryPillars_NgocCase() {
        val logic = BaZiLogic(mockSolarTerms)
        
        // Born: 1992-10-23 17:30 (UTC+7)
        // Year: Nhâm Thân (yearCanIdx = 8, yearChiIdx = 8)
        // Month: Canh Tuất (monthCanIdx = 6, monthChiIdx = 10)
        // Day: Nhâm Thân (dayCanIdx = 8, dayChiIdx = 8)
        // Hour: Kỷ Dậu (hourCanIdx = 5, hourChiIdx = 9)
        
        val input = UserInput(
            name = "Nguyễn Thị Ngọc",
            solarDay = 23,
            solarMonth = 10,
            solarYear = 1992,
            hour = 17,
            gender = Gender.NU,
            isLunar = false,
            longitude = 105.0 // No correction
        )

        val result = logic.calculateBaZi(input)
        
        // Verify Pillars first to ensure birth date is correct
        println("DEBUG: Year=${result.year.stem} ${result.year.branch}")
        println("DEBUG: Month=${result.month.stem} ${result.month.branch}")
        println("DEBUG: Day=${result.day.stem} ${result.day.branch}")
        println("DEBUG: Hour=${result.hour.stem} ${result.hour.branch}")

        assertEquals("Year pillar branch should be Thân", "Thân", result.year.branch)
        assertEquals("Month pillar branch should be Tuất", "Tuất", result.month.branch)
        assertEquals("Day pillar stem should be Nhâm", "Nhâm", result.day.stem)
        assertEquals("Hour pillar branch should be Dậu", "Dậu", result.hour.branch)
        
        // Verify Thai Nguyên (Fetal Origin)
        assertNotNull("Thai Nguyên should not be null", result.fetalOrigin)
        assertEquals("Thai Nguyên Stem", "Tân", result.fetalOrigin?.stem)
        assertEquals("Thai Nguyên Branch", "Sửu", result.fetalOrigin?.branch)
        
        // Verify Mệnh Cung (Life Palace)
        assertNotNull("Mệnh Cung should not be null", result.lifePalace)
        assertEquals("Mệnh Cung Stem", "Canh", result.lifePalace?.stem)
        assertEquals("Mệnh Cung Branch", "Tuất", result.lifePalace?.branch)
        
        
        println("✓ Thai Nguyên: ${result.fetalOrigin?.stem} ${result.fetalOrigin?.branch}")
        println("✓ Mệnh Cung: ${result.lifePalace?.stem} ${result.lifePalace?.branch}")

        // 9. Đào Hoa (Day: Thân -> Hoa: Dậu)
        val daoHoa = result.shenShaList.find { it.name == "Đào Hoa" }
        assertNotNull("Should have Đào Hoa", daoHoa)
        assertEquals("Đào Hoa should be at Hour for Ngọc", "Giờ", daoHoa?.pillar)
        
        // 10. Thiên Y (Month: Tuất -> Y: Dậu)
        val thienY = result.shenShaList.find { it.name == "Thiên Y" }
        assertNotNull("Should have Thiên Y", thienY)
        assertEquals("Thiên Y should be at Hour for Ngọc", "Giờ", thienY?.pillar)

        println("✓ Đào Hoa: ${daoHoa?.pillar}")
        println("✓ Thiên Y: ${thienY?.pillar}")
        
        // 11-12. Check other stars for extra coverage
        // Thân year -> Hồng Loan: Mùi, Thiên Hỉ: Sửu
        // No Mùi or Sửu in Ngọc's chart, so these should not appear
        val hongLoan = result.shenShaList.find { it.name == "Hồng Loan" }
        val thienHi = result.shenShaList.find { it.name == "Thiên Hỉ" }
        assertEquals("Should NOT have Hồng Loan for Ngọc", null, hongLoan)
        assertEquals("Should NOT have Thiên Hỉ for Ngọc", null, thienHi)
    }
}
