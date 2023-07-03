package com.example.eduputinf151873


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.eduputinf151873.databinding.MultipleGameScreenBinding

class MultipleExtensionsActivity : ComponentActivity() {
    private lateinit var extensionDbHandler: ExtensionsDataBase
    private lateinit var binding: MultipleGameScreenBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.multiple_game_screen)


        extensionDbHandler = ExtensionsDataBase(this, null, null, 1)

        val extensionData = extensionDbHandler.getAllExtensions()

        // Set up your RecyclerView and adapter here
        val recyclerView: RecyclerView = findViewById(R.id.recycler_view)
        val adapter = ViewExtensionsAdapter(extensionData)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

    }
}