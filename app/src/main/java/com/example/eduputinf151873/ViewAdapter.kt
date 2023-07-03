package com.example.eduputinf151873

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.eduputinf151873.databinding.SingleGameRecViewBinding

class ViewAdapter(private val games: List<Game>) : RecyclerView.Adapter<ViewAdapter.ViewHolder>() {



    inner class ViewHolder(binding: SingleGameRecViewBinding ) : RecyclerView.ViewHolder(binding.root) {
        var order_number = binding.orderNumber
        val game_image = binding.gameImage
        var title_tv = binding.titleTv
        var year_tv = binding.yearTv
    }
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

            val inflater = LayoutInflater.from(parent.context)
            val SingleGameRecBinding = SingleGameRecViewBinding.inflate(inflater, parent, false)
            return ViewHolder(SingleGameRecBinding)
        }

        override fun getItemCount(): Int {
            return games.size
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            val game = games[position]
            holder.order_number.text = (position + 1).toString()
            holder.title_tv.text = game.name
            holder.year_tv.text= game.yearPublished
            Glide.with(holder.itemView.context).load(game.image).into(holder.game_image)

        }
    }
