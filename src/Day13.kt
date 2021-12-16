import utils.Matrix
import java.awt.Point

fun foldVertically(source: Matrix<Boolean>, foldLine: Int): Matrix<Boolean> {
    val result = Matrix.createEmpty(source.columns, source.rows - foldLine -1, false)
    for (y in 0 until foldLine) {
        val foldedY = foldLine + foldLine - y
        for (x in 0 until source.columns) {
            result[x, y] = source[x, y] || source[x, foldedY]
        }
    }
    return result
}

fun foldHorizontally(source: Matrix<Boolean>, foldLine: Int): Matrix<Boolean> {
    val result = Matrix.createEmpty(source.columns - foldLine - 1, source.rows, false)
    for (y in 0 until source.rows) {
        for (x in 0 until foldLine) {
            val foldedX = foldLine + foldLine - x
            result[x, y] = source[x, y] || source[foldedX, y]
        }
    }
    return result
}

fun main() {

    fun part1(input: List<Point>, folds: Int): Int {
        val columns = input.map { it.x }.maxOrNull() ?: 0
        val rows = input.map { it.y }.maxOrNull() ?: 0
        var paper = Matrix.createEmpty(columns, rows, false)
        for (point in input) {
            paper[point.x, point.y] = true
        }
        paper = if (folds > 0) foldVertically(paper, folds) else foldHorizontally(paper, folds * -1)
        return paper.filter { it }.size
    }

    fun part2(input: List<Point>, folds: List<Int>): String {
        val columns = input.map { it.x }.maxOrNull() ?: 0
        val rows = input.map { it.y }.maxOrNull() ?: 0
        var paper = Matrix.createEmpty(columns, rows, false)
        for (point in input) {
            paper[point.x, point.y] = true
        }
        for (fold in folds) {
            paper = if (fold > 0) foldVertically(paper, fold) else foldHorizontally(paper, fold * -1)
        }
        return paper.toString { x -> if (x) "*" else "" }
    }

    val testInput = readInput("13_test") { x -> x.split(",").map { it.toInt() } }.map { x -> Point(x[0], x[1]) }
    check(part1(testInput, 7) == 17)

    val input = readInput("13") { x -> x.split(",").map { it.toInt() } }.map { x -> Point(x[0], x[1]) }
    println(part1(input, -655))
    println(part2(input, listOf(-655, 447, -327, 223, -163, 111, -81, 55, -40, 27, 13, 6)))
}

//fold along y=7
//fold along x=5

//fold along x=655
//fold along y=447
//fold along x=327
//fold along y=223
//fold along x=163
//fold along y=111
//fold along x=81
//fold along y=55
//fold along x=40
//fold along y=27
//fold along y=13
//fold along y=6
