import common.Solver
import common.runAndTime
import common.loadInput

fun main() {
    val input = loadInput("day-05.txt")
    val solver = Day05()
    runAndTime(solver, input)
}

class Day05 : Solver {

    override fun part1(input: List<String>): Long {
        val lines = parseLineData(input)
        val seaFloorMap = createMap(lines)
        val horizOrVertLines = lines.filter {
            it.first.first == it.second.first || it.first.second == it.second.second
        }
        drawLines(horizOrVertLines, seaFloorMap)
        val numMultipleVents = seaFloorMap.flatMap { row -> row.filter { it > 1 } }.count()
        return numMultipleVents.toLong()
    }

    override fun part2(input: List<String>): Long {
        val lines = parseLineData(input)
        val seaFloorMap = createMap(lines)
        drawLines(lines, seaFloorMap)
        val numMultipleVents = seaFloorMap.flatMap { row -> row.filter { it > 1 } }.count()
        return numMultipleVents.toLong()
    }

    private fun drawLines(
        lines: List<Pair<Pair<Int, Int>, Pair<Int, Int>>>,
        seaFloorMap: Array<Array<Int>>
    ) {
        lines.forEach { line ->
            val startX = line.first.first
            val endX = line.second.first
            val startY = line.first.second
            val endY = line.second.second

            if (startX < endX) {
                var y = startY
                if (startY < endY) {
                    for (x in startX..endX) {
                        seaFloorMap[y][x] = seaFloorMap[y][x] + 1
                        y = y + 1
                    }
                } else if (startY > endY) {
                    for (x in startX..endX) {
                        seaFloorMap[y][x] = seaFloorMap[y][x] + 1
                        y = y - 1
                    }
                } else {
                    for (x in startX..endX) {
                        seaFloorMap[y][x] = seaFloorMap[y][x] + 1
                    }
                }
            } else if (startX > endX) {
                var y = startY
                if (startY < endY) {
                    for (x in startX downTo endX) {
                        seaFloorMap[y][x] = seaFloorMap[y][x] + 1
                        y = y + 1
                    }
                } else if (startY > endY) {
                    for (x in startX downTo endX) {
                        seaFloorMap[y][x] = seaFloorMap[y][x] + 1
                        y = y - 1
                    }
                } else {
                    for (x in startX downTo endX) {
                        seaFloorMap[y][x] = seaFloorMap[y][x] + 1
                    }
                }
            } else {
                val x = startX
                if (startY < endY) {
                    for (y in startY..endY) {
                        seaFloorMap[y][x] = seaFloorMap[y][x] + 1
                    }
                } else if (startY > endY) {
                    for (y in startY downTo endY) {
                        seaFloorMap[y][x] = seaFloorMap[y][x] + 1
                    }
                } else {
                    val y = startY
                    seaFloorMap[y][x] = seaFloorMap[y][x] + 1
                }
            }
        }
    }

    private fun createMap(lines: List<Pair<Pair<Int, Int>, Pair<Int, Int>>>): Array<Array<Int>> {
        val maxY = lines.flatMap {
            listOf(it.first.second, it.second.second)
        }.maxOf { it }
        val maxX = lines.flatMap {
            listOf(it.first.first, it.second.first)
        }.maxOf { it }

        val seaFloorMap = Array(maxY + 1) { Array(maxX + 1) { 0 } }
        return seaFloorMap
    }

    private fun parseLineData(input: List<String>): List<Pair<Pair<Int, Int>, Pair<Int, Int>>> {
        val lines = input.map { line ->
            line.split(" -> ")
        }.map {
            val from = it[0].split(',')
            val to = it[1].split(',')
            Pair(Pair(from[0].toInt(), from[1].toInt()), Pair(to[0].toInt(), to[1].toInt()))
        }
        return lines
    }
}
