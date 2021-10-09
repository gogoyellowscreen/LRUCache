import kotlin.collections.HashMap

abstract class LRUCache<K, V>(private val capacity: Int) {

    protected var size = 0
    protected val map = HashMap<K, Node<K, V>>()
    protected val doublyLinkedList = DoublyLinkedList<K, V>()

    fun put(key: K, value: V) {
        val sizeBeforeOp = size
        doPut(key, value)
        assert(size >= sizeBeforeOp) {
            "Size decreases after put operation."
        }
        runCommonAsserts(value)
    }

    fun get(key: K): V? {
        val sizeBeforeOp = size
        val value = doGet(key)
        assert(sizeBeforeOp == size) {
            "Read operation modifies cache."
        }
        runCommonAsserts(value)
        return value
    }

    private fun runCommonAsserts(getOrPutValue: V?) {
        assert(size in 0 until capacity) {
            "Invalid size."
        }
        assert(mapKeysAndKeyListAreSame()) {
            "LRUCache is in inconsistent state."
        }
        getOrPutValue?.let {
            assert(doublyLinkedList.first.value == it) {
                "Get or put value is not most recent."
            }
        }
    }

    private fun mapKeysAndKeyListAreSame(): Boolean {
        val listKeys = doublyLinkedList.map { it.key }

        if (listKeys.size != map.keys.size) return false

        return map.keys.containsAll(doublyLinkedList.map { it.key })
                && doublyLinkedList.map { it.key }.containsAll(map.keys)
    }

    protected abstract fun doPut(key: K, value: V)
    protected abstract fun doGet(key: K): V?
}
