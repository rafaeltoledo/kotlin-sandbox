package net.rafaeltoledo.stak.ui

import android.support.v7.widget.RecyclerView
import android.view.View
import kotlinx.android.synthetic.main.item_user.view.*
import net.rafaeltoledo.stak.data.User
import net.rafaeltoledo.stak.util.loadUrl

class UserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    fun bind(user: User, listener: (User) -> Unit) = with(itemView) {
        userName.text = user.displayName
        userImage.loadUrl(user.profileImage)
        userLocation.text = user.location
        setOnClickListener { listener(user) }
    }
}

class LoadingViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)