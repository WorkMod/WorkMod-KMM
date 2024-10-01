package app.workmod.workmod_kmm.common

import java.io.File

actual open class FileWriter actual constructor(val dirPath: String) {

    private var filePath: String = ""
    private var file: File? = null

    actual fun setFile(fileName: String) {

        val dirFile = File(dirPath + File.separator + "cv")
        if (!dirFile.exists()) {
            dirFile.mkdirs()
        }
        //this.filePath = dirPath + File.separator + "cv" + File.separator + fileName
        file = File(dirFile.path, fileName)

        if (!file!!.exists()) {
            file!!.createNewFile();
        }
        this.filePath = file!!.path
    }

    actual fun getFilePath(): String {
        return this.filePath
    }

    actual fun writeBytes(bytes: ByteArray) {
        file?.appendBytes(bytes)
    }

    actual fun close() {}

}