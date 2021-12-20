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
        val mapping = input.first()
        val photo = Photo.from(input.drop(2))

        val veryEnhancedPhoto = (1..50).fold(photo) { acc, _ -> acc.enhance(mapping) }

        return veryEnhancedPhoto.litPixels.size.toLong()
    }

    data class Photo (
        val litPixels: Set<Pixel>,
        val outOfRangePixelsLit: Boolean = false
    ) {
        private val minLitX = litPixels.minOf { it.x }
        private val minLitY = litPixels.minOf { it.y }
        private val maxLitX = litPixels.maxOf { it.x }
        private val maxLitY = litPixels.maxOf { it.y }
        
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
                    val newChar = mapping[mappingCodeForPixel(pixel)]
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

//        fun print() {
//            for (y in minLitY .. maxLitY) {
//                for (x in minLitX .. maxLitX) {
//                    val pixel = Pixel(x, y)
//                    print(if (litPixels.contains(pixel)) '#' else '.')
//                }
//                println()
//            }
//            println()
//        }

        private fun mappingCodeForPixel(at: Pixel): Int {
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
        
        private fun isLit(at: Pixel): Boolean {
            return if (at.x < minLitX || at.y < minLitY || at.x > maxLitX || at.y > maxLitY) {
                outOfRangePixelsLit
            } else {
                litPixels.contains(at)
            }
        }
    }

    data class Pixel(
        val x: Int,
        val y: Int
    )
}
