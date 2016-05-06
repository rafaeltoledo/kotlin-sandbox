package net.rafaeltoledo.stak.data.api

import android.support.annotation.VisibleForTesting
import com.google.gson.FieldNamingPolicy
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiCaller {

    var api: StakApi = build()

    @VisibleForTesting
    fun build(url: String = "https://api.stackexchange.com") = Retrofit.Builder()
            .baseUrl(url)
            .client(OkHttpClient.Builder()
                    .addInterceptor(
                            HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
                    )
                    .build())
            .addConverterFactory(GsonConverterFactory.create(
                    GsonBuilder()
                            .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                            .create()))
            .build()
            .create(StakApi::class.java)
}