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
        val uniqueSegmentNumbers = setOf(2, 3, 4, 7)
        val counts = input.map { line ->
            val (_, outputs) = line.split(" | ")
            val uniqueDigits = outputs.split(" ").filter { uniqueSegmentNumbers.contains(it.length) }
            uniqueDigits.size
        }
        return counts.sum().toLong()
    }

    override fun part2(input: List<String>): Long {
        val counts = input.map { line ->

            val (inputPart, outputPart) = line.split(" | ")
            val inputs = inputPart.split(" ")
            val outputs = outputPart.split(" ")

            val digits = mutableMapOf<String, Set<Char>>()
            digits["1"] = inputs.first{ it.length == 2 }.toSet()
            digits["7"] = inputs.first{ it.length == 3 }.toSet()
            digits["4"] = inputs.first{ it.length == 4 }.toSet()
            digits["8"] = inputs.first{ it.length == 7 }.toSet()

            val fiveSegmentDigits = inputs.filter { it.length == 5 }.map { it.toSet() } // 2, 3, 5
            digits["3"] = fiveSegmentDigits.first { it.containsAll(digits["1"]!!) }
            val twoAndFive = fiveSegmentDigits - setOf(digits["3"]!!)

            val sixSegmentDigits = inputs.filter { it.length == 6 }.map { it.toSet() } // 0, 6, 9
            digits["9"] = sixSegmentDigits.first { it.containsAll(digits["4"]!!) }
            val zeroAndSix = sixSegmentDigits - setOf(digits["9"]!!)
            digits["0"] = zeroAndSix.first { it.containsAll(digits["1"]!!) }
            digits["6"] = (zeroAndSix - setOf(digits["0"]!!)).first()

            digits["5"] = twoAndFive.first { (it - digits["6"]!!).isEmpty() }
            digits["2"] = (twoAndFive - setOf(digits["5"]!!)).first()

            val setsToDigits = digits.map { it.value to it.key }.toMap()

            val number = outputs.map {
                setsToDigits[it.toSet()]
            }.joinToString("").toInt()

            number
        }

        return counts.sum().toLong()
    }

    private fun String.sorted(): String = this.toCharArray().sorted().joinToString("")
}
