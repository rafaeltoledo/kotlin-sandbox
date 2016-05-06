package net.rafaeltoledo.stak.ui.adapter

import android.os.Parcel
import android.os.Parcelable

class SavedState(val page: Int, val items: Array<out Parcelable>) : Parcelable {

    constructor(parcel: Parcel) : this(parcel.readInt(),
            parcel.readParcelableArray(ClassLoader.getSystemClassLoader()))

    override fun writeToParcel(dest: Parcel?, flags: Int) {
        dest?.writeInt(page)
        dest?.writeParcelableArray(items, 0)
    }

    override fun describeContents() = 0

    companion object {

        val CREATOR: Parcelable.Creator<SavedState> = object : Parcelable.Creator<SavedState> {

            override fun createFromParcel(parcel: Parcel) = SavedState(parcel)

            override fun newArray(size: Int): Array<SavedState?> {
                return arrayOfNulls(size)
            }
        }
    }
}