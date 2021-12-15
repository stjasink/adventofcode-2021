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

        val cellDistances: Map<Point, Int>
        val visited: MutableSet<Point>
        val tentativeDistances: MutableMap<Point, Int>
        val maxY: Int
        val maxX: Int

        init {
            val grid0 = mutableMapOf<Point, Int>()
            input.forEachIndexed { y, line ->
                line.toCharArray().forEachIndexed { x, c ->
                    grid0[Point(x, y)] = c.digitToInt()
                }
            }
            if (expandTimes == 1) {
                cellDistances = grid0
                maxY = input.size - 1
                maxX = input[0].length - 1
            } else {
                val grid0SizeY = input.size
                val grid0SizeX = input[0].length
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
                maxY = grid0SizeY * expandTimes - 1
                maxX = grid0SizeX * expandTimes - 1
                cellDistances = expandedGrid
            }

//            for (y in 0..maxY) {
//                for (x in 0..maxX) {
//                    print(cellDistances[Point(x, y)])
//                }
//                println()
//            }
//            println()

            visited = mutableSetOf()
            tentativeDistances = mutableMapOf()
            cellDistances.forEach { (key, _) ->
                tentativeDistances[key] = Int.MAX_VALUE
            }
        }

        fun findDistance(): Int {
            val start = Point(0, 0)
            val end = Point(maxX, maxY)
            tentativeDistances[start] = 0

            println("Number of points: ${cellDistances.size}")

            setNeighbourDistancesFor(start, 0)
            while (visited.size < cellDistances.size) {
                val point = lowestDistanceUnvisitedPoint()
                if (point == end) {
                    return tentativeDistances[point]!!
                }
                setNeighbourDistancesFor(point, tentativeDistances[point]!!)
                visited.add(point)
                if (visited.size % 100 == 0) {
                    println("Visited ${visited.size}")
                }
            }

            return 0
        }

        fun lowestDistanceUnvisitedPoint(): Point {
            return tentativeDistances
                .filterKeys { !visited.contains(it) }
                .minByOrNull { it.value }!!
                .key
        }

        fun setNeighbourDistancesFor(point: Point, distanceSoFar: Int) {
            val neighbours = point.getNeighbours()
            neighbours.forEach { neighbour ->
                if (!visited.contains(neighbour)) {
                    val neighbourDistance = cellDistances[neighbour]!! + tentativeDistances[point]!!
                    if (neighbourDistance < tentativeDistances[neighbour]!!) {
                        tentativeDistances[neighbour] = neighbourDistance
                    }
                }
            }
        }

        fun Point.getNeighbours(): List<Point> {
            val neighbours = mutableListOf<Point>()
            if (x > 0) neighbours.add(Point(x-1, y))
            if (y > 0) neighbours.add(Point(x, y-1))
            if (x < maxX) neighbours.add(Point(x+1, y))
            if (y < maxY) neighbours.add(Point(x, y+1))
            return neighbours
        }

    }
}
