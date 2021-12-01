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
        var totalIncreases: Long = 0
        var previousDepth: Long? = null

        input.forEach {
            val depth = it.toLong()
            if (previousDepth != null) {
                if (depth > previousDepth!!) {
                    totalIncreases += 1
                }
            }
            previousDepth = depth
        }

        return totalIncreases
    }

    override fun part2(input: List<String>): Long {
        val groupedDepths = mutableListOf<Long>()
        for (i in 0 .. input.size - 3) {
            groupedDepths.add(input.subList(i, i + 3).sumOf { it.toLong() })
        }
        return part1(groupedDepths.map { it.toString() })
    }
}
