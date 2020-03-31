package com.CSCI5708.dalbike

import android.Manifest
import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.budiyev.android.codescanner.*


class CameraActivity : AppCompatActivity() {
    private lateinit var camerScanner: CodeScanner
    private val PERMISSION_REQUEST_CODE = 200


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_camera)
        supportActionBar!!.setTitle("Scan bike QR")

        askForCameraPermission()

        val scannerView = findViewById<CodeScannerView>(R.id.scanner_view)

        camerScanner = CodeScanner(this, scannerView)

        camerScanner.camera = CodeScanner.CAMERA_BACK // or CAMERA_FRONT or specific camera id
        camerScanner.formats = CodeScanner.ALL_FORMATS // list of type BarcodeFormat,
        // ex. listOf(BarcodeFormat.QR_CODE)
        camerScanner.autoFocusMode = AutoFocusMode.SAFE // or CONTINUOUS
        camerScanner.scanMode = ScanMode.SINGLE // or CONTINUOUS or PREVIEW
        camerScanner.isAutoFocusEnabled = true // Whether to enable auto focus or not
        camerScanner.isFlashEnabled = false // Whether to enable flash or not

        // Callbacks
        camerScanner.decodeCallback = DecodeCallback {
            runOnUiThread {
                Toast.makeText(this, "Bike added to your account ${it.text}", Toast.LENGTH_LONG).show()
                val intent = Intent(this, HomeActivity::class.java)
                startActivity(intent)
            }
        }

        scannerView.setOnClickListener {
            camerScanner.startPreview()
        }
    }

    override fun onResume() {
        super.onResume()
        camerScanner.startPreview()
    }

    override fun onPause() {
        camerScanner.releaseResources()
        super.onPause()
    }

    private fun askForCameraPermission() {
        ActivityCompat.requestPermissions(
            this, arrayOf(Manifest.permission.CAMERA),
            PERMISSION_REQUEST_CODE
        )
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        results: IntArray
    ) {
        when (requestCode) {
            PERMISSION_REQUEST_CODE -> if (results.size > 0 && results[0] === PackageManager.PERMISSION_GRANTED) {
            } else {
                Toast.makeText(applicationContext, "Permission Denied", Toast.LENGTH_SHORT)
                    .show()
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                        != PackageManager.PERMISSION_GRANTED
                    ) {
                        displayDialogBoxForGrantingPermission("You need to allow access permissions",
                            DialogInterface.OnClickListener { dialog, which ->
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                    askForCameraPermission()
                                }
                            })
                    }
                }
            }
        }
    }

    private fun displayDialogBoxForGrantingPermission(
        message: String,
        okListener: DialogInterface.OnClickListener
    ) {
        AlertDialog.Builder(this@CameraActivity)
            .setMessage(message)
            .setPositiveButton("OK", okListener)
            .setNegativeButton("Cancel", null)
            .create()
            .show()
    }

}
