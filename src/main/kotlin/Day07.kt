import common.Solver
import common.runAndTime
import common.loadInput
import kotlin.math.absoluteValue

fun main() {
    val input = loadInput("day-07.txt")
    val solver = Day07()
    runAndTime(solver, input)
}

class Day07 : Solver {

    override fun part1(input: List<String>): Long {
        val startPositions = input.first().split(",").map { it.toInt() }
        val maxPos = startPositions.maxOrNull()!!


        val fuelPerPos = Array(maxPos + 1) { 1000 }
        for (pos in 0 .. maxPos) {
            val fuelPerSub = startPositions.map { (it - pos).absoluteValue }
            val totalFuel = fuelPerSub.sum()
            fuelPerPos[pos] = totalFuel
        }

        val minFuel = fuelPerPos.minOrNull()!!

        return minFuel.toLong()
    }

    override fun part2(input: List<String>): Long {
        val startPositions = input.first().split(",").map { it.toInt() }
        val maxPos = startPositions.maxOrNull()!!


        val fuelPerPos = Array(maxPos + 1) { 1000 }
        for (pos in 0 .. maxPos) {
            val fuelPerSub = startPositions.map { fuelForMove((it - pos).absoluteValue) }
            val totalFuel = fuelPerSub.sum()
            fuelPerPos[pos] = totalFuel
        }

        val minFuel = fuelPerPos.minOrNull()!!

        return minFuel.toLong()
    }

    private fun fuelForMove(n: Int): Int {
        return (n * (n + 1)) / 2
    }
}
