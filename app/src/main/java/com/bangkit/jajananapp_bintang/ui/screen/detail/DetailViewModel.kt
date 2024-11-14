package com.bangkit.jajananapp_bintang.ui.screen.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bangkit.jajananapp_bintang.data.JajananRepository
import com.bangkit.jajananapp_bintang.data.model.Jajanan
import com.bangkit.jajananapp_bintang.data.model.OrderJajanan
import com.bangkit.jajananapp_bintang.ui.common.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val repository: JajananRepository
) : ViewModel() {
    private val _uiState: MutableStateFlow<UiState<OrderJajanan>> =
        MutableStateFlow(UiState.Loading)
    val uiState: StateFlow<UiState<OrderJajanan>>
        get() = _uiState

    fun init(id:Int) {
        getMakananbyId(id)
    }

    fun getMakananbyId(makananId: Int) {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            _uiState.value = UiState.Success(repository.getOrderMakananById(makananId))
        }
    }

    fun addToCart(makanan: Jajanan, count: Int) {
        viewModelScope.launch {
            repository.updateOrderMakanan(makanan.id, count)
        }
    }
}