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
        val routesToEnd = findRoutesThroughCaves(input) { toCave, currentRoute -> allowOnlySingleSmallCaves(toCave, currentRoute) }
        return routesToEnd.size.toLong()
    }

    override fun part2(input: List<String>): Long {
        val routesToEnd = findRoutesThroughCaves(input) { toCave, currentRoute -> allowOneDoubleSmallCave(toCave, currentRoute) }
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

    private fun parseInputs(input: List<String>): Cave {
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

    private fun findRoutesThroughCaves(input: List<String>, canTakeExit: (Cave, List<Cave>) -> Boolean): List<List<Cave>> {
        val startCave = parseInputs(input)
        val routesToEnd = mutableListOf<List<Cave>>()
        val currentRoute = mutableListOf<Cave>()

        fun takeAStep(fromCave: Cave) {
            currentRoute.add(fromCave)
            if (fromCave.isEnd()) {
                routesToEnd.add(currentRoute)
                currentRoute.removeLast()
            } else {
                fromCave.exits.forEach { exit ->
                    if (canTakeExit(exit, currentRoute)) {
                        takeAStep(exit)
                    }
                }
                currentRoute.removeLast()
            }
        }

        takeAStep(startCave)

        return routesToEnd
    }

    private fun allowOnlySingleSmallCaves(toCave: Cave, currentRoute: List<Cave>): Boolean {
        return toCave.isBig() || !currentRoute.contains(toCave)
    }

    private fun allowOneDoubleSmallCave(toCave: Cave, currentRoute: List<Cave>): Boolean {
        if (toCave.isStart()) return false
        if (toCave.isEnd()) return true
        if (toCave.isBig()) return true

        val visitedSmallCaves = currentRoute.filterNot { it.isBig() }
        val visitedASmallCaveTwice = visitedSmallCaves.groupingBy { it.name }.eachCount().values.contains(2)
        return !visitedSmallCaves.contains(toCave) || !visitedASmallCaveTwice
    }
}
