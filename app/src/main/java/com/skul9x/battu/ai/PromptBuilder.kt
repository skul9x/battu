package com.skul9x.battu.ai

import com.skul9x.battu.data.BaZiData
import com.skul9x.battu.data.Gender
import org.json.JSONArray
import org.json.JSONObject

/**
 * PromptBuilder - Build JSON prompt for Gemini AI to interpret Bát Tự charts
 * 
 * This prompt follows the methodology of classical Bát Tự texts:
 * - Tử Bình Chân Thuyên (子平真詮)
 * - Trích Thiên Tùy (滴天髓)
 * - Uyên Hải Tử Bình (淵海子平)
 * 
 * The prompt structure ensures AI provides:
 * - Evidence-based analysis (every statement must cite Can/Chi/Ten Gods)
 * - Structured output (8 sections: Summary, Day Master, Use God, Personality, Career, Wealth, Relationships, Health, Advice)
 * - No speculation when data is insufficient
 */
object PromptBuilder {
    
    /**
     * Build complete JSON prompt for Bát Tự interpretation
     */
    fun buildJsonPrompt(name: String, gender: Gender, baZiData: BaZiData): String {
        val json = JSONObject()
        
        // Role and methodology
        json.put("role", "AI chuyên luận Bát Tự Tử Bình (Tứ Trụ)")
        
        // Style
        json.put("style", JSONObject().apply {
            put("tone", "Điềm đạm – phân tích mệnh lý. Xưng 'Tôi', gọi người xem 'Bạn'.")
            put("language", "Vietnamese")
        })
        
        // Methodology sources
        json.put("methodology_sources", JSONArray(listOf(
            "Tử Bình Chân Thuyên (子平真詮)",
            "Trích Thiên Tùy (滴天髓)",
            "Uyên Hải Tử Bình (淵海子平)"
        )))
        
        // Objective
        json.put("objective", "Phân tích Bát Tự theo cấu trúc Can Chi – không suy đoán cảm tính")
        
        // Absolute rules
        json.put("absolute_rules", buildAbsoluteRules())
        
        // Analysis pipeline
        json.put("analysis_pipeline", buildAnalysisPipeline())
        
        // Output format
        json.put("output_format", buildOutputFormat())
        
        // Chart data
        json.put("chart_data", buildChartData(name, gender, baZiData))
        
        return json.toString(2) // Pretty print with indent 2
    }
    
    /**
     * Build absolute rules that AI must follow
     */
    private fun buildAbsoluteRules(): JSONObject {
        return JSONObject().apply {
            put("must_do", JSONArray(listOf(
                "Mọi nhận định BẮT BUỘC phải có căn cứ Can Chi",
                "Phân biệt rõ: Nhật Chủ, Dụng Thần, Hỉ Thần, Kỵ Thần",
                "Phân tích Tàng Can và trọng số ảnh hưởng",
                "Xét quan hệ Sinh Khắc giữa các Can Chi",
                "Đánh giá Nhật Chủ vượng/suy dựa trên Ngũ Hành balance"
            )))
            
            put("must_not", JSONArray(listOf(
                "Không suy đoán khi thiếu dữ liệu",
                "Không dùng câu chung chung (VD: 'số giàu', 'số khổ') mà không chỉ rõ căn cứ",
                "Không khẳng định tuyệt đối - phải dùng ngôn ngữ xác suất ('thường', 'có xu hướng', 'nếu...')"
            )))
            
            put("evidence_format", "(Căn cứ: [Can/Chi] + [Thập Thần] + [Ngũ Hành] + [Quan hệ Sinh Khắc])")
        }
    }
    
    /**
     * Build analysis pipeline (step-by-step process)
     */
    private fun buildAnalysisPipeline(): JSONArray {
        return JSONArray(listOf(
            "Bước 1: Xác định Nhật Chủ (Day Master) vượng/suy",
            "Bước 2: Tìm Dụng Thần (Use God) / Hỉ Thần (Favorable God) / Kỵ Thần (Unfavorable God)",
            "Bước 3: Phân tích Thập Thần (Ten Gods) → Tính cách, năng lực",
            "Bước 4: Phân tích Ngũ Hành balance → Sức khỏe",
            "Bước 5: Phân tích quan hệ Can Chi → Sự nghiệp, tài lộc, tình duyên",
            "Bước 6: Tổng hợp và lời khuyên"
        ))
    }
    
    /**
     * Build output format structure
     */
    private fun buildOutputFormat(): JSONObject {
        return JSONObject().apply {
            put("sections", JSONObject().apply {
                put("A", "Tóm tắt lá số (5-10 dòng)")
                put("B", "Phân tích Nhật Chủ vượng suy")
                put("C", "Dụng Thần & Hỉ Thần")
                put("D", "Phân tích tính cách (dựa trên Thập Thần)")
                put("E", "Sự nghiệp & Tài lộc")
                put("F", "Tình duyên & Gia đình")
                put("G", "Sức khỏe (dựa trên Ngũ Hành balance)")
                put("H", "Lời khuyên tổng hợp")
            })
            
            put("markdown", true)
            put("use_headers", true)
            put("use_bullet_points", true)
        }
    }
    
