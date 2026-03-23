package com.skul9x.battu.ui.viewmodel

import android.app.Application
import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.skul9x.battu.ai.GeminiClient
import com.skul9x.battu.ai.PromptBuilder
import com.skul9x.battu.core.BaZiLogic
import com.skul9x.battu.data.BaZiData
import com.skul9x.battu.data.Gender
import com.skul9x.battu.data.UserInput
import com.skul9x.battu.data.local.HistoryDatabase
import com.skul9x.battu.data.local.HistoryEntity
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.io.InputStreamReader

/**
 * Shared JSON configuration for robustness and compatibility
 */
private val baZiJson = Json {
    ignoreUnknownKeys = true
    isLenient = true
    encodeDefaults = true
    coerceInputValues = true
}

// DataStore extension
private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "battu_settings")

/**
 * ViewModel for BatTu application
 * Manages user input state and BaZi calculation
 */
class BatTuViewModel(application: Application) : AndroidViewModel(application) {
    
    // Load solar-term.json from assets
    private val solarTermsJson: String by lazy {
        application.assets.open("solar-term.json").use { inputStream ->
            InputStreamReader(inputStream).readText()
        }
    }
    
    // BaZi calculation engine
    private val baZiLogic: BaZiLogic by lazy {
        BaZiLogic(solarTermsJson)
    }
    
    // History Database
    private val historyDao by lazy {
        HistoryDatabase.getDatabase(application).historyDao()
    }
    
    // UI State for Input Screen
    private val _inputState = MutableStateFlow(InputScreenState())
    val inputState: StateFlow<InputScreenState> = _inputState.asStateFlow()
    
    // Calculated BaZi Data
    private val _baZiData = MutableStateFlow<BaZiData?>(null)
    val baZiData: StateFlow<BaZiData?> = _baZiData.asStateFlow()
    
    // Loading state
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()
    
    // Error state
    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()
    
    // AI Integration State
    private val _isLoadingAI = MutableStateFlow(false)
    val isLoadingAI: StateFlow<Boolean> = _isLoadingAI.asStateFlow()
    
    private val _aiResult = MutableStateFlow("")
    val aiResult: StateFlow<String> = _aiResult.asStateFlow()
    
    private val _aiError = MutableStateFlow<String?>(null)
    val aiError: StateFlow<String?> = _aiError.asStateFlow()
    
    // Settings State
    private val _apiKeys = MutableStateFlow<List<String>>(emptyList())
    val apiKeys: StateFlow<List<String>> = _apiKeys.asStateFlow()
    
    private val _selectedModel = MutableStateFlow("gemini-2.5-flash")
    val selectedModel: StateFlow<String> = _selectedModel.asStateFlow()
    
    // DataStore keys
    private val API_KEYS_KEY = stringPreferencesKey("api_keys")
    private val SELECTED_MODEL_KEY = stringPreferencesKey("selected_model")
    
    // GeminiClient instance
    private var geminiClient: GeminiClient? = null
    
    init {
        // Load settings from DataStore
        loadSettings()
    }
    
    /**
     * Update user name
     */
    fun updateName(name: String) {
        _inputState.value = _inputState.value.copy(name = name)
    }
    
    /**
     * Update gender
     */
    fun updateGender(gender: Gender) {
        _inputState.value = _inputState.value.copy(gender = gender)
    }
    
    /**
     * Update birth date
     */
    fun updateBirthDate(year: Int, month: Int, day: Int) {
        _inputState.value = _inputState.value.copy(
            birthYear = year,
            birthMonth = month,
            birthDay = day
        )
    }
    
    /**
     * Update birth hour (0-23)
     */
    fun updateBirthHour(hour: Int) {
        _inputState.value = _inputState.value.copy(birthHour = hour)
    }
    
    /**
     * Toggle between Solar and Lunar calendar
     */
    fun toggleCalendarType() {
        _inputState.value = _inputState.value.copy(
            isLunar = !_inputState.value.isLunar
        )
    }
    
    /**
     * Update longitude
     */
    fun updateLongitude(longitude: Double) {
        _inputState.value = _inputState.value.copy(longitude = longitude)
    }
    
    /**
     * Toggle day boundary at 23:00
     */
    fun toggleDayBoundary() {
        _inputState.value = _inputState.value.copy(
            dayBoundaryAt23 = !_inputState.value.dayBoundaryAt23
        )
    }
    
