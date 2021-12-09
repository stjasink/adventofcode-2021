import common.Solver
import common.runAndTime
import common.loadInput

fun main() {
    val input = loadInput("day-09.txt")
    val solver = Day09()
    runAndTime(solver, input)
}

class Day09 : Solver {

    override fun part1(input: List<String>): Long {
         val grid = input.map { row ->
             row.toCharArray().map { it.toString().toInt() }
         }

        val lowPoints = mutableListOf<Int>()

        grid.forEachIndexed { rowNum, row ->
            row.forEachIndexed { colNum, col ->
                if (isLowPoint(grid, rowNum, colNum)) {
                    lowPoints += grid[rowNum][colNum]
                }
             }
        }

        return lowPoints.map { it + 1 }.sum().toLong()
    }

    override fun part2(input: List<String>): Long {
        val grid = input.map { row ->
            row.toCharArray().map { it.toString().toInt() }
        }

        val numRows = grid.size
        val numCols = grid.first().size

        val basinSizes = mutableListOf<Long>()

        grid.forEachIndexed { rowNum, row ->
            row.forEachIndexed { colNum, col ->
                if (isLowPoint(grid, rowNum, colNum)) {
                    val alreadySeen = mutableListOf(Pair(rowNum, colNum))
                    val countLeft = if (colNum > 0) countNonNines(grid, rowNum, colNum-1, alreadySeen) else 0
                    val countRight = if (colNum < numCols - 1) countNonNines(grid, rowNum, colNum+1, alreadySeen) else 0
                    val countUp = if (rowNum > 0) countNonNines(grid, rowNum-1, colNum, alreadySeen) else 0
                    val countDown = if (rowNum < numRows - 1) countNonNines(grid, rowNum+1, colNum, alreadySeen) else 0
                    val basinSize = countLeft + countRight + countUp + countDown + 1
                    basinSizes.add(basinSize.toLong())
                }
            }
        }

        val threeLargest = basinSizes.sortedDescending().take(3)

        return threeLargest.reduce { acc, i ->  acc * i }
    }

    private fun countNonNines(grid: List<List<Int>>, rowNum: Int, colNum: Int, alreadySeen: MutableList<Pair<Int, Int>>): Int {
        if (alreadySeen.contains(Pair(rowNum, colNum)) || grid[rowNum][colNum] == 9) {
            return 0
        }
        var count = 1
        alreadySeen.add(Pair(rowNum, colNum))
        if (!alreadySeen.contains(Pair(rowNum, colNum-1)) && colNum > 0) count += countNonNines(grid, rowNum, colNum-1, alreadySeen)
        if (!alreadySeen.contains(Pair(rowNum, colNum+1)) && colNum < grid.first().size - 1) count += countNonNines(grid, rowNum, colNum+1, alreadySeen)
        if (!alreadySeen.contains(Pair(rowNum-1, colNum)) && rowNum > 0) count += countNonNines(grid, rowNum-1, colNum, alreadySeen)
        if (!alreadySeen.contains(Pair(rowNum+1, colNum)) && rowNum < grid.size - 1) count += countNonNines(grid, rowNum+1, colNum, alreadySeen)
        return count
    }

    private fun isLowPoint(grid: List<List<Int>>, rowNum: Int, colNum: Int): Boolean {
        val neighbours = findNeighbours(grid, rowNum, colNum)
        return grid[rowNum][colNum] < neighbours.minOf { it }
    }

    private fun findNeighbours(grid: List<List<Int>>, rowNum: Int, colNum: Int): List<Int> {
        if (rowNum == 0) {
            return if (colNum == 0) {
                listOf(grid[rowNum][colNum+1], grid[rowNum+1][colNum])
            } else if (colNum == grid.first().size - 1) {
                listOf(grid[rowNum][colNum-1], grid[rowNum+1][colNum])
            } else {
                listOf(grid[rowNum][colNum-1], grid[rowNum][colNum+1], grid[rowNum+1][colNum])
            }
        } else if (rowNum == grid.size - 1) {
            return if (colNum == 0) {
                listOf(grid[rowNum][colNum+1], grid[rowNum-1][colNum])
            } else if (colNum == grid.first().size - 1) {
                listOf(grid[rowNum][colNum-1], grid[rowNum-1][colNum])
            } else {
                listOf(grid[rowNum][colNum-1], grid[rowNum][colNum+1], grid[rowNum-1][colNum])
            }
        } else {
            return if (colNum == 0) {
                listOf(grid[rowNum][colNum+1], grid[rowNum+1][colNum], grid[rowNum-1][colNum])
            } else if (colNum == grid.first().size - 1) {
                listOf(grid[rowNum][colNum-1], grid[rowNum+1][colNum], grid[rowNum-1][colNum])
            } else {
                listOf(grid[rowNum][colNum-1], grid[rowNum][colNum+1], grid[rowNum+1][colNum], grid[rowNum-1][colNum])
            }
        }
    }


}
