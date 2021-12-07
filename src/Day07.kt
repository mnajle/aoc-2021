import kotlin.math.absoluteValue

fun main() {

    fun part1(input: List<Int>): Int {
        val half = input.sorted().size / 2
        val median = input.sorted()[half]
        return input
            .map { x -> x - median }
            .sumOf { x -> x.absoluteValue }
    }

    fun part2(input: List<Int>): Int {
        val average = input.average().toInt()
        val candidates = average -1.. average + 1
        val costs = candidates.map { candidate ->
            input
                .map { x -> x - candidate }
                .map { x -> x.absoluteValue }
                .sumOf { x -> (1..x).sum() }
        }
        val minCost = costs.minOf { it }
        val min = candidates.toList()[costs.indexOf(minCost)]

        return input
            .map { x -> x - min }
            .map { x -> x.absoluteValue }
            .sumOf { x -> (1..x).sum() }
    }

    fun parse(input: String) = input.split(",").map { it.toInt() }

    val testInput = parse(readInput("07_test")[0])
    check(part1(testInput) == 37)
    check(part2(testInput) == 168)

    val input = parse(readInput("07")[0])
    println(part1(input))
    println(part2(input))
}
