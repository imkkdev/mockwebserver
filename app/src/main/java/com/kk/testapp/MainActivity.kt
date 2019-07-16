package com.kk.testapp

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        requestDto()
    }

    fun onClick(view: View) {
        requestDto()
    }

    private fun requestDto() {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val map = HashMap<String, Any>()
                map["token"] = "123"
                val response = TestAPI.defaultInstance(Constants.URL).getPokemonAsync(map).await()
                println(response)
            } catch (e: Exception) {
                println("okhttp exception = " + e.message)
            }
        }
    }
}
