class LRUCacheImpl<K, V>(val capacity: Int) : LRUCache<K, V>(capacity) {
    override fun doPut(key: K, value: V) {
        map[key]?.let { node ->
            node.value = value
            doublyLinkedList.moveToFront(node)
            return
        }

        if (size == capacity) {
            val lruKey = doublyLinkedList.last
            doublyLinkedList.removeLast()
            map.remove(lruKey.key)
            size--
        }
        val newNode = doublyLinkedList.addFirst(key, value)
        map[key] = newNode
        size++
    }

    override fun doGet(key: K): V? {
        val node = map[key] ?: return null
        doublyLinkedList.moveToFront(node)
        return node.value
    }
}
