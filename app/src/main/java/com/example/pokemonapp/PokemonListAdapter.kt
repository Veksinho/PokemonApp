package com.example.pokemonapp

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade
import com.example.pokemonapp.Models.Pokemon

class PokemonListAdapter (internal var context: Context, internal var pokemonList:ArrayList<Pokemon>) : RecyclerView.Adapter<PokemonListAdapter.ViewHolder>() {

    inner class ViewHolder (itemView:View) : RecyclerView.ViewHolder(itemView){
        internal var img_pokemon: ImageView
        internal var txt_pokemon: TextView
        init {

            img_pokemon = itemView.findViewById(R.id.imageView) as ImageView
            txt_pokemon = itemView.findViewById(R.id.textView) as TextView
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): PokemonListAdapter.ViewHolder {
        val itemView = LayoutInflater.from(context).inflate(R.layout.item_pokemon, parent,false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var p:Pokemon = pokemonList!![position]
        holder.txt_pokemon.text = p.name

        var urlParts: List<String> = p.url!!.split("/")
        var number:Int = urlParts[urlParts.size - 2].toInt()

        Glide.with(context)
            .load("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/$number.png")
            .centerCrop()
            .transition(withCrossFade())
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .into(holder.img_pokemon)

    }

    override fun getItemCount(): Int {
        return pokemonList!!.size
    }

    fun addPokemonList(newPokemonList: ArrayList<Pokemon>?) {
        pokemonList.addAll(newPokemonList!!)
        notifyDataSetChanged()
    }
}