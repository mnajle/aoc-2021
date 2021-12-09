package utils

class Matrix<T>(input: List<List<T>>) {
    class MatrixIndex(val x: Int, val y: Int)

    private val matrix: List<List<T>>

    val columns: Int
    val rows: Int

    init {
        matrix = input
        rows = input.size
        columns = input[0].size
    }

    override fun toString(): String {
        return matrix.joinToString("\n") { line -> line.joinToString("\t") }
    }

    operator fun get(i: Int, j: Int): T {
        if (checkIndex(i, j)) {
            return matrix[j][i]
        } else {
            throw Exception("[$i, $j] is not a valid coordinate for the matrix")
        }
    }

    fun checkIndex(column: Int, row: Int): Boolean {
        return (column in 0 until columns) && (row in 0 until rows)
    }

    fun getAdjacent(i: Int, j: Int): List<T> {
        val adj = mutableListOf<T>()
        if (i - 1 > -1) adj.add(this[i - 1, j])
        if (i + 1 < this.columns) adj.add(this[i + 1, j])
        if (j - 1 > -1) adj.add(this[i, j - 1])
        if (j + 1 < this.rows) adj.add(this[i, j + 1])
        return adj
    }

    fun getAdjacent(index: MatrixIndex): List<T> {
        return this.getAdjacent(index.x, index.y)
    }

    fun forEachIndexed(callback: (item: T, index: MatrixIndex) -> Unit) {
        for (y in 0 until this.rows) {
            for (x in 0 until this.columns) {
                callback(this[x, y], MatrixIndex(x, y))
            }
        }
    }

}
