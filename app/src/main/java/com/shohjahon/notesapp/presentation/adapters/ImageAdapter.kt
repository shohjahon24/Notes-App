package com.shohjahon.notesapp.presentation.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.shohjahon.notesapp.databinding.ItemImageBinding
import com.shohjahon.notesapp.domain.model.note.Image
import com.shohjahon.notesapp.util.readBitmapFromStorage
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class ImageAdapter @Inject constructor(
    @ApplicationContext private val context: Context
) :
    RecyclerView.Adapter<ImageAdapter.ViewHolder>() {

    private val limit = 12

    private var data: ArrayList<Image> = ArrayList()

    fun setData(data: List<Image>, changedPosition: Int = -1) {
        this.data = ArrayList(data)
        if (changedPosition != -1)
            notifyItemChanged(changedPosition)
        else
            notifyDataSetChanged()
    }

    fun getData() =  data

    fun addData(data: Image) {
        this.data.add(data)
        notifyItemInserted(this.data.size)
    }

    fun removeData(id: Int) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding: ItemImageBinding =
            ItemImageBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind()
    override fun getItemCount(): Int {
        return if(data.size > limit){
            limit
        } else {
            data.size
        }
    }

    inner class ViewHolder(val binding: ItemImageBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind() {
            binding.apply {
                Glide.with(context).load(data[adapterPosition].bitmap).into(img)
                img.clipToOutline = true
                remove.setOnClickListener {
                    data.removeAt(adapterPosition)
                    notifyItemChanged(adapterPosition)
                }
            }
        }
    }
}