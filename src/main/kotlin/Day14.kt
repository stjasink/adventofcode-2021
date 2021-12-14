import common.Solver
import common.runAndTime
import common.loadInput

fun main() {
    val input = loadInput("day-14.txt")
    val solver = Day14()
    runAndTime(solver, input)
}

class Day14 : Solver {

    override fun part1(input: List<String>): Long {
        return calculate(input, 10)
    }

    override fun part2(input: List<String>): Long {
        return calculate(input, 40)
    }

    private fun calculate(input: List<String>, iterations: Int): Long {
        val startString = input.first()
        val replacementMap = input.drop(2).map { line ->
            val (match, newLetter) = line.split(" -> ")
            val replacements = listOf(match[0] + newLetter, newLetter + match[1])
            match to replacements
        }.toMap()

        var pairCount = startString.windowed(2).groupBy { it }.map { it.key to it.value.size.toLong() }.toMap()
        for (i in 0 until iterations) {
            val newPairCount = mutableMapOf<String, Long>()
            pairCount.forEach { (pair, count) ->
                val replacements = replacementMap[pair]
                replacements!!.forEach { newPair ->
                    if (newPairCount[newPair] == null) {
                        newPairCount[newPair] = count
                    } else {
                        newPairCount[newPair] = (newPairCount[newPair]!! + count)
                    }
                }
            }
            pairCount = newPairCount
        }

        val letterCounts = pairCount
            .map { it.key.first() to it.value } // only count first letter because pairs overlap
            .groupBy { it.first }
            .map { entry -> entry.key to entry.value.sumOf { it.second } }
            .toMap()
            .toMutableMap()
        val finalLetter = startString.last() // need to increment last letter's count because it doesn't overlap
        letterCounts[finalLetter] = letterCounts[finalLetter]!! + 1

        val minCount = letterCounts.minByOrNull { it.value }!!.value
        val maxCount = letterCounts.maxByOrNull { it.value }!!.value
        return maxCount - minCount
    }
}
