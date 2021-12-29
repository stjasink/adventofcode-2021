import common.Solver
import common.runAndTime
import common.loadInput

fun main() {
    val input = loadInput("day-22.txt")
    val solver = Day22()
    runAndTime(solver, input)
}

class Day22 : Solver {

    override fun part1(input: List<String>): Long {
        val reactor = mutableSetOf<Cube>()
        val targetRange = -50..50

        input.forEach { line ->
            val (onOff, ranges) = parseLine(line)
            if (ranges[0].intersect(targetRange).isNotEmpty() && ranges[1].intersect(targetRange).isNotEmpty() && ranges[2].intersect(targetRange).isNotEmpty()) {
                for (x in ranges[0]) {
                    for (y in ranges[1]) {
                        for (z in ranges[2]) {
                            val cube = Cube(x, y, z)
                            if (onOff) {
                                reactor.add(cube)
                            } else {
                                reactor.remove(cube)
                            }
                        }
                    }
                }
            }
        }

        return reactor.size.toLong()
    }

    override fun part2(input: List<String>): Long {
        return 0L
    }

    fun parseLine(line: String): Pair<Boolean, List<IntRange>> {
        val parts = line.split(" ")
        val onOff = if (parts[0] == "on") true else false
        val ranges = parseRanges(parts[1])
        return onOff to ranges
    }

    fun parseRanges(line: String): List<IntRange> {
        return line.split(",")
            .map { it.substringAfter("=") }
            .map { it.split("..") }
            .map { IntRange(it[0].toInt(), it[1].toInt()) }
    }

    data class Cube(
        val x: Int,
        val y: Int,
        val z: Int
    )
}
