import utils.Matrix

fun count(matrix: Matrix<Int>, x: Int, y: Int, checked: MutableList<Pair<Int, Int>>): Int {
    if (!matrix.checkIndex(x, y) || matrix[x, y] == 9 || checked.contains(Pair(x, y))) {
        return 0
    } else {
        checked.add(Pair(x, y))
        return 1 + (count(matrix, x, y + 1, checked) +
                count(matrix, x, y - 1, checked) +
                count(matrix, x + 1, y, checked) +
                count(matrix, x - 1, y, checked)
                )
    }
}

fun main() {

    fun part1(input: Matrix<Int>): Int {
        val lowPoints = mutableListOf<Int>()
        input.forEachIndexed { item, index ->
            if (input.getAdjacent(index).all { item < it }) {
                lowPoints.add(item)
            }
        }
        return lowPoints.sumOf { x -> x + 1 }
    }

    fun part2(input: Matrix<Int>): Int {
        val checked = mutableListOf<Pair<Int, Int>>()
        val scores = mutableListOf<Int>()
        input.forEachIndexed { item, index ->
            if (input.getAdjacent(index).all { item < it }) {
                val basinScore = count(input, index.x, index.y, checked)
                scores.add(basinScore)
            }
        }
        return scores
            .sortedWith(reverseOrder())
            .slice(0..2)
            .reduce { x, y -> x * y }
    }

    fun cleanInput(string: String) = string.split("").filter { x -> x.isNotEmpty() }.map { it.toInt() }
    val testInput = Matrix(readInput("09_test", ::cleanInput))
    check(part1(testInput) == 15)
    check(part2(testInput) == 1134)

    val input = Matrix(readInput("09", ::cleanInput))
    println(part1(input))
    println(part2(input))
}
