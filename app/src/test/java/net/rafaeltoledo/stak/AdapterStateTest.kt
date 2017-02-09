package net.rafaeltoledo.stak

import net.rafaeltoledo.stak.data.User
import net.rafaeltoledo.stak.ui.adapter.UserAdapter
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import kotlin.test.assertEquals

@RunWith(RobolectricTestRunner::class)
@Config(sdk = intArrayOf(25), constants = BuildConfig::class)
class AdapterStateTest {

    @Test
    fun assertThatAdapterIsConsistentAfterRestoring() {
        val adapter = UserAdapter({ }, { })
        adapter.addAll(listOf(
                User("Johny", "http://example.com/johny.jpg", "Moon", "http://johny.example.com"),
                User("Michael", "http://example.com/michael.jpg", "Mars", "http://michael.example.com"),
                User("Thomas", "http://example.com/thomas.jpg", "Pluto", "http://thomas.example.com")
        ))

        val parcelable = adapter.onSaveInstanceState()

        val newAdapter = UserAdapter({ }, { })
        newAdapter.onRestoreInstanceState(parcelable!!)

        assertEquals(adapter.itemCount, newAdapter.itemCount)
        assertEquals(adapter.items[0].displayName, newAdapter.items[0].displayName)
    }
}