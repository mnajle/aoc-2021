import kotlin.math.absoluteValue

fun main() {

    fun part1(start: Pair<Int, Int>, end: Pair<Int, Int>): Int {
        val limit = (end.second + 1).absoluteValue
        return limit * (limit + 1) / 2
    }

    fun part2(input: List<String>): Int {
        return 1
    }

    test(1, part1(Pair(20, -5), Pair(30, -10)), 45)
//    test(2, part2(testInput), 0)

//    val input = readInput("XX") { x -> x }
    println(part1(Pair(102, -90), Pair(157, -146)))
//    println(part2(input))
}
