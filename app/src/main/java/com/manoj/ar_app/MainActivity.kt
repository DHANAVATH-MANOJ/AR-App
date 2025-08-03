package com.manoj.ar_app

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

data class DrillItem(
    val name: String,
    val imageRes: Int,
    val description: String,
    val tips: String
)


class MainActivity : AppCompatActivity() {

    private lateinit var spinner: Spinner
    private lateinit var startButton: Button
    private lateinit var drillList: List<DrillItem>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        drillList = listOf(
            DrillItem("Porsche 911 Turbo", R.drawable.car1, getString(R.string.Car1_info), getString(R.string.Car1_tip)),
            DrillItem("Jiotto Caspita", R.drawable.car2, getString(R.string.Car2_info), getString(R.string.Car2_tip)),
            DrillItem("Ford Mustang Coupe", R.drawable.car3, getString(R.string.Car3_info), getString(R.string.Car3_tip))
        )

        spinner = findViewById(R.id.drillSpinner)
        startButton = findViewById(R.id.startArButton)


        val adapter = object : ArrayAdapter<DrillItem>(
            this,
            R.layout.spinner_item,
            drillList
        ) {
            override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
                return createCustomView(position, convertView, parent)
            }

            override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
                return createCustomView(position, convertView, parent)
            }

            private fun createCustomView(position: Int, convertView: View?, parent: ViewGroup): View {
                val view = layoutInflater.inflate(R.layout.spinner_item, parent, false)
                val drill = getItem(position)
                val image = view.findViewById<ImageView>(R.id.drillImage)
                val name = view.findViewById<TextView>(R.id.drillName)
                image.setImageResource(drill?.imageRes ?: 0)
                name.text = drill?.name ?: ""
                return view
            }
        }

        spinner.adapter = adapter

        val viewDetailsButton = findViewById<Button>(R.id.viewDrillDetailsButton)
        viewDetailsButton.setOnClickListener {
            val selectedDrill = drillList[spinner.selectedItemPosition]
            val intent = Intent(this, CarDetailActivity::class.java).apply {
                putExtra("imageRes", selectedDrill.imageRes)
                putExtra("name", selectedDrill.name)
                putExtra("description", selectedDrill.description)
                putExtra("tips", selectedDrill.tips)
            }
            startActivity(intent)
        }

        startButton.setOnClickListener {
            val selectedDrill = drillList[spinner.selectedItemPosition]
            val intent = Intent(this, ARView::class.java)
            intent.putExtra("DRILL_NAME", selectedDrill.name)
            startActivity(intent)
        }
    }
}
