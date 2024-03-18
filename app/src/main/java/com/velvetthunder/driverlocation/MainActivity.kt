package com.velvetthunder.driverlocation

import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.text.InputType
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices

class MainActivity : AppCompatActivity() {

    private val REQUEST_CODE = 101
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private lateinit var currentLocation: Location

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)
        fetchLastLocation()
        }

    private fun fetchLastLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION), REQUEST_CODE)
            return
        }
        val task = fusedLocationProviderClient.lastLocation
        task.addOnSuccessListener { location ->
            if (location != null) {
                currentLocation = location
                val currentLat = (currentLocation.latitude * 3.14159)/180
                val currentLon = (currentLocation.longitude * 3.14159)/180
                var LocOneCoordinates = floatArrayOf()
                var LocTwoCoordinates = floatArrayOf()
                var LocThreeCoordinates = floatArrayOf()
                var LocFourCoordinates = floatArrayOf()
                var LocFiveCoordinates = floatArrayOf()
                val LocOneName = "Location 1"
                val LocTwoName = "Location 2"
                val LocThreeName = "Location 3"
                val LocFourName = "Location 4"
                val LocFiveName = "Location 5"
                findViewById<Button>(R.id.NameReset).setOnClickListener{
                    Log.d("BTNPRESS", "Names Reset")
                    findViewById<TextView>(R.id.LocOne).text = LocOneName
                    findViewById<TextView>(R.id.LocTwo).text = LocTwoName
                    findViewById<TextView>(R.id.LocThree).text = LocThreeName
                    findViewById<TextView>(R.id.LocFour).text = LocFourName
                    findViewById<TextView>(R.id.LocFive).text = LocFiveName
                }
                findViewById<Button>(R.id.CoordinateReset).setOnClickListener {
                    Log.d("BTNPRESS", "Coordinates Reset")
                    LocOneCoordinates = floatArrayOf()
                    LocTwoCoordinates = floatArrayOf()
                    LocThreeCoordinates = floatArrayOf()
                    LocFourCoordinates = floatArrayOf()
                    LocFiveCoordinates = floatArrayOf()
                }
                findViewById<Button>(R.id.button).setOnClickListener {
                    Log.d("BTNPRESS", "Distance calculated")
                    findViewById<TextView>(R.id.LocTwoDist).text = String.format("%.2f km", kotlin.math.acos(kotlin.math.sin(LocTwoCoordinates[0])*kotlin.math.sin(currentLat)+kotlin.math.cos(LocTwoCoordinates[0])*kotlin.math.cos(currentLat)*kotlin.math.cos(currentLon-LocTwoCoordinates[1]))*6371)
                    findViewById<TextView>(R.id.LocFourDist).text = String.format("%.2f km", kotlin.math.acos(kotlin.math.sin(LocFourCoordinates[0])*kotlin.math.sin(currentLat)+kotlin.math.cos(LocFourCoordinates[0])*kotlin.math.cos(currentLat)*kotlin.math.cos(currentLon-LocFourCoordinates[1]))*6371)
                    findViewById<TextView>(R.id.LocOneDist).text = String.format("%.2f km", kotlin.math.acos(kotlin.math.sin(LocOneCoordinates[0])*kotlin.math.sin(currentLat)+kotlin.math.cos(LocOneCoordinates[0])*kotlin.math.cos(currentLat)*kotlin.math.cos(currentLon-LocOneCoordinates[1]))*6371)
                    findViewById<TextView>(R.id.LocThreeDist).text = String.format("%.2f km", kotlin.math.acos(kotlin.math.sin(LocThreeCoordinates[0])*kotlin.math.sin(currentLat)+kotlin.math.cos(LocThreeCoordinates[0])*kotlin.math.cos(currentLat)*kotlin.math.cos(currentLon-LocThreeCoordinates[1]))*6371)
                    findViewById<TextView>(R.id.LocFiveDist).text = String.format("%.2f km", kotlin.math.acos(kotlin.math.sin(LocFiveCoordinates[0])*kotlin.math.sin(currentLat)+kotlin.math.cos(LocFiveCoordinates[0])*kotlin.math.cos(currentLat)*kotlin.math.cos(currentLon-LocFiveCoordinates[1]))*6371)
                }
                findViewById<Button>(R.id.LocOneNameEditor).setOnClickListener{
                    val alertDialog = AlertDialog.Builder(this)
                    alertDialog.setTitle("Change Location Name")
                    alertDialog.setMessage("Enter the name of the new location.")
                    val newName = EditText(this)
                    newName.inputType = InputType.TYPE_CLASS_TEXT
                    alertDialog.setView(newName)
                    alertDialog.setPositiveButton("Yes"){_, _ ->
                        findViewById<TextView>(R.id.LocOne).text = newName.text.toString()
                        Toast.makeText(applicationContext,"clicked yes",Toast.LENGTH_SHORT).show()
                    }
                    alertDialog.setNeutralButton("Cancel"){_, _ ->
                        Toast.makeText(applicationContext,"clicked cancel\n operation cancel",Toast.LENGTH_SHORT).show()
                    }
                    alertDialog.show()
                }
                findViewById<Button>(R.id.LocOneCoordinateEditor).setOnClickListener{
                    val alertDialogBuilder = AlertDialog.Builder(this)
                    alertDialogBuilder.setTitle("Change Location Coordinates")
                    val linearLayout = LinearLayout(this)
                    linearLayout.orientation = LinearLayout.VERTICAL
                    val newLat = EditText(this)
                    newLat.inputType = InputType.TYPE_CLASS_NUMBER
                    newLat.hint = "Enter latitude"
                    linearLayout.addView(newLat)
                    val newLon = EditText(this)
                    newLon.inputType = InputType.TYPE_CLASS_NUMBER
                    newLon.hint = "Enter longitude"
                    linearLayout.addView(newLon)
                    alertDialogBuilder.setView(linearLayout)
                    alertDialogBuilder.setPositiveButton("OK") { _, _ ->
                        LocOneCoordinates[0] = newLat.text.toString().toFloat()
                        LocOneCoordinates[1] = newLon.text.toString().toFloat()
                        Log.d("OK PRESSED", newLat.text.toString())
                        Log.d("OK PRESSED", newLon.text.toString())
                        Toast.makeText(applicationContext,"clicked yes",Toast.LENGTH_SHORT).show()
                    }
                    alertDialogBuilder.setNegativeButton("Cancel") { _, _ ->
                        Toast.makeText(applicationContext,"clicked cancel\n operation cancel",Toast.LENGTH_SHORT).show()
                    }
                    val alertDialog = alertDialogBuilder.create()
                    alertDialog.show()
                }
                findViewById<Button>(R.id.LocTwoNameEditor).setOnClickListener{
                    val alertDialog = AlertDialog.Builder(this)
                    alertDialog.setTitle("Change Location Name")
                    alertDialog.setMessage("Enter the name of the new location.")
                    val newName = EditText(this)
                    newName.inputType = InputType.TYPE_CLASS_TEXT
                    alertDialog.setView(newName)
                    alertDialog.setPositiveButton("Yes"){_, _ ->
                        findViewById<TextView>(R.id.LocTwo).text = newName.text.toString()
                        Toast.makeText(applicationContext,"clicked yes",Toast.LENGTH_SHORT).show()
                    }
                    alertDialog.setNeutralButton("Cancel"){_, _ ->
                        Toast.makeText(applicationContext,"clicked cancel\n operation cancel",Toast.LENGTH_SHORT).show()
                    }
                    alertDialog.show()
                }
                findViewById<Button>(R.id.LocTwoCoordinateEditor).setOnClickListener{
                    val alertDialogBuilder = AlertDialog.Builder(this)
                    alertDialogBuilder.setTitle("Change Location Coordinates")
                    val linearLayout = LinearLayout(this)
                    linearLayout.orientation = LinearLayout.VERTICAL
                    val newLat = EditText(this)
                    newLat.inputType = InputType.TYPE_CLASS_NUMBER
                    newLat.hint = "Enter latitude"
                    linearLayout.addView(newLat)
                    val newLon = EditText(this)
                    newLon.inputType = InputType.TYPE_CLASS_NUMBER
                    newLon.hint = "Enter longitude"
                    linearLayout.addView(newLon)
                    alertDialogBuilder.setView(linearLayout)
                    alertDialogBuilder.setPositiveButton("OK") { _, _ ->
                        LocTwoCoordinates[0] = newLat.text.toString().toFloat()
                        LocTwoCoordinates[1] = newLon.text.toString().toFloat()
                        Toast.makeText(applicationContext,"clicked yes",Toast.LENGTH_SHORT).show()
                    }
                    alertDialogBuilder.setNegativeButton("Cancel") { _, _ ->
                        Toast.makeText(applicationContext,"clicked cancel\n operation cancel",Toast.LENGTH_SHORT).show()
                    }
                    val alertDialog = alertDialogBuilder.create()
                    alertDialog.show()
                }
                findViewById<Button>(R.id.LocFourNameEditor).setOnClickListener{
                    val alertDialog = AlertDialog.Builder(this)
                    alertDialog.setTitle("Change Location Name")
                    alertDialog.setMessage("Enter the name of the new location.")
                    val newName = EditText(this)
                    newName.inputType = InputType.TYPE_CLASS_TEXT
                    alertDialog.setView(newName)
                    alertDialog.setPositiveButton("Yes"){_, _ ->
                        findViewById<TextView>(R.id.LocFour).text = newName.text.toString()
                        Toast.makeText(applicationContext,"clicked yes",Toast.LENGTH_SHORT).show()
                    }
                    alertDialog.setNeutralButton("Cancel"){_, _ ->
                        Toast.makeText(applicationContext,"clicked cancel\n operation cancel",Toast.LENGTH_SHORT).show()
                    }
                    alertDialog.show()
                }
                findViewById<Button>(R.id.LocFourCoordinateEditor).setOnClickListener{
                    val alertDialogBuilder = AlertDialog.Builder(this)
                    alertDialogBuilder.setTitle("Change Location Coordinates")
                    val linearLayout = LinearLayout(this)
                    linearLayout.orientation = LinearLayout.VERTICAL
                    val newLat = EditText(this)
                    newLat.inputType = InputType.TYPE_CLASS_NUMBER
                    newLat.hint = "Enter latitude"
                    linearLayout.addView(newLat)
                    val newLon = EditText(this)
                    newLon.inputType = InputType.TYPE_CLASS_NUMBER
                    newLon.hint = "Enter longitude"
                    linearLayout.addView(newLon)
                    alertDialogBuilder.setView(linearLayout)
                    alertDialogBuilder.setPositiveButton("OK") { _, _ ->
                        LocFourCoordinates[0] = newLat.text.toString().toFloat()
                        LocFourCoordinates[1] = newLon.text.toString().toFloat()
                        Toast.makeText(applicationContext,"clicked yes",Toast.LENGTH_SHORT).show()
                    }
                    alertDialogBuilder.setNegativeButton("Cancel") { _, _ ->
                        Toast.makeText(applicationContext,"clicked cancel\n operation cancel",Toast.LENGTH_SHORT).show()
                    }
                    val alertDialog = alertDialogBuilder.create()
                    alertDialog.show()
                }
                findViewById<Button>(R.id.LocThreeNameEditor).setOnClickListener{
                    val alertDialog = AlertDialog.Builder(this)
                    alertDialog.setTitle("Change Location Name")
                    alertDialog.setMessage("Enter the name of the new location.")
                    val newName = EditText(this)
                    newName.inputType = InputType.TYPE_CLASS_TEXT
                    alertDialog.setView(newName)
                    alertDialog.setPositiveButton("Yes"){_, _ ->
                        findViewById<TextView>(R.id.LocThree).text = newName.text.toString()
                        Toast.makeText(applicationContext,"clicked yes",Toast.LENGTH_SHORT).show()
                    }
                    alertDialog.setNeutralButton("Cancel"){_, _ ->
                        Toast.makeText(applicationContext,"clicked cancel\n operation cancel",Toast.LENGTH_SHORT).show()
                    }
                    alertDialog.show()
                }
                findViewById<Button>(R.id.LocThreeCoordinateEditor).setOnClickListener{
                    val alertDialogBuilder = AlertDialog.Builder(this)
                    alertDialogBuilder.setTitle("Change Location Coordinates")
                    val linearLayout = LinearLayout(this)
                    linearLayout.orientation = LinearLayout.VERTICAL
                    val newLat = EditText(this)
                    newLat.inputType = InputType.TYPE_CLASS_NUMBER
                    newLat.hint = "Enter latitude"
                    linearLayout.addView(newLat)
                    val newLon = EditText(this)
                    newLon.inputType = InputType.TYPE_CLASS_NUMBER
                    newLon.hint = "Enter longitude"
                    linearLayout.addView(newLon)
                    alertDialogBuilder.setView(linearLayout)
                    alertDialogBuilder.setPositiveButton("OK") { _, _ ->
                        LocThreeCoordinates[0] = newLat.text.toString().toFloat()
                        LocThreeCoordinates[1] = newLon.text.toString().toFloat()
                        Toast.makeText(applicationContext,"clicked yes",Toast.LENGTH_SHORT).show()
                    }
                    alertDialogBuilder.setNegativeButton("Cancel") { _, _ ->
                        Toast.makeText(applicationContext,"clicked cancel\n operation cancel",Toast.LENGTH_SHORT).show()
                    }
                    val alertDialog = alertDialogBuilder.create()
                    alertDialog.show()
                }
                findViewById<Button>(R.id.LocFiveNameEditor).setOnClickListener{
                    val alertDialog = AlertDialog.Builder(this)
                    alertDialog.setTitle("Change Location Name")
                    alertDialog.setMessage("Enter the name of the new location.")
                    val newName = EditText(this)
                    newName.inputType = InputType.TYPE_CLASS_TEXT
                    alertDialog.setView(newName)
                    alertDialog.setPositiveButton("Yes"){_, _ ->
                        findViewById<TextView>(R.id.LocFive).text = newName.text.toString()
                        Toast.makeText(applicationContext,"clicked yes",Toast.LENGTH_SHORT).show()
                    }
                    alertDialog.setNeutralButton("Cancel"){_, _ ->
                        Toast.makeText(applicationContext,"clicked cancel\n operation cancel",Toast.LENGTH_SHORT).show()
                    }
                    alertDialog.show()
                }
                findViewById<Button>(R.id.LocFiveCoordinateEditor).setOnClickListener{
                    val alertDialogBuilder = AlertDialog.Builder(this)
                    alertDialogBuilder.setTitle("Change Location Coordinates")
                    val linearLayout = LinearLayout(this)
                    linearLayout.orientation = LinearLayout.VERTICAL
                    val newLat = EditText(this)
                    newLat.inputType = InputType.TYPE_CLASS_NUMBER
                    newLat.hint = "Enter latitude"
                    linearLayout.addView(newLat)
                    val newLon = EditText(this)
                    newLon.inputType = InputType.TYPE_CLASS_NUMBER
                    newLon.hint = "Enter longitude"
                    linearLayout.addView(newLon)
                    alertDialogBuilder.setView(linearLayout)
                    alertDialogBuilder.setPositiveButton("OK") { _, _ ->
                        LocFiveCoordinates[0] = newLat.text.toString().toFloat()
                        LocFiveCoordinates[1] = newLon.text.toString().toFloat()
                        Toast.makeText(applicationContext,"clicked yes",Toast.LENGTH_SHORT).show()
                    }
                    alertDialogBuilder.setNegativeButton("Cancel") { _, _ ->
                        Toast.makeText(applicationContext,"clicked cancel\n operation cancel",Toast.LENGTH_SHORT).show()
                    }
                    val alertDialog = alertDialogBuilder.create()
                    alertDialog.show()
                }
            }
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            REQUEST_CODE -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    fetchLastLocation()
                }
            }
        }
    }
}
