import kotlin.math.ceil

class Day10 : Day {

    private val nodes = HashMap<Pair<Int, Int>, Node10>()
    override fun solve1(input: String): Any {

        input.lines().forEachIndexed { y, line ->
            line.forEachIndexed { x, char ->
                nodes[x to y] = Node10(x, y, char)
            }
        }

        input.lines().forEachIndexed { y, line ->
            line.forEachIndexed { x, char ->
                nodes[x to y]?.apply {
                    when (char) {
                        'F' -> this.neighbors = listOf(nodes[x to y + 1], nodes[x + 1 to y])
                        'L' -> this.neighbors = listOf(nodes[x to y - 1], nodes[x + 1 to y])
                        '7' -> this.neighbors = listOf(nodes[x to y + 1], nodes[x - 1 to y])
                        'J' -> this.neighbors = listOf(nodes[x to y - 1], nodes[x - 1 to y])
                        '|', 'S' -> this.neighbors = listOf(nodes[x to y - 1], nodes[x to y + 1])
                        '-' -> this.neighbors = listOf(nodes[x - 1 to y], nodes[x + 1 to y])
                    }
                }
            }
        }

        val startNode = getStartNode(nodes)!!
        startNode.steps = 0
        var previousNode = startNode
        var currentNode = startNode.neighbors[0]!!

        while (currentNode != startNode) {
            if (currentNode.steps == -1) currentNode.steps = previousNode.steps + 1
            val nextNode = currentNode.neighbors.filter { it?.steps!! <= 0 }.minBy { it?.steps!! }
            previousNode = currentNode
            currentNode = nextNode!!
        }

        return ceil(previousNode.steps / 2.0)
    }

    override fun solve2(input: String): Any {
        markNonLoop(nodes)
        return nodes.values.count { it.char == 'X' && isInsideLoop(it) }
    }

    private fun isInsideLoop(node: Node10): Boolean {
        var inside = false
        val s = buildString { (0..node.x).forEach { append(nodes[it to node.y]?.char) } }
            .filter { "FLJ7|S".contains(it) }

        s.indices.forEach {
            when (s[it]) {
                'F' -> inside = inside xor (s[it + 1] == 'J')
                'L' -> inside = inside xor (s[it + 1] == '7')
                '|', 'S' -> inside = !inside
            }
        }

        return inside
    }

    override fun fileName(): String = "10"
    private fun getStartNode(map: HashMap<Pair<Int, Int>, Node10>): Node10? = map.values.find { it.char == 'S' }

    private fun markNonLoop(map: HashMap<Pair<Int, Int>, Node10>) {
        map.forEach { (_, node) ->
            if (node.steps == -1) {
                node.char = 'X'
            }
        }
    }
}

class Node10(val x: Int, val y: Int, var char: Char) {
    lateinit var neighbors: List<Node10?>
    var steps = -1

}