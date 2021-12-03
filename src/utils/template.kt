fun main() {

    fun part1(input: List<String>): Int {
        return 0
    }

    fun part2(input: List<String>): Int {
        return 1
    }

    val testInput = readInput("XX_test") { x -> x }
    check(part1(testInput) == 0)
    check(part2(testInput) == 1)

    val input = readInput("XX") { x -> x }
    println(part1(input))
    println(part2(input))
}
