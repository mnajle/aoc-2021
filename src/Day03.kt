class Vector(ints: Array<Int>) {
    private var sequence: Array<Int>
    init {
        this.sequence = ints
    }
    constructor(bits: String) : this(bits.toCharArray().map { x -> x.digitToInt() }.toTypedArray())
    constructor(bits: List<Int>) : this(bits.toTypedArray())

    operator fun plus(bit: Vector): Vector {
        val sum = this.sequence.zip(bit.sequence) { xv, yv -> xv + yv }
        return Vector(sum)
    }
    operator fun times(function: (n: Int) -> Int): Vector {
        return Vector(this.sequence.map{ n -> function.invoke(n)}.joinToString(""))
    }
    operator fun get(index: Int): Int {
        return this.sequence[index]
    }
    fun toInt(): Int {
        return this.sequence.joinToString("").toInt(2)
    }
    override fun toString(): String {
        return "[" + this.sequence.joinToString(",") + "]"
    }
}

fun main() {
    fun findMostCommonDigit(input: List<Vector>): Vector {
        val oneCount = input.reduce { count, binary -> count + binary }
        return oneCount * { count -> if (count < input.size - count) 0 else 1 }
    }
    fun findMostCommonDigit(input: List<Vector>, index: Int): Int {
        return findMostCommonDigit(input)[index]
    }

    fun filterMostCommon(input: List<Vector>, index: Int): Vector {
        val sum = findMostCommonDigit(input, index)
        val result = input.filter { it[index] == sum }
        if (result.size == 1) {
            return result[0]
        } else {
            return filterMostCommon(result, index + 1)
        }
    }

    fun filterLeastCommon(input: List<Vector>, index: Int): Vector {
        val sum = findMostCommonDigit(input, index)
        val result = input.filter { it[index] != sum }
        if (result.size == 1) {
            return result[0]
        } else {
            return filterLeastCommon(result, index + 1)
        }
    }

    fun part1(input: List<Vector>): Int {
        val gammaBinary = findMostCommonDigit(input)
        val epsilonBinary = gammaBinary * { bit -> if (bit == 1) 0 else 1 }
        return gammaBinary.toInt() * epsilonBinary.toInt()
    }

    fun part2(input: List<Vector>): Int {
        val oxygenRating = filterMostCommon(input, 0).toInt()
        val co2Rating = filterLeastCommon(input, 0).toInt()
        return oxygenRating * co2Rating
    }

    val testInput = readInput("03_test") { line -> Vector(line) }
    check(part1(testInput) == 198)
    check(part2(testInput) == 230)

    val input = readInput("03") { line -> Vector(line) }
    println(part1(input))
    println(part2(input))
}
