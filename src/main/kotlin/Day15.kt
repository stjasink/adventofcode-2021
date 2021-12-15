import common.Solver
import common.runAndTime
import common.loadInput

fun main() {
    val input = loadInput("day-15.txt")
    val solver = Day15()
    runAndTime(solver, input)
}

class Day15 : Solver {

    override fun part1(input: List<String>): Long {
        val grid = Grid(input, 1)
        return grid.findDistance().toLong()
    }

    override fun part2(input: List<String>): Long {
        val grid = Grid(input, 5)
        return grid.findDistance().toLong()
    }

    data class Point(
        val x: Int,
        val y: Int
    )

    class Grid(input: List<String>, expandTimes: Int) {

        private val cellDistances: Map<Point, Int>
        private val visited: MutableSet<Point>
        private val tentativeDistances: MutableMap<Point, Int>
        private val maxY: Int
        private val maxX: Int
        private val calculatedAndUnvisited: MutableMap<Point, Int> = mutableMapOf()

        init {
            val grid0 = mutableMapOf<Point, Int>()
            input.forEachIndexed { y, line ->
                line.toCharArray().forEachIndexed { x, c ->
                    grid0[Point(x, y)] = c.digitToInt()
                }
            }

            cellDistances = expandGrid(grid0, expandTimes)
            maxY = cellDistances.keys.maxOf { it.y }
            maxX = cellDistances.keys.maxOf { it.x }

            visited = mutableSetOf()
            tentativeDistances = mutableMapOf()
        }

        private fun expandGrid(grid0: MutableMap<Point, Int>, expandTimes: Int): Map<Point, Int> {
            val grid0SizeY = grid0.keys.maxOf { it.y } + 1
            val grid0SizeX = grid0.keys.maxOf { it.x } + 1
            val expandedGrid = mutableMapOf<Point, Int>()
            for (x in 0 until expandTimes) {
                for (y in 0 until expandTimes) {
                    grid0.forEach { (point, risk0) ->
                        val newX = point.x + (grid0SizeX * x)
                        val newY = point.y + (grid0SizeY * y)
                        val newPoint = Point(newX, newY)
                        val newRisk = (risk0 + x + y)
                        val newRiskLooped = if (newRisk > 9) (newRisk % 10) + 1 else newRisk
                        expandedGrid[newPoint] = newRiskLooped
                    }
                }
            }
            return expandedGrid
        }

        fun findDistance(): Int {
            val start = Point(0, 0)
            val end = Point(maxX, maxY)
            tentativeDistances[start] = 0

            setNeighbourDistancesFor(start)
            while (visited.size < cellDistances.size) {
                val point = lowestDistanceUnvisitedPoint()
                if (point == end) {
                    return tentativeDistances[point]!!
                }
                setNeighbourDistancesFor(point)
                visited.add(point)
                calculatedAndUnvisited.remove(point)
            }

            return 0
        }

        private fun lowestDistanceUnvisitedPoint(): Point {
            return calculatedAndUnvisited
                .minByOrNull { it.value }!!
                .key
        }

        private fun setNeighbourDistancesFor(point: Point) {
            val neighbours = point.getNeighbours()
            neighbours.forEach { neighbour ->
                if (!visited.contains(neighbour)) {
                    val neighbourDistance = cellDistances[neighbour]!! + tentativeDistances[point]!!
                    if (tentativeDistances[neighbour] == null) {
                        tentativeDistances[neighbour] = neighbourDistance
                        calculatedAndUnvisited[neighbour] = neighbourDistance
                    } else {
                        if (neighbourDistance < tentativeDistances[neighbour]!!) {
                            tentativeDistances[neighbour] = neighbourDistance
                            calculatedAndUnvisited[neighbour] = neighbourDistance
                        }
                    }
                }
            }
        }

        private fun Point.getNeighbours(): List<Point> {
            val neighbours = mutableListOf<Point>()
            if (x > 0) neighbours.add(Point(x-1, y))
            if (y > 0) neighbours.add(Point(x, y-1))
            if (x < maxX) neighbours.add(Point(x+1, y))
            if (y < maxY) neighbours.add(Point(x, y+1))
            return neighbours
        }

    }
}
