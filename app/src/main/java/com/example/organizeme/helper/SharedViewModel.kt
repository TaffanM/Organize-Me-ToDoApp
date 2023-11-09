package com.example.organizeme.helper

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SharedViewModel: ViewModel() {
    val bmiResult = MutableLiveData<String>()

}