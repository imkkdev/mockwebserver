package com.kk.testapp

import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.RecordedRequest

class PokeMainTest : MockBase(PokeMainActivity::class.java) {

    override fun jump() {
        super.jump()
    }

    override fun dispatch(request: RecordedRequest?): MockResponse {
        return when (request?.path) {
            "v2/pokemon" -> MockResponse().setBody(pokemon)
            else -> MockResponse().setResponseCode(404)
        }
    }

    private val pokemon = "{\"count\":1,\"next\":\"Next\",\"previous\":\"Previous\",\"result\":[\"name\":\"Poke\",\"url\":\"www.google.com\",\"type\":[\"type1\",\"type2\"]]}"
}