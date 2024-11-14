package com.bangkit.jajananapp_bintang.ui.screen.homepage

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bangkit.jajananapp_bintang.data.JajananRepository
import com.bangkit.jajananapp_bintang.data.model.OrderJajanan
import com.bangkit.jajananapp_bintang.ui.common.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor (
    private val repository: JajananRepository
) : ViewModel() {

    private val _uiState: MutableStateFlow<UiState<List<OrderJajanan>>> = MutableStateFlow(UiState.Loading)
    val uiState: StateFlow<UiState<List<OrderJajanan>>>
        get() = _uiState

    fun getAllMakanan() {
        viewModelScope.launch {
            repository.getsMakanan()
                .catch {
                    _uiState.value = UiState.Error(it.message.toString())
                }
                .collect{
                    _uiState.value = UiState.Success(it.sortedBy { it.makanan.nama })
                }
        }
    }

    private val _query = mutableStateOf("")
    val query: State<String> get() = _query

    fun searchMakanan(newQuery: String) {
        _query.value = newQuery
        viewModelScope.launch {
            repository.searchMakanan(_query.value)
                .catch {
                    _uiState.value = UiState.Error(it.message.toString())
                }
                .collect {
                    _uiState.value = UiState.Success(it.sortedBy { it.makanan.nama })
                }
        }
    }
}