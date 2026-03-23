package com.skul9x.battu

import com.skul9x.battu.core.BaZiLogic
import com.skul9x.battu.data.UserInput
import com.skul9x.battu.data.Gender
import org.junit.Test
import org.junit.Assert.*

/**
 * Integration test for Bát Tự calculation logic
 * Verifies that the cloned logic produces correct results
 */
class BaZiLogicTest {
    private val logic = BaZiLogic("{}")

    @Test
    fun testBaZiCalculation_Example1() {
        // Test case: 1990-05-15 10:30 Male
        val input = UserInput(
            name = "Test User",
            solarDay = 15,
            solarMonth = 5,
            solarYear = 1990,
            hour = 10,
            gender = Gender.NAM,
            isLunar = false
        )

        val result = logic.calculateBaZi(input)

        // Verify result structure
        assertNotNull("BaZi result should not be null", result)
        assertNotNull("Year pillar should not be null", result.year)
        assertNotNull("Month pillar should not be null", result.month)
        assertNotNull("Day pillar should not be null", result.day)
        assertNotNull("Hour pillar should not be null", result.hour)

        // Verify pillars have stem and branch
        assertTrue("Year pillar should have stem", result.year.stem.isNotEmpty())
        assertTrue("Year pillar should have branch", result.year.branch.isNotEmpty())
        assertTrue("Day pillar should have stem", result.day.stem.isNotEmpty())
        assertTrue("Day pillar should have branch", result.day.branch.isNotEmpty())

        // Verify Ten Gods are calculated
        assertNotNull("Ten Gods should be calculated", result.tenGods)
        
        println("✓ Test 1 passed: ${result.year.stem} ${result.year.branch} year")
    }

    @Test
    fun testBaZiCalculation_Example2() {
        // Test case: 2000-01-01 00:00 Female
        val input = UserInput(
            name = "Test User 2",
            solarDay = 1,
            solarMonth = 1,
            solarYear = 2000,
            hour = 0,
            gender = Gender.NU,
            isLunar = false
        )

        val result = logic.calculateBaZi(input)

        // Verify basic structure
        assertNotNull("BaZi result should not be null", result)
        
        // Verify all four pillars exist
        assertNotNull("Year pillar exists", result.year)
        assertNotNull("Month pillar exists", result.month)
        assertNotNull("Day pillar exists", result.day)
        assertNotNull("Hour pillar exists", result.hour)

        // Verify elements are assigned
        assertTrue("Year pillar has element", result.year.stemElement.isNotEmpty())
        assertTrue("Day pillar has element", result.day.stemElement.isNotEmpty())

        println("✓ Test 2 passed: ${result.day.stem} ${result.day.branch} day master")
    }

    @Test
    fun testLunarConversion() {
        // Test lunar date input
        val input = UserInput(
            name = "Lunar Test",
            solarDay = 1,
            solarMonth = 1,
            solarYear = 2000,
            hour = 12,
            gender = Gender.NAM,
            isLunar = true
        )

        val result = logic.calculateBaZi(input)

        assertNotNull("Lunar calculation should work", result)
        assertTrue("Should have valid pillars", result.year.stem.isNotEmpty())
        
        println("✓ Lunar test passed")
    }

    @Test
    fun testHiddenStems() {
        // Test that hidden stems are calculated
        val input = UserInput(
            name = "Hidden Stems Test",
            solarDay = 10,
            solarMonth = 6,
            solarYear = 1995,
            hour = 14,
            gender = Gender.NAM,
            isLunar = false
        )

        val result = logic.calculateBaZi(input)

        // Verify hidden stems exist
        assertTrue("Year pillar should have hidden stems", 
            result.year.hiddenStems.isNotEmpty())
        assertTrue("Day pillar should have hidden stems", 
            result.day.hiddenStems.isNotEmpty())

        println("✓ Hidden stems test passed: ${result.day.hiddenStems.size} stems in day pillar")
    }

    @Test
    fun testElementBalance() {
        // Test element balance calculation
        val input = UserInput(
            name = "Element Test",
            solarDay = 20,
            solarMonth = 8,
            solarYear = 1988,
            hour = 8,
            gender = Gender.NU,
            isLunar = false
        )

        val result = logic.calculateBaZi(input)

        // Verify element balance is calculated
        assertTrue("Element balance should be calculated", 
            result.elementBalance.isNotEmpty())
        assertTrue("Should have 5 elements", 
            result.elementBalance.size == 5)

        println("✓ Element balance test passed: ${result.elementBalance}")
    }

    @Test
    fun testInteractions_BanTamHop() {
        // Create dummy pillars
        fun createDummyPillar(branch: String) = com.skul9x.battu.data.Pillar(
            stem = "Giáp", stemYinYang = "Dương", stemElement = "Mộc",
            branch = branch, branchYinYang = "Dương", branchElement = "Mộc"
        )

        val pillars = mapOf(
            "Năm" to createDummyPillar("Thân"),
            "Tháng" to createDummyPillar("Tý"),
            "Ngày" to createDummyPillar("Dần"),
            "Giờ" to createDummyPillar("Ngọ")
        )

        val interactions = logic.calculateInteractions(pillars)
        
        // Should have "Bán Hợp" (Thân-Tý and Dần-Ngọ)
        val banThuy = interactions.find { it.description.contains("Thân - Tý") }
        val banHoa = interactions.find { it.description.contains("Dần - Ngọ") }
        
        assertNotNull("Bán Tam Hợp Thủy should be detected", banThuy)
        assertNotNull("Bán Tam Hợp Hỏa should be detected", banHoa)
        assertEquals("Bán Hợp Thủy", banThuy?.typeName)
    }

    @Test
    fun testInteractions_TamHopPriority() {
        fun createDummyPillar(branch: String) = com.skul9x.battu.data.Pillar(
            stem = "Giáp", stemYinYang = "Dương", stemElement = "Mộc",
            branch = branch, branchYinYang = "Dương", branchElement = "Mộc"
        )

        // Case: Thân, Tý, Thìn -> Should be Tam Hợp, NO separate Bán Tam Hợp
        val pillars = mapOf(
            "Năm" to createDummyPillar("Thân"),
            "Tháng" to createDummyPillar("Tý"),
            "Ngày" to createDummyPillar("Thìn"),
            "Giờ" to createDummyPillar("Mão")
        )

        val interactions = logic.calculateInteractions(pillars)
        
        val containsTamHop = interactions.any { it.typeName.startsWith("Tam Hợp") }
        val containsBanTamHop = interactions.any { it.typeName.startsWith("Bán Hợp") }
        
        assertTrue("Should contain Tam Hợp", containsTamHop)
        assertFalse("Should NOT contain Bán Tam Hợp (redundant)", containsBanTamHop)
    }
}
