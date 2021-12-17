import common.Solver
import common.runAndTime
import common.loadInput
import kotlin.math.absoluteValue
import kotlin.math.max

fun main() {
    val input = loadInput("day-17.txt")
    val solver = Day17()
    runAndTime(solver, input)
}

class Day17 : Solver {

    override fun part1(input: List<String>): Long {
        val target = parseTarget(input.first())
        val landed = plotAllWithMaxHeight(target)
        return landed.values.maxOrNull()!!.toLong()
    }

    override fun part2(input: List<String>): Long {
        val target = parseTarget(input.first())
        val landed = plotAllWithMaxHeight(target)
        return landed.size.toLong()
    }

    private fun plotAllWithMaxHeight(target: Target): Map<Pair<Int, Int>, Int> {
        val landed = mutableMapOf<Pair<Int, Int>, Int>()
        for (yVel in target.yRange.first..target.yRange.first.absoluteValue) {
            for (xVel in 1..target.xRange.last) {
                val (foundTarget, maxY) = plotLaunch(xVel, yVel, target)
                if (foundTarget) {
                    landed.put(Pair(xVel, yVel), maxY)
                }
            }
        }
        return landed
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
            xVel = if (xVel > 0) xVel - 1 else if (xVel < 0) xVel + 1 else 0
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
