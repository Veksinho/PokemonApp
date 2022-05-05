package com.example.pokemonapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pokemonapp.Models.Pokemon
import com.example.pokemonapp.Models.PokemonResponse
import com.example.pokemonapp.Pokeapi.PokeapiService
import retrofit2.*
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {

    private var retrofit:Retrofit? = null
    val TAG:String = "POKEDEX"

    lateinit var recyclerView:RecyclerView
    lateinit var pokemonListAdapter:PokemonListAdapter
    var pokemonList = ArrayList<Pokemon>()
    var offset:Int = 0
    var fitToChange:Boolean? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        pokemonListAdapter = PokemonListAdapter(this,pokemonList)
        recyclerView.setHasFixedSize(true)
        recyclerView.adapter = pokemonListAdapter
        var lm = GridLayoutManager(this,3)
        recyclerView.layoutManager = lm
        recyclerView.addOnScrollListener( object : RecyclerView.OnScrollListener(){
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                if(dy > 0){
                    var visibleItemCount:Int = lm.childCount
                    var totalItemCount:Int = lm.itemCount
                    var pastVisibleItems:Int = lm.findFirstVisibleItemPosition()
                        if((visibleItemCount + pastVisibleItems) >= totalItemCount){
                            Log.i(TAG, "We made it to the end!")

                            fitToChange = false
                            offset += 20
                            fetchData(offset)
                        }
                }
            }
        })

        retrofit = Retrofit.Builder()
            .baseUrl("http://pokeapi.co/api/v2/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        fitToChange = true
        fetchData(offset)
    }

    private fun fetchData(offset: Int) {
        var service = retrofit!!.create(PokeapiService::class.java)
        var pokemonResponseCall:Call<PokemonResponse> = service.getPokemonList(20, offset)

        pokemonResponseCall.enqueue( object : Callback<PokemonResponse> {
            override fun onResponse(
                call: Call<PokemonResponse>,
                response: Response<PokemonResponse>
            ) {
                fitToChange = true
                if(response.isSuccessful) {
                    var pokemonResponse = response.body() as PokemonResponse
                    var pokemonList = pokemonResponse.results

                    pokemonListAdapter.addPokemonList(pokemonList)
                }else{
                    Log.e(TAG, "onResponse: " + response.errorBody())
                }
            }

            override fun onFailure(call: Call<PokemonResponse>, t: Throwable) {
                fitToChange = true
                Log.e(TAG, "on Failure: " + t.message)
            }
        })
    }
}