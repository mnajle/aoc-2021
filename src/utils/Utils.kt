import java.io.File
import java.math.BigInteger
import java.security.MessageDigest

/**
 * Reads lines from the given input txt file.
 */
fun readInput(name: String) = File("src/input", "$name.txt").readLines()
fun <T> readInput(name: String, function: (line: String) -> T) = readInput(name).map { function.invoke(it) }


/**
 * Converts string to md5 hash.
 */
fun String.md5(): String = BigInteger(1, MessageDigest.getInstance("MD5").digest(toByteArray())).toString(16)

fun <T> test(item: Any, actual: T, expected: T) {
    check(actual == expected) { "Check $item Failed. Expected $expected got $actual" }
}