package com.example.memeshareapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.Toast
import com.bumptech.glide.Glide
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private lateinit var memeImage: ImageView
    private lateinit var shareBtn: Button
    private lateinit var nextBtn: Button
    private lateinit var progressBar: ProgressBar
    var currentImageUrl: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        memeImage= findViewById(R.id.meme_image)
        progressBar = findViewById(R.id.progress_bar)
        shareBtn= findViewById(R.id.btn_share)
        nextBtn= findViewById(R.id.btn_next)

        nextBtn.setOnClickListener {

            loadMemeData()
        }
        shareBtn.setOnClickListener {

            shareMeme()
        }
        loadMemeData()
    }
    private fun loadMemeData(){

        progressBar.visibility = View.VISIBLE

        RetrofitInstance.apiInterface.getData().enqueue(object : Callback<ApiResponse?> {
            override fun onResponse(call: Call<ApiResponse?>, response: Response<ApiResponse?>) {

                progressBar.visibility = View.GONE
                currentImageUrl = response.body()?.url
                Glide.with(this@MainActivity).load(currentImageUrl).into(memeImage)
            }

            override fun onFailure(call: Call<ApiResponse?>, t: Throwable) {

                progressBar.visibility = View.GONE
                Toast.makeText(this@MainActivity,"Something went wrong", Toast.LENGTH_SHORT).show()
            }
        })
    }
    private fun shareMeme(){

        val intent= Intent(Intent.ACTION_SEND)
        intent.type="text/plain"
        intent.putExtra(Intent.EXTRA_TEXT,"Hey check out this cool meme $currentImageUrl")
        val chooser= Intent.createChooser(intent,"Share this Meme using....")
        startActivity(chooser)

    }
}