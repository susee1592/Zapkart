package com.susee.zap.ui.activity

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.susee.zap.data.model.LandingData
import com.susee.zap.data.repo.MainRepo
import com.susee.zap.utils.DataState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val repo: MainRepo) : ViewModel() {
    private val _landing = MutableStateFlow<List<LandingData>>(listOf())
    val landingData get() = _landing.asStateFlow()

    private val _isLoading = MutableStateFlow<Boolean>(false)
    val isLoading get() = _isLoading.asStateFlow()

    fun fetchLandingData() {
        viewModelScope.launch {
            viewModelScope.launch {
                repo.fetchLandingData().onEach {
                    when (it) {
                        is DataState.Loading -> {
                            _isLoading.value = true
                        }

                        is DataState.Success -> {
                            _landing.value = it.data
                            _isLoading.value = false
                        }

                        is DataState.Error -> {
                            _isLoading.value = false
                        }
                    }
                }.launchIn(viewModelScope)
            }
        }
    }
}