import common.Solver
import common.runAndTime
import common.loadInput

fun main() {
    val input = loadInput("day-18.txt")
    val solver = Day18()
    runAndTime(solver, input)
}

class Day18 : Solver {

    override fun part1(input: List<String>): Long {
        return 0L
    }

    override fun part2(input: List<String>): Long {
        return 0L
    }


}

data class SnailNumber(
    val leftVal: Int?,
    val left: SnailNumber?,
    val rightVal: Int?,
    val right: SnailNumber?,
    val depth: Int
) {
    companion object {
        fun from(input: String) = numberFromString(input, 1)
    }

    fun explode(): SnailNumber {
        var leftRegularNumber: Pair<SnailNumber, Char>? = null
        var rightRegularNumber: Pair<SnailNumber, Char>? = null
        var exploder: SnailNumber? = null

        fun findExploder(number: SnailNumber) {
            if (exploder == null && (number.leftVal != null)) {
                leftRegularNumber = Pair(number, 'L')
            }

            if (number.depth == 4 && exploder == null) { // only find one exploder per turn
                if (number.left != null) {
                    exploder = number.left
                } else if (number.right != null) {
                    exploder = number.right
                }
            }

            number.left?.let { if (it != exploder) findExploder(it) }

            if (exploder == null && (number.rightVal != null)) {
                leftRegularNumber = Pair(number, 'R')
            }

            if (exploder != null && number.right != exploder && rightRegularNumber == null && number.leftVal != null) {
                rightRegularNumber = Pair(number, 'L')
            }

            number.right?.let { if (it != exploder) findExploder(it) }

            if (exploder != null && number != exploder && rightRegularNumber == null && number.rightVal != null) {
                rightRegularNumber = Pair(number, 'R')
            }
        }

        fun doExploding(number: SnailNumber): SnailNumber {
            val newLeftVal = if (number == leftRegularNumber?.first && leftRegularNumber?.second == 'L') {
                number.leftVal!! + exploder!!.leftVal!!
            } else if (number == rightRegularNumber?.first && rightRegularNumber?.second == 'L') {
                number.leftVal!! + exploder!!.rightVal!!
            } else if (number.left == exploder) {
                0
            } else number.leftVal

            val newLeft = if (number.left == exploder) null else number.left?.let { doExploding(it) }

            val newRightVal = if (number == leftRegularNumber?.first && leftRegularNumber?.second == 'R') {
                number.rightVal!! + exploder!!.leftVal!!
            } else if (number == rightRegularNumber?.first && rightRegularNumber?.second == 'R') {
                number.rightVal!! + exploder!!.rightVal!!
            } else if (number.right == exploder) {
                0
            } else number.rightVal

            val newRight = if (number.right == exploder) null else number.right?.let { doExploding(it) }

            return SnailNumber(newLeftVal, newLeft, newRightVal, newRight, number.depth)
        }

        findExploder(this)
        return if (exploder != null) {
            doExploding(this)
        } else {
            this
        }
    }

    fun split(): SnailNumber {
        val newLeftVal: Int?
        val newRightVal: Int?
        val newLeft: SnailNumber?
        val newRight: SnailNumber?

        if (leftVal != null) {
            if (leftVal >= 10) {
                val splitLeftVal = leftVal / 2
                val splitRightVal = leftVal / 2 + (if (leftVal % 2 == 1) 1 else 0)
                newLeftVal = null
                newLeft = SnailNumber(splitLeftVal, null, splitRightVal, null, depth+1)
            } else {
                newLeftVal = leftVal
                newLeft = null
            }
        } else {
            newLeftVal = null
            newLeft = left!!.split()
        }
        if (rightVal != null) {
            if (rightVal >= 10) {
                val splitLeftVal = rightVal / 2
                val splitRightVal = rightVal / 2 + (if (rightVal % 2 == 1) 1 else 0)
                newRightVal = null
                newRight = SnailNumber(splitLeftVal, null, splitRightVal, null, depth+1)
            } else {
                newRightVal = rightVal
                newRight = null
            }
        }  else {
            newRightVal = null
            newRight = right!!.split()
        }

        return SnailNumber(newLeftVal, newLeft, newRightVal, newRight, depth)
    }

    private fun explodeOrSplit(): SnailNumber {
        val exploded = explode()
        if (exploded != this) {
            return exploded
        }
        return split()
    }

    fun reduce(): SnailNumber {
        var previousReduction = this
        println(this.toSnailString())
//        println(this.toDepthString())
        do {
            val thisReduction = previousReduction.explodeOrSplit()
            println(thisReduction.toSnailString())
//            println(thisReduction.toDepthString())
            if (thisReduction == previousReduction) {
                return thisReduction
            }
            previousReduction = thisReduction
        } while (true)
    }

    fun toSnailString(): String {
        val leftString = leftVal ?: left!!.toSnailString()
        val rightString = rightVal ?: right!!.toSnailString()
        return "[$leftString,$rightString]"
    }

    fun toDepthString(): String {
        val leftString = left?.toDepthString().orEmpty()
        val rightString = right?.toDepthString().orEmpty()
        return "[$leftString $depth $rightString]"
    }
}

fun numberFromString(input: String, depth: Int): SnailNumber {

    fun splitOnTopLevelComma(input: String): Pair<String, String> {
        var openBracketCount = 0
        input.forEachIndexed { index, c ->
            when (c) {
                '[' -> openBracketCount += 1
                ']' -> openBracketCount -= 1
                ',' -> {
                    if (openBracketCount == 0) {
                        return Pair(input.substring(0, index), input.substring(index+1))
                    }
                }
            }
        }
        throw IllegalStateException("Did not find match")
    }

    val twoDigits = Regex("^\\[(\\d+),(\\d+)]")
    val firstDigit = Regex("^\\[(\\d+),(\\[.*)]\$")
    val lastDigit = Regex("^\\[(.*),(\\d+)]\$")

    if (twoDigits.matches(input)) {
        val matches = twoDigits.matchEntire(input)!!.groupValues
        return SnailNumber(matches[1].toInt(), null, matches[2].toInt(), null, depth)
    } else if (firstDigit.matches(input)) {
        val matches = firstDigit.matchEntire(input)!!.groupValues
        return SnailNumber(matches[1].toInt(), null, null, numberFromString(matches[2], depth+1), depth)
    } else if (lastDigit.matches(input)) {
        val matches = lastDigit.matchEntire(input)!!.groupValues
        return SnailNumber(null, numberFromString(matches[1], depth+1), matches[2].toInt(), null, depth)
    } else if (input.startsWith("[[")) {
        val inputWithoutBrackets = input.drop(1).dropLast(1)
        val (leftString, rightString) = splitOnTopLevelComma(inputWithoutBrackets)
        return SnailNumber(null, numberFromString(leftString, depth+1), null, numberFromString(rightString, depth+1), depth)
    } else {
        throw IllegalStateException("Not sure what is happening here: $input")
    }

}

