import common.Solver
import common.runAndTime
import common.loadInput
import kotlin.math.absoluteValue
import kotlin.math.max
import kotlin.math.min

fun main() {
    val input = loadInput("day-22.txt")
    val solver = Day22()
    runAndTime(solver, input)
}

class Day22 : Solver {

    override fun part1(input: List<String>): Long {
        var reactor = listOf<Cuboid>()
        val targetRange = -50..50

        input.forEach { line ->
            val (onOff, cuboid) = parseLine(line)
            if (cuboid.x.intersect(targetRange).isNotEmpty() && cuboid.y.intersect(targetRange).isNotEmpty() && cuboid.z.intersect(targetRange).isNotEmpty()) {
                reactor = if (onOff) {
                    reactor.map { it.turnOff(cuboid) } + cuboid
                } else {
                    reactor.map { it.turnOff(cuboid) }
                }
            }

        }

        return reactor.map { it.count() }.sum()

    }

    override fun part2(input: List<String>): Long {
        var reactor = listOf<Cuboid>()

        input.forEach { line ->
            val (onOff, cuboid) = parseLine(line)
            reactor = if (onOff) {
                reactor.map { it.turnOff(cuboid) } + cuboid
            } else {
                reactor.map { it.turnOff(cuboid) }
            }
        }

        return reactor.map { it.count() }.sum()
    }

    private fun parseLine(line: String): Pair<Boolean, Cuboid> {
        val parts = line.split(" ")
        val onOff = if (parts[0] == "on") true else false
        val ranges = parseRanges(parts[1])
        return onOff to Cuboid(ranges[0], ranges[1], ranges[2])
    }

    private fun parseRanges(line: String): List<IntRange> {
        return line.split(",")
            .map { it.substringAfter("=") }
            .map { it.split("..") }
            .map { IntRange(it[0].toInt(), it[1].toInt()) }
    }

    data class Cuboid(
        val x: IntRange,
        val y: IntRange,
        val z: IntRange,
        val holes: List<Cuboid> = emptyList()
    ) {
        fun turnOff(off: Cuboid): Cuboid {
            val hole = this.overlap(off)
            return if (hole != null) {
                this.copy(holes = holes.map { it.turnOff(hole) } + hole)
            } else {
                this
            }
        }
        
        fun overlap(other: Cuboid): Cuboid? {
            val xOverlaps = (x.start <= other.x.start && x.endInclusive >= other.x.start) || (other.x.start <= x.start && other.x.endInclusive >= x.start)
            val yOverlaps = (y.start <= other.y.start && y.endInclusive >= other.y.start) || (other.y.start <= y.start && other.y.endInclusive >= y.start)
            val zOverlaps = (z.start <= other.z.start && z.endInclusive >= other.z.start) || (other.z.start <= z.start && other.z.endInclusive >= z.start)
            
            if (xOverlaps && yOverlaps && zOverlaps) {
                val xOverlap = max(x.start, other.x.start) .. min(x.endInclusive, other.x.endInclusive)
                val yOverlap = max(y.start, other.y.start) .. min(y.endInclusive, other.y.endInclusive)
                val zOverlap = max(z.start, other.z.start) .. min(z.endInclusive, other.z.endInclusive)

                return Cuboid(xOverlap, yOverlap, zOverlap)
            } else {
                return null
            }
        }

        fun count(): Long {
            var on =
                ((x.endInclusive - x.start).absoluteValue + 1).toLong() * ((y.endInclusive - y.start).absoluteValue + 1).toLong() * ((z.endInclusive - z.start).absoluteValue + 1).toLong()
            holes.forEach { hole ->
                on -= hole.count()
            }
            return on.coerceAtLeast(0L)
        }
    }
}
