import common.Solver
import common.runAndTime
import common.loadInput
import kotlin.IllegalStateException

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

        var currentOxygenList = input
        for (index in 0 until input.first().length) {
            var numZeroes = 0
            var numOnes = 0
            currentOxygenList.forEach { line ->
                if (line[index] == '0') {
                    numZeroes += 1
                } else if (line[index] == '1') {
                    numOnes += 1
                }
            }
            val digitToKeepForOxygen = if (numZeroes > numOnes) '0' else '1'
            if (currentOxygenList.size > 1) {
                currentOxygenList = currentOxygenList.filter { it[index] == digitToKeepForOxygen }
            }
        }

        var currentCO2List = input
        for (index in 0 until input.first().length) {
            var numZeroes = 0
            var numOnes = 0
            currentCO2List.forEach { line ->
                if (line[index] == '0') {
                    numZeroes += 1
                } else if (line[index] == '1') {
                    numOnes += 1
                }
            }
            val digitToKeepForCO2 = if (numZeroes <= numOnes) '0' else '1'
            if (currentCO2List.size > 1) {
                currentCO2List = currentCO2List.filter { it[index] == digitToKeepForCO2 }
            }
        }

        if (currentOxygenList.size > 1) {
            throw IllegalStateException("Oxygen list is size ${currentOxygenList.size}")
        }
        if (currentCO2List.size > 1) {
            throw IllegalStateException("CO2 list is size ${currentCO2List.size}")
        }

        val oxygenGenerator = currentOxygenList.first().toLong(2)
        val c02Scrubber = currentCO2List.first().toLong(2)

        return oxygenGenerator * c02Scrubber
    }

    private fun findNumWithDigits(input: List<String>, mostCommon: List<Int>): String {
        var currentList = input
        mostCommon.forEachIndexed { index, digit ->
            val newList = currentList.filter { it[index].digitToInt() == digit }
            if (newList.size == 1) {
                return newList.first()
            } else {
                currentList = newList
            }
        }
        throw IllegalStateException("Did not find a single value")
    }
}
