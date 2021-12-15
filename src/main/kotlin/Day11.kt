import common.Solver
import common.runAndTime
import common.loadInput

fun main() {
    val input = loadInput("day-11.txt")
    val solver = Day11()
    runAndTime(solver, input)
}

class Day11 : Solver {

    override fun part1(input: List<String>): Long {
        val grid = input.map { row ->
            row.toCharArray().map { it.toString().toInt() }.toMutableList()
        }
        var flashCount = 0L

        fun printGrid() {
            for (rowNum in 0 until 10) {
                for (colNum in 0 until 10) {
                    print(grid[rowNum][colNum])
                }
                println()
            }
            println()
        }

        val flashedThisTurn = mutableSetOf<Pair<Int, Int>>()
        fun doFlash(rowNum: Int, colNum: Int) {
            // over 9 will flash and 0 has already flashed
            if (grid[rowNum][colNum] > 9) {
                flashCount += 1
                grid[rowNum][colNum] = 0
                flashedThisTurn.add(Pair(rowNum, colNum))

                val startRowNum = (rowNum-1).coerceAtLeast(0)
                val endRowNum = (rowNum+1).coerceAtMost(9)
                val startColNum = (colNum-1).coerceAtLeast(0)
                val endColNum = (colNum+1).coerceAtMost(9)

                for (incRowNum in startRowNum .. endRowNum) {
                    for (incColNum in startColNum .. endColNum) {
                        if (!(rowNum == incRowNum && colNum == incColNum)) {
                            if (!flashedThisTurn.contains(Pair(incRowNum, incColNum))) {
                                grid[incRowNum][incColNum] = grid[incRowNum][incColNum] + 1
                            }
                        }
                    }
                }
            }
        }

        for (turn in 0 until 100) {

            // increment
            for (rowNum in 0 until 10) {
                for (colNum in 0 until 10) {
                    grid[rowNum][colNum] = grid[rowNum][colNum] + 1
                }
            }

            // flash
            var startFlashCount = flashCount
            var newFlashes: Long
            do {
                for (rowNum in 0 until 10) {
                    for (colNum in 0 until 10) {
                        doFlash(rowNum, colNum)
                    }

                }
                newFlashes = flashCount - startFlashCount
                startFlashCount = flashCount
            } while (newFlashes > 0L)
            flashedThisTurn.clear()

        }


        return flashCount
    }

    override fun part2(input: List<String>): Long {
        val grid = input.map { row ->
            row.toCharArray().map { it.toString().toInt() }.toMutableList()
        }
        var flashCount = 0L

        fun printGrid() {
            for (rowNum in 0 until 10) {
                for (colNum in 0 until 10) {
                    print(grid[rowNum][colNum])
                }
                println()
            }
            println()
        }

        val flashedThisTurn = mutableSetOf<Pair<Int, Int>>()
        fun doFlash(rowNum: Int, colNum: Int) {
            // over 9 will flash and 0 has already flashed
            if (grid[rowNum][colNum] > 9) {
                flashCount += 1
                grid[rowNum][colNum] = 0
                flashedThisTurn.add(Pair(rowNum, colNum))

                val startRowNum = (rowNum-1).coerceAtLeast(0)
                val endRowNum = (rowNum+1).coerceAtMost(9)
                val startColNum = (colNum-1).coerceAtLeast(0)
                val endColNum = (colNum+1).coerceAtMost(9)

                for (incRowNum in startRowNum .. endRowNum) {
                    for (incColNum in startColNum .. endColNum) {
                        if (!(rowNum == incRowNum && colNum == incColNum)) {
                            if (!flashedThisTurn.contains(Pair(incRowNum, incColNum))) {
                                grid[incRowNum][incColNum] = grid[incRowNum][incColNum] + 1
                            }
                        }
                    }
                }
            }
        }

        for (turn in 0 until 10000) {

            // increment
            for (rowNum in 0 until 10) {
                for (colNum in 0 until 10) {
                    grid[rowNum][colNum] = grid[rowNum][colNum] + 1
                }
            }

            // flash
            var startFlashCount = flashCount
            var newFlashes: Long
            do {
                for (rowNum in 0 until 10) {
                    for (colNum in 0 until 10) {
                        doFlash(rowNum, colNum)
                    }

                }
                newFlashes = flashCount - startFlashCount
                startFlashCount = flashCount
            } while (newFlashes > 0L)

            if (flashedThisTurn.size == 100) {
                return turn.toLong() + 1
            }

            flashedThisTurn.clear()
        }

        return 0L
    }
}
