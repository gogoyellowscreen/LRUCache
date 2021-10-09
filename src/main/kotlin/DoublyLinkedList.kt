data class Node<K, V>(var next: Node<K, V>?, var prev: Node<K, V>?, val key: K?, var value: V?) : Iterator<Node<K, V>> {
    constructor() : this(null, null, null, null)

    override fun hasNext() = next != null

    override fun next() = next ?: throw NoSuchElementException()
}

class DoublyLinkedList<K, V> : Iterable<Node<K, V>> {
    private val head = Node<K, V>()
    private val tail = head
    var size = 0
        private set

    val first: Node<K, V>
        get() = head.next ?: throw NoSuchElementException()

    val last: Node<K, V>
        get() = if (head != tail) tail else throw NoSuchElementException()

    fun moveToFront(node: Node<K, V>) {
        TODO("Not yet implemented")
    }

    fun removeLast() {
        TODO("Not yet implemented")
    }

    fun addFirst(key: K, value: V): Node<K, V> {
        TODO("Not yet implemented")
    }

    override fun iterator() = head
}
