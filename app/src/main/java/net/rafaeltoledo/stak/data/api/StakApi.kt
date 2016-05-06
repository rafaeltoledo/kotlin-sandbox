package net.rafaeltoledo.stak.data.api

import net.rafaeltoledo.stak.data.User
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface StakApi {

    @GET("2.2/users?order=desc&sort=reputation&site=stackoverflow")
    fun getUsers(@Query("page") page: Int): Call<ApiCollection<User>>
}