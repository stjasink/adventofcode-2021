import common.Solver
import common.runAndTime
import common.loadInput

fun main() {
    val input = loadInput("day-13.txt")
    val solver = Day13()
    runAndTime(solver, input)
}

class Day13 : Solver {

    override fun part1(input: List<String>): Long {
        val points = parsePoints(input)
        val folds = parseFolds(input)
        val startGrid = plotStartGrid(points)

        val result = folds.take(1).fold(startGrid) { grid, fold ->
            grid.fold(fold)
        }

        return result.countDots().toLong()
    }

    override fun part2(input: List<String>): Long {
        val points = parsePoints(input)
        val folds = parseFolds(input)
        val startGrid = plotStartGrid(points)

        val result = folds.fold(startGrid) { grid, fold ->
            grid.fold(fold)
        }

        result.print()
        return 0L
    }

    private fun plotStartGrid(points: List<Point>): Grid {
        val maxX = points.maxOf { it.x }
        val maxY = points.maxOf { it.y }

        val startGrid = Grid(maxX + 1, maxY + 1)
        points.forEach { point ->
            startGrid.makeDot(point)
        }
        return startGrid
    }

    data class Point(
        val x: Int,
        val y: Int
    )

    data class FoldLine(
        val direction: String,
        val value: Int
    )

    class Grid(xSize: Int, ySize: Int) {
        val grid: Array<Array<Boolean>>

        init {
            grid = Array(ySize) { Array(xSize) { false } }
        }

        constructor(data: Array<Array<Boolean>>) : this(data[0].size, data.size) {
            for (y in 0 until data.size) {
                for (x in 0 until data[0].size) {
                    grid[y][x] = data[y][x]
                }
            }
        }

        fun makeDot(point: Point) {
            grid[point.y][point.x] = true
        }

        fun splitAlongY(value: Int): Pair<Grid, Grid> {
            val topRows = grid.copyOfRange(0, value)
            val bottomRows = grid.copyOfRange(value + 1, grid.size)
            return Pair(Grid(topRows), Grid(bottomRows))
        }

        fun flipAlongY(): Grid {
            val flippedData = Array<Array<Boolean>>(grid.size) { emptyArray() }
            for (i in grid.size - 1 downTo 0) {
                flippedData[i] = grid[grid.size - i - 1]
            }
            return Grid(flippedData)
        }

        fun overlay(with: Grid): Grid {
            val newData = grid.copyOf()
            for (y in 0 until newData.size) {
                for (x in 0 until newData[0].size) {
                    newData[y][x] = newData[y][x] || with.grid[y][x]
                }
            }
            return Grid(newData)
        }

        fun foldY(value: Int): Grid {
            val (top, bottom) = splitAlongY(value)
            // pad in some empty lines if bottom part is smaller, so that they can overlay exactly
            val paddedBottom = if (top.grid.size > bottom.grid.size) {
                val numFewer = top.grid.size - bottom.grid.size
                val extraRows = Array(numFewer) { Array(bottom.grid[0].size) { false } }
                val paddedData = grid + extraRows
                Grid(paddedData)
            } else bottom

            if (top.grid.size < bottom.grid.size) {
                throw IllegalStateException("Did not expect top to be smaller")
            }
            val flippedBottom = paddedBottom.flipAlongY()
            return top.overlay(flippedBottom)
        }

        fun foldX(value: Int): Grid {
            val transposed = transpose()
            val folded = transposed.foldY(value)
            return folded.transpose()
        }

        fun fold(foldLine: FoldLine): Grid {
            if (foldLine.direction == "y") {
                return foldY(foldLine.value)
            } else {
                return foldX(foldLine.value)
            }
        }

        fun transpose(): Grid {
            val xSize = grid[0].size
            val ySize = grid.size
            val newGrid = Grid(ySize, xSize) // other way round
            for (y in 0 until ySize) {
                for (x in 0 until xSize) {
                    newGrid.grid[x][y] = grid[y][x]
                }
            }
            return newGrid
        }

        fun print() {
            grid.forEach { row ->
                row.forEach { print(if (it) '#' else ' ') }
                println()
            }
        }

        fun countDots(): Int {
            return grid.sumOf { row ->
                row.count { it }
            }
        }
    }

    private fun parsePoints(input: List<String>): List<Point> {
        return input.mapNotNull { line ->
            if (line.contains(",")) {
                val (x,y) = line.split(",")
                Point(x.toInt(), y.toInt())
            } else {
                null
            }
        }
    }

    private fun parseFolds(input: List<String>): List<FoldLine> {
        return input.mapNotNull { line ->
            if (line.startsWith("fold along")) {
                val usefulPart = line.substringAfter("fold along ")
                val (dir, value) = usefulPart.split("=")
                FoldLine(dir, value.toInt())
            } else {
                null
            }
        }
    }

}

