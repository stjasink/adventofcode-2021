import common.Solver
import common.runAndTime
import common.loadInput

fun main() {
    val input = loadInput("day-01.txt")
    val solver = Day01()
    runAndTime(solver, input)
}

class Day01 : Solver {

    override fun part1(input: List<String>): Long {
        return countIncreasingDepths(input.map { it.toLong() })
    }

    override fun part2(input: List<String>): Long {
        val groupedDepths = mutableListOf<Long>()
        for (i in 0 .. input.size - 3) {
            groupedDepths.add(input.subList(i, i + 3).sumOf { it.toLong() })
        }
        return countIncreasingDepths(groupedDepths)
    }

    private fun countIncreasingDepths(depths: List<Long>): Long {
        var totalIncreases: Long = 0
        var previousDepth: Long? = null

        depths.forEach { depth ->
            if (previousDepth != null) {
                if (depth > previousDepth!!) {
                    totalIncreases += 1
                }
            }
            previousDepth = depth
        }

        return totalIncreases
    }
}
