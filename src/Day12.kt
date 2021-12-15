fun main() {

    fun part1(input: List<Pair<String, String>>): Int {
        val graph = Graph(input)
        fun isAvailable(node: String, currentPath: MutableList<String>) : Boolean {
            return node !in currentPath || node.uppercase() == node
        }
        return graph.getAllPaths(::isAvailable, "start", "end").size
    }

    fun part2(input: List<Pair<String, String>>): Int {
        val graph = Graph(input)
        fun isAvailable(node: String, currentPath: MutableList<String>) : Boolean {
            val lowerDups = currentPath
                .filter { n -> n in currentPath && !listOf("start", "end").contains(n) }
                .filter { n -> n in currentPath && n.lowercase() == n }
                .groupingBy { it }
                .eachCount()
                .filter { it.value > 1 }
                .isNotEmpty()

            if (node !in currentPath || node.uppercase() == node || node == "end") {
                return true
            }
            return node != "start" && !lowerDups
        }
        return graph.getAllPaths(::isAvailable, "start", "end").size
    }

    val testInput = readInput("12_test") { x -> x.split("-").let { Pair(it[0], it[1]) } }
    check(part1(testInput) == 226)
    check(part2(testInput) == 3509)

    val input = readInput("12") { x -> x.split("-").let { Pair(it[0], it[1]) } }
    println(part1(input))
    println(part2(input))
}
