import kotlin.math.ceil

class Day10 : Day {

    val nodes = ArrayList<Node10>()
    override fun solve1(input: String): Any {

        val lines = input.lines()
        lines.forEachIndexed { y, line ->
            line.forEachIndexed { x, char ->
                run {
                    nodes.add(Node10(x, y, char))
                }
            }
        }

        lines.forEachIndexed { y, line ->
            line.forEachIndexed { x, char ->
                run {
                    when (char) {
                        'F' -> getNode(nodes, x, y)?.addNeighbor(getNode(nodes, x, y + 1))
                            ?.addNeighbor(getNode(nodes, x + 1, y))

                        'L' -> getNode(nodes, x, y)?.addNeighbor(getNode(nodes, x, y - 1))
                            ?.addNeighbor(getNode(nodes, x + 1, y))

                        '7' -> getNode(nodes, x, y)?.addNeighbor(getNode(nodes, x, y + 1))
                            ?.addNeighbor(getNode(nodes, x - 1, y))

                        'J' -> getNode(nodes, x, y)?.addNeighbor(getNode(nodes, x, y - 1))
                            ?.addNeighbor(getNode(nodes, x - 1, y))

                        '|', 'S' -> getNode(nodes, x, y)?.addNeighbor(getNode(nodes, x, y - 1))
                            ?.addNeighbor(getNode(nodes, x, y + 1))

                        '-' -> getNode(nodes, x, y)?.addNeighbor(getNode(nodes, x - 1, y))
                            ?.addNeighbor(getNode(nodes, x + 1, y))
                    }
                }
            }
        }

        val startNode = getStartNode(nodes)!!
        startNode.steps = 0
        var previousNode = startNode
        var currentNode = startNode.neighbors[0]

        while (currentNode != startNode) {
            if (currentNode.steps == -1) currentNode.steps = previousNode.steps + 1
            val nextNode = currentNode.neighbors.filter { it.steps <= 0 }.minBy { it.steps }
            previousNode = currentNode
            currentNode = nextNode
        }

        return ceil(previousNode.steps / 2.0)
    }

    override fun solve2(input: String): Any {
        markNonLoop(nodes)
        var innerNodes = 0
        for (node in nodes.filter { it.char == 'X' }) {
            var inside = false
            var s = buildString { for (i in 0..node.x) append(getNode(nodes, i, node.y)?.char) }
            s = s.filter { "FLJ7|S".contains(it) }
            for (i in s.indices) {
                when (s[i]) {
                    'F' -> inside = inside.xor(s[i + 1] == 'J')
                    'L' -> inside = inside.xor(s[i + 1] == '7')
                    '|', 'S' -> inside = !inside
                }
            }
            if (inside) {
                innerNodes++
            }
        }

        return innerNodes
    }

    override fun fileName(): String = "10"

    private fun getNode(list: List<Node10>, x: Int, y: Int): Node10? {
        for (node in list) {
            if (node.x == x && node.y == y) {
                return node
            }
        }
        return null
    }

    private fun getStartNode(list: List<Node10>): Node10? {
        for (node in list) {
            if (node.char == 'S') {
                return node
            }
        }
        return null
    }

    private fun markNonLoop(list: List<Node10>) {
        for (node in list) {
            if (node.steps == -1) {
                node.char = 'X'
            }
        }
    }
}

class Node10(val x: Int, val y: Int, var char: Char) {
    val neighbors = ArrayList<Node10>()
    var steps = -1

    fun addNeighbor(node: Node10?): Node10 {
        if (node != null) {
            neighbors.add(node)
        }
        return this
    }
}