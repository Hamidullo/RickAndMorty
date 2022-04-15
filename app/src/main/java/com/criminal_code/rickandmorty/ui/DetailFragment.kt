package com.criminal_code.rickandmorty.ui

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.criminal_code.rickandmorty.R
import com.criminal_code.rickandmorty.model.data.detail.Detail
import com.squareup.picasso.Picasso

class DetailFragment : Fragment() {

    private lateinit var viewModel: CharacterViewModel
    private var characterId: Int = 81
    private lateinit var detail: Detail
    private lateinit var imgDetail: ImageView
    private lateinit var nameDetail: TextView
    private lateinit var statusDetail: TextView
    private lateinit var speciesDetail: TextView
    private lateinit var locationDetail: TextView
    private lateinit var genderDetail: TextView
    private lateinit var typeDetail: TextView
    private lateinit var episodeCountDetail: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val sharedPreferences: SharedPreferences = this.requireActivity().getSharedPreferences("RickAndMorty",
            Context.MODE_PRIVATE)
        characterId = sharedPreferences.getInt("characterID",81)!!
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_detail, container, false)

        imgDetail = view.findViewById(R.id.detailIVCharacter)
        nameDetail = view.findViewById(R.id.detailNameCharacter)
        statusDetail = view.findViewById(R.id.detailStatusCharacter)
        speciesDetail = view.findViewById(R.id.detailSpeciesCharacter)
        locationDetail = view.findViewById(R.id.detailTVLocCharacter)
        genderDetail = view.findViewById(R.id.detailGenderCharacter)
        typeDetail = view.findViewById(R.id.detailTypeCharacter)
        episodeCountDetail = view.findViewById(R.id.detailEpisodeCountCharacter)

        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this)[CharacterViewModel::class.java]

        viewModel.getDetail(characterId)!!.observe(viewLifecycleOwner,
            Observer<Detail?> { detailTemp ->
                println(detailTemp)
                detail = detailTemp

                Picasso.get().load(detail.image).into(imgDetail)
                nameDetail.text = detail.name
                statusDetail.text = detail.status
                speciesDetail.text = detail.species
                locationDetail.text = detail.location.name
                genderDetail.text = detail.gender
                typeDetail.text = detail.type
                episodeCountDetail.text = detail.episode.size.toString()

            })
    }

    override fun onStop() {
        super.onStop()
        viewModel.cancel()
    }
}