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
        val groupedDepths = input.windowed(3).map { group -> group.sumOf { it.toLong() } }
        return countIncreasingDepths(groupedDepths)
    }

    private fun countIncreasingDepths(depths: List<Long>): Long {
        return depths
            .windowed(2)
            .count { depthPair -> depthPair[1] > depthPair[0] }
            .toLong()
    }
}
