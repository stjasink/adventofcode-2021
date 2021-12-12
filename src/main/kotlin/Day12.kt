import common.Solver
import common.runAndTime
import common.loadInput

fun main() {
    val input = loadInput("day-12.txt")
    val solver = Day12()
    runAndTime(solver, input)
}

class Day12 : Solver {

    override fun part1(input: List<String>): Long {
        val startCave = parseInputs(input)
        val routesToEnd = mutableListOf<List<Cave>>()
        val currentRoute = mutableListOf<Cave>()

        fun canTakeExit(toCave: Cave): Boolean {
            return toCave.isBig() || !currentRoute.contains(toCave)
        }

        fun takeAStep(fromCave: Cave) {
            currentRoute.add(fromCave)
            if (fromCave.isEnd()) {
                routesToEnd.add(currentRoute)
                currentRoute.removeLast()
            } else {
                fromCave.exits.forEach { exit ->
                    if (canTakeExit(exit)) {
                        takeAStep(exit)
                    }
                }
                currentRoute.removeLast()
            }
        }

        takeAStep(startCave)

        return routesToEnd.size.toLong()
    }

    override fun part2(input: List<String>): Long {
        val startCave = parseInputs(input)
        val routesToEnd = mutableListOf<List<Cave>>()
        val currentRoute = mutableListOf<Cave>()

        fun canTakeExit(toCave: Cave): Boolean {
            if (toCave.isStart()) return false
            if (toCave.isEnd()) return true
            if (toCave.isBig()) return true

            val visitedSmallCaves = currentRoute.filterNot { it.isBig() }
            val visitedASmallCaveTwice = visitedSmallCaves.groupingBy { it.name }.eachCount().values.contains(2)
            return !visitedSmallCaves.contains(toCave) || !visitedASmallCaveTwice
        }

        fun takeAStep(fromCave: Cave) {
            currentRoute.add(fromCave)
            if (fromCave.isEnd()) {
                routesToEnd.add(currentRoute)
                currentRoute.removeLast()
            } else {
                fromCave.exits.forEach { exit ->
                    if (canTakeExit(exit)) {
                        takeAStep(exit)
                    }
                }
                currentRoute.removeLast()
            }
        }

        takeAStep(startCave)

        return routesToEnd.size.toLong()
    }

    class Cave (
        val name: String,
        val exits: MutableSet<Cave>) {
        fun isBig() = name.first().isUpperCase()
        fun isStart() = (name == "start")
        fun isEnd() = (name == "end")
        override fun toString(): String = name
    }

    fun parseInputs(input: List<String>): Cave {
        val caves = mutableMapOf<String, Cave>()

        input.forEach { path ->
            val (start, end) = path.split("-")
            if (caves[start] == null) {
                caves[start] = Cave(start, mutableSetOf())
            }
            if (caves[end] == null) {
                caves[end] = Cave(end, mutableSetOf())
            }
            caves[start]!!.exits.add(caves[end]!!)
            caves[end]!!.exits.add(caves[start]!!)
        }

        return caves["start"]!!
    }
}
