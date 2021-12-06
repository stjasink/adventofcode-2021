import common.Solver
import common.runAndTime
import common.loadInput

fun main() {
    val input = loadInput("day-06.txt")
    val solver = Day06()
    runAndTime(solver, input)
}

class Day06 : Solver {

    override fun part1(input: List<String>): Long {
        return countFish(input, 80)
    }

    override fun part2(input: List<String>): Long {
        return countFish(input, 256)
    }

    private fun countFish(input: List<String>, numGenerations: Int): Long {
        val fish = input.first().split(",").map { it.toInt() }

        var fishAges = Array(9) { 0L }
        fish.forEach { thisFish ->
            fishAges[thisFish] = fishAges[thisFish] + 1
        }

        for (i in 0 until numGenerations) {
            val newFishAges = Array(9) { 0L }

            for (age in 8 downTo 1) {
                newFishAges[age - 1] = fishAges[age]
            }
            newFishAges[6] = newFishAges[6] + fishAges[0]
            newFishAges[8] = newFishAges[8] + fishAges[0]

            fishAges = newFishAges
        }

        return fishAges.sum()
    }
}
