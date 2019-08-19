package com.kk.testapp

import android.content.Intent
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.RecordedRequest

class PokeDetailTest : MockBase(PokeDetailActivity::class.java) {

    override fun jump() {
        val intent = Intent()
        intent.putExtra("name", "poke")
        jumpImpl(intent)
    }

    override fun dispatch(request: RecordedRequest?): MockResponse {
        return MockResponse().setBody(body)
    }

    private val body = "{\"name\":\"Poke\",\"url\":\"www.google.com\",\"type\":[\"type1\",\"type2\"]}"
}