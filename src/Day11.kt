import utils.Matrix

fun main() {

    fun part1(input: List<List<Int>>): Int {
        val matrix = Matrix(input)
        var flashCount = 0
        for (step in 1..100) {
            val flashed = mutableListOf<Matrix.MatrixIndex>()
            fun checkForFlash(index: Matrix.MatrixIndex) {
                if (matrix[index] > 9 && index !in flashed) {
                    flashed.add(index)
                    matrix.getAdjacentWithIndex(index).forEach { (item, i) ->
                        matrix[i]++
                        checkForFlash(i)
                    }
                }
            }

            matrix.forEachIndexed { item, index ->
                matrix[index] = item + 1
            }
            matrix.forEachIndexed { item, index ->
                checkForFlash(index)
            }
            matrix.forEachIndexed { item, index ->
                if (item > 9) {
                    flashCount ++
                    matrix[index] = 0
                }
            }
        }
        return flashCount
    }

    fun part2(input: List<List<Int>>): Int {
        val matrix = Matrix(input)
        var nSync = false
        var step = 0
        while (!nSync) {
            val flashed = mutableListOf<Matrix.MatrixIndex>()
            fun checkForFlash(index: Matrix.MatrixIndex) {
                if (matrix[index] > 9 && index !in flashed) {
                    flashed.add(index)
                    matrix.getAdjacentWithIndex(index).forEach { (item, i) ->
                        matrix[i]++
                        checkForFlash(i)
                    }
                }
            }

            matrix.forEachIndexed { item, index ->
                matrix[index] = item + 1
            }
            matrix.forEachIndexed { item, index ->
                checkForFlash(index)
            }
            matrix.forEachIndexed { item, index ->
                if (item > 9) {
                    matrix[index] = 0
                }
            }
            if (matrix.filter { item -> item != 0 }.isEmpty()) {
                nSync = true
            }
            step++
        }
        return step
    }

    fun getInput(fileName: String) = readInput(fileName) { line ->
        line.split("").filter { str -> str.isNotBlank() }.map { char -> char.toInt() }
    }

    val testInput = getInput("11_test")
    check(part1(testInput) == 1656)
    check(part2(testInput) == 195)

    val input = getInput("11")
    println(part1(input))
    println(part2(input))
}
