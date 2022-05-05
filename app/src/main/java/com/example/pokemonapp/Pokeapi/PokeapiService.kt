package com.example.pokemonapp.Pokeapi

import com.example.pokemonapp.Models.PokemonResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface PokeapiService {

    @GET("pokemon")
    fun getPokemonList(@Query("limit") limit:Int, @Query("offset") offset: Int):Call<PokemonResponse>
}