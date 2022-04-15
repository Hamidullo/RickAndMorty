package com.criminal_code.rickandmorty.repository

import com.criminal_code.rickandmorty.model.api.Api
import com.criminal_code.rickandmorty.model.data.character.Character
import com.criminal_code.rickandmorty.model.data.detail.Detail

class CharacterRepository(private val api: Api): ICharacterRepository{

    override suspend fun loadCharacter(page: Int): Character = api.loadCharacter(page)
    override suspend fun loadDetail(id: Int): Detail = api.loadDetail(id)

    companion object {
        private var instance: CharacterRepository? = null
        operator fun invoke(): ICharacterRepository {
            var localIns = instance
            if (localIns == null) {
                val api = Api()
                localIns = CharacterRepository(api)
                instance = localIns
            }
            return localIns
        }
    }
}