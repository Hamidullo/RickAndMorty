package com.criminal_code.rickandmorty.ui

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import androidx.core.widget.NestedScrollView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.criminal_code.rickandmorty.R
import com.criminal_code.rickandmorty.adapter.CharacterAdapter
import com.criminal_code.rickandmorty.model.data.character.Result


class CharacterFragment : Fragment() {

    private lateinit var viewModel: CharacterViewModel
    private var characterList: ArrayList<Result> = ArrayList<Result>()
    private lateinit var characterRV: RecyclerView
    private lateinit var characterAdapter: CharacterAdapter
    private lateinit var progressBar: ProgressBar
    private lateinit var nestedScrollView: NestedScrollView
    private var page = 1
    private val limit = 42

    private lateinit var fragmentSendIdListener: OnFragmentSendIdListener

    override fun onAttach(context: Context) {
        super.onAttach(context)
        fragmentSendIdListener = try {
            context as OnFragmentSendIdListener
        } catch (e: ClassCastException) {
            throw ClassCastException("$context должен реализовывать интерфейс OnFragmentInteractionListener")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.character_fragment, container, false)
        characterRV = view.findViewById(R.id.idRVCharacter)
        progressBar = view.findViewById(R.id.idPBLoading)
        nestedScrollView = view.findViewById(R.id.idNestedSV)
        return  view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this)[CharacterViewModel::class.java]
        val onClickListener: CharacterAdapter.OnCastClickListener =
            object : CharacterAdapter.OnCastClickListener {
                override fun onCastClick(
                    position: Int
                ) {
                    fragmentSendIdListener.onSendIdDetail(position+1)
                    Toast.makeText(requireContext(), "$position", Toast.LENGTH_SHORT).show()
                }
            }
        characterAdapter = CharacterAdapter(characterList,requireContext(),onClickListener)
        characterRV.adapter = characterAdapter
        characterRV.addItemDecoration(
            DividerItemDecoration(
                context,
                DividerItemDecoration.VERTICAL
            )
        )
        getData(page)
        nestedScrollView.setOnScrollChangeListener(NestedScrollView.OnScrollChangeListener { v, scrollX, scrollY, oldScrollX, oldScrollY ->
            if (scrollY == v.getChildAt(0).measuredHeight - v.measuredHeight) {
                progressBar.visibility = View.VISIBLE
                getData(++page)
            }
        })
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun getData(pageIn: Int){
        if (pageIn > limit){
            Toast.makeText(requireContext(), "That's all the data..", Toast.LENGTH_SHORT).show()
            progressBar.visibility = View.GONE
            return
        }
        viewModel.getMovies(pageIn)!!.observe(viewLifecycleOwner,
            Observer<List<Result>?> { charList ->
                characterList.addAll(charList)
                progressBar.visibility = View.INVISIBLE
                characterAdapter.notifyDataSetChanged()
            })
    }

    internal interface OnFragmentSendIdListener {
        fun onSendIdDetail(id: Int)
    }

    override fun onStop() {
        super.onStop()
        viewModel.cancel()
    }
}