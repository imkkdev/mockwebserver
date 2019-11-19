package com.kk.testapp

import okhttp3.mockwebserver.MockResponse

class PokeMainTest : MockBase(PokeMainActivity::class.java) {

    // add additional request here
    override fun addPageResponse(): MutableList<Pair<String, MockResponse>> {
        return mutableListOf(
            "/v3/pokemon" to MockResponse().setBody(getJson("json/pokemon.json")),
            // next activity mock res
            "/v2/pokemon/poke" to MockResponse().setBody(getJson("json/pokemon.json"))
        )
    }

}