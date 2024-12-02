package com.tamesys.workmode.common

import kotlinx.cinterop.ExperimentalForeignApi
import platform.Foundation.NSDocumentDirectory
import platform.Foundation.NSFileManager
import platform.Foundation.NSURL
import platform.Foundation.NSUserDomainMask

actual open class FileWriter actual constructor(dirPath: String) {

    private var filePath: String = ""

    @OptIn(ExperimentalForeignApi::class)
    actual fun setFile(fileName: String) {
        val documentDirectory: NSURL? = NSFileManager.defaultManager.URLForDirectory(
            directory = NSDocumentDirectory,
            inDomain = NSUserDomainMask,
            appropriateForURL = null,
            create = false,
            error = null,
        )
        filePath =  requireNotNull(documentDirectory).path + "/$dataStoreFileName"
    }

    actual fun getFilePath(): String {
        return filePath
    }

    actual fun writeBytes(bytes: ByteArray) {

    }

    actual fun close() {
    }



}