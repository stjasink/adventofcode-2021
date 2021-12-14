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
        val startString = input.first()
        val replacements = input.drop(2).map { line ->
            val (match, newLetter) = line.split(" -> ")
            val replacement = match[0] + newLetter + match[1]
            match to replacement
        }.toMap()

        var polymer = startString
        for (i in 0 until 10) {
            val newPolymer = StringBuilder()
            newPolymer.append(polymer.first())
            polymer.windowed(2).forEach { pair ->
                val foundReplacement = replacements[pair]
                if (foundReplacement == null) {
                    newPolymer.append(pair)
                } else {
                    newPolymer.append(foundReplacement.drop(1))
                }
            }
            polymer = newPolymer.toString()
        }

        val counts = polymer.toList().groupBy { it }.map { it.key to it.value.size }
        val minCount = counts.minByOrNull { it.second }!!.second
        val maxCount = counts.maxByOrNull { it.second }!!.second

        return maxCount.toLong() - minCount.toLong()
    }

    override fun part2(input: List<String>): Long {
        return 0L
    }
}
