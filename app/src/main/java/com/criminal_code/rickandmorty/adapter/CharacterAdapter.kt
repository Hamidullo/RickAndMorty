package com.criminal_code.rickandmorty.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.criminal_code.rickandmorty.R
import com.criminal_code.rickandmorty.model.data.character.Result
import com.criminal_code.rickandmorty.ui.DetailFragment
import com.squareup.picasso.Picasso


class CharacterAdapter(private val characterList: ArrayList<Result>,
                       private val context: Context,
                       private val onClickListener: OnCastClickListener?): RecyclerView.Adapter<CharacterAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View = LayoutInflater.from(context).inflate(R.layout.character_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val characterModal: Result = characterList[position]
        holder.nameTV.text = characterModal.name
        if (characterModal.type == ""){
            holder.typeTV.text = characterModal.species
        } else {
            holder.typeTV.text = characterModal.type
        }
        holder.genderTV.text = characterModal.gender

        Picasso.get().load(characterModal.image).into(holder.characterIV)

        holder.itemView.setOnClickListener {
            onClickListener!!.onCastClick(position)
        }
    }

    override fun getItemCount(): Int = characterList.size

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nameTV: TextView = itemView.findViewById(R.id.idTVName)
        val typeTV: TextView = itemView.findViewById(R.id.idTVType)
        val genderTV: TextView = itemView.findViewById(R.id.idTVGender)
        val characterIV: ImageView = itemView.findViewById(R.id.idIVCharacter)

    }

    interface OnCastClickListener {
        fun onCastClick(position: Int)
    }
}