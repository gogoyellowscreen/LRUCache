import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull
import kotlin.test.assertTrue

private const val INITIAL_CAPACITY = 5
class LRUCacheTest {

    private lateinit var underTest: LRUCache<String, Int>

    @Before
    fun setUp() {
        underTest = LRUCacheImpl(INITIAL_CAPACITY)
    }

    @Test
    fun `get value returns putted value`() {
        val (key, value) = "key" to 0
        underTest.put(key, value)
        assertEquals(0, underTest.get(key))
    }

    @Test
    fun `cache size always less or equal to capacity`() {
        for (num in 0..10) {
            underTest.put("key$num", num)
            assertTrue(underTest.size <= INITIAL_CAPACITY)
        }
    }

    @Test
    fun `cache returns null if value is not used too long`() {
        val (key, value) = "key" to 0
        underTest.put(key, value)
        for (num in 1..5) {
            underTest.put("key$num", num)
        }
        assertNull(underTest.get(key))
    }

    @Test
    fun `cache returns value if it is used often`() {
        val (key, value) = "key" to 0
        underTest.put(key, value)
        for (num in 1..10) {
            underTest.get(key)
            underTest.put("key$num", num)
        }
        assertEquals(value, underTest.get(key))
    }

    @Test
    fun `double put doesnt insert but update`() {
        val key = "key"
        val (value1, value2) = 1 to 2
        underTest.put(key, value1)
        underTest.put(key, value2)
        assertEquals(1, underTest.size)
        assertEquals(value2, underTest.get(key))
    }

    @Test
    fun `contains only recent values`() {
        for (num in 1..10) {
            underTest.put("key$num", num)
        }
        for (num in 1..5) {
            assertNull(underTest.get("key$num"))
        }
        for (num in 6..10) {
            assertEquals(num, underTest.get("key$num"))
        }
    }
}