    /**
     * Validate input and calculate BaZi chart
     * @return true if validation passed and calculation started
     */
    fun calculateBaZi(): Boolean {
        val state = _inputState.value
        
        // Validation
        val validationError = validateInput(state)
        if (validationError != null) {
            _error.value = validationError
            return false
        }
        
        // Clear previous error
        _error.value = null
        _isLoading.value = true
        
        viewModelScope.launch {
            try {
                val userInput = UserInput(
                    name = state.name,
                    solarDay = state.birthDay,
                    solarMonth = state.birthMonth,
                    solarYear = state.birthYear,
                    hour = state.birthHour,
                    gender = state.gender,
                    isLunar = state.isLunar,
                    longitude = state.longitude,
                    dayBoundaryAt23 = state.dayBoundaryAt23
                )
                
                // Calculate BaZi using core logic
                val result = baZiLogic.calculateBaZi(userInput)
                _baZiData.value = result
                
            } catch (e: Exception) {
                _error.value = "Lỗi tính toán: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
        
        return true
    }
    
    /**
     * Validate user input
     * @return error message if validation fails, null if valid
     */
    private fun validateInput(state: InputScreenState): String? {
        // Name validation
        if (state.name.isBlank()) {
            return "Vui lòng nhập họ tên"
        }
        
        // Year validation (1900-2049 supported by lunar data)
        if (state.birthYear < 1900 || state.birthYear > 2049) {
            return "Năm sinh phải từ 1900 đến 2049"
        }
        
        // Month validation
        if (state.birthMonth < 1 || state.birthMonth > 12) {
            return "Tháng sinh không hợp lệ"
        }
        
        // Day validation (basic check)
        if (state.birthDay < 1 || state.birthDay > 31) {
            return "Ngày sinh không hợp lệ"
        }
        
        // Hour validation
        if (state.birthHour < 0 || state.birthHour > 23) {
            return "Giờ sinh không hợp lệ"
        }
        
        // Longitude validation
        if (state.longitude < -180.0 || state.longitude > 180.0) {
            return "Kinh độ phải từ -180 đến 180"
        }
        
        return null
    }
    
    /**
     * Clear error message
     */
    fun clearError() {
        _error.value = null
    }
    
    /**
     * Generates a JSON prompt from BaZiData to send to AI
     * @return JSON string or empty if no data
     */
    fun generateJsonPrompt(): String {
        val data = _baZiData.value ?: return ""
        val state = _inputState.value
        return PromptBuilder.buildJsonPrompt(state.name, state.gender, data)
    }
    
    /**
     * Reset all state
     */
    fun reset() {
        _inputState.value = InputScreenState()
        _baZiData.value = null
        _error.value = null
        _isLoading.value = false
    }
    
    // ==================== AI Integration ====================
    
    /**
     * Generate AI interpretation using GeminiClient
     */
    fun generateAIInterpretation() {
        val data = _baZiData.value
        if (data == null) {
            _aiError.value = "Chưa có dữ liệu Bát Tự để phân tích"
            return
        }
        
        if (_apiKeys.value.isEmpty()) {
            _aiError.value = "Vui lòng thêm API Key trong Cài đặt"
            return
        }
        
        // Initialize GeminiClient if needed
        if (geminiClient == null) {
            geminiClient = GeminiClient(
                apiKeys = _apiKeys.value,
                modelName = _selectedModel.value
            )
        }
        
        _isLoadingAI.value = true
        _aiResult.value = ""
        _aiError.value = null
        
        viewModelScope.launch {
            try {
                // Create UserInput from current state
                val userInput = UserInput(
                    name = _inputState.value.name,
                    solarDay = _inputState.value.birthDay,
                    solarMonth = _inputState.value.birthMonth,
                    solarYear = _inputState.value.birthYear,
                    hour = _inputState.value.birthHour,
                    gender = _inputState.value.gender,
                    isLunar = _inputState.value.isLunar,
                    longitude = 105.8,
                    dayBoundaryAt23 = true
                )
                
                // Stream AI response
                geminiClient!!.generateReadingStream(data, userInput)
                    .catch { e ->
                        _aiError.value = "Lỗi AI: ${e.message}"
                        _isLoadingAI.value = false
                    }
                    .collect { chunk ->
                        _aiResult.value += chunk
                    }
                
                _isLoadingAI.value = false
                
                // Save to history after AI result is complete
                saveToHistory()
                
            } catch (e: Exception) {
                _aiError.value = "Lỗi: ${e.message}"
                _isLoadingAI.value = false
            }
        }
    }
    
    /**
     * Retry AI generation
     */
    fun retryAIGeneration() {
        generateAIInterpretation()
    }
    
    /**
     * Clear AI result
     */
    fun clearAIResult() {
        _aiResult.value = ""
        _aiError.value = null
    }
    
    // ==================== Settings Management ====================
    
    /**
     * Load settings from DataStore
     */
    private fun loadSettings() {
        viewModelScope.launch {
            getApplication<Application>().dataStore.data.collect { preferences ->
                // Load API keys (stored as comma-separated string)
                val keysString = preferences[API_KEYS_KEY] ?: ""
                _apiKeys.value = if (keysString.isNotBlank()) {
                    keysString.split(",")
                } else {
                    emptyList()
                }
                
                // Load selected model
                _selectedModel.value = preferences[SELECTED_MODEL_KEY] ?: "gemini-2.5-flash"
                
                // Update GeminiClient if keys changed
                if (_apiKeys.value.isNotEmpty()) {
                    geminiClient = GeminiClient(
                        apiKeys = _apiKeys.value,
                        modelName = _selectedModel.value
                    )
                }
            }
        }
    }
    
    /**
     * Add API key
     */
    fun addApiKey(key: String) {
        val trimmedKey = key.trim()
        if (trimmedKey.isBlank() || _apiKeys.value.contains(trimmedKey)) {
            return
        }
        
        val newKeys = _apiKeys.value + trimmedKey
        _apiKeys.value = newKeys
        
        // Save to DataStore
        viewModelScope.launch {
            getApplication<Application>().dataStore.edit { preferences ->
                preferences[API_KEYS_KEY] = newKeys.joinToString(",")
            }
        }
        
        // Update GeminiClient
        geminiClient = GeminiClient(
            apiKeys = newKeys,
            modelName = _selectedModel.value
        )
    }
    
    /**
     * Remove API key by index
     */
    fun removeApiKey(index: Int) {
        if (index < 0 || index >= _apiKeys.value.size) return
        
        val newKeys = _apiKeys.value.toMutableList().apply { removeAt(index) }
        _apiKeys.value = newKeys
        
        // Save to DataStore
        viewModelScope.launch {
            getApplication<Application>().dataStore.edit { preferences ->
                preferences[API_KEYS_KEY] = newKeys.joinToString(",")
            }
        }
        
        // Update GeminiClient
        if (newKeys.isNotEmpty()) {
            geminiClient = GeminiClient(
                apiKeys = newKeys,
                modelName = _selectedModel.value
            )
        } else {
            geminiClient = null
        }
    }
    
    /**
     * Update selected model
     */
    fun updateSelectedModel(model: String) {
        _selectedModel.value = model
        
        // Save to DataStore
        viewModelScope.launch {
            getApplication<Application>().dataStore.edit { preferences ->
                preferences[SELECTED_MODEL_KEY] = model
            }
        }
        
        // Update GeminiClient
        if (_apiKeys.value.isNotEmpty()) {
            geminiClient = GeminiClient(
                apiKeys = _apiKeys.value,
                modelName = model
            )
        }
    }
    
    /**
     * Test API key connection
     */
    fun testApiKey(key: String, model: String, onResult: (String) -> Unit) {
        viewModelScope.launch {
            try {
                val result = GeminiClient.testConnection(key, model)
                if (result.isSuccess) {
                    onResult(result.getOrNull() ?: "✅ Kết nối thành công")
                } else {
                    onResult("❌ Lỗi: ${result.exceptionOrNull()?.message}")
                }
            } catch (e: Exception) {
                onResult("❌ Lỗi: ${e.message}")
            }
        }
    }

    /**
     * Save current BaZi data and AI result to history
     */
    fun saveToHistory() {
        val data = _baZiData.value ?: return
        val state = _inputState.value
        val aiResultText = _aiResult.value.ifBlank { null }
        
        viewModelScope.launch {
            try {
                val entity = HistoryEntity(
                    name = state.name,
                    gender = state.gender.name,
                    birthDate = "${state.birthDay}/${state.birthMonth}/${state.birthYear}",
                    birthHour = state.birthHour,
                    chartJson = baZiJson.encodeToString(data),
                    promptJson = generateJsonPrompt(),
                    aiResult = aiResultText
                )
                historyDao.insert(entity)
            } catch (e: Exception) {
                _error.value = "Lỗi lưu lịch sử: ${e.message}"
            }
        }
    }

    /**
     * Load a saved BaZi chart from history
     */
     fun loadFromHistory(entity: HistoryEntity) {
        try {
            val data = baZiJson.decodeFromString<BaZiData>(entity.chartJson)
            _baZiData.value = data
            _aiResult.value = entity.aiResult ?: ""
            
            // Reconstruct input state
            val dateParts = entity.birthDate.split("/")
            if (dateParts.size == 3) {
                // Safely parse gender to avoid IllegalArgumentException
                val gender = try {
                    Gender.valueOf(entity.gender)
                } catch (e: Exception) {
                    Gender.NAM // Fallback
                }

                _inputState.value = _inputState.value.copy(
                    name = entity.name,
                    gender = gender,
                    birthDay = dateParts[0].toIntOrNull() ?: 1,
                    birthMonth = dateParts[1].toIntOrNull() ?: 1,
                    birthYear = dateParts[2].toIntOrNull() ?: 2000,
                    birthHour = entity.birthHour
                )
            }
            
            _error.value = null
            _aiError.value = null
        } catch (e: Exception) {
            _error.value = "Lỗi tải lịch sử: Dữ liệu cũ không tương thích (${e.message})"
        }
    }
}

/**
 * UI State for Input Screen
 */
data class InputScreenState(
    val name: String = "",
    val gender: Gender = Gender.NAM,
    val birthYear: Int = 2000,
    val birthMonth: Int = 1,
    val birthDay: Int = 1,
    val birthHour: Int = 0,
    val isLunar: Boolean = false,
    val longitude: Double = 105.8, // Default: Hanoi
    val dayBoundaryAt23: Boolean = true
)
