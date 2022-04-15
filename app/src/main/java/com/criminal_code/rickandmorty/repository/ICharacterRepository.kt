package com.criminal_code.rickandmorty.repository

import com.criminal_code.rickandmorty.model.data.character.Character
import com.criminal_code.rickandmorty.model.data.detail.Detail

interface ICharacterRepository {
    suspend fun loadCharacter(page: Int): Character
    suspend fun loadDetail(id: Int): Detail
}