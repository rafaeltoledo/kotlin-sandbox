package net.rafaeltoledo.stak.data

import android.os.Parcel
import android.os.Parcelable

data class User(val displayName: String, val profileImage: String, val location: String, val link: String) : Parcelable {

    constructor(parcel: Parcel) : this(parcel.readString(), parcel.readString(),
            parcel.readString(), parcel.readString())

    override fun writeToParcel(dest: Parcel?, flags: Int) {
        dest?.writeString(displayName)
        dest?.writeString(profileImage)
        dest?.writeString(location)
        dest?.writeString(link)
    }

    override fun describeContents(): Int = 0

    companion object {

        @JvmStatic
        val CREATOR: Parcelable.Creator<User> = object : Parcelable.Creator<User> {
            override fun createFromParcel(parcel: Parcel) = User(parcel)

            override fun newArray(size: Int): Array<User?> = arrayOfNulls(size)
        }
    }
}