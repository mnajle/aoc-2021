fun main() {

    fun part1(polymer: String, rules: Map<String, String>): Int {
        var finalPolymer = polymer.slice(polymer.indices)
        repeat(10) {
            val extended = finalPolymer.zipWithNext { a, b -> "" + a + rules["$a$b"] + b }
            finalPolymer =
                extended[0] + extended
                    .slice(1 until extended.size)
                    .joinToString("") { x -> "" + x[1] + x[2] }
        }
        val charCount = finalPolymer.split("").filter { it.isNotBlank() }.groupingBy { it }.eachCount()
        val min = charCount.minByOrNull { it.value }?.value ?: 0
        val max = charCount.maxByOrNull { it.value }?.value ?: 0
        return max - min
    }

    fun part2(polymer: String, rules: Map<String, String>): Long {
        var pairCount = mutableMapOf<String, Long>()
        val charCount = mutableMapOf<Char, Long>()

        polymer.zipWithNext()
            .forEach { (a, b) ->
                val entry = a.toString() + b
                pairCount[entry] = pairCount.getOrDefault(entry, 0) + 1
            }
        polymer.split("").filter { x -> x.isNotBlank() }.map{ x -> x[0]}.forEach { char ->
            charCount[char] = charCount.getOrDefault(char, 0) + 1
        }
        repeat(40) {
            val stepData = mutableMapOf<String, Long>()
            for (entry in pairCount) {
                val complement = rules[entry.key] ?: ""
                val (a, b) = entry.key.toCharArray()
                val entryA = a + complement
                val entryB = complement + b
                stepData[entryA] = stepData.getOrDefault(entryA, 0) + entry.value
                stepData[entryB] = stepData.getOrDefault(entryB, 0) + entry.value
                val c = complement[0]
                charCount[c] = charCount.getOrDefault(c, 0) + 1 * entry.value
            }
            pairCount = stepData.toMutableMap()
        }
        val min = charCount.minByOrNull { it.value }?.value ?: 0
        val max = charCount.maxByOrNull { it.value }?.value ?: 0
        return max - min
    }

    fun getInput(fileName: String): Pair<String, Map<String, String>> {
        val lines = readInput(fileName).iterator()
        val rules = mutableMapOf<String, String>()
        val polymer = lines.next()
        lines.next()
        lines.forEachRemaining { line ->
            val (key, value) = line.split(" -> ")
            rules[key] = value
        }
        return Pair(polymer, rules)
    }

    val (polymerTest, rulesTest) = getInput("14_test")
    check(part1(polymerTest, rulesTest) == 1588)
    check(part2(polymerTest, rulesTest) == 2188189693529)

    val (polymer, rules) = getInput("14")
    println(part1(polymer, rules))
    println(part2(polymer, rules))
}
