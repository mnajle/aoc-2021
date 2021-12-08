fun Boolean.toInt() = if (this) 1 else 0
operator fun String.minus(string: String) = this.toCharArray().filter { !string.contains(it) }.joinToString("")

class Thingy(string: String) {
    var input: List<String>
    var output: List<String>

    init {
        fun parse(sequence: String) = sequence.trim().split(" ").map { it.toCharArray().sorted().joinToString("") }
        this.input = parse(string.split("|")[0])
        this.output = parse(string.split("|")[1])
    }
}

fun contains(string: String, code: String) = string.split("").containsAll(code.split(""))
fun diff(a: String, b: String) = a.split("") - b.split("").toSet()

fun main() {

    fun part1(entries: List<Thingy>): Int {
        val lengths: List<Int> = listOf(2, 3, 4, 7)
        return entries.sumOf { entry -> entry.output.sumOf { digit -> lengths.contains(digit.length).toInt() } }
    }

    fun part2(entries: List<Thingy>): Int {
        var sum = 0
        for (entry in entries) {
            val codes = arrayOf("", "", "", "", "", "", "", "", "", "")
            codes[1] = entry.input.first { it.length == 2 }
            codes[7] = entry.input.first { it.length == 3 }
            codes[4] = entry.input.first { it.length == 4 }
            codes[8] = entry.input.first { it.length == 7 }
            codes[3] = entry.input.first { it.length == 5 && contains(it, codes[7]) }
            codes[6] = entry.input.first { it.length == 6 && !contains(it, codes[7]) }
            codes[9] = entry.input.first { it.length == 6 && contains(it, codes[4]) }
            codes[0] = entry.input.first { it.length == 6 && it !in listOf(codes[6], codes[9]) }

            val a = diff(codes[7], codes[1])[0]
            val g = diff(codes[9], codes[4]).first { x -> x != a }
            val d = diff(codes[3] - a - g, codes[7])[0]
            codes[5] = entry.input
                .filter { it.length == 5 && it != codes[3] }
                .first { code -> diff(code - a -g - d, codes[4]).isEmpty() }
            codes[2] = (entry.input - codes)[0]

            val output = entry.output
                .map { codes.indexOf(it) }
                .joinToString("").toInt()
            sum += output
        }
        return sum
    }

    val testInput = readInput("08_test") { x -> Thingy(x) }
    check(part1(testInput) == 26)
    check(part2(testInput) == 61229)

    val input = readInput("08") { x -> Thingy(x) }
    println(part1(input))
    println(part2(input))
}
