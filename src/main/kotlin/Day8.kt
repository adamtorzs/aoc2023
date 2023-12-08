import kotlin.math.max

class Day8 : Day {

    private var steps: String = ""
    private var nodes: HashMap<String, Node> = HashMap()
    override fun solve1(input: String): Any {
        steps = input.lines().first()
        getNodes(input.lines().toMutableList().drop(1))

        var currentNode = "AAA"
        var stepCounter: Long = 0

        while (currentNode != "ZZZ") {
            currentNode = when (steps[(stepCounter % steps.length).toInt()]) {
                'L' -> nodes[currentNode]?.left!!
                'R' -> nodes[currentNode]?.right!!
                else -> throw Exception("AAAAAAAAAA")
            }
            stepCounter++
        }
        return stepCounter
    }

    override fun solve2(input: String): Any {
        val startingNodes = nodes.keys.filter { it.last() == 'A' }
        val stepsForNodes = ArrayList<Long>()
        for (startingNode in startingNodes) {
            var stepCounter: Long = 0
            var currentNode = startingNode
            while (currentNode.last() != 'Z') {
                currentNode = when (steps[((stepCounter % steps.length).toInt())]) {
                    'L' -> nodes[currentNode]?.left!!
                    'R' -> nodes[currentNode]?.right!!
                    else -> throw Exception("AAAAAAAAAA")
                }
                stepCounter++
            }
            stepsForNodes.add(stepCounter)
        }

        return stepsForNodes.reduce(this::findLCM)
    }

    private fun getNodes(lines: List<String>): HashMap<String, Node> {
        val filteredLines = lines.toMutableList().filter { it.isNotEmpty() }

        for (line in filteredLines) {
            nodes[line.substring(0, 3)] = Node(line.substring(7, 10), line.substring(12, 15))
        }

        return nodes
    }

    private fun findLCM(a: Long, b: Long): Long {
        val larger = max(a, b)
        val maxLcm = a * b
        var lcm = larger
        while (lcm <= maxLcm) {
            if (lcm % a == lcm % b && lcm % a == 0L) {
                return lcm
            }
            lcm += larger
        }
        return maxLcm
    }

    override fun fileName(): String = "8"
}

class Node(val left: String, val right: String)