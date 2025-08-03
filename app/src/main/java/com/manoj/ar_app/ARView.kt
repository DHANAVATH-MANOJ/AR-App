package com.manoj.ar_app

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isGone
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton
import com.google.ar.core.Config
import io.github.sceneview.ar.ArSceneView
import io.github.sceneview.ar.node.ArModelNode
import io.github.sceneview.ar.node.PlacementMode
import io.github.sceneview.math.Position

class ARView : AppCompatActivity() {

    private lateinit var sceneView: ArSceneView
    private lateinit var placeButton: ExtendedFloatingActionButton
    private lateinit var modelNode: ArModelNode
    private var selectedDrill: String? = null
    private lateinit var planeStatusText: TextView
    private var isPlaced = false
    private var isEditMode = false

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_arview)

        selectedDrill = intent.getStringExtra("DRILL_NAME")

        sceneView = findViewById<ArSceneView>(R.id.sceneView).apply {
            this.lightEstimationMode = Config.LightEstimationMode.DISABLED

        }
        planeStatusText = findViewById(R.id.planeStatus)
        placeButton = findViewById(R.id.place)


        placeButton.isEnabled = false
        placeButton.alpha = 0.5f
        planeStatusText.text = "Detecting plane..."

        placeButton.setOnClickListener {
            when {
                !isPlaced -> {
                    modelNode.anchor()
                    isPlaced = true
                    placeButton.text = "Edit"
                    sceneView.planeRenderer.isVisible = false
                }
                !isEditMode -> {
                    isEditMode = true
                    modelNode.isEditable = true
                    placeButton.text = "Done"

                    sceneView.planeRenderer.isVisible = true

                    Toast.makeText(
                        this,
                        "Use two fingers to zoom, rotate, or drag to move the model.",
                        Toast.LENGTH_LONG
                    ).show()
                }
                else -> {
                    isEditMode = false
                    modelNode.isEditable = false
                    placeButton.text = "Edit"
                    sceneView.planeRenderer.isVisible = false
                }
            }
        }

        val modelPath = when (selectedDrill) {
            "Porsche 911 Turbo" -> "models/car1.glb"
            "Jiotto Caspita" -> "models/car2.glb"
            "Ford Mustang Coupe" -> "models/car3.glb"
            else -> "models/car1.glb" // Default fallback
        }

        modelNode = ArModelNode(sceneView.engine, PlacementMode.PLANE_HORIZONTAL).apply {
            isVisible = false
            loadModelGlbAsync(
                glbFileLocation = modelPath,
                scaleToUnits = 3f,
                centerOrigin = Position(0.25f)
            ) {
            }
            onPoseChanged = {
                if (isTracking) {
                    isVisible = true
                    planeStatusText.isGone = true
                    placeButton.isEnabled = true
                    placeButton.alpha = 1f
                } else {
                    isVisible = false
                    planeStatusText.isGone = false
                    placeButton.isEnabled = false
                    placeButton.alpha = 0.5f
                }
            }
            onAnchorChanged = {
            }

            isEditable = false
        }

        sceneView.addChild(modelNode)
    }

    private fun placeModel() {
        modelNode.anchor()
        sceneView.planeRenderer.isVisible = false
    }
}