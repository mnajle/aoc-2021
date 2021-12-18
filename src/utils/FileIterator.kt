package utils

import java.io.File
import java.io.InputStreamReader

open class FileIterator(file: File, chunkSize: Int) : Iterator<String> {
    protected val streamReader: InputStreamReader
    protected var ended = false
    protected val nullChar: Int = 0
    protected var lastChar: Int = 0
    protected val chunkSize: Int

    init {
        this.streamReader = file.reader()
        this.chunkSize = chunkSize
    }

    override fun hasNext(): Boolean {
        return !ended
    }

    override fun next(): String {
        var count = 0
        var result = ""
        var currentChar = if (lastChar == nullChar) this.streamReader.read() else lastChar
        while (currentChar != -1 && count < chunkSize) {
            result += currentChar.toChar()
            count++
            currentChar = this.streamReader.read()
            lastChar = currentChar
        }
        if (currentChar == -1) {
            this.ended = true
        }
        return result
    }
}
