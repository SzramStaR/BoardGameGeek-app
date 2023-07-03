package com.example.eduputinf151873

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.eduputinf151873.databinding.SingleExtensionRecViewBinding
import com.example.eduputinf151873.databinding.SingleGameRecViewBinding

class ViewExtensionsAdapter(private val extensions: List<Extension>) : RecyclerView.Adapter<ViewExtensionsAdapter.ViewExtensionHolder>() {



    inner class ViewExtensionHolder(binding: SingleExtensionRecViewBinding ) : RecyclerView.ViewHolder(binding.root) {
        var order_number = binding.orderNumber
        val extension_image = binding.gameImage
        var title_tv = binding.titleTv
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewExtensionHolder {

        val inflater = LayoutInflater.from(parent.context)
        val SingleExtensionRecViewBinding = SingleExtensionRecViewBinding.inflate(inflater, parent, false)
        return ViewExtensionHolder(SingleExtensionRecViewBinding)
    }

    override fun getItemCount(): Int {
        return extensions.size
    }

    override fun onBindViewHolder(holder: ViewExtensionHolder, position: Int) {
        val game = extensions[position]
        holder.order_number.text = (position + 1).toString()
        holder.title_tv.text = game.name
        Glide.with(holder.itemView.context).load(game.image).into(holder.extension_image)

    }
}
