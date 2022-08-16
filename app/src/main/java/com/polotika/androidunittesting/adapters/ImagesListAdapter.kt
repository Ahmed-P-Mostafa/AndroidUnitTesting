package com.polotika.androidunittesting.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.polotika.androidunittesting.R
import kotlinx.android.synthetic.main.item_image.view.*
import javax.inject.Inject

class ImagesListAdapter @Inject constructor() :
    RecyclerView.Adapter<ImagesListAdapter.ViewHolder>() {

    var images: List<String>
        get() = differ.currentList
        set(value) = differ.submitList(value)

    class ViewHolder(binding: View) : RecyclerView.ViewHolder(binding)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_image,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val url = images[position]
        holder.itemView.apply {
            Glide.with(this).load(url).into(ivShoppingImage)

            setOnClickListener {
                onItemClickListener?.let { click ->
                    click(url)
                }
            }
        }
    }
    override fun getItemCount() = images.size

    private var onItemClickListener: ((String) -> Unit)? = null

    fun setOnItemClickListener(listener: (String) -> Unit) {
        onItemClickListener = listener
    }

    private val diffCallback = object : DiffUtil.ItemCallback<String>() {
        override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem == newItem
        }
    }

    private val differ = AsyncListDiffer(this, diffCallback)
}

