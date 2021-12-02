fun main() {
    fun parseInput(input: String): Pair<String, Int> {
        val split = input.split(" ")
        return Pair(split[0], split[1].toInt())
    }

    fun part1(input: List<Pair<String, Int>>): Int {
        var x = 0
        var y = 0
        input
                .forEach { data ->
                    run {
                        when (data.first) {
                            "forward" -> x += data.second
                            "down" -> y += data.second
                            "up" -> y -= data.second
                        }
                    }
                }
        return x * y
    }

    fun part2(input: List<Pair<String, Int>>): Int {
        var x = 0
        var y = 0
        var aim = 0
        input
                .forEach { data ->
                    run {
                        when (data.first) {
                            "forward" -> {
                                x += data.second
                                y += data.second * aim
                            }
                            "down" -> aim += data.second
                            "up" -> aim -= data.second
                        }
                    }
                }
        return x * y
    }

    val testInput = readInput("02_test", ::parseInput)
    check(part1(testInput) == 150)
    check(part2(testInput) == 900)

    val input = readInput("02", ::parseInput)
    println(part1(input))
    println(part2(input))
}
