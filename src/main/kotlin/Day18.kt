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
        var leftRegularNumber: SnailNumber? = null
        var rightRegularNumber: SnailNumber? = null
        var exploder: SnailNumber? = null

        fun findExploder(number: SnailNumber) {
            if (exploder == null && (number.leftVal != null)) {
                leftRegularNumber = number
            }
            if (number.depth == 4) {
                if (number.left != null) {
                    exploder = number.left
                } else if (number.right != null) {
                    exploder = number.right
                }
            }
            if (exploder != null && number != exploder && (number.leftVal != null || number.rightVal != null)) {
                rightRegularNumber = number
            }
            number.left?.let { findExploder(it) }
            number.right?.let { findExploder(it) }
        }

        fun doExploding(number: SnailNumber): SnailNumber {
            val newLeftVal = if (number == leftRegularNumber) {
                if (number.rightVal != null) {
                    number.rightVal + exploder!!.leftVal!!
                } else if (number.leftVal != null) {
                    number.leftVal + exploder!!.leftVal!!
                } else {
                    throw IllegalStateException("Expected leftVal or rightVal to be not null")
                }
            } else if (number.left == exploder) {
                0
            } else number.leftVal

            val newRightVal = if (number == rightRegularNumber) {
                if (number.leftVal != null) {
                    number.leftVal + exploder!!.rightVal!!
                } else if (number.rightVal != null) {
                    number.rightVal + exploder!!.rightVal!!
                } else {
                    throw IllegalStateException("Expected leftVal or rightVal to be not null")
                }
            } else if (number.right == exploder) {
                0
            } else number.rightVal

            val newLeft = if (number.left == exploder) null else number.left?.let { doExploding(it) }

            val newRight = if (number.right == exploder) null else number.right?.let { doExploding(it) }

            return SnailNumber(newLeftVal, newLeft, newRightVal, newRight, depth)
        }

        findExploder(this)
        return if (exploder != null) {
            doExploding(this)
        } else {
            this
        }
    }

    fun reduce(): SnailNumber {
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
            newLeft = left!!.reduce()
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
            newRight = right!!.reduce()
        }



        return SnailNumber(newLeftVal, newLeft, newRightVal, newRight, depth)
    }



    fun toSnailString(): String {
        val leftString = leftVal ?: left!!.toSnailString()
        val rightString = rightVal ?: right!!.toSnailString()
        return "[$leftString,$rightString]"
    }
}



//
//fun doExplode(input: String): String {
//    val leftBracketPattern = Regex("\\[\\[\\[\\[(\\d),(\\d)]")
//    val numberPattern = Regex("(\\d+)")
//    val leftNumberPattern = Regex("\\[(\\d+)")
//
//    val newStringToLeft: String
//    val newStringToRight: String
//
//    if (leftBracketPattern.find(input) != null) {
//        println("matched left")
//        val match = leftBracketPattern.find(input)!!
//        val stringToLeft = input.substring(0, match.range.first)
//        val stringToRight = input.substring(match.range.last+1)
//        val leftNumberMatches = leftNumberPattern.findAll(stringToLeft).toList()
//        val rightNumberMatch = numberPattern.find(stringToRight)
//
//        newStringToLeft = if (leftNumberMatches.isNotEmpty()) {
//            val leftNumberMatch = leftNumberMatches.last()
//            val newValue = match.groupValues[1].toInt() + leftNumberMatch.groupValues[1].toInt()
//            stringToLeft.replaceRange(leftNumberMatch.range, newValue.toString())
//        } else stringToLeft
//
//        newStringToRight = if (rightNumberMatch != null) {
//            val newValue = match.groupValues[2].toInt() + rightNumberMatch.groupValues[1].toInt()
//            stringToRight.replaceRange(rightNumberMatch.range, newValue.toString())
//        } else stringToRight
//
//        return newStringToLeft + "[[[0" + newStringToRight
//    }
//
//    return ""
//}
//
//fun doExplode2(input: String): String {
//
//    val leftBracketPattern = Regex("\\[\\[\\[\\[(\\d),(\\d)]")
//    val numberPattern = Regex("(\\d+)")
//    val leftNumberPattern = Regex("\\[(\\d+)")
//
//    val newStringToLeft: String
//    val newStringToRight: String
//
//    if (input.contains("[[[[") && input.indexOf("[[[[") > 0) {
//        val matchStart = input.indexOf("[[[[")
//        val stringToLeft = input.substring(0, matchStart+1)
//        val stringToRight = input.substring(matchStart+9)
//        val leftNumberMatches = leftNumberPattern.findAll(stringToLeft).toList()
//        val rightNumberMatch = numberPattern.find(stringToRight)
//
//        newStringToLeft = if (leftNumberMatches.isNotEmpty()) {
//            val leftNumberMatch = leftNumberMatches.last()
//            val oldValue = input[matchStart+5].digitToInt()
//            val newValue = oldValue + leftNumberMatch.groupValues[1].toInt()
//            stringToLeft.replaceRange(leftNumberMatch.range, newValue.toString())
//        } else stringToLeft
//
//        newStringToRight = if (rightNumberMatch != null) {
//            val oldValue = input[matchStart+7].digitToInt()
//            val newValue = oldValue + rightNumberMatch.groupValues[1].toInt()
//            stringToRight.replaceRange(rightNumberMatch.range, newValue.toString())
//        } else stringToRight
//
//        return newStringToLeft + "[[[0" + newStringToRight
//    }
//
//    return ""
//}


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

