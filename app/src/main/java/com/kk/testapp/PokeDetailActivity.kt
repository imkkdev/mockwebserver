package com.kk.testapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PokeDetailActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestDetail()
    }

    private fun requestDetail() {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = TestAPI.defaultInstance(Constants.URL).getPokemonDetailAsync("poke")
                println(response)
            } catch (e: Exception) {
                println("okhttp exception = " + e.message)
            }
        }
    }

}