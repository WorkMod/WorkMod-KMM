package com.tamesys.workmode

import kotlinx.coroutines.CoroutineScope

expect open class BaseViewModel() {

    val scope: CoroutineScope
}