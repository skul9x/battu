package com.skul9x.battu

import com.skul9x.battu.data.*
import kotlinx.serialization.json.Json
import org.junit.Test
import org.junit.Assert.*

class MigrationTest {

    private val baZiJson = Json {
        ignoreUnknownKeys = true
        isLenient = true
        encodeDefaults = true
        coerceInputValues = true
    }

    @Test
    fun testLegacyJsonMigration() {
        // A minimal version of BaZiData JSON (from an older build)
        val legacyJson = """
        {
            "birthInfo": "Hanoi, GMT+7",
            "year": {
                "stem": "Giáp",
                "stemYinYang": "Dương",
                "stemElement": "Mộc",
                "branch": "Tý",
                "branchYinYang": "Dương",
                "branchElement": "Thủy"
            },
            "month": {
                "stem": "Giáp",
                "stemYinYang": "Dương",
                "stemElement": "Mộc",
                "branch": "Tý",
                "branchYinYang": "Dương",
                "branchElement": "Thủy"
            },
            "day": {
                "stem": "Giáp",
                "stemYinYang": "Dương",
                "stemElement": "Mộc",
                "branch": "Tý",
                "branchYinYang": "Dương",
                "branchElement": "Thủy"
            },
            "hour": {
                "stem": "Giáp",
                "stemYinYang": "Dương",
                "stemElement": "Mộc",
                "branch": "Tý",
                "branchYinYang": "Dương",
                "branchElement": "Thủy"
            },
            "tenGods": {
                "dayMaster": "Mộc"
            },
            "currentTerm": "Lập Xuân",
            "nextTerm": "Vũ Thủy",
            "nextTermTime": "2024-02-19 12:00"
        }
        """.trimIndent()

        // Decode into NEW BaZiData
        val baziData = baZiJson.decodeFromString<BaZiData>(legacyJson)

        // Verify fundamental data is kept
        assertEquals("Giáp", baziData.year.stem)
        assertEquals("Tý", baziData.year.branch)
        assertEquals("Mộc", baziData.tenGods.dayMaster)

        // Verify NEW fields have default values (no crash)
        assertTrue(baziData.interactions.isEmpty())
        assertTrue(baziData.shenShaList.isEmpty())
        assertTrue(baziData.luckPillars.isEmpty())
        assertEquals("", baziData.dayMasterStrength)
        
        // Verify default pillar fields
        assertNull(baziData.year.napAm)
        assertTrue(baziData.year.hiddenStems.isEmpty())
    }
}
