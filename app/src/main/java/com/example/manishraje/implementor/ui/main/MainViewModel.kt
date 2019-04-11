package com.example.manishraje.implementor.ui.main

import android.arch.lifecycle.ViewModel
import com.example.manishraje.implementor.SQLConnection

class MainViewModel : ViewModel() {
    // TODO: Implement the ViewModel
    var sqlConnection = SQLConnection()

    init {
        // Thread(Runnable { kotlin.run { sqlConnection.connect(); } }).start()
    }
}
