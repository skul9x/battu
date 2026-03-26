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
        val currentYear = java.util.Calendar.getInstance().get(java.util.Calendar.YEAR)
        val currentAge = currentYear - baZiData.birthYear
        
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
        json.put("chart_data", buildChartData(name, gender, baZiData, currentAge))
        
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
                "Đánh giá Nhật Chủ vượng/suy dựa trên Ngũ Hành balance",
                "Xem xét kỹ các tổ hợp Bán Tam Hợp và Củng Hợp ảnh hưởng tới Ngũ Hành",
                "Tuổi khởi Đại Vận tính chính xác tới từng tháng/ngày, phân mốc giao vận chuẩn xác",
                "Xét Tuần Không (Không Vong): Chi bị Tuần Không sẽ giảm lực lượng",
                "Đánh giá Thai Nguyên và Mệnh Cung để bổ trợ luận đoán",
                "Kiểm tra Phục Ngâm (trụ trùng) ảnh hưởng tới ổn định lá số",
                "Sử dụng Lưu Niên của Đại Vận hiện tại để dự báo cụ thể từng năm"
            )))
            
            put("must_not", JSONArray(listOf(
                "Không suy đoán khi thiếu dữ liệu",
                "Không dùng câu chung chung (VD: 'số giàu', 'số khổ') mà không chỉ rõ căn cứ",
                "Không khẳng định tuyệt đối - phải dùng ngôn ngữ xác suất ('thường', 'có xu hướng', 'nếu...')"
            )))
            
            put("evidence_format", "(Căn cứ: [Can/Chi] + [Thập Thần] + [Ngũ Hành] + [Quan hệ Sinh Khắc])")
        }
    }
    
    private fun buildAnalysisPipeline(): JSONArray {
        return JSONArray(listOf(
            "Tầng Căn Bản: Xác định Nhật Chủ (Day Master) vượng/suy (BẮT BUỘC xét dựa trên Vòng Trường Sinh tại Lệnh Tháng - chi Tháng)",
            "Tầng Biến Hóa: Đánh giá các tổ hợp Tương Hợp, Bán Tam Hợp, Củng Hợp làm thay đổi thế trận Ngũ Hành (lực lượng ngầm)",
            "Tầng Thời Gian: Sử dụng các mốc khởi Đại Vận chính xác (start_age, start_months, start_days) để đưa ra lời khuyên khớp thời điểm",
            "Tầng Tuần Không: Quan sát các chi bị Không Vong (đã được trừ vào điểm số Ngũ Hành 50%); xét ảnh hưởng của Không Vong tới các tầng tương tác khác",
            "Tầng Trụ Phụ: Thai Nguyên (gốc rễ) + Mệnh Cung (hậu vận) bổ trợ luận đoán",
            "Bước 4: Tìm Dụng Thần / Hỉ Thần / Kỵ Thần dựa trên 3 tầng trên",
            "Bước 5: Phân tích Thập Thần → Tính cách, năng lực",
            "Bước 6: Phân tích sự nghiệp, tài lộc, tình duyên, sức khỏe",
            "Bước 7: Tổng hợp và lời khuyên"
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
    private fun buildChartData(name: String, gender: Gender, data: BaZiData, currentAge: Int): JSONObject {
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
                put("note", "Điểm số Ngũ Hành đã được máy tính tối ưu: ĐÃ BAO GỒM hệ số Lệnh Tháng (x2.0) và hình phạt Tuần Không (x0.5). Chú ý sự thay đổi Lực lượng ngầm do Tam Hợp/Bán Tam Hợp tạo ra (xem INTERACTIONS).")
            })
            
            // Shen Sha (Thần Sát)
            put("shen_sha", buildShenShaJson(data.shenShaList))
            
            // Interactions (Hợp/Xung/Hình/Hại)
            put("interactions", buildInteractionsJson(data.interactions))
            
            // Luck Pillars (Đại Vận)
            put("luck_pillars", buildLuckPillarsJson(data.luckPillars, currentAge))
            
            // Xun Kong (Tuần Không / Không Vong)
            put("xun_kong", data.xunKong?.let { xk ->
                JSONObject().apply {
                    put("year_void", JSONArray(xk.yearVoid))
                    put("day_void", JSONArray(xk.dayVoid))
                    put("note", "Các Chi rơi vào Tuần Không sẽ bị giảm lực lượng (đã trừ 50% vào element_balance), ảnh hưởng tới khả năng tương tác và xung lực của Chi đó.")
                }
            } ?: JSONObject())

            // Auxiliary Pillars (Trụ Phụ)
            put("auxiliary_pillars", JSONObject().apply {
                data.fetalOrigin?.let {
                    put("fetal_origin", JSONObject().apply {
                        put("name", "Thai Nguyên")
                        put("stem", it.stem)
                        put("branch", it.branch)
                        put("description", it.description)
                    })
                }
                data.lifePalace?.let {
                    put("life_palace", JSONObject().apply {
                        put("name", "Mệnh Cung")
                        put("stem", it.stem)
                        put("branch", it.branch)
                        put("description", it.description)
                    })
                }
            })
            
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
                hsObj.put("ten_god", hs.tenGod ?: "")
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

    private fun buildLuckPillarsJson(list: List<com.skul9x.battu.data.LuckPillar>, currentAge: Int): JSONArray {
        val array = JSONArray()
        list.forEach { lp ->
            val isCurrent = currentAge >= lp.startAge && currentAge <= lp.endAge
            
            val obj = JSONObject().apply {
                put("start_age", lp.startAge)
                put("start_months", lp.startMonths)
                put("start_days", lp.startDays)
                put("display_age", lp.displayAge)
                put("age_range", "${lp.startAge}-${lp.endAge}")
                put("stem", lp.stem)
                put("branch", lp.branch)
                put("is_current", isCurrent)
                
                // Only include annual pillars for current luck period to save tokens
                if (isCurrent && lp.annualPillars.isNotEmpty()) {
                    val annualArray = JSONArray()
                    lp.annualPillars.forEach { ap ->
                        annualArray.put(JSONObject().apply {
                            put("year", ap.year)
                            put("stem", ap.stem)
                            put("branch", ap.branch)
                            put("age", ap.ageDisplay)
                        })
                    }
                    put("annual_pillars", annualArray)
                }
                
                put("note", "Đại Vận bắt đầu chính xác từ: ${lp.displayAge}. Lưu ý sự tác động của Can/Chi Đại Vận lên Nhật Chủ và các trụ khác.")
            }
            array.put(obj)
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
