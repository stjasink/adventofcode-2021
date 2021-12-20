import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class Day18Test {

//    @Test
    fun part1Test() {
        val input = """
            [[[0,[5,8]],[[1,7],[9,6]]],[[4,[1,2]],[[1,4],2]]]
            [[[5,[2,8]],4],[5,[[9,9],0]]]
            [6,[[[6,2],[5,6]],[[7,6],[4,7]]]]
            [[[6,[0,7]],[0,9]],[4,[9,[9,0]]]]
            [[[7,[6,4]],[3,[1,3]]],[[[5,5],1],9]]
            [[6,[[7,3],[3,2]]],[[[3,8],[5,7]],4]]
            [[[[5,4],[7,7]],8],[[8,3],8]]
            [[9,3],[[9,9],[6,[4,9]]]]
            [[2,[[7,7],7]],[[5,8],[[9,3],[0,2]]]]
            [[[[5,2],5],[8,[3,7]]],[[5,[7,5]],[4,4]]]
        """.trimIndent().split('\n')

        val answer = Day18().part1(input)
        assertEquals(4140L, answer)
    }

    @Test
    fun part1Test1() {
        val input = """
            [1,1]
            [2,2]
            [3,3]
            [4,4]
        """.trimIndent().split('\n')

        val answer = Day18().addAll(input)
        assertEquals("[[[[1,1],[2,2]],[3,3]],[4,4]]", answer.toSnailString())
    }

    @Test
    fun part1Test2() {
        val input = """
            [1,1]
            [2,2]
            [3,3]
            [4,4]
            [5,5]
        """.trimIndent().split('\n')

        val answer = Day18().addAll(input)
        assertEquals("[[[[3,0],[5,3]],[4,4]],[5,5]]", answer.toSnailString())
    }

    @Test
    fun part1Test3() {
        val input = """
            [1,1]
            [2,2]
            [3,3]
            [4,4]
            [5,5]
            [6,6]
        """.trimIndent().split('\n')

        val answer = Day18().addAll(input)
        assertEquals("[[[[5,0],[7,4]],[5,5]],[6,6]]", answer.toSnailString())
    }

//    @Test
    fun part1Test4() {
        val input = """
            [[[0,[4,5]],[0,0]],[[[4,5],[2,6]],[9,5]]]
            [7,[[[3,7],[4,3]],[[6,3],[8,8]]]]
            [[2,[[0,8],[3,4]]],[[[6,7],1],[7,[1,6]]]]
            [[[[2,4],7],[6,[0,5]]],[[[6,8],[2,8]],[[2,1],[4,5]]]]
            [7,[5,[[3,8],[1,4]]]]
            [[2,[2,2]],[8,[8,1]]]
            [2,9]
            [1,[[[9,3],9],[[9,0],[0,7]]]]
            [[[5,[7,4]],7],1]
            [[[[4,2],2],6],[8,7]]
        """.trimIndent().split('\n')

        val answer = Day18().addAll(input)
        assertEquals("[[[[8,7],[7,7]],[[8,6],[7,7]]],[[[0,7],[6,6]],[8,7]]]", answer.toSnailString())
    }

    @Test
    fun part1TestAdd1() {
        val num1 = "[[[0,[4,5]],[0,0]],[[[4,5],[2,6]],[9,5]]]"
        val num2 = "[7,[[[3,7],[4,3]],[[6,3],[8,8]]]]"
        val expected = "[[[[4,0],[5,4]],[[7,7],[6,0]]],[[8,[7,7]],[[7,9],[5,0]]]]"
        assertEquals(expected, SnailNumber.from(num1).add(SnailNumber.from(num2)).toSnailString())
    }

    @Test
    fun part1TestParse1() {
        val answer = SnailNumber.from("[1,1]")
        assertEquals(SnailNumber(1, null, 1, null, 1), answer)
    }

    @Test
    fun part1TestParse2() {
        val answer = SnailNumber.from("[[0,4],5]")
        assertEquals(SnailNumber(null, SnailNumber(0, null, 4, null, 2), 5, null, 1), answer)
    }

    @Test
    fun part1TestParse3() {
        val answer = SnailNumber.from("[0,[4,5]]")
        assertEquals(SnailNumber(0, null, null, SnailNumber(4, null, 5, null, 2), 1), answer)
    }

    @Test
    fun part1TestParse4() {
        val answer = SnailNumber.from("[0,[4,[1,2]]]")
        assertEquals(SnailNumber(0, null, null, SnailNumber(4, null, null, SnailNumber(1, null, 2, null, 3), 2), 1), answer)
    }

    @Test
    fun part1TestParse5() {
        val input = "[[[0,[4,5]],[0,0]],[[[4,5],[2,6]],[9,5]]]"
        assertEquals(SnailNumber.from(input).toSnailString(), input)
    }

    @Test
    fun part1TestParse6() {
        val input = "[[[[[9,8],1],2],3],4]"
        assertEquals(SnailNumber.from(input).toSnailString(), input)
    }

    @Test
    fun part1TestSplit1() {
        val input = "[[[[0,7],4],[15,[0,13]]],[1,1]]"
        val output = "[[[[0,7],4],[[7,8],[0,13]]],[1,1]]"
        assertEquals(output, SnailNumber.from(input).split().toSnailString())
    }

    @Test
    fun part1TestSplit2() {
        val input = "[[[[0,7],4],[[7,8],[0,13]]],[1,1]]"
        val output = "[[[[0,7],4],[[7,8],[0,[6,7]]]],[1,1]]"
        assertEquals(output, SnailNumber.from(input).split().toSnailString())
    }

    @Test
    fun part1TestSplit3() {
        val input = "[[[[4,0],[5,0]],[[2,0],[15,5]]],[10,[[11,9],[11,0]]]]"
        val output = "[[[[4,0],[5,0]],[[2,0],[[7,8],5]]],[10,[[11,9],[11,0]]]]"
        assertEquals(output, SnailNumber.from(input).split().toSnailString())
    }

    @Test
    fun part1TestExplode1() {
        val input = "[[[[[9,8],1],2],3],4]"
        val output = "[[[[0,9],2],3],4]"
        assertEquals(output, SnailNumber.from(input).explode().toSnailString())
    }

    @Test
    fun part1TestExplode2() {
        val input = "[7,[6,[5,[4,[3,2]]]]]"
        val output = "[7,[6,[5,[7,0]]]]"
        assertEquals(output, SnailNumber.from(input).explode().toSnailString())
    }

    @Test
    fun part1TestExplode3() {
        val input = "[[6,[5,[4,[3,2]]]],1]"
        val output = "[[6,[5,[7,0]]],3]"
        assertEquals(output, SnailNumber.from(input).explode().toSnailString())
    }

    @Test
    fun part1TestExplode4() {
        val input = "[[3,[2,[1,[7,3]]]],[6,[5,[4,[3,2]]]]]"
        val output = "[[3,[2,[8,0]]],[9,[5,[4,[3,2]]]]]"
        assertEquals(output, SnailNumber.from(input).explode().toSnailString())
    }

    @Test
    fun part1TestExplode5() {
        val input = "[[3,[2,[8,0]]],[9,[5,[4,[3,2]]]]]"
        val output = "[[3,[2,[8,0]]],[9,[5,[7,0]]]]"
        assertEquals(output, SnailNumber.from(input).explode().toSnailString())
    }

    @Test
    fun part1TestExplode6() {
        val input = "[[[[[4,3],4],4],[7,[[8,4],9]]],[1,1]]"
        val output = "[[[[0,7],4],[7,[[8,4],9]]],[1,1]]"
        assertEquals(output, SnailNumber.from(input).explode().toSnailString())
    }

    @Test
    fun part1TestExplode7() {
        val input = "[[[[0,7],4],[7,[[8,4],9]]],[1,1]]"
        val output = "[[[[0,7],4],[15,[0,13]]],[1,1]]"
        assertEquals(output, SnailNumber.from(input).explode().toSnailString())
    }
//
//    @Test
//    fun part1TestReduce1() {
//        val input = "[[3,[2,[1,[7,3]]]],[6,[5,[4,[3,2]]]]]"
//        val output = "[[3,[2,[8,0]]],[9,[5,[4,[3,2]]]]]"
//        assertEquals(output, SnailNumber.from(input).reduce().toSnailString())
//    }
//
//    @Test
//    fun part1TestReduce2() {
//        val input = "[[3,[2,[1,[7,3]]]],[6,[5,[4,[3,2]]]]]"
//        val output = "[[3,[2,[8,0]]],[9,[5,[4,[3,2]]]]]"
//        assertEquals(output, SnailNumber.from(input).reduce().toSnailString())
//    }

    @Test
    fun part1TestReduce3() {
        val input = "[[[[[4,3],4],4],[7,[[8,4],9]]],[1,1]]"
        val output = "[[[[0,7],4],[[7,8],[6,0]]],[8,1]]"
        assertEquals(output, SnailNumber.from(input).reduce().toSnailString())
    }

    @Test
    fun part1TestMagnitude1() {
        val input = "[[1,2],[[3,4],5]]"
        assertEquals(143, SnailNumber.from(input).magnitude())
    }

    @Test
    fun part1TestMagnitude2() {
        val input = "[[[[0,7],4],[[7,8],[6,0]]],[8,1]]"
        assertEquals(1384, SnailNumber.from(input).magnitude())
    }

    @Test
    fun part1TestMagnitude3() {
        val input = "[[[[1,1],[2,2]],[3,3]],[4,4]]"
        assertEquals(445, SnailNumber.from(input).magnitude())
    }

    @Test
    fun part1TestMagnitude4() {
        val input = "[[[[3,0],[5,3]],[4,4]],[5,5]]"
        assertEquals(791, SnailNumber.from(input).magnitude())
    }

    @Test
    fun part1TestMagnitude5() {
        val input = "[[[[5,0],[7,4]],[5,5]],[6,6]]"
        assertEquals(1137, SnailNumber.from(input).magnitude())
    }

    @Test
    fun part1TestMagnitude6() {
        val input = "[[[[8,7],[7,7]],[[8,6],[7,7]]],[[[0,7],[6,6]],[8,7]]]"
        assertEquals(3488, SnailNumber.from(input).magnitude())
    }

    @Test
    fun part2Test() {
        val input = """
        """.trimIndent().split('\n')

        val answer = Day18().part2(input)
        assertEquals(0L, answer)
    }
}
