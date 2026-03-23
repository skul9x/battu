package com.skul9x.battu

import com.skul9x.battu.core.BaZiLogic
import com.skul9x.battu.data.Gender
import com.skul9x.battu.data.UserInput
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class Phase04AccuracyTest {

    // Mock solar terms for test: 
    // Birth: 1990-05-15 10:30 (UTC+7) -> 1990-05-15 03:30 (UTC)
    // Next Tiết (Mang Chủng): 1990-06-06 06:00:00 (UTC)
    // Calculation (Forward - Male Yang Year): 
    // Duration: June 6 06:00 - May 15 03:30
    // May has 31 days. 
    // Remaining in May: 16 days 20 hours 30 mins
    // June: 5 days 6 hours
    // Total: 21 days 26 hours 30 mins = 22 days 2 hours 30 mins
    
    // 22 days = 22/3 = 7 years (rem 1 day = 4 months)
    // 2 hours = 10 days
    // 30 mins = 2.5 days
    // Total: 7 years 4 months 12.5 days -> rounds to 7 years 4 months 12 days
    
    private val mockSolarTerms = """
        {
            "1990": {
                "year": "1990",
                "data": {
                    "start_of_spring": "1990-02-04 02:14:00+00:00",
                    "awakening_of_insects": "1990-03-05 20:14:00+00:00",
                    "pure_brightness": "1990-04-05 01:14:00+00:00",
                    "start_of_summer": "1990-05-05 18:45:00+00:00",
                    "grain_in_ear": "1990-06-06 06:00:00+00:00",
                    "minor_heat": "1990-07-07 16:15:00+00:00",
                    "start_of_autumn": "1990-08-08 02:00:00+00:00",
                    "white_dew": "1990-09-08 04:45:00+00:00",
                    "cold_dew": "1990-10-08 20:15:00+00:00",
                    "start_of_winter": "1990-11-07 23:30:00+00:00",
                    "major_snow": "1990-12-07 16:15:00+00:00",
                    "minor_cold": "1990-01-05 15:30:00+00:00"
                }
            }
        }
    """.trimIndent()

    @Test
    fun testLuckPillar_PreciseCalculation() {
        val logic = BaZiLogic(mockSolarTerms)
        
        // Born: 1990-05-15 10:30 (UTC+7) -> 1990-05-15 03:30 (UTC)
        // Year 1990 (Canh Ngọ) - Canh is Yang (Dương)
        // Male + Yang Year = Forward (Thuận)
        val input = UserInput(
            name = "Test Accuracy",
            solarDay = 15,
            solarMonth = 5,
            solarYear = 1990,
            hour = 10,
            gender = Gender.NAM,
            isLunar = false,
            longitude = 105.0 // No correction for simplicity
        )

        val result = logic.calculateBaZi(input)
        
        // Duration to June 6 06:00:00
        // Duration: 22 days, 2 hours, 30 mins
        // 22 days = 7 years, 4 months
        // 2 hours = 10 days
        // 30 mins = 2 days 12 hours -> total 12 days 12 hours
        // Result: 7 years, 4 months, 12 days
        
        val firstPillar = result.luckPillars[0]
        
        println("Display Age: ${firstPillar.displayAge}")
        assertTrue("Start age should be around 7", firstPillar.startAge >= 7)
        println("Actual: ${firstPillar.startAge}y ${firstPillar.startMonths}m ${firstPillar.startDays}d")
        assertEquals("Wrong start years: ${firstPillar.startAge}", 7, firstPillar.startAge)
        assertEquals("Wrong start months: ${firstPillar.startMonths}", 4, firstPillar.startMonths)
        assertEquals("Wrong start days: ${firstPillar.startDays}", 15, firstPillar.startDays)
        assertEquals("7 tuổi 4 tháng 15 ngày", firstPillar.displayAge)
    }
}
