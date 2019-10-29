package com.kk.testapp

import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.RecordedRequest
import java.nio.charset.Charset

class PokeMainTest : MockBase(PokeMainActivity::class.java) {

    override fun jump() {
        super.jump()
    }

    override fun dispatch(request: RecordedRequest?): MockResponse {
        return when (request?.path) {
            "v1/pokemon" -> MockResponse().setBody(pokemon)
            "v2/pokemon" -> MockResponse().setBody(getJson("json/pokemon.json")) // can use json.file in androidTest
            else -> MockResponse().setResponseCode(404)
        }
    }

    private val pokemon = "{\"count\":1,\"next\":\"Next\",\"previous\":\"Previous\",\"result\":[\"name\":\"Poke\",\"url\":\"www.google.com\",\"type\":[\"type1\",\"type2\"]]}"

    private fun getJson(path: String): String {
        val stream = this.javaClass.classLoader.getResource(path)
        return stream.readBytes().toString(Charset.defaultCharset())
    }
}