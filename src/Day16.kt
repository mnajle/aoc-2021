class Package() {
    var version = 0
    var type = 0
    var value: Long = 0
    val subPackages = mutableListOf<Package>()
    override fun toString(): String {
        return "(V${version} T${type}) ${if (value > 0) value else "-"} ${subPackages.ifEmpty { "-" }}"
    }

    fun getPayload(): Long {
        return when (type) {
            0 -> subPackages.sumOf { it.getPayload() }
            1 -> subPackages.fold(1) {res, x -> res * x.getPayload()}
            2 -> subPackages.minOf { it.getPayload() }
            3 -> subPackages.maxOf { it.getPayload() }
            4 -> value
            5 -> (subPackages[0].getPayload() > subPackages[1].getPayload()).toInt().toLong()
            6 -> (subPackages[0].getPayload() < subPackages[1].getPayload()).toInt().toLong()
            7 -> (subPackages[0].getPayload() == subPackages[1].getPayload()).toInt().toLong()
            else -> 0
        }
    }
}

fun String.parseBinary() = this.toInt(2).toString(10).toInt()
fun String.parseBinaryLong() = this.toLong(2).toString(10).toLong()

fun parsePackage(data: String, startingIndex: Int = 0, packageCount: Int = Int.MAX_VALUE): Pair<List<Package>, Int> {
    val result = mutableListOf<Package>()
    var index = startingIndex
    fun take(amount: Int): String {
        val substring = data.substring(index).take(amount)
        index += amount
        return substring
    }

    val limit = data.length
    while (limit - index >= 11 && result.size < packageCount) {
        val packet = Package()
        packet.version = take(3).parseBinary()
        packet.type = take(3).parseBinary()
        if (packet.type == 4) {
            var lastBlock = false
            var bin = ""
            while (!lastBlock) {
                lastBlock = take(1) == "0"
                bin += take(4)
            }
            packet.value = bin.parseBinaryLong()
        } else {
            val lengthType = take(1).parseBinary()
            if (lengthType == 0) {
                val subPackageSize = take(15).parseBinary()
                val subPackageContent = take(subPackageSize)
                packet.subPackages.addAll(parsePackage(subPackageContent).first)
            } else {
                val subPackageCount = take(11).parseBinary()
                val (packages, n) = parsePackage(data, index, subPackageCount)
                packet.subPackages.addAll(packages)
                index = n
            }
        }
        result.add(packet)
    }
    return Pair(result, index)
}

fun main() {

    fun part1(input: String): Long {
        val packages = parsePackage(input).first
        fun getSum(pack: Package): Long {
            return pack.version + pack.subPackages.sumOf { x -> getSum(x) }
        }
        return packages.sumOf { x -> getSum(x) }
    }

    fun part2(input: String): Long {
        val packages = parsePackage(input).first
        return packages[0].getPayload()
    }

    fun parseInput(str: String) =
        str.split("").filter { x -> x.isNotBlank() }.map { x -> x.toInt(16).toString(2).padStart(4, '0') }
            .joinToString("")

    fun getInput(file: String) = parseInput(readInput(file)[0])

    check(part1(parseInput("8A004A801A8002F478")) == 16.toLong()) { "1.1 Failed" }
    check(part1(parseInput("620080001611562C8802118E34")) == 12.toLong()) { "1.2 Failed" }
    check(part1(parseInput("C0015000016115A2E0802F182340")) == 23.toLong()) { "1.3 Failed" }
    check(part1(parseInput("A0016C880162017C3686B18A3D4780")) == 31.toLong()) { "1.4 Failed" }

    check(part2(parseInput("C200B40A82")) == 3.toLong()) { "2.1 Failed" }
    check(part2(parseInput("04005AC33890")) == 54.toLong()) { "2.2 Failed" }
    check(part2(parseInput("880086C3E88112")) == 7.toLong()) { "2.3 Failed" }
    check(part2(parseInput("CE00C43D881120")) == 9.toLong()) { "2.4 Failed" }
    check(part2(parseInput("D8005AC2A8F0")) == 1.toLong()) { "2.5 Failed" }
    check(part2(parseInput("F600BC2D8F")) == 0.toLong()) { "2.6 Failed" }
    check(part2(parseInput("9C005AC2F8F0")) == 0.toLong()) { "2.8 Failed" }
    check(part2(parseInput("9C0141080250320F1802104A08")) == 1.toLong()) { "2.9 Failed" }

    val input = getInput("16")
    println(part1(input))
    println(part2(input))
}
