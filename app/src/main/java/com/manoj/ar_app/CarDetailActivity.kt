package com.manoj.ar_app

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat

class CarDetailActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_IMAGE_RES = "imageRes"
        const val EXTRA_NAME = "name"
        const val EXTRA_DESCRIPTION = "description"
        const val EXTRA_TIPS = "tips"

        private const val DEFAULT_NAME = "Unknown Car"
        private const val DEFAULT_DESCRIPTION = "No description available"
        private const val DEFAULT_TIPS = "No tips available"
    }

    private lateinit var carImageView: ImageView
    private lateinit var carNameTextView: TextView
    private lateinit var carDescriptionTextView: TextView
    private lateinit var carTipsTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_car_detail)

        setupToolbar()
        initializeViews()
        populateCarDetails()
    }

    private fun setupToolbar() {
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
            title = getString(R.string.car_details_title)
        }
    }

    private fun initializeViews() {
        carImageView = findViewById(R.id.carImageView)
        carNameTextView = findViewById(R.id.carNameTextView)
        carDescriptionTextView = findViewById(R.id.carDescriptionTextView)
        carTipsTextView = findViewById(R.id.carTipsTextView)
    }

    private fun populateCarDetails() {
        val carData = extractCarDataFromIntent()

        with(carData) {
            setCarImage(imageResId)
            setCarName(name)
            setCarDescription(description)
            setCarTips(tips)
        }
    }

    private fun extractCarDataFromIntent(): CarData {
        return CarData(
            imageResId = intent.getIntExtra(EXTRA_IMAGE_RES, 0),
            name = intent.getStringExtra(EXTRA_NAME) ?: DEFAULT_NAME,
            description = intent.getStringExtra(EXTRA_DESCRIPTION) ?: DEFAULT_DESCRIPTION,
            tips = intent.getStringExtra(EXTRA_TIPS) ?: DEFAULT_TIPS
        )
    }

    private fun setCarImage(imageResId: Int) {
        if (imageResId != 0) {
            carImageView.setImageResource(imageResId)
        } else {
            // Set placeholder image if no image resource provided
            carImageView.setImageDrawable(
                ContextCompat.getDrawable(this, R.drawable.car1)
            )
        }
    }

    private fun setCarName(name: String) {
        carNameTextView.text = name
    }

    private fun setCarDescription(description: String) {
        carDescriptionTextView.text = description
    }

    private fun setCarTips(tips: String) {
        carTipsTextView.text = tips
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    private data class CarData(
        val imageResId: Int,
        val name: String,
        val description: String,
        val tips: String
    )
}