package com.skul9x.battu

import com.skul9x.battu.ai.PromptBuilder
import com.skul9x.battu.core.BaZiLogic
import com.skul9x.battu.data.Gender
import com.skul9x.battu.data.UserInput
import org.json.JSONObject
import org.junit.Assert.assertTrue
import org.junit.Test

/**
 * Unit test for PromptBuilder Phase 04 updates
 */
class PromptBuilderTest {
    private val logic = BaZiLogic("{}")

    @Test
    fun testBuildJsonPrompt_Structure() {
        // Test case: 1990-05-15 10:30 Male
        val input = UserInput(
            name = "Thành",
            solarDay = 15,
            solarMonth = 5,
            solarYear = 1990,
            hour = 10,
            gender = Gender.NAM,
            isLunar = false
        )

        val result = logic.calculateBaZi(input)
        val prompt = PromptBuilder.buildJsonPrompt(input.name, input.gender, result)

        // Parse resulting JSON
        val root = JSONObject(prompt)
        assertTrue("Should have chart_data", root.has("chart_data"))
        
        val chartData = root.getJSONObject("chart_data")
        
        // Check Phase 04 new fields
        assertTrue("Should have shen_sha", chartData.has("shen_sha"))
        assertTrue("Should have interactions", chartData.has("interactions"))
        assertTrue("Should have luck_pillars", chartData.has("luck_pillars"))
        assertTrue("Should have xun_kong", chartData.has("xun_kong"))
        
        val xunKong = chartData.getJSONObject("xun_kong")
        assertTrue("Should have year_void", xunKong.has("year_void"))
        assertTrue("Should have day_void", xunKong.has("day_void"))
        
        // Check element_balance fields
        val balance = chartData.getJSONObject("element_balance")
        assertTrue("Should have season", balance.has("season"))
        assertTrue("Should have day_master_strength", balance.has("day_master_strength"))
        
        // Check pillars and hidden_stems structure
        val pillars = chartData.getJSONObject("pillars")
        val yearPillar = pillars.getJSONObject("year")
        
        assertTrue("Pillar should have life_stage", yearPillar.has("life_stage"))
        assertTrue("Pillar should have hidden_stems", yearPillar.has("hidden_stems"))
        
        val hiddenStems = yearPillar.getJSONArray("hidden_stems")
        if (hiddenStems.length() > 0) {
            val firstStem = hiddenStems.get(0)
            assertTrue("hidden_stems should be objects, not strings", firstStem is JSONObject)
            val stemObj = firstStem as JSONObject
            assertTrue("Should have stem field", stemObj.has("stem"))
            assertTrue("Should have percentage field", stemObj.has("percentage"))
            assertTrue("Should have type field", stemObj.has("type"))
            assertTrue("Should have ten_god field", stemObj.has("ten_god"))
        }

        // Dump prompt to prompt.txt
        java.io.File("../prompt.txt").writeText(prompt)
        println("✓ [Phase 05] Prompt JSON structure verified successfully and exported to prompt.txt!")
        // DEBUG: println(prompt)
    }
}
