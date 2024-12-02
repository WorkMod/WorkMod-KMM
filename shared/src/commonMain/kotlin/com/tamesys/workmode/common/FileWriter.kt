package com.tamesys.workmode.common

expect open class FileWriter(dirPath: String) {
    fun setFile(fileName: String)
    fun getFilePath(): String
    fun writeBytes(bytes: ByteArray)
    fun close()
}