import common.Solver
import common.runAndTime
import common.loadInput

fun main() {
    val input = loadInput("day-08.txt")
    val solver = Day08()
    runAndTime(solver, input)
}

class Day08 : Solver {

    override fun part1(input: List<String>): Long {
        val uniqueSegmentCounts = setOf(2, 3, 4, 7)
        val counts = input.map { line ->
            val (_, outputs) = line.split(" | ")
            val uniqueDigits = outputs.split(" ").filter { uniqueSegmentCounts.contains(it.length) }
            uniqueDigits.size
        }
        return counts.sum().toLong()
    }

    override fun part2(input: List<String>): Long {
        val counts = input.map { line ->
            val (inputPart, outputPart) = line.split(" | ")
            val inputs = inputPart.split(" ")
            val outputs = outputPart.split(" ")

            // digits with unique segment counts
            val segments1 = inputs.first{ it.length == 2 }.toSet()
            val segments7 = inputs.first{ it.length == 3 }.toSet()
            val segments4 = inputs.first{ it.length == 4 }.toSet()
            val segments8 = inputs.first{ it.length == 7 }.toSet()

            // start on five-segment digits
            val twoThreeAndFive = inputs.filter { it.length == 5 }.map { it.toSet() } // 2, 3, 5
            val segments3 = twoThreeAndFive.first { it.containsAll(segments1) } // 3 is the only one that overlaps 1
            val twoAndFive = twoThreeAndFive - setOf(segments3)

            // all the six-segment digits
            val zeroSixAndNine = inputs.filter { it.length == 6 }.map { it.toSet() } // 0, 6, 9
            val segments9 = zeroSixAndNine.first { it.containsAll(segments4) } // 9 overlaps 4
            val zeroAndSix = zeroSixAndNine - setOf(segments9)
            val segments0 = zeroAndSix.first { it.containsAll(segments1) } // 0 overlaps 1
            val segments6 = zeroAndSix.first { it != segments0 }

            // finish five-segment digits
            val segments5 = twoAndFive.first { (it - segments6).isEmpty() } // 6 is a superset of 5
            val segments2 = twoAndFive.first { it != segments5 }

            val segmentsToDigits = mapOf(
                segments0 to "0", segments1 to "1", segments2 to "2", segments3 to "3", segments4 to "4",
                segments5 to "5", segments6 to "6", segments7 to "7", segments8 to "8", segments9 to "9"
            )

            val number = outputs.map {
                segmentsToDigits[it.toSet()]
            }.joinToString("").toInt()

            number
        }

        return counts.sum().toLong()
    }

}
