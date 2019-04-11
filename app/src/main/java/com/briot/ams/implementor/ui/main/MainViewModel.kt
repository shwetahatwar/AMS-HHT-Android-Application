package com.briot.ams.implementor.ui.main

import android.arch.lifecycle.ViewModel
import com.briot.ams.implementor.SQLConnection

class MainViewModel : ViewModel() {
    // TODO: Implement the ViewModel
    var sqlConnection = SQLConnection()

    init {
        // Thread(Runnable { kotlin.run { sqlConnection.connect(); } }).start()
    }
}
