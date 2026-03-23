package com.skul9x.battu

import com.skul9x.battu.core.BaZiLogic
import com.skul9x.battu.data.UserInput
import com.skul9x.battu.data.Gender
import org.junit.Test
import org.junit.Assert.*
import org.json.JSONObject

class LuckPillarTest {

    // Mock solar terms for tests
    // March 5, 2024 17:22:00 Hanoi (10:22:00 UTC) -> Kinh Trập
    // April 4, 2024 21:41:00 Hanoi (14:41:00 UTC) -> Thanh Minh
    private val mockSolarTerms = """
        {
          "2024": {
            "data": {
              "awakening_of_insects": "2024-03-05 10:22:00",
              "pure_brightness": "2024-04-04 14:41:00",
              "start_of_spring": "2024-02-04 08:26:53",
              "spring_showers": "2024-02-19 04:12:00",
              "spring_equinox": "2024-03-20 03:06:00",
              "grain_rain": "2024-04-19 13:59:00",
              "start_of_summer": "2024-05-05 00:09:00",
              "grain_buds": "2024-05-20 12:59:00",
              "grain_in_ear": "2024-06-05 04:09:00",
              "summer_solstice": "2024-06-21 20:50:00",
              "minor_heat": "2024-07-06 14:19:00",
              "major_heat": "2024-07-22 07:44:00",
              "start_of_autumn": "2024-08-07 00:09:00",
              "end_of_heat": "2024-08-22 14:54:00",
              "white_dew": "2024-09-07 03:10:00",
              "autumn_equinox": "2024-09-22 12:43:00",
              "cold_dew": "1924-10-08 13:52:12",
              "frost": "1924-10-23 16:44:25",
              "start_of_winter": "2024-11-07 00:19:00",
              "minor_snow": "2024-11-21 21:49:00",
              "major_snow": "2024-12-06 15:16:00",
              "winter_solstice": "2024-12-21 09:20:00",
              "minor_cold": "2024-01-05 15:49:00",
              "major_cold": "2024-01-20 02:07:00"
            }
          }
        }
    """.trimIndent()

    private val logic = BaZiLogic(mockSolarTerms)

    @Test
    fun testLuckPillarStartAge_Forward() {
        // Born: 2024-03-23 10:00:00 Hanoi
        // Giáp Thìn year (Yang) -> Male is Thuận (Forward)
        // Next Term: Thanh Minh (2024-04-04 14:41:00 UTC)
        // Note: 2024-03-23 10:00 Hanoi = 2024-03-23 03:00 UTC
        
        val input = UserInput(
            name = "Test Forward",
            solarDay = 23,
            solarMonth = 3,
            solarYear = 2024,
            hour = 10,
            gender = Gender.NAM
        )

        val result = logic.calculateBaZi(input)
        val firstLuck = result.luckPillars[0]

        println("Actual Forward Start: ${firstLuck.startAge}y ${firstLuck.startMonths}m ${firstLuck.startDays}d")
        println("Display: ${firstLuck.displayAge}")
        
        assertEquals(4, firstLuck.startAge)
        assertEquals(1, firstLuck.startMonths)
        assertEquals(25, firstLuck.startDays)
        assertEquals("4 tuổi 1 tháng 25 ngày", firstLuck.displayAge)
    }

    @Test
    fun testLuckPillarStartAge_Backward() {
        // Born: 2024-03-23 10:00:00 Hanoi
        // Giáp Thìn year (Yang) -> Female is Nghịch (Backward)
        // Previous Term: Kinh Trập (2024-03-05 10:22:00 UTC)
        
        val input = UserInput(
            name = "Test Backward",
            solarDay = 23,
            solarMonth = 3,
            solarYear = 2024,
            hour = 10,
            gender = Gender.NU
        )

        val result = logic.calculateBaZi(input)
        val firstLuck = result.luckPillars[0]
        
        println("Actual Backward Start: ${firstLuck.startAge}y ${firstLuck.startMonths}m ${firstLuck.startDays}d")

        assertEquals(5, firstLuck.startAge)
        assertEquals(10, firstLuck.startMonths)
        assertEquals(23, firstLuck.startDays)
    }
}
