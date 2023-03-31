package com.example.aop.part2.imagedisplay.views

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.aop.part2.imagedisplay.R
import com.example.aop.part2.imagedisplay.databinding.ItemPhotoBinding
import com.example.aop.part2.imagedisplay.models.PhotoResponseItem

class PhotoAdapter : RecyclerView.Adapter<PhotoAdapter.ViewHolder>() {

    private val photos: MutableList<PhotoResponseItem> = mutableListOf()
    private var tempTerm: String? = null

    fun addList(list: MutableList<PhotoResponseItem>) {
        photos.addAll(list)
        notifyDataSetChanged()
    }

    fun addSearchedList(list: MutableList<PhotoResponseItem>, searchTerm: String?) {
        Log.d("JB", "tempTerm : $tempTerm , searchTerm : $searchTerm ")
        if (tempTerm != searchTerm) {
            photos.clear()
            photos.addAll(list)
        } else {
            photos.addAll(list)
        }
        tempTerm = searchTerm
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemPhotoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(photos[position])
    }

    override fun getItemCount(): Int = photos.size

    inner class ViewHolder(private val binding: ItemPhotoBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(photo: PhotoResponseItem) {
            Log.d("JB", "${photo.urls!!.regular}")

            val dimensionRatio = photo.height?.div(photo.width?.toFloat()!!)
            val targetWidth = binding.root.resources.displayMetrics.widthPixels - (binding.root.paddingStart + binding.root.paddingEnd)
            val targetHeight = (targetWidth * dimensionRatio!!).toInt()

            binding.contentsContainer.layoutParams =
                binding.contentsContainer.layoutParams.apply {
                    height = targetHeight
                }

            // photo
            Glide.with(binding.root)
                .load(photo.urls?.regular)
                .thumbnail(
                    Glide.with(binding.root)
                        .load(photo.urls?.thumb)
                        .transition(DrawableTransitionOptions.withCrossFade()))
                .override(targetWidth, targetHeight)
                .into(binding.photoImageView)
        }
    }
}