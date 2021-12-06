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
        var fishAges = Array(9) { 0L }.toMutableList()
        fish.forEach { thisFish ->
            fishAges[thisFish] = fishAges[thisFish] + 1
        }

        for (i in 0 until numGenerations) {
            // shift left all ages and add new fish at age 8 for each that was at age 0 before
            val newFishAges = (fishAges.drop(1) + fishAges[0]).toMutableList()
            // fish that made new fish will come back at 6
            newFishAges[6] = newFishAges[6] + fishAges[0]
            fishAges = newFishAges
        }

        return fishAges.sum()
    }
}
