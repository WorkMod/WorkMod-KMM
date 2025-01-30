package com.tamesys.workmode.common


inline fun <T> MutableList<T>.removeIf(predicate: (T) -> Boolean): MutableList<T> {
    val destination = mutableListOf<T>()
    for (element in this) if (!predicate(element)) destination.add(element)
    return destination
}