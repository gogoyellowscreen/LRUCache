import java.lang.IllegalStateException
import javax.swing.text.html.HTMLDocument

data class Node<K, V>(var next: Node<K, V>?, var prev: Node<K, V>?, val key: K?, var value: V?) {
    constructor() : this(null, null, null, null)
    constructor(key: K?, value: V?) : this(null, null, key, value)
}

class DoublyLinkedList<K, V> : Iterable<Node<K, V>> {
    private val head = Node<K, V>()
    private var tail = head

    val first: Node<K, V>
        get() = head.next ?: throw NoSuchElementException()

    val last: Node<K, V>
        get() = if (head != tail) tail else throw NoSuchElementException()

    fun moveToFront(node: Node<K, V>) {
        if (node == tail) {
            tail = node.prev ?: throw IllegalStateException("Must be pre-tail node when non empty.")
        }
        node.apply {
            next?.prev = prev
            prev?.next = next
            next = null
            prev = null
            addFirst(this)
        }
    }

    fun removeLast() {
        tail.prev?.let { prev ->
            prev.next = null
            tail.prev = null
            tail = prev
        }
    }

    fun addFirst(key: K, value: V): Node<K, V> {
        return addFirst(Node(key, value))
    }

    private fun addFirst(newNode: Node<K, V>): Node<K, V> {
        return newNode.apply {
            next = head.next
            prev = head
            next?.prev = this
            head.next = this
            if (head == tail) {
                tail = this
            }
        }
    }

    override fun iterator() = object : Iterator<Node<K, V>> {
        private var iterator: Node<K, V> = head
        override fun hasNext() = iterator.next != null
        override fun next(): Node<K, V> {
            iterator = iterator.next ?: throw NoSuchElementException()
            return iterator
        }
    }
}
