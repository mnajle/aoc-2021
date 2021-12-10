import java.util.*

val charSet = mapOf(
    Pair('(', ')'),
    Pair('[', ']'),
    Pair('{', '}'),
    Pair('<', '>'),
)
val scoresErrorDetection = mapOf(
    Pair(')', 3),
    Pair(']', 57),
    Pair('}', 1197),
    Pair('>', 25137),
)
val scoresAutocomplete = mapOf(
    Pair(')', 1),
    Pair(']', 2),
    Pair('}', 3),
    Pair('>', 4),
)

class Line(private val content: String) {
    val isValid: Boolean
    var invalidChar: Char? = null
    val nonClosed: List<Char>

    init {
        val chunkOpen = ArrayDeque<Char>()
        var valid = true
        val it = content.toCharArray().iterator()
        while (valid && it.hasNext()) {
            val currentChar = it.next()
            if (currentChar in charSet.keys) {
                chunkOpen.push(currentChar)
            } else {
                if (currentChar != charSet[chunkOpen.pop()]) {
                    this.invalidChar = currentChar
                    valid = false
                }
            }
        }
        this.isValid = valid
        this.nonClosed = chunkOpen.toList()
    }

    override fun toString() = this.content
}

fun main() {

    fun part1(input: List<Line>): Int {
        return input
            .filter { line -> !line.isValid }
            .map { line -> line.invalidChar }
            .sumOf { x -> scoresErrorDetection.getOrDefault(x, 0) }
    }

    fun part2(input: List<Line>): Long {
        val scores = input
            .filter { l -> l.isValid }
            .map { line ->
                var lineScore = (0).toLong()
                for (char in line.nonClosed) {
                    lineScore *= 5
                    lineScore += scoresAutocomplete[charSet[char]] ?: 0
                }
                lineScore
            }
        val middleIndex = scores.size / 2
        return scores.sorted()[middleIndex]
    }

    fun getInput(fileName: String) = readInput(fileName) { x -> Line(x) }
    val testInput = getInput("10_test")
    check(part1(testInput) == 26397)
    check(part2(testInput) == (288957).toLong())

    val input = getInput("10")
    println(part1(input))
    println(part2(input))
}
