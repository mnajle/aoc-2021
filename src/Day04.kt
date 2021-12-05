class BingoCard() {
    private var content: Array<MutableList<Pair<Int, Boolean>>> = arrayOf(
        mutableListOf(Pair(0, false)),
        mutableListOf(Pair(0, false)),
        mutableListOf(Pair(0, false)),
        mutableListOf(Pair(0, false)),
        mutableListOf(Pair(0, false)),
    )

    constructor(input: List<List<Int>>) : this() {
        for (line in input.withIndex()) {
            this.content[line.index] = line.value.map { x -> Pair(x, false) }.toMutableList()
        }
    }

    override fun toString(): String {
        return this.content.map { list ->
            list.map { pair ->
                if (pair.second) "[${pair.first}]" else "_${pair.first}_"
            }.joinToString(",")
        }.joinToString("\n").plus("\n")
    }

    fun checkNumber(number: Int) {
        for (line in this.content) {
            val i = line.indexOf(Pair(number, false))
            if (i != -1) {
                line[i] = Pair(number, true)
            }
        }
    }

    fun won() = this.hasLine() or this.hasColumn()

    private fun hasLine() = this.content.any { line -> line.all { x -> x.second } }
    private fun hasColumn() = this.content
        .reduce { x, y -> x.indices.map { i -> Pair(0, x[i].second and y[i].second) }.toMutableList() }
        .any { x -> x.second}
    fun score(): Int {
        return this.content
            .map { line -> line.map { x -> if (!x.second) x.first else 0 }.reduce { x, y -> x + y } }
            .reduce { x, y -> x + y }
    }
}


fun main() {

    fun parseInput(input: List<String>): Pair<List<Int>, List<BingoCard>> {
        val gameInput = input[0].split(",").map { x -> x.toInt() }
        val cards = mutableListOf<BingoCard>()
        val lines = input.slice(1 until input.size).filter { it != "" }.map {
            it.split(" ").filter { x -> x != "" }.map { x -> x.toInt() }.toMutableList()
        }
        for (index in (0..lines.size step 5).zipWithNext()) {
            val cardContent = lines.slice(index.first until index.second)
            cards.add(BingoCard(cardContent))
        }
        return Pair(gameInput, cards)
    }

    fun part1(gameInput: List<Int>, cards: List<BingoCard>): Int {
        for (number in gameInput) {
            cards.forEach { card -> card.checkNumber(number) }
            val winner = cards.filter { it.won() }
            if (winner.isNotEmpty()) {
                return winner[0].score() * number
            }
        }
        return 0
    }

    fun part2(gameInput: List<Int>, cards: List<BingoCard>): Int {
        for (number in gameInput) {
            val nonWinners = cards.filter { !it.won() }
            nonWinners.forEach { card -> card.checkNumber(number) }
            if (nonWinners.size == 1 && nonWinners[0].won()) {
                return nonWinners[0].score() * number
            }
        }
        return 1
    }

    val testInput = parseInput(readInput("04_test"))
    check(part1(testInput.first, testInput.second) == 4512)
    check(part2(testInput.first, testInput.second) == 1924)

    val input = parseInput(readInput("04"))
    println(part1(input.first, input.second))
    println(part2(input.first, input.second))
}
