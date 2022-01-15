fun main() {

    fun part1(input: List<String>): Int {
        return 0
    }

    fun part2(input: List<String>): Int {
        return 1
    }

    val testInput = readInput("XX_test") { x -> x }
    test(1, part1(testInput), 0)
    test(2, part2(testInput), 1)

    val input = readInput("XX") { x -> x }
    println(part1(input))
    println(part2(input))
}
