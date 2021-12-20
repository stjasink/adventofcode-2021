import common.Solver
import common.runAndTime
import common.loadInput

fun main() {
    val input = loadInput("day-20.txt")
    val solver = Day20()
    runAndTime(solver, input)
}

class Day20 : Solver {

    override fun part1(input: List<String>): Long {
        val mapping = input.first()
        val photo = Photo.from(input.drop(2))

        val enhancedPhoto1 = photo.enhance(mapping)
        val enhancedPhoto2 = enhancedPhoto1.enhance(mapping)

        return enhancedPhoto2.litPixels.size.toLong()
    }

    override fun part2(input: List<String>): Long {
        return 0L
    }

    data class Photo (
        val litPixels: Set<Pixel>,
        val outOfRangePixelsLit: Boolean = false
    ) {
        val minLitX = litPixels.minOf { it.x }
        val minLitY = litPixels.minOf { it.y }
        val maxLitX = litPixels.maxOf { it.x }
        val maxLitY = litPixels.maxOf { it.y }
        
        companion object {
            fun from(input: List<String>): Photo {
                val foundLitPixels = mutableSetOf<Pixel>()
                input.forEachIndexed { y, row ->
                    row.forEachIndexed { x, c ->
                        if (c == '#') {
                            foundLitPixels.add(Pixel(x, y))
                        }
                    }
                }
                return Photo(foundLitPixels)
            }
        }

        fun enhance(mapping: String): Photo {
            val enhancedLitPixels = mutableSetOf<Pixel>()
            for (y in minLitY-1 .. maxLitY+1) {
                for (x in minLitX-1 .. maxLitX+1) {
                    val pixel = Pixel(x, y)
                    val mappingIndex = codeForPixel(pixel)
                    val newChar = mapping[mappingIndex]
                    if (newChar == '#') {
                        enhancedLitPixels.add(pixel)
                    }
                }
            }
            val enhancedOutOfRangePixelsLit = if (outOfRangePixelsLit) {
                mapping[511] == '#'
            } else {
                mapping[0] == '#'
            }
            return Photo(enhancedLitPixels, enhancedOutOfRangePixelsLit)
        }

        fun print() {
            for (y in minLitY .. maxLitY) {
                for (x in minLitX .. maxLitX) {
                    val pixel = Pixel(x, y)
                    print(if (litPixels.contains(pixel)) '#' else '.')
                }
                println()
            }
            println()
        }

        fun codeForPixel(at: Pixel): Int {
            val binaryString = StringBuilder()
            binaryString.append(if (isLit(Pixel(at.x-1,at.y-1))) '1' else '0')
            binaryString.append(if (isLit(Pixel(at.x,at.y-1))) '1' else '0')
            binaryString.append(if (isLit(Pixel(at.x+1,at.y-1))) '1' else '0')
            binaryString.append(if (isLit(Pixel(at.x-1,at.y))) '1' else '0')
            binaryString.append(if (isLit(Pixel(at.x,at.y))) '1' else '0')
            binaryString.append(if (isLit(Pixel(at.x+1,at.y))) '1' else '0')
            binaryString.append(if (isLit(Pixel(at.x-1,at.y+1))) '1' else '0')
            binaryString.append(if (isLit(Pixel(at.x,at.y+1))) '1' else '0')
            binaryString.append(if (isLit(Pixel(at.x+1,at.y+1))) '1' else '0')
            return binaryString.toString().toInt(2)
        }
        
        fun isLit(at: Pixel): Boolean {
            if (at.x < minLitX || at.y < minLitY || at.x > maxLitX || at.y > maxLitY) {
                return outOfRangePixelsLit
            } else {
                return litPixels.contains(at)
            }
        }
//
//        fun findMinAndMaxLit(): List<Int> {
//            val minX = litPixels.minOf { it.x }
//            val minY = litPixels.minOf { it.y }
//            val maxX = litPixels.maxOf { it.x }
//            val maxY = litPixels.maxOf { it.y }
//            return listOf(minX, minY, maxX, maxY)
//        }

    }

    data class Pixel(
        val x: Int,
        val y: Int
    )
}