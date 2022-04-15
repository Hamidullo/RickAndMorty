package com.criminal_code.rickandmorty

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import com.criminal_code.rickandmorty.ui.CharacterFragment

class MainActivity : AppCompatActivity(), CharacterFragment.OnFragmentSendIdListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.navigation)
        return navController.navigateUp()
    }

    override fun onSendIdDetail(id: Int) {
        val sharedPreferences: SharedPreferences = this.getSharedPreferences("RickAndMorty",
            Context.MODE_PRIVATE)
        val editor: SharedPreferences.Editor =  sharedPreferences.edit()
        editor.putInt("characterID", id)
        editor.apply()
        editor.commit()
        val navController: NavController =
            Navigation.findNavController(this, R.id.nav_fragment)
        navController.navigate(R.id.action_characterFragment_to_detailFragment)
    }
}