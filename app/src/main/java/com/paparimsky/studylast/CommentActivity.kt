package com.paparimsky.studylast

import android.app.Activity
import android.content.Intent
import android.content.res.ColorStateList
import android.database.Cursor
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.core.widget.doOnTextChanged
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.paparimsky.studylast.databinding.ActivityCommentBinding
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.io.ByteArrayOutputStream

class CommentActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCommentBinding

    private var name: String? = ""
    private var text: String? = ""
    private var picturePath: String? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCommentBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.addComment.backgroundTintList = ColorStateList.valueOf(ContextCompat.getColor(this, R.color.yellow))

        binding.addPhoto.isVisible = true
        binding.imageViewCard.isVisible = false

        binding.cross.setOnClickListener {
            finish()
        }
        binding.nameText.doOnTextChanged{s,_,_,_ ->
            name = s.toString()
            activateButton()
        }
        binding.textText.doOnTextChanged{s,_,_,_ ->
            text = s.toString()
            activateButton()
        }
        binding.addPhoto.setOnClickListener {
            openGallery()
        }
        binding.imageViewCard.setOnClickListener {
            openGallery()
        }

        val db = MainDB.getDb(this)
        binding.addComment.setOnClickListener {
            var img = if(picturePath != null) picturePath else null
            val comment = CommentEntity(
                null,
                name!!,
                text!!,
                img
            )
            Thread{
                db.getDao().insertComment(comment)
            }.start()
            finish()
        }
    }

    private fun activateButton(){
        if (name?.isNotEmpty() == true && text?.isNotEmpty() == true){
            binding.addComment.isEnabled = true
            binding.addComment.setTextColor(ContextCompat.getColor(this, R.color.background))
            binding.addComment.backgroundTintList = ColorStateList.valueOf(ContextCompat.getColor(this, R.color.yellow))
        }else{
            binding.addComment.isEnabled = false
            binding.addComment.setTextColor(ContextCompat.getColor(this, R.color.lite_yellow))
            binding.addComment.backgroundTintList = ColorStateList.valueOf(ContextCompat.getColor(this, R.color.yellow))
        }
    }

    private fun openGallery() {
        val galleryIntent = Intent(Intent.ACTION_PICK)
        galleryIntent.type = "image/*"
        pickImageLauncher.launch(galleryIntent)
    }

    private val pickImageLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val data: Intent? = result.data
            val selectedImageUri = data?.data
            binding.imageViewPhoto.setImageURI(selectedImageUri)
            val imagePath = getPathFromUri(selectedImageUri)
            imagePath?.let {
                picturePath = imagePath
            }
            binding.addPhoto.isVisible = false
            binding.imageViewCard.isVisible = true
        }
    }
    private fun getPathFromUri(uri: Uri?): String? {
        uri ?: return null
        var cursor: Cursor? = null
        val projection = arrayOf(MediaStore.Images.Media.DATA)
        try {
            cursor = contentResolver.query(uri, projection, null, null, null)
            val columnIndex = cursor?.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
            cursor?.moveToFirst()
            return cursor?.getString(columnIndex!!)
        } finally {
            cursor?.close()
        }
    }

}