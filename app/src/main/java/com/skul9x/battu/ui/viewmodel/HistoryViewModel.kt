package com.skul9x.battu.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.skul9x.battu.data.local.HistoryDatabase
import com.skul9x.battu.data.local.HistoryEntity
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

/**
 * ViewModel for History Screen
 */
class HistoryViewModel(application: Application) : AndroidViewModel(application) {
    
    // History Database access
    private val historyDao = HistoryDatabase.getDatabase(application).historyDao()
    
    // Observed history list
    val historyItems: StateFlow<List<HistoryEntity>> = historyDao.getAllHistory()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )
    
    /**
     * Delete a history item
     */
    fun deleteHistory(entity: HistoryEntity) {
        viewModelScope.launch {
            historyDao.delete(entity)
        }
    }
    
    /**
     * Clear all history
     */
    fun clearAllHistory() {
        viewModelScope.launch {
            historyDao.deleteAll()
        }
    }
}
