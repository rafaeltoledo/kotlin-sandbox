package net.rafaeltoledo.stak.ui.adapter

import android.os.Parcelable
import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import net.rafaeltoledo.stak.R
import net.rafaeltoledo.stak.data.User
import net.rafaeltoledo.stak.ui.LoadingViewHolder
import net.rafaeltoledo.stak.ui.UserViewHolder
import net.rafaeltoledo.stak.util.inflate

class UserAdapter(private val listener: (User) -> Unit, private val loadMore: (Int) -> Unit) :
        RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var page = 0
    var items: MutableList<User> = arrayListOf()
    private var dontNotify = false

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is UserViewHolder) {
            holder.bind(items[position], listener)
        } else {
            if (!dontNotify) loadMore(++page)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder? {
        return if (viewType == 0) UserViewHolder(parent.inflate(R.layout.item_user))
        else LoadingViewHolder(parent.inflate(R.layout.item_loading))
    }

    override fun getItemViewType(position: Int) = if (dontNotify || position != itemCount - 1) 0 else 1

    override fun getItemCount() = items.size + if (dontNotify) 0 else 1

    fun addAll(items: List<User>, continueLoading: Boolean = true) {
        this.items.addAll(items)
        dontNotify = continueLoading.not()
        notifyDataSetChanged()
    }

    fun onRestoreInstanceState(state: Parcelable) {
        if (state is SavedState) {
            page = state.page
            items = state.items.toMutableList() as MutableList<User>
        }
    }

    fun onSaveInstanceState(): Parcelable? {
        return SavedState(page, items.toTypedArray())
    }
}