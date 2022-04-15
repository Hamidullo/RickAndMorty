package com.criminal_code.rickandmorty.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.criminal_code.rickandmorty.model.data.character.Result
import com.criminal_code.rickandmorty.model.data.detail.Detail
import com.criminal_code.rickandmorty.repository.CharacterRepository
import com.criminal_code.rickandmorty.repository.ICharacterRepository
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class CharacterViewModel : ViewModel(), CoroutineScope {
    private val repository: ICharacterRepository = CharacterRepository()
    private val mainJob = SupervisorJob()
    private var characterList: MutableLiveData<List<Result>>? = null
    private var characterDetail: MutableLiveData<Detail>? = null

    fun getMovies(page: Int): MutableLiveData<List<Result>>? {
        if (characterList == null) {
            characterList = MutableLiveData<List<Result>>()
            loadCharacter(page)
        } else {
            println(page)
            characterList = null
            characterList = MutableLiveData<List<Result>>()
            loadCharacter(page)
        }
        return characterList
    }

    fun getDetail(id: Int): MutableLiveData<Detail>? {
        if (characterDetail == null) {
            characterDetail = MutableLiveData<Detail>()
            loadDetail(id)
        }
        return characterDetail
    }

    private fun loadCharacter(page: Int) {
        launch(Dispatchers.Main) {
            try {
                val characterListTemp = withContext(Dispatchers.IO) {
                    repository.loadCharacter(page)
                }
                characterList!!.value = characterListTemp.results
            } catch (e: Exception) {
                e.printStackTrace()

            } finally {

            }
        }
    }

    private fun loadDetail(id: Int) {
        launch(Dispatchers.Main) {
            try {
                val detailTemp = withContext(Dispatchers.IO) {
                    repository.loadDetail(id)
                }
                characterDetail!!.value = detailTemp
            } catch (e: Exception) {
                e.printStackTrace()

            } finally {

            }
        }
    }

    fun cancel() {
        mainJob.cancel()
    }

    override val coroutineContext: CoroutineContext = mainJob

}