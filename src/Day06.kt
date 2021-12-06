fun initFishCount(input: List<Int>): Array<Long> {
    return arrayOf(
        input.count { it == 0 }.toLong(),
        input.count { it == 1 }.toLong(),
        input.count { it == 2 }.toLong(),
        input.count { it == 3 }.toLong(),
        input.count { it == 4 }.toLong(),
        input.count { it == 5 }.toLong(),
        input.count { it == 6 }.toLong(),
        input.count { it == 7 }.toLong(),
        input.count { it == 8 }.toLong(),
    )
}

fun main() {

    fun part2(input: List<Int>, days: Int): Long {
        val fishCount = initFishCount(input)
        for (i in 1..days) {
            val mustRestart = fishCount[0]
            fishCount[0] = fishCount[1]
            fishCount[1] = fishCount[2]
            fishCount[2] = fishCount[3]
            fishCount[3] = fishCount[4]
            fishCount[4] = fishCount[5]
            fishCount[5] = fishCount[6]
            fishCount[6] = fishCount[7] + mustRestart
            fishCount[7] = fishCount[8]
            fishCount[8] = mustRestart
        }
        return fishCount.reduce { x, y -> x + y }
    }

    fun part1(input: List<Int>): Long {
        return part2(input, 80)
    }

    fun parse(input: String) = input.split(",").map { it.toInt() }
    val testInput = parse(readInput("06_test")[0])
    check(part1(testInput) == (5934).toLong())
    check(part2(testInput, 256) == 26984457539)

    val input = parse(readInput("06")[0])
    println(part1(input))
    println(part2(input, 256))
}
