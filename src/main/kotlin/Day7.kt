class Day7 : Day {
    override fun solve1(input: String): Any =
        solve(input, ::Hand)

    override fun solve2(input: String): Any =
        solve(input, ::Hand2)

    private fun <T : Hand> solve(input: String, createHand: (String, Long) -> T): Long {
        val lines = input.lines()
        var hands = lines.map {
            val split = it.split(" ")
            createHand(split[0], split[1].toLong())
        }
        hands = hands.sorted()
        var sum: Long = 0
        hands.forEachIndexed { index, hand -> sum += (index + 1) * hand.bet }
        return sum
    }

    override fun fileName(): String = "7"
}

open class Hand(val cards: String, val bet: Long) : Comparable<Hand> {
    open val cardStrength: String = "23456789TJQKA"
    private fun getOrder(): Long {
        var order: Long = when {
            isFiveOfAKind() -> 6
            isFourOfAKind() -> 5
            isFullHouse() -> 4
            isThreeOfAKind() -> 3
            isTwoPair() -> 2
            isOnePair() -> 1
            else -> 0
        }
        for (card in cards) {
            order *= 100
            order += cardStrength.indexOf(card) + 1
        }
        return order
    }

    open fun isFiveOfAKind(): Boolean =
        cards == cards[0].toString().repeat(5)

    open fun isFourOfAKind(): Boolean =
        cards.count { it == cards[0] } == 4 || cards.count { it == cards[1] } == 4

    open fun isThreeOfAKind(): Boolean =
        cards.count { it == cards[0] } == 3 || cards.count { it == cards[1] } == 3 || cards.count { it == cards[2] } == 3

    open fun isFullHouse(): Boolean =
        cards.all { out -> cards.count { it == out } in 2..3 }

    open fun isTwoPair(): Boolean =
        cards.map { out -> cards.count { it == out } }.count { it == 2 } == 4

    open fun isOnePair(): Boolean =
        cards.map { out -> cards.count { it == out } }.count { it == 1 } == 3

    override fun compareTo(other: Hand): Int =
        getOrder().compareTo(other.getOrder())
}

class Hand2(cards: String, bet: Long) : Hand(cards, bet) {
    override val cardStrength: String = "J23456789TQKA"

    override fun isFiveOfAKind(): Boolean {
        val nonJoker = cards.filter { it != 'J' }
        return nonJoker.isEmpty() || nonJoker.count { it == nonJoker[0] } == nonJoker.length
    }

    override fun isFourOfAKind(): Boolean {
        val nonJoker = cards.filter { it != 'J' }
        return nonJoker.count { it == nonJoker[0] } == nonJoker.length - 1 || nonJoker.count { it == nonJoker[1] } == nonJoker.length - 1
    }

    override fun isThreeOfAKind(): Boolean {
        val nonJoker = cards.filter { it != 'J' }
        return nonJoker.count { it == nonJoker[0] } == nonJoker.length - 2 || nonJoker.count { it == nonJoker[1] } == nonJoker.length - 2 || nonJoker.count { it == nonJoker[2] } == nonJoker.length - 2
    }

    override fun isFullHouse(): Boolean =
        cards.toSet().size == 2 || (cards.toSet().size == 3 && cards.contains('J'))

    override fun isOnePair(): Boolean =
        cards.map { out -> cards.count { it == out } }
            .count { it == 1 } == 3 || (cards.map { out -> cards.count { it == out } }
            .count { it == 1 } == 5 && cards.contains('J'))

}