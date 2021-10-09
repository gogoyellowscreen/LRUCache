import java.util.*
import kotlin.collections.HashMap

abstract class LRUCache<K, V>(private val capacity: Int) {

    protected var size = 0
    protected val map = HashMap<K, V>()
    protected val keyList = LinkedList<K>()

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
            assert(keyList.first == it) {
                "Get or put value is not most recent."
            }
        }
    }

    private fun mapKeysAndKeyListAreSame(): Boolean {
        if (map.size != keyList.size) return false

        return map.keys.containsAll(keyList) && keyList.containsAll(map.keys)
    }

    abstract fun doPut(key: K, value: V)
    abstract fun doGet(key: K): V?
}
