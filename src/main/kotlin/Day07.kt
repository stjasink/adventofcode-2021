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
        return calculateMinFuelNeeded(parseStartPositions(input)) { numSpaces -> linearFuelUsage(numSpaces) }.toLong()
    }

    override fun part2(input: List<String>): Long {
        return calculateMinFuelNeeded(parseStartPositions(input)) { numSpaces -> risingFuelUsage(numSpaces) }.toLong()
    }

    private fun parseStartPositions(input: List<String>): List<Int> {
        val startPositions = input.first().split(",").map { it.toInt() }
        return startPositions
    }

    private fun calculateMinFuelNeeded(startPositions: List<Int>, fuelForMove: (Int) -> Int): Int {
        val maxPos = startPositions.maxOrNull()!!
        val fuelPerPos = Array(maxPos + 1) { Int.MAX_VALUE }
        for (pos in 0 .. maxPos) {
            val fuelPerSub = startPositions.map { fuelForMove((it - pos).absoluteValue) }
            val totalFuel = fuelPerSub.sum()
            fuelPerPos[pos] = totalFuel
        }
        return fuelPerPos.minOrNull()!!
    }

    private fun linearFuelUsage(spaces: Int): Int {
        return spaces
    }

    private fun risingFuelUsage(spaces: Int): Int {
        return (spaces * (spaces + 1)) / 2
    }
}
