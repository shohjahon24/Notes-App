package com.shohjahon.notesapp.presentation.adapters

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.shohjahon.notesapp.databinding.ItemHomeBinding
import com.shohjahon.notesapp.domain.model.home.HomeNote
import com.shohjahon.notesapp.util.readBitmapFromStorage
import dagger.hilt.android.qualifiers.ApplicationContext
import java.text.SimpleDateFormat
import java.util.Locale
import javax.inject.Inject

class HomeAdapter @Inject constructor(@ApplicationContext private val context: Context): RecyclerView.Adapter<HomeAdapter.ViewHolder>() {

    private var data: ArrayList<HomeNote> = ArrayList()

    var itemCallBack: ItemCallBack? = null

    fun setData(data: List<HomeNote>) {
        this.data = ArrayList(data)
        notifyDataSetChanged()
    }

    fun addData(note: HomeNote) {
        this.data.add(note)
        notifyItemInserted(data.size)
    }

    fun deleteData(position: Int) {
        this.data.removeAt(position)
        notifyItemRemoved(position)
    }

    inner class ViewHolder(private val binding: ItemHomeBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind() {
            binding.apply {
                title.text = data[adapterPosition].title
                description.text = data[adapterPosition].description
                cvImg.visibility = if (data[adapterPosition].img.isEmpty()) View.GONE else View.VISIBLE
                val bitmap = readBitmapFromStorage(context.filesDir.absolutePath, "${data[adapterPosition].img}.png")
                Glide.with(context).load(bitmap).into(img)
                val dateFormat = SimpleDateFormat("hh:mm aa, MM/dd", Locale.getDefault())
                time.text = dateFormat.format(data[adapterPosition].time)

            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemHomeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        val holder = ViewHolder(binding)
        binding.content.setOnClickListener { itemCallBack?.onClicked(data[holder.adapterPosition].time) }
        binding.content.setOnLongClickListener { itemCallBack?.onLongClicked(data[holder.adapterPosition].time, holder.adapterPosition); true}
        return holder
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind()
    }
}