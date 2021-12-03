import common.Solver
import common.runAndTime
import common.loadInput

fun main() {
    val input = loadInput("day-03.txt")
    val solver = Day03()
    runAndTime(solver, input)
}

class Day03 : Solver {

    override fun part1(input: List<String>): Long {
        val numZeros = Array(input.first().length){0L}.toMutableList()
        val numOnes = Array(input.first().length){0L}.toMutableList()

        input.forEach { line ->
            line.forEachIndexed { index, c ->
                if (c == '0') {
                    numZeros.set(index, numZeros.get(index) + 1)
                } else if (c == '1') {
                    numOnes.set(index, numOnes.get(index) + 1)
                }
            }
        }

        val gammaRate = mutableListOf<Int>()
        val epsilonRate = mutableListOf<Int>()

        for (i in 0 until numZeros.size) {
            if (numZeros[i] > numOnes[i]) {
                gammaRate.add(0)
                epsilonRate.add(1)
            } else if (numZeros[i] < numOnes[i]) {
                gammaRate.add(1)
                epsilonRate.add(0)
            } else {
                println("Error!  The same at index $i")
            }
        }

        val gammaRateNum = gammaRate.joinToString("").toLong(2)
        val epsilonRateNum = epsilonRate.joinToString("").toLong(2)

        return gammaRateNum * epsilonRateNum
    }

    override fun part2(input: List<String>): Long {
        val oxygenGenerator = filterBasedOnDigits(input) { numZeroes, numOnes -> if (numZeroes > numOnes) '0' else '1' }
        val cO2Scrubber = filterBasedOnDigits(input) { numZeroes, numOnes -> if (numZeroes <= numOnes) '0' else '1' }
        return oxygenGenerator.toLong(2) * cO2Scrubber.toLong(2)
    }

    private fun filterBasedOnDigits(input: List<String>, digitSelector: (Int, Int) -> Char): String {
        var currentList = input
        for (index in 0 until input.first().length) {
            val (numZeroes, numOnes) = countZeroesAndOnes(currentList, index)
            val digitToKeep = digitSelector(numZeroes, numOnes)
            if (currentList.size == 1) {
                return currentList.first()
            } else {
                currentList = currentList.filter { it[index] == digitToKeep }
            }
        }
        return currentList.first()
    }

    private fun countZeroesAndOnes(
        currentOxygenList: List<String>,
        index: Int
    ): Pair<Int, Int> {
        var numZeroes = 0
        var numOnes = 0
        currentOxygenList.forEach { line ->
            if (line[index] == '0') {
                numZeroes += 1
            } else if (line[index] == '1') {
                numOnes += 1
            }
        }
        return Pair(numZeroes, numOnes)
    }

}
