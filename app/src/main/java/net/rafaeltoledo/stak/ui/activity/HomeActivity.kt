package net.rafaeltoledo.stak.ui.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_home.*
import net.rafaeltoledo.stak.R
import net.rafaeltoledo.stak.data.User
import net.rafaeltoledo.stak.data.api.ApiCaller
import net.rafaeltoledo.stak.ui.adapter.UserAdapter
import org.jetbrains.anko.browse
import org.jetbrains.anko.ctx
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

class HomeActivity : AppCompatActivity() {

    lateinit var layoutManager: RecyclerView.LayoutManager
    lateinit var adapter: UserAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        layoutManager = LinearLayoutManager(ctx)
        adapter = UserAdapter({ openInBrowser(it) }, { load(it) })

        if (savedInstanceState != null) {
            layoutManager.onRestoreInstanceState(savedInstanceState.getParcelable("lm"))
            adapter.onRestoreInstanceState(savedInstanceState.getParcelable("adapter"))
        }

        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = adapter
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        outState?.putParcelable("lm", layoutManager.onSaveInstanceState())
        outState?.putParcelable("adapter", adapter.onSaveInstanceState())
    }

    private fun load(page: Int) {
        doAsync() {
            val response = ApiCaller.api.getUsers(page).execute()
            uiThread {
                if (response.isSuccessful) {
                    adapter.addAll(response.body().items, response.body().hasMore)
                }
            }
        }
    }

    private fun openInBrowser(user: User) {
        browse(user.link)
    }
}