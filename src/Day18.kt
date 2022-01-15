class SnailNode() {
    var number: Long = 0
    var snail: Snail? = null
    var nested: SnailNode? = null
//    fun assign(long: Long) {
//        this.number = long
//    }
//    fun assign(snail: Snail) {
//        this.snail = snail
//        this.nested = SnailNode()
//    }
}

class Snail() {
    val a = SnailNode()
    val b = SnailNode()

    companion object {
        fun parseString(string: String): Snail {
            val result = Snail()
            val it = string.iterator()
            var index = 0
            var found = false
            var open = 0
            while (it.hasNext() && !found) {
                val char = it.next()
                if (char == '[') {
                    open++
                }
                if (char == ']') {
                    open--
                }
                if (char == ',' && open == 1) {
                    found = true
                }
                else {
                    index ++
                }
            }

            val first = string.substring(0..index)
            val second = string.substring(index)

            fun assign(snail: SnailNode, substring: String) {
                if (substring.toLongOrNull() != null) {
                    snail.number = substring.toLong()
                }
                else {
                    snail.snail = parseString(substring)
                }
            }
            assign(result.a, first)
            assign(result.b, second)

            return result
        }
    }

    init {

    }

    fun reduce() {

    }

    private fun explode() {

    }

    private fun split() {

    }
}

fun main() {

    fun part1(input: List<Snail>): Int {
        return 0
    }

    fun part2(input: List<String>): Int {
        return 1
    }

    val testInput = readInput("18_test") { x -> Snail.parseString(x) }
    test(1, part1(testInput), 0)
//    test(2, part2(testInput), 1)

    val input = readInput("18") { x -> x }
//    println(part1(input))
    println(part2(input))
}
