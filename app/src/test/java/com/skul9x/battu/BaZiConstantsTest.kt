package com.skul9x.battu

import com.skul9x.battu.data.*
import org.junit.Test
import org.junit.Assert.*

/**
 * Unit tests for BaZiConstants and Phase 01 core data structures
 */
class BaZiConstantsTest {

    @Test
    fun testNapAmLookup_CorrectValues() {
        // TC-01: Verify correct Nap Am for specific pairs
        
        // Nhâm Thân -> Kiếm Phong Kim
        val napAm1 = BaZiConstants.getNapAm("Nhâm", "Thân")
        assertNotNull(napAm1)
        assertEquals("Kiếm Phong Kim", napAm1?.name)
        assertEquals(Element.KIM, napAm1?.element)
        
        // Mậu Thìn -> Đại Lâm Mộc
        val napAm2 = BaZiConstants.getNapAm("Mậu", "Thìn")
        assertNotNull(napAm2)
        assertEquals("Đại Lâm Mộc", napAm2?.name)
        assertEquals(Element.MOC, napAm2?.element)
        
        // Quý Hợi -> Đại Hải Thủy
        val napAm3 = BaZiConstants.getNapAm("Quý", "Hợi")
        assertNotNull(napAm3)
        assertEquals("Đại Hải Thủy", napAm3?.name)
        assertEquals(Element.THUY, napAm3?.element)
    }

    @Test
    fun testHiddenStems_CorrectWeights() {
        // TC-02: Verify hidden stems and weights for specific branches
        
        // Thìn -> Mậu: 60% (Bản Khí), Ất: 30% (Trung Khí), Quý: 10% (Dư Khí)
        val stemsThin = BaZiConstants.getHiddenStems("Thìn")
        assertEquals(3, stemsThin.size)
        
        val mau = stemsThin.find { it.stem == "Mậu" }
        assertEquals(60, mau?.percentage)
        assertEquals(HiddenStemType.BAN_KHI, mau?.type)
        
        val at = stemsThin.find { it.stem == "Ất" }
        assertEquals(30, at?.percentage)
        assertEquals(HiddenStemType.TRUNG_KHI, at?.type)
        
        val quy = stemsThin.find { it.stem == "Quý" }
        assertEquals(10, quy?.percentage)
        assertEquals(HiddenStemType.DU_KHI, quy?.type)
        
        // Tý -> Quý: 100% (Bản Khí)
        val stemsTy = BaZiConstants.getHiddenStems("Tý")
        assertEquals(1, stemsTy.size)
        assertEquals("Quý", stemsTy[0].stem)
        assertEquals(100, stemsTy[0].percentage)
    }

    @Test
    fun testMainHiddenStem_ReturnCorrectBảnKhí() {
        // TC-03: Verify getMainHiddenStem returns the correct main stem
        
        assertEquals("Mậu", BaZiConstants.getMainHiddenStem("Thìn"))
        assertEquals("Quý", BaZiConstants.getMainHiddenStem("Tý"))
        assertEquals("Kỷ", BaZiConstants.getMainHiddenStem("Sửu"))
        assertEquals("Giáp", BaZiConstants.getMainHiddenStem("Dần"))
        assertEquals("Bính", BaZiConstants.getMainHiddenStem("Tỵ"))
        assertEquals("Canh", BaZiConstants.getMainHiddenStem("Thân"))
    }

    @Test
    fun testTenGodCalculation_CorrectLogic() {
        // TC-04: Verify calculation based on Day Master
        
        // Day Master Giáp (Mộc/Dương)
        val dm = "Giáp"
        
        // Same Element
        assertEquals("Tỷ Kiên", BaZiConstants.calculateTenGod(dm, "Giáp")) // Same polarity
        assertEquals("Kiếp Tài", BaZiConstants.calculateTenGod(dm, "Ất"))   // Different polarity
        
        // I Produce (Mộc sinh Hỏa)
        assertEquals("Thực Thần", BaZiConstants.calculateTenGod(dm, "Bính")) // Same polarity (Dương)
        assertEquals("Thương Quan", BaZiConstants.calculateTenGod(dm, "Đinh")) // Different polarity (Âm)
        
        // I Control (Mộc khắc Thổ)
        assertEquals("Thiên Tài", BaZiConstants.calculateTenGod(dm, "Mậu")) // Same polarity (Dương)
        assertEquals("Chính Tài", BaZiConstants.calculateTenGod(dm, "Kỷ"))   // Different polarity (Âm)
        
        // Control ME (Kim khắc Mộc)
        assertEquals("Thất Sát", BaZiConstants.calculateTenGod(dm, "Canh")) // Same polarity (Dương)
        assertEquals("Chính Quan", BaZiConstants.calculateTenGod(dm, "Tân")) // Different polarity (Âm)
        
        // Produce ME (Thủy sinh Mộc)
        assertEquals("Thiên Ấn", BaZiConstants.calculateTenGod(dm, "Nhâm")) // Same polarity (Dương)
        assertEquals("Chính Ấn", BaZiConstants.calculateTenGod(dm, "Quý"))   // Different polarity (Âm)
    }

    @Test
    fun testTenGodForBranch_UsesMainHiddenStem() {
        // TC-05: Verify branch Ten God logic (demonstration using helper)
        
        // Example: Day Master Giáp (Mộc), Branch Thìn (Main Hidden Stem is Mậu - Thổ)
        val dm = "Giáp"
        val branch = "Thìn"
        val mainStem = BaZiConstants.getMainHiddenStem(branch)
        assertEquals("Mậu", mainStem)
        
        val tenGod = BaZiConstants.calculateTenGod(dm, mainStem)
        assertEquals("Thiên Tài", tenGod)
    }

    @Test
    fun testLifeStages_CorrectLogic() {
        // TC-08: Check Yang Stem (Giáp)
        assertEquals("Trường Sinh", BaZiConstants.getLifeStage("Giáp", "Hợi"))
        assertEquals("Đế Vượng", BaZiConstants.getLifeStage("Giáp", "Mão"))
        assertEquals("Tuyệt", BaZiConstants.getLifeStage("Giáp", "Thân"))
        
        // TC-08b: Check Yin Stem (Ất) - Nghịch hành
        assertEquals("Trường Sinh", BaZiConstants.getLifeStage("Ất", "Ngọ"))
        assertEquals("Đế Vượng", BaZiConstants.getLifeStage("Ất", "Dần")) // Nghịch từ Ngọ: Ngọ(TS), Tỵ(MD), Thìn(QD), Mão(LQ), Dần(DV)
        assertEquals("Tử", BaZiConstants.getLifeStage("Ất", "Hợi"))
    }

    @Test
    fun testSeasonAndStrength_CorrectLogic() {
        // TC-09: Season mapping
        assertEquals(BaZiConstants.Season.XUAN, BaZiConstants.getSeason("Dần"))
        assertEquals(BaZiConstants.Season.HA, BaZiConstants.getSeason("Ngọ"))
        assertEquals(BaZiConstants.Season.TU_QUY, BaZiConstants.getSeason("Thìn"))
        
        // Day Master Strength in Season
        val spring = BaZiConstants.Season.XUAN
        assertEquals("Vượng", BaZiConstants.getDayMasterStrength(spring, "Giáp")) // Mộc in Spring
        assertEquals("Tướng", BaZiConstants.getDayMasterStrength(spring, "Bính")) // Hỏa in Spring
        assertEquals("Tử", BaZiConstants.getDayMasterStrength(spring, "Canh"))    // Kim in Spring
    }
}
