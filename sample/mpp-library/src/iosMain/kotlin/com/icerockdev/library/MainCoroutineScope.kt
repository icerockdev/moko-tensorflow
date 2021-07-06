package com.icerockdev.library

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel

class MainCoroutineScope : CoroutineScope by CoroutineScope(Dispatchers.Main + SupervisorJob()) {
    fun close() {
        this.cancel()
    }
}
