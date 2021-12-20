import utils.Matrix
import utils.Matrix.MatrixIndex
import java.util.*


fun main() {

    fun doDijkstra(matrix: Matrix<Int>, start: MatrixIndex): Int {
        val costs = Matrix.createEmpty(matrix.columns-1, matrix.rows-1, Int.MAX_VALUE)
        val checked = mutableSetOf(start)
        val priorityQueue = PriorityQueue { i1: MatrixIndex, i2: MatrixIndex -> costs[i1] - costs[i2] }
        costs[start] = 0
        priorityQueue.add(start)

        while (priorityQueue.isNotEmpty()) {
            val currentIndex = priorityQueue.remove()
            val availableNeighbours =
                matrix.getAdjacentWithIndex(currentIndex, false).filter { (x, i) -> !checked.contains(i) };
            for ((item, index) in availableNeighbours) {
                val nextCost = costs[currentIndex] + item;
                if (nextCost < costs[index]) {
                    costs[index] = nextCost;
                    priorityQueue.add(index);
                }
            }
        }
        return costs[matrix.lastIndex()]
    }

    fun part1(input: List<List<Int>>): Int {
        val map = Matrix(input)
        val start = MatrixIndex(0, 0)
        return doDijkstra(map, start)
    }

    fun part2(input: List<List<Int>>): Int {
        val extendedCave = mutableListOf<List<Int>>()
        for (row in input) {
            val newRow = mutableListOf<Int>()
            for (i in 1..4) {
                val append = row
                    .map { it + i }
                    .map { if (it > 9) it - 9 else it }
                newRow.addAll(append)
            }
            extendedCave.add(row + newRow)
        }
        val newRows = mutableListOf<List<Int>>()
        for (i in 1..4) {
            for (row in extendedCave) {
                val append = row
                    .map { it + i }
                    .map { if (it > 9) it - 9 else it }
                newRows.add(append)
            }
        }
        extendedCave.addAll(newRows)
        val map = Matrix(extendedCave)
        return doDijkstra(map, MatrixIndex(0, 0))
    }

    fun cleanInput(string: String) =
        string.split("").filter { x -> x.isNotEmpty() }.map { it.toInt() }

    val testInput = readInput("15_test") { x -> cleanInput(x) }
    check(part1(testInput) == 40) { "check 1 failed" }
    check(part2(testInput) == 315) { "check 2 failed" }

    val input = readInput("15") { x -> cleanInput(x) }
    println(part1(input))
    println(part2(input))
}
