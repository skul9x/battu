package com.skul9x.battu.ai

import com.google.ai.client.generativeai.GenerativeModel
import com.google.ai.client.generativeai.type.GenerateContentResponse
import com.skul9x.battu.data.BaZiData
import com.skul9x.battu.data.UserInput
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

/**
 * GeminiClient - AI client for Bát Tự interpretation
 * 
 * Features:
 * - Multi API key rotation
 * - Model priority fallback (gemini-3-flash → gemini-2.5-flash → ...)
 * - Streaming response with Flow<String>
 * - Smart error handling (quota, network, invalid key)
 * 
 * Cloned from TuViAndroid-BatTu/GeminiClient.kt and adapted for Bát Tự
 */
class GeminiClient(
    private var apiKeys: List<String>,
    private var modelName: String = "gemini-3-flash-preview"
) {
    
    companion object {
        /**
         * Model priority list: Best reasoning → Fastest fallback
         * "Hết nạc mới vạc tới xương" - Flash models only
         */
        val MODEL_PRIORITY = listOf(
            "gemini-3-flash-preview",
            "gemini-2.5-flash",
            "gemini-2.5-flash-lite",
            "gemini-flash-latest",
            "gemini-flash-lite-latest"
        )

        /**
         * Test connection with a specific model
         * @return Result.success with message or Result.failure with exception
         */
        suspend fun testConnection(apiKey: String, modelName: String): Result<String> {
            return try {
                val model = GenerativeModel(
                    modelName = modelName,
                    apiKey = apiKey
                )
                val response = model.generateContent("Xin chào")
                if (response.text != null) {
                    Result.success("Kết nối thành công với $modelName!")
                } else {
                    Result.failure(Exception("Không nhận được phản hồi từ Google."))
                }
            } catch (e: Exception) {
                Result.failure(e)
            }
        }

        /**
         * Detect if an exception is a quota/rate limit error
         */
        fun isQuotaError(exception: Exception): Boolean {
            val msg = exception.message?.lowercase() ?: ""
            return msg.contains("429") ||
                   msg.contains("quota") ||
                   msg.contains("rate limit") ||
                   msg.contains("resource exhausted") ||
                   msg.contains("too many requests")
        }
    }

    // Secondary constructor for single API key (backward compatibility)
    constructor(apiKey: String, modelName: String = "gemini-3-flash-preview") : this(
        apiKeys = if (apiKey.isNotBlank()) listOf(apiKey) else emptyList(),
        modelName = modelName
    )

    /**
     * Update configuration with multiple API keys
     */
    fun updateConfig(apiKeys: List<String>, modelName: String) {
        this.apiKeys = apiKeys
        this.modelName = modelName
    }

    /**
     * Update configuration with single API key
     */
    fun updateConfig(apiKey: String, modelName: String) {
        this.apiKeys = if (apiKey.isNotBlank()) listOf(apiKey) else emptyList()
        this.modelName = modelName
    }

    /**
     * Get the prompt for copying to clipboard (for user to paste elsewhere)
     */
    fun getPromptForCopy(data: BaZiData, userInput: UserInput): String {
        return PromptBuilder.buildJsonPrompt(userInput.name, userInput.gender, data)
    }

    /**
     * Get the currently configured model name
     */
    fun getModelName(): String = modelName

    /**
     * Generate AI reading with smart fallback:
     * 1. Try all models (priority order) with current API key
     * 2. If all models fail, rotate to next API key and retry
     * 3. If all keys and models exhausted, show "Hết Quota API"
     * 
     * @param data BaZiData calculated from user input
     * @param userInput Original user input (name, gender, birth date/time)
     * @return Flow<String> streaming AI response text
     */
    fun generateReadingStream(data: BaZiData, userInput: UserInput): Flow<String> = flow {
        if (apiKeys.isEmpty() || apiKeys.all { it.isBlank() }) {
            emit("❌ Lỗi: Chưa có API Key. Vui lòng vào Cài đặt để nhập API Key.")
            return@flow
        }

        val prompt = PromptBuilder.buildJsonPrompt(userInput.name, userInput.gender, data)
        
        // Build the model try order: selected model first, then priority list
        val modelsToTry = mutableListOf(modelName)
        modelsToTry.addAll(MODEL_PRIORITY.filter { it != modelName })

        var success = false
        var lastError: Exception? = null
        var keyIndex = 0

        // Outer loop: API Keys
        while (keyIndex < apiKeys.size && !success) {
            val currentKey = apiKeys[keyIndex]
            
            // Silent switch - no message needed

            // Inner loop: Models (priority order)
            for (modelToUse in modelsToTry) {
                try {
                    val model = GenerativeModel(
                        modelName = modelToUse,
                        apiKey = currentKey
                    )

                    // Silent switch - no message needed (fallback is invisible to user)

                    val responseFlow: Flow<GenerateContentResponse> = model.generateContentStream(prompt)
                    
                    responseFlow.collect { chunk ->
                        chunk.text?.let { emit(it) }
                    }
                    
                    success = true
                    break // Success, exit model loop
                    
                } catch (e: Exception) {
                    lastError = e
                    
                    if (isQuotaError(e)) {
                        // Quota error: Try next model
                        continue
                    } else {
                        // Other error (e.g., model not found): Also try next
                        continue
                    }
                }
            }

            if (!success) {
                // All models failed for this key, try next key
                keyIndex++
            }
        }

        if (!success) {
            emit("\n\n❌ **Hết Quota API**\n\nĐã thử tất cả API Keys và Models nhưng không thành công.\nVui lòng thêm API Key mới trong Cài đặt.\n\nLỗi cuối: ${lastError?.message ?: "Không xác định"}")
        }
    }
}
