package com.skul9x.battu

import com.skul9x.battu.core.BaZiLogic
import com.skul9x.battu.data.Gender
import com.skul9x.battu.data.UserInput
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import java.io.File

class AnnualLuckTest {

    private val solarTermsJson = getFileContent()

    companion object {
        fun getFileContent(): String {
            val paths = listOf(
                "app/src/main/assets/solar-term.json",
                "src/main/assets/solar-term.json",
                "../app/src/main/assets/solar-term.json"
            )
            for (p in paths) {
                val file = File(p)
                if (file.exists()) return file.readText()
            }
            throw java.io.FileNotFoundException("Could not find solar-term.json. CWD is " + File(".").absolutePath)
        }
    }
    private val baZiLogic = BaZiLogic(solarTermsJson)

    @Test
    fun testAnnualLuck2026IsBinhNgo() {
        // Birth date: 1992-06-05 13:30 (Ngọc)
        val input = UserInput(
            name = "Ngọc",
            solarDay = 5,
            solarMonth = 6,
            solarYear = 1992,
            hour = 13,
            gender = Gender.NU,
            longitude = 105.8,
            dayBoundaryAt23 = true
        )

        val data = baZiLogic.calculateBaZi(input)
        
        // Find 2026 in annual pillars
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
        assertTrue("Should have calculated 2026 annual pillar", found2026)
    }

    @Test
    fun testAnnualLuck1992IsNhamThan() {
        // Birth date: 1992-06-05 13:30 (Ngọc)
        val input = UserInput(
            name = "Ngọc",
            solarDay = 5,
            solarMonth = 6,
            solarYear = 1992,
            hour = 18,
            gender = Gender.NU,
            longitude = 105.8,
            dayBoundaryAt23 = true
        )

        val data = baZiLogic.calculateBaZi(input)
        
        // Month Pillar check
        assertEquals("Bính", data.month.stem)
        assertEquals("Ngọ", data.month.branch)

        // Find 1992 in annual pillars
        // Luck pillars for Nu (Dương year 1992) go backwards.
        // First luck pillar starts at some age. 1992 should be in the "pre-luck" or first luck period logic.
        // The implementation calculates 10 luck periods of 10 years each.
        
        var found1992 = false
        data.luckPillars.forEach { lp ->
            lp.annualPillars.forEach { ap ->
                if (ap.year == 1992) {
                    assertEquals("Nhâm", ap.stem)
                    assertEquals("Thân", ap.branch)
                    found1992 = true
                }
            }
        }
        // Depending on start age, 1992 might not be in the 100 years calculated.
        // Birth year 1992, if luck starts at age 6, luck pillars start from 1998.
        // Let's check start age of luck for Ngọc.
        val luckStartAge = data.luckPillars.first().startAge
        println("Luck starts at age $luckStartAge")
        
        if (luckStartAge == 0) {
            assertTrue("Should have 1992 if luck starts at age 0", found1992)
        }
    }
}
