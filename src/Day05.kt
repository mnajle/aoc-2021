import java.awt.Point
import kotlin.math.sign

fun parseInput(line: String): Pair<Point, Point> {
    fun vector(string: String) = string.split(",").map { it.toInt() }.let { a ->
        Point(a[0], a[1])
    }
    return line.split(" -> ").let { array ->
        Pair(vector(array[0]), vector(array[1]))
    }
}

fun sortPoints(pair: Pair<Point, Point>): Pair<Point, Point> {
    val a = pair.first
    val b = pair.second
    val mustRevert = if (a.x != b.x)
                     a.x > b.x
                     else a.y > b.y
    return if (mustRevert) Pair(b, a) else pair
}

fun getRange(rangeDefinition: Pair<Point, Point>): List<Point> {
    val range = mutableListOf<Point>()
    val first = sortPoints(rangeDefinition).first
    val last = sortPoints(rangeDefinition).second
    val direction = (last.y - first.y).sign

    if (first.x != last.x) {
        var y = first.y
        for (x in first.x..last.x) {
            range.add(Point(x, y))
            y += 1 * direction
        }
    } else {
        val x = first.x
        val yRange = if (direction > 0) first.y..last.y else (first.y..last.y).reversed()
        for (y in yRange) {
            range.add(Point(x, y))
        }
    }

    return range
}

fun main() {

    fun part2(input: List<Pair<Point, Point>>): Int {
        val matrix: MutableMap<Point, Int> = mutableMapOf()
        input.map { x -> getRange(x) }.flatten().forEach { point ->
            val count = matrix[point]
            if (count != null) {
                matrix[point] = count + 1
            } else {
                matrix[point] = 1
            }
        }
        return matrix.filter { entry -> entry.value >= 2 }.size
    }

    fun part1(input: List<Pair<Point, Point>>): Int {
        val nonDiagonal = input.filter { x -> x.first.x == x.second.x || x.first.y == x.second.y }
        return part2(nonDiagonal)
    }

    val testInput = readInput("05_test", ::parseInput)
    check(part1(testInput) == 5)
    check(part2(testInput) == 12)

    val input = readInput("05", ::parseInput)
    println(part1(input))
    println(part2(input))
}
