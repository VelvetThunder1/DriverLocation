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
                var ganapathyCoordinates = floatArrayOf(0.1926162816.toFloat(), 1.343521297.toFloat())
                var ganthipuramJunctionCoordinates = floatArrayOf(0.192351043892.toFloat(), 1.34339854792.toFloat())
                var ganthipuramBusCoordinates = floatArrayOf(0.192344394188.toFloat(), 1.34332229448.toFloat())
                var omniCoordinates = floatArrayOf(0.19238597456636091776.toFloat(), 1.3434082196001300158.toFloat())
                var avinashiCoordinates = floatArrayOf(0.1920280637585700112.toFloat(), 1.3433241783833804739.toFloat())
                val ganthipuramBusName = "Ganthipuram Bus Stop"
                val ganapathyBusName = "Ganapathy Bus Stop"
                val omniBusName = "Omni Bus Stand"
                val ganthipuramJunctionName = "Ganthipuram Junction"
                val avinashiRdName = "Avinashi Rd"
                findViewById<Button>(R.id.NameReset).setOnClickListener{
                    Log.d("BTNPRESS", "Names Reset")
                    findViewById<TextView>(R.id.GanthipuramBus).text = ganthipuramBusName
                    findViewById<TextView>(R.id.GanapathyBus).text = ganapathyBusName
                    findViewById<TextView>(R.id.OmniBus).text = omniBusName
                    findViewById<TextView>(R.id.GanthipuramJunction).text = ganthipuramJunctionName
                    findViewById<TextView>(R.id.AvinashiRd).text = avinashiRdName
                }
                findViewById<Button>(R.id.CoordinateReset).setOnClickListener {
                    Log.d("BTNPRESS", "Coordinates Reset")
                    ganapathyCoordinates = floatArrayOf(0.1926162816.toFloat(), 1.343521297.toFloat())
                    ganthipuramJunctionCoordinates = floatArrayOf(0.192351043892.toFloat(), 1.34339854792.toFloat())
                    ganthipuramBusCoordinates = floatArrayOf(0.192344394188.toFloat(), 1.34332229448.toFloat())
                    omniCoordinates = floatArrayOf(0.19238597456636091776.toFloat(), 1.3434082196001300158.toFloat())
                    avinashiCoordinates = floatArrayOf(0.1920280637585700112.toFloat(), 1.3433241783833804739.toFloat())
                }
                findViewById<Button>(R.id.button).setOnClickListener {
                    Log.d("BTNPRESS", "Distance calculated")
                    findViewById<TextView>(R.id.GanapathyBusDist).text = String.format("%.2f km", kotlin.math.acos(kotlin.math.sin(ganapathyCoordinates[0])*kotlin.math.sin(currentLat)+kotlin.math.cos(ganapathyCoordinates[0])*kotlin.math.cos(currentLat)*kotlin.math.cos(currentLon-ganapathyCoordinates[1]))*6371)
                    findViewById<TextView>(R.id.GanthipuramJunctionDist).text = String.format("%.2f km", kotlin.math.acos(kotlin.math.sin(ganthipuramJunctionCoordinates[0])*kotlin.math.sin(currentLat)+kotlin.math.cos(ganthipuramJunctionCoordinates[0])*kotlin.math.cos(currentLat)*kotlin.math.cos(currentLon-ganthipuramJunctionCoordinates[1]))*6371)
                    findViewById<TextView>(R.id.GanthipuramBusDist).text = String.format("%.2f km", kotlin.math.acos(kotlin.math.sin(ganthipuramBusCoordinates[0])*kotlin.math.sin(currentLat)+kotlin.math.cos(ganthipuramBusCoordinates[0])*kotlin.math.cos(currentLat)*kotlin.math.cos(currentLon-ganthipuramBusCoordinates[1]))*6371)
                    findViewById<TextView>(R.id.OmniBusDist).text = String.format("%.2f km", kotlin.math.acos(kotlin.math.sin(omniCoordinates[0])*kotlin.math.sin(currentLat)+kotlin.math.cos(omniCoordinates[0])*kotlin.math.cos(currentLat)*kotlin.math.cos(currentLon-omniCoordinates[1]))*6371)
                    findViewById<TextView>(R.id.AvinashiDist).text = String.format("%.2f km", kotlin.math.acos(kotlin.math.sin(avinashiCoordinates[0])*kotlin.math.sin(currentLat)+kotlin.math.cos(avinashiCoordinates[0])*kotlin.math.cos(currentLat)*kotlin.math.cos(currentLon-avinashiCoordinates[1]))*6371)
                }
                findViewById<Button>(R.id.GanthipuramBusNameEditor).setOnClickListener{
                    val alertDialog = AlertDialog.Builder(this)
                    alertDialog.setTitle("Change Location Name")
                    alertDialog.setMessage("Enter the name of the new location.")
                    val newName = EditText(this)
                    newName.inputType = InputType.TYPE_CLASS_TEXT
                    alertDialog.setView(newName)
                    alertDialog.setPositiveButton("Yes"){_, _ ->
                        findViewById<TextView>(R.id.GanthipuramBus).text = newName.text.toString()
                        Toast.makeText(applicationContext,"clicked yes",Toast.LENGTH_SHORT).show()
                    }
                    alertDialog.setNeutralButton("Cancel"){_, _ ->
                        Toast.makeText(applicationContext,"clicked cancel\n operation cancel",Toast.LENGTH_SHORT).show()
                    }
                    alertDialog.show()
                }
                findViewById<Button>(R.id.GanthipuramBusCoordinateEditor).setOnClickListener{
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
                        ganthipuramBusCoordinates[0] = newLat.text.toString().toFloat()
                        ganthipuramBusCoordinates[1] = newLon.text.toString().toFloat()
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
                findViewById<Button>(R.id.GanapathyNameEditor).setOnClickListener{
                    val alertDialog = AlertDialog.Builder(this)
                    alertDialog.setTitle("Change Location Name")
                    alertDialog.setMessage("Enter the name of the new location.")
                    val newName = EditText(this)
                    newName.inputType = InputType.TYPE_CLASS_TEXT
                    alertDialog.setView(newName)
                    alertDialog.setPositiveButton("Yes"){_, _ ->
                        findViewById<TextView>(R.id.GanapathyBus).text = newName.text.toString()
                        Toast.makeText(applicationContext,"clicked yes",Toast.LENGTH_SHORT).show()
                    }
                    alertDialog.setNeutralButton("Cancel"){_, _ ->
                        Toast.makeText(applicationContext,"clicked cancel\n operation cancel",Toast.LENGTH_SHORT).show()
                    }
                    alertDialog.show()
                }
                findViewById<Button>(R.id.GanapathyCoordinateEditor).setOnClickListener{
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
                        ganapathyCoordinates[0] = newLat.text.toString().toFloat()
                        ganapathyCoordinates[1] = newLon.text.toString().toFloat()
                        Toast.makeText(applicationContext,"clicked yes",Toast.LENGTH_SHORT).show()
                    }
                    alertDialogBuilder.setNegativeButton("Cancel") { _, _ ->
                        Toast.makeText(applicationContext,"clicked cancel\n operation cancel",Toast.LENGTH_SHORT).show()
                    }
                    val alertDialog = alertDialogBuilder.create()
                    alertDialog.show()
                }
                findViewById<Button>(R.id.GanthipuramJunctionNameEditor).setOnClickListener{
                    val alertDialog = AlertDialog.Builder(this)
                    alertDialog.setTitle("Change Location Name")
                    alertDialog.setMessage("Enter the name of the new location.")
                    val newName = EditText(this)
                    newName.inputType = InputType.TYPE_CLASS_TEXT
                    alertDialog.setView(newName)
                    alertDialog.setPositiveButton("Yes"){_, _ ->
                        findViewById<TextView>(R.id.GanthipuramJunction).text = newName.text.toString()
                        Toast.makeText(applicationContext,"clicked yes",Toast.LENGTH_SHORT).show()
                    }
                    alertDialog.setNeutralButton("Cancel"){_, _ ->
                        Toast.makeText(applicationContext,"clicked cancel\n operation cancel",Toast.LENGTH_SHORT).show()
                    }
                    alertDialog.show()
                }
                findViewById<Button>(R.id.GanthipuramJunctionCoordinateEditor).setOnClickListener{
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
                        ganthipuramJunctionCoordinates[0] = newLat.text.toString().toFloat()
                        ganthipuramJunctionCoordinates[1] = newLon.text.toString().toFloat()
                        Toast.makeText(applicationContext,"clicked yes",Toast.LENGTH_SHORT).show()
                    }
                    alertDialogBuilder.setNegativeButton("Cancel") { _, _ ->
                        Toast.makeText(applicationContext,"clicked cancel\n operation cancel",Toast.LENGTH_SHORT).show()
                    }
                    val alertDialog = alertDialogBuilder.create()
                    alertDialog.show()
                }
                findViewById<Button>(R.id.OmniBusNameEditor).setOnClickListener{
                    val alertDialog = AlertDialog.Builder(this)
                    alertDialog.setTitle("Change Location Name")
                    alertDialog.setMessage("Enter the name of the new location.")
                    val newName = EditText(this)
                    newName.inputType = InputType.TYPE_CLASS_TEXT
                    alertDialog.setView(newName)
                    alertDialog.setPositiveButton("Yes"){_, _ ->
                        findViewById<TextView>(R.id.OmniBus).text = newName.text.toString()
                        Toast.makeText(applicationContext,"clicked yes",Toast.LENGTH_SHORT).show()
                    }
                    alertDialog.setNeutralButton("Cancel"){_, _ ->
                        Toast.makeText(applicationContext,"clicked cancel\n operation cancel",Toast.LENGTH_SHORT).show()
                    }
                    alertDialog.show()
                }
                findViewById<Button>(R.id.OmniBusCoordinateEditor).setOnClickListener{
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
                        omniCoordinates[0] = newLat.text.toString().toFloat()
                        omniCoordinates[1] = newLon.text.toString().toFloat()
                        Toast.makeText(applicationContext,"clicked yes",Toast.LENGTH_SHORT).show()
                    }
                    alertDialogBuilder.setNegativeButton("Cancel") { _, _ ->
                        Toast.makeText(applicationContext,"clicked cancel\n operation cancel",Toast.LENGTH_SHORT).show()
                    }
                    val alertDialog = alertDialogBuilder.create()
                    alertDialog.show()
                }
                findViewById<Button>(R.id.AvinashiNameEditor).setOnClickListener{
                    val alertDialog = AlertDialog.Builder(this)
                    alertDialog.setTitle("Change Location Name")
                    alertDialog.setMessage("Enter the name of the new location.")
                    val newName = EditText(this)
                    newName.inputType = InputType.TYPE_CLASS_TEXT
                    alertDialog.setView(newName)
                    alertDialog.setPositiveButton("Yes"){_, _ ->
                        findViewById<TextView>(R.id.AvinashiRd).text = newName.text.toString()
                        Toast.makeText(applicationContext,"clicked yes",Toast.LENGTH_SHORT).show()
                    }
                    alertDialog.setNeutralButton("Cancel"){_, _ ->
                        Toast.makeText(applicationContext,"clicked cancel\n operation cancel",Toast.LENGTH_SHORT).show()
                    }
                    alertDialog.show()
                }
                findViewById<Button>(R.id.AvinashiCoordinateEditor).setOnClickListener{
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
                        avinashiCoordinates[0] = newLat.text.toString().toFloat()
                        avinashiCoordinates[1] = newLon.text.toString().toFloat()
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
