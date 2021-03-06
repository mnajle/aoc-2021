fun main() {

    fun part1(input: List<Int>): Int {
        return input
                .zipWithNext()
                .filter { pair -> pair.second > pair.first }
                .size
    }

    fun part2(input: List<Int>): Int {
        val groups = input
                .subList(0, input.size - 2)
                .indices
                .map { i -> input[i] + input[i + 1] + input[i + 2] }
        return part1(groups)
    }

    val testInput = readInput("01_test") { x -> x.toInt() }
    check(part1(testInput) == 7)
    check(part2(testInput) == 5)

    val input = readInput("01") { x -> x.toInt() }
    println(part1(input))
    println(part2(input))
}
