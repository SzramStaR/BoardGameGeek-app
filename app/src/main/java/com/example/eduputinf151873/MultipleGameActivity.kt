package com.example.eduputinf151873


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.eduputinf151873.databinding.MultipleGameScreenBinding

class MultipleGameActivity : ComponentActivity()
{
    private lateinit var gameDbHandler: GamesDataBase
    private lateinit var binding: MultipleGameScreenBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.multiple_game_screen)

        gameDbHandler = GamesDataBase(this, null, null, 1)

        val gamesData = gameDbHandler.getAllGames()

        // Set up your RecyclerView and adapter here
        val recyclerView: RecyclerView = findViewById(R.id.recycler_view)
        val adapter = ViewAdapter(gamesData)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter





    }
}

