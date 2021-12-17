import common.Solver
import common.runAndTime
import common.loadInput
import kotlin.math.max

fun main() {
    val input = loadInput("day-17.txt")
    val solver = Day17()
    runAndTime(solver, input)
}

class Day17 : Solver {

    override fun part1(input: List<String>): Long {
        val target = parseTarget(input.first())

//        println("x=$targetXRange, y=$targetYRange")

        var totalMaxY = 0

        // we want to find highest, so always increasing y
        for (yVel in 1..5000) {
            for (xVel in 1..target.xRange.last) {
                val (foundTarget, maxY) = plotLaunch(xVel, yVel, target)
                if (foundTarget) {
                    totalMaxY = max(totalMaxY, maxY)
                }
            }
        }

        return totalMaxY.toLong()
    }

    override fun part2(input: List<String>): Long {
        return 0L
    }

    fun plotLaunch(xVelStart: Int, yVelStart: Int, target: Target): Pair<Boolean, Int> {
        var x = 0
        var y = 0
        var xVel = xVelStart
        var yVel = yVelStart
        var yMax = y

        do {
            if (isWithinTarget(x, y, target)) return Pair(true, yMax)

            x += xVel
            y += yVel
            yMax = max(yMax, y)

            xVel = if (xVel > 0) xVel - 1 else xVel + 1
            yVel -= 1

        } while (!isPastTarget(x, y, target))

        return Pair(false, yMax)
    }

    private fun isWithinTarget(x: Int, y: Int, target: Target): Boolean {
        return x in target.xRange && y in target.yRange
    }

    private fun isPastTarget(x: Int, y: Int, target: Target): Boolean {
        return x > target.xRange.last || y < target.yRange.first
    }

    private fun parseTarget(input: String): Target {
        val (xPart, yPart) = input.substringAfter("target area:").split(",")
        val (xMin, xMax) = xPart.substringAfter(" x=").split("..").map { it.toInt() }
        val (yMin, yMax) = yPart.substringAfter(" y=").split("..").map { it.toInt() }
        return Target(xMin..xMax, yMin..yMax)
    }

    data class Target(
        val xRange: IntRange,
        val yRange: IntRange
    )
}
