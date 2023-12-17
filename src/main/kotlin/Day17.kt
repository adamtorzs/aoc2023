import java.util.*

class Day17 : Day {

    data class QueueItem(val point: Pair<Int, Int>, val direction: Direction, val steps: Int, val cost: Int) :
        Comparable<QueueItem> {
        override fun compareTo(other: QueueItem): Int = this.cost.compareTo(other.cost)
    }

    override fun solve1(input: String): Any {
        val lines = input.lines()
        val pq = PriorityQueue<QueueItem>()
        val seen = HashSet<Triple<Pair<Int, Int>, Direction?, Int>>()

        pq.add(QueueItem(0 to 0, Direction.LEFT, 0, 0))
        pq.add(QueueItem(0 to 0, Direction.RIGHT, 0, 0))
        pq.add(QueueItem(0 to 0, Direction.UP, 0, 0))
        pq.add(QueueItem(0 to 0, Direction.DOWN, 0, 0))

        val endPoint = lines.first().lastIndex to lines.lastIndex

        while (pq.isNotEmpty()) {
            val current = pq.poll()
            if (seen.contains(Triple(current.point, current.direction, current.steps))) {
                continue
            }
            if (current.point == endPoint) return current.cost
            seen.add(Triple(current.point, current.direction, current.steps))
            val nextPoints: ArrayList<QueueItem> = ArrayList()
            if (current.steps < 3) {
                val nextPoint = current.direction.nextInDirection(current.point.first, current.point.second)
                nextPoints.add(
                    QueueItem(
                        nextPoint,
                        current.direction,
                        current.steps + 1,
                        current.cost + getCost(nextPoint, lines)
                    )
                )
            }
            val changeDirs = when (current.direction.isVertical()) {
                true -> listOf(Direction.RIGHT, Direction.LEFT)
                false -> listOf(Direction.DOWN, Direction.UP)
            }
            for (nextDir in changeDirs) {
                val nextPoint = nextDir.nextInDirection(current.point.first, current.point.second)
                nextPoints.add(QueueItem(nextPoint, nextDir, 1, current.cost + getCost(nextPoint, lines)))
            }
            pq.addAll(nextPoints)
        }
        return -1
    }

    override fun solve2(input: String): Any {
        val lines = input.lines()
        val pq = PriorityQueue<QueueItem>()
        val seen = HashSet<Triple<Pair<Int, Int>, Direction, Int>>()

        pq.add(QueueItem(0 to 0, Direction.LEFT, 0, 0))
        pq.add(QueueItem(0 to 0, Direction.RIGHT, 0, 0))
        pq.add(QueueItem(0 to 0, Direction.UP, 0, 0))
        pq.add(QueueItem(0 to 0, Direction.DOWN, 0, 0))


        val endPoint = lines.first().lastIndex to lines.lastIndex

        while (pq.isNotEmpty()) {
            val current = pq.poll()
            if (seen.contains(Triple(current.point, current.direction, current.steps))) {
                continue
            }

            if (current.point == endPoint && current.steps >= 4) return current.cost
            seen.add(Triple(current.point, current.direction, current.steps))
            val nextPoints: ArrayList<QueueItem> = ArrayList()
            if (current.steps < 10) {
                val nextPoint = current.direction.nextInDirection(current.point.first, current.point.second)
                nextPoints.add(
                    QueueItem(
                        nextPoint,
                        current.direction,
                        current.steps + 1,
                        current.cost + getCost(nextPoint, lines)
                    )
                )
            }
            val changeDirs = when (current.direction.isVertical()) {
                true -> listOf(Direction.RIGHT, Direction.LEFT)
                false -> listOf(Direction.DOWN, Direction.UP)
            }
            if (current.steps >= 4) {
                for (nextDir in changeDirs) {
                    val nextPoint = nextDir.nextInDirection(current.point.first, current.point.second)
                    nextPoints.add(QueueItem(nextPoint, nextDir, 1, current.cost + getCost(nextPoint, lines)))
                }
            }
            pq.addAll(nextPoints)
        }
        return -1
    }

    private fun validPointInGrid(point: Pair<Int, Int>, grid: List<String>): Boolean =
        point.first >= 0 && point.second >= 0 && point.first <= grid.first().lastIndex && point.second <= grid.lastIndex

    private fun getCost(point: Pair<Int, Int>, grid: List<String>): Int =
        when (validPointInGrid(point, grid)) {
            true -> grid[point.first][point.second].digitToInt()
            false -> Int.MAX_VALUE - 10000
        }

    override fun fileName(): String = "17"
}