package com.sixteenhalves.orangeadmin.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sixteenhalves.orangeadmin.domain.User
import com.sixteenhalves.orangeadmin.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel
    @Inject
    constructor(
        private val userRepository: UserRepository,
    ) : ViewModel() {
        private val _user = MutableLiveData<User?>()
        val user = _user as LiveData<User?>

        private val _branchesExist = MutableStateFlow<Boolean>(false)
        val branchesExist = _branchesExist.asStateFlow()

        init {
            viewModelScope.launch {
                try {
                    val result = userRepository.getUser()
                    _user.value = result.getOrNull()
                } catch (e: Exception) {
                    Log.e("MAIN VIEW MODEL", "Error: ${e.message}")
                }
            }
        }
    }
