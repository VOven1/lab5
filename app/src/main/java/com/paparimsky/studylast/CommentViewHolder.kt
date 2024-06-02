package com.paparimsky.studylast

import android.graphics.BitmapFactory
import android.graphics.Paint
import android.net.Uri
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.google.android.material.color.MaterialColors
import java.io.File
import java.text.SimpleDateFormat

class CommentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val name: TextView = itemView.findViewById(R.id.card_name)
    private val text: TextView = itemView.findViewById(R.id.card_text)
    private val photo: ImageView = itemView.findViewById(R.id.photo)


    fun bind(model: CommentEntity) {
        name.text = model.name
        text.text = model.text
        if(model.imageData == null){
            photo.setImageResource(R.drawable.poster)
        }else{
            val file = File(model.imageData!!)
            val uri = Uri.fromFile(file)
            Glide.with(itemView)
                .load(uri)
                .into(photo)
        }
    }
}