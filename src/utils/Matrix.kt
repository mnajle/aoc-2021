package utils


class Matrix<T>(input: List<List<T>>) {
    companion object {
        fun <T> createEmpty(columns: Int, rows: Int, defaultValue: T): Matrix<T> {
            val content = mutableListOf<List<T>>()
            for (y in 0..rows) {
                val row = mutableListOf<T>()
                for (x in 0..columns) {
                    row.add(defaultValue)
                }
                content.add(row)
            }
            return Matrix(content)
        }
    }

    class MatrixIndex(val x: Int, val y: Int) {
        override fun toString(): String {
            return "x: ${this.x}, y: ${this.y}"
        }

        override fun equals(other: Any?): Boolean {
            if (other !is MatrixIndex) {
                return false
            }
            return this.x == other.x && this.y == other.y
        }

        override fun hashCode(): Int {
            return 31 * x + y
        }
    }

    class MatrixItem<T>(val item: T, val index: MatrixIndex) {
        override fun toString(): String {
            return "(${item}, (${index.x}, ${index.y}))"
        }

        operator fun component1(): T {
            return this.item
        }

        operator fun component2(): MatrixIndex {
            return this.index
        }
    }

    private val matrix: List<MutableList<T>>

    val columns: Int
    val rows: Int

    init {
        matrix = input.map { list -> list.toMutableList() }
        rows = input.size
        columns = input[0].size
    }

    override fun toString(): String {
        return matrix.joinToString("\n") { line -> line.joinToString("\t") }
    }

    fun toString(stringfy: (T) -> String): String {
        return matrix.joinToString("\n") { line -> line.joinToString("\t") { x -> stringfy(x) } }
    }

    operator fun get(i: Int, j: Int): T {
        if (checkIndex(i, j)) {
            return matrix[j][i]
        } else {
            throw Exception("[$i, $j] is not a valid coordinate for the matrix")
        }
    }

    operator fun get(index: MatrixIndex): T {
        return this[index.x, index.y]
    }

    operator fun set(i: Int, j: Int, value: T) {
        matrix[j][i] = value
    }

    operator fun set(index: MatrixIndex, value: T) {
        matrix[index.y][index.x] = value
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

    fun getAdjacentWithIndex(index: MatrixIndex, diagonal: Boolean = true): List<MatrixItem<T>> {
        val x = index.x
        val y = index.y
        var indexes = listOf(
            MatrixIndex(x - 1, y - 1),
            MatrixIndex(x - 1, y),
            MatrixIndex(x - 1, y + 1),
            MatrixIndex(x, y - 1),
            MatrixIndex(x, y + 1),
            MatrixIndex(x + 1, y - 1),
            MatrixIndex(x + 1, y),
            MatrixIndex(x + 1, y + 1),
        )
        if (!diagonal) {
            indexes = listOf(
                MatrixIndex(x - 1, y),
                MatrixIndex(x, y - 1),
                MatrixIndex(x, y + 1),
                MatrixIndex(x + 1, y),
            )
        }
        return indexes.filter { a -> this.checkIndex(a.x, a.y) }.map { i ->
            MatrixItem(this[i], i)
        }
    }

    fun forEachIndexed(callback: (item: T, index: MatrixIndex) -> Unit) {
        for (y in 0 until this.rows) {
            for (x in 0 until this.columns) {
                callback(this[x, y], MatrixIndex(x, y))
            }
        }
    }

    fun filter(predicate: (item: T) -> Boolean): List<T> {
        val result = mutableListOf<T>()
        for (y in 0 until this.rows) {
            for (x in 0 until this.columns) {
                if (predicate(this[x, y])) {
                    result.add(this[x, y])
                }
            }
        }
        return result
    }

    fun lastIndex(): MatrixIndex {
        return MatrixIndex(this.columns - 1, this.rows - 1)
    }

}
