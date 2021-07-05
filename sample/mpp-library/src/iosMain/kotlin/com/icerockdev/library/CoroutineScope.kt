package com.icerockdev.library

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob

fun createCoroutineScope() {
    CoroutineScope(Dispatchers.Main + SupervisorJob())
}