    /**
     * Build chart data from BaZiData
     */
    private fun buildChartData(name: String, gender: Gender, data: BaZiData): JSONObject {
        return JSONObject().apply {
            // Person info
            put("person", JSONObject().apply {
                put("name", name)
                put("gender", if (gender == Gender.NAM) "Nam" else "Nữ")
            })
            
            // Four Pillars
            put("pillars", JSONObject().apply {
                put("year", buildPillarJson(data.year))
                put("month", buildPillarJson(data.month))
                put("day", buildPillarJson(data.day))
                put("hour", buildPillarJson(data.hour))
            })
            
            // Ten Gods
            put("ten_gods", buildTenGodsJson(data.tenGods))
            
            // Element balance & Strength
            put("element_balance", JSONObject().apply {
                put("Kim", data.elementBalance["Kim"] ?: 0)
                put("Mộc", data.elementBalance["Mộc"] ?: 0)
                put("Thủy", data.elementBalance["Thủy"] ?: 0)
                put("Hỏa", data.elementBalance["Hỏa"] ?: 0)
                put("Thổ", data.elementBalance["Thổ"] ?: 0)
                put("season", data.season)
                put("day_master_strength", data.dayMasterStrength)
            })
            
            // Shen Sha (Thần Sát)
            put("shen_sha", buildShenShaJson(data.shenShaList))
            
            // Interactions (Hợp/Xung/Hình/Hại)
            put("interactions", buildInteractionsJson(data.interactions))
            
            // Luck Pillars (Đại Vận)
            put("luck_pillars", buildLuckPillarsJson(data.luckPillars))
            
            // Day Master (Nhật Chủ)
            put("day_master", JSONObject().apply {
                put("stem", data.day.stem)
                put("element", data.day.stemElement)
                put("description", "Nhật Chủ là ${data.day.stem} (${data.day.stemElement})")
            })
        }
    }
    
    /**
     * Build JSON for a single pillar
     */
    private fun buildPillarJson(pillar: com.skul9x.battu.data.Pillar): JSONObject {
        return JSONObject().apply {
            put("stem", pillar.stem)
            put("stem_element", pillar.stemElement)
            put("branch", pillar.branch)
            put("branch_element", pillar.branchElement)
            put("nap_am", pillar.napAm?.name ?: "")
            put("life_stage", pillar.lifeStage ?: "")
            
            val hiddenStemsArray = JSONArray()
            pillar.hiddenStems.forEach { hs ->
                val hsObj = JSONObject()
                hsObj.put("stem", hs.stem)
                hsObj.put("percentage", hs.percentage)
                hsObj.put("type", hs.type.name)
                hiddenStemsArray.put(hsObj)
            }
            put("hidden_stems", hiddenStemsArray)
        }
    }

    private fun buildShenShaJson(list: List<com.skul9x.battu.data.ShenSha>): JSONArray {
        val array = JSONArray()
        list.forEach { ss ->
            array.put(JSONObject().apply {
                put("name", ss.name)
                put("pillar", ss.pillar)
                put("type", ss.type)
                put("description", ss.description)
            })
        }
        return array
    }

    private fun buildInteractionsJson(list: List<com.skul9x.battu.data.Interaction>): JSONArray {
        val array = JSONArray()
        list.forEach { inter ->
            array.put(JSONObject().apply {
                put("type", inter.typeName)
                put("pillars", JSONArray(inter.pillars))
                put("description", inter.description)
            })
        }
        return array
    }

    private fun buildLuckPillarsJson(list: List<com.skul9x.battu.data.LuckPillar>): JSONArray {
        val array = JSONArray()
        list.forEach { lp ->
            array.put(JSONObject().apply {
                put("age", "${lp.startAge}-${lp.endAge}")
                put("pillar", "${lp.stem} ${lp.branch}")
            })
        }
        return array
    }
    
    /**
     * Build JSON for Ten Gods
     */
    private fun buildTenGodsJson(tenGods: com.skul9x.battu.data.TenGods): JSONObject {
        return JSONObject().apply {
            put("year_stem", tenGods.yearStemGod)
            put("year_branch", tenGods.yearBranchGod)
            put("month_stem", tenGods.monthStemGod)
            put("month_branch", tenGods.monthBranchGod)
            put("day_stem", "Nhật Chủ")
            put("day_branch", tenGods.dayBranchGod)
            put("hour_stem", tenGods.hourStemGod)
            put("hour_branch", tenGods.hourBranchGod)
            
            // Explanation
            put("explanation", JSONObject().apply {
                put("Chính Ấn", "Người bảo hộ, học vấn, danh dự")
                put("Thiên Ấn", "Người bảo hộ gián tiếp, tài năng")
                put("Chính Quan", "Quyền lực chính thống, kỷ luật")
                put("Thất Sát", "Quyền lực phi chính thống, quyết đoán")
                put("Chính Tài", "Tài chính ổn định, vợ (nam)")
                put("Thiên Tài", "Tài chính linh hoạt, người yêu (nam)")
                put("Thực Thần", "Sáng tạo, biểu đạt, con cái")
                put("Thương Quan", "Tài năng, phản kháng, con cái")
                put("Kiếp Tài", "Bạn bè, đối thủ, anh em")
                put("Tỷ Kiên", "Bản thân, anh em, đối thủ")
            })
        }
    }
}
