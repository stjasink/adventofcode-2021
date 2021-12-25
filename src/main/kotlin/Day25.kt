import Day25.CucumberDirection.*
import common.Solver
import common.runAndTime
import common.loadInput

fun main() {
    val input = loadInput("day-25.txt")
    val solver = Day25()
    runAndTime(solver, input)
}

class Day25 : Solver {

    override fun part1(input: List<String>): Long {
        var seaFloor = SeaFloor.from(input)
//        println("Initial State:")
//        seaFloor.print()
        for (turn in 1..10_000) {
            val movedSeaFloor = seaFloor.move()
//            println("After $turn steps:")
//            movedSeaFloor.print()
            if (movedSeaFloor == seaFloor) {
                return turn.toLong()
            }
            seaFloor = movedSeaFloor
        }

        return 0L
    }

    override fun part2(input: List<String>): Long {
        return 0L
    }

    data class SeaFloor(val cucumbers: Map<Point, CucumberDirection>,
                    val xSize: Int,
                    val ySize: Int) {


        companion object {
            fun from(input: List<String>): SeaFloor {
                val startLocations = mutableMapOf<Point, CucumberDirection>()
                input.forEachIndexed { y, row ->
                    row.forEachIndexed { x, char ->
                        when (char) {
                            '>' -> startLocations[Point(x, y)] = East
                            'v' -> startLocations[Point(x, y)] = South
                        }
                    }
                }
                return SeaFloor(startLocations, input.first().length, input.size)
            }
        }

        fun move(): SeaFloor {
            val newLocations = mutableMapOf<Point, CucumberDirection>()

            val allEast = cucumbers.filterValues { it == East }
            allEast.forEach { (oldLoc, _) ->
                val newLoc = oldLoc.moveTo(East, xSize, ySize)
                // moving first, so check against all old locations
                if (cucumbers.containsKey(newLoc)) {
                    newLocations[oldLoc] = East
                } else {
                    newLocations[newLoc] = East
                }
            }

            val allSouth = cucumbers.filterValues { it == South }
            allSouth.forEach { (oldLoc, _) ->
                val newLoc = oldLoc.moveTo(South, xSize, ySize)
                // moving second, so check against already-moved East plus non-moved South
                if (newLocations.containsKey(newLoc) || allSouth.containsKey(newLoc)) {
                    newLocations[oldLoc] = South
                } else {
                    newLocations[newLoc] = South
                }
            }

            return SeaFloor(newLocations, xSize, ySize)
        }

        fun print() {
            for (y in 0 until ySize) {
                for (x in 0 until xSize) {
                    val printChar = when(cucumbers[Point(x, y)]) {
                        East -> '>'
                        South -> 'v'
                        null -> '.'
                    }
                    print(printChar)
                }
                println()
            }
            println()
        }

    }

    enum class CucumberDirection {
        East,
        South
    }

    data class Point (
        val x: Int,
        val y: Int) {

        fun moveTo(dir: CucumberDirection, xSize: Int, ySize: Int): Point {
            val newX = when(dir) {
                East -> (x + 1) % xSize
                South -> x
            }
            val newY = when(dir) {
                East -> y
                South -> (y + 1) % ySize
            }
            return Point(newX, newY)
        }
    }
}

