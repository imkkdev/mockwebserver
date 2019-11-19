package com.kk.testapp

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.kk.testapp.model.MessageDto
import com.kk.testapp.model.Pokemon
import com.kk.testapp.model.PokemonResponse
import kotlinx.coroutines.Deferred
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import java.util.concurrent.TimeUnit

@JvmSuppressWildcards
interface TestAPI {

    @POST("v2/pokemon")
    fun getPokemonAsync(@Body map: Map<String, Any>): Deferred<PokemonResponse>

    @GET("v2/pokemon/{name}")
    fun getPokemonDetailAsync(@Path("name") name: String): Deferred<Pokemon>

    @GET("v2/msg")
    fun getPokemonMessageAsync(): Deferred<List<MessageDto>>

    companion object {
        fun defaultInstance(baseUrl: String): TestAPI {
            val interceptor = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
            val client = OkHttpClient.Builder().addInterceptor(interceptor).connectTimeout(3, TimeUnit.SECONDS)
                .readTimeout(3, TimeUnit.SECONDS).writeTimeout(3, TimeUnit.SECONDS).build()
            return Retrofit.Builder()
                .baseUrl(baseUrl)
                .addCallAdapterFactory(CoroutineCallAdapterFactory())
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()
                .create(TestAPI::class.java)
        }
    }


}