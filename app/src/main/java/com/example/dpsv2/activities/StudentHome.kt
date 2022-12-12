package com.example.dpsv2.activities

import android.Manifest
import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.location.Geocoder
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.*
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.dpsv2.BuildConfig
import com.example.dpsv2.MainActivity
import com.example.dpsv2.R
import com.example.dpsv2.databinding.ActivityStudentHomeBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.Status
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.widget.AutocompleteSupportFragment
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetBehavior.BottomSheetCallback
import com.google.firebase.auth.FirebaseAuth
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type
import java.util.*
import kotlin.collections.ArrayList


class StudentHome : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityStudentHomeBinding
    private lateinit var bottomSheetDialog : View
    private lateinit var recent_searches : ArrayList<String>
    private lateinit var listview : ListView
    private lateinit var arrayAdapter : ArrayAdapter<String>

    private var locationManager : LocationManager? = null

    @SuppressLint("MissingPermission")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStudentHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        loadRecents()


        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                signout()
            }
        }
        this.onBackPressedDispatcher.addCallback(this, callback)


        val sharedpref = this.getSharedPreferences("dpsv2",MODE_PRIVATE) ?: return
        val sharedprefeditor = sharedpref.edit()


        locationManager = getSystemService(LOCATION_SERVICE) as LocationManager?
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync{
            mMap = it
        }

        try {
            if (ContextCompat.checkSelfPermission(
                    applicationContext,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                    101
                )

            }else{
                locationManager?.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0L, 0f,locationListener )
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        bottomSheetDialog = findViewById(R.id.student_home_bottom_sheet)
        var mBottomSheetBehavior = BottomSheetBehavior.from(bottomSheetDialog)
        mBottomSheetBehavior.addBottomSheetCallback(object : BottomSheetCallback() {
            override fun onStateChanged(bottomSheet: View, newState: Int) {}
            override fun onSlide(bottomSheet: View, slideOffset: Float) {
            }
        })


        val apiKey = BuildConfig.apiKey
        if (!Places.isInitialized()) {
            Places.initialize(applicationContext, "AIzaSyDBBg7jEIDF6a_q5KFzrHe-WZFZ5mtYdxs")
        }
//        val placesClient = Places.createClient(this)
        val autocompleteSupportFragment = supportFragmentManager
            .findFragmentById(R.id.autocomplete_fragment) as AutocompleteSupportFragment?
        val fView : View? = autocompleteSupportFragment!!.view
        val etext : EditText? = fView?.findViewById(com.google.android.libraries.places.R.id.places_autocomplete_search_input)
        fView!!.findViewById<View>(com.google.android.libraries.places.R.id.places_autocomplete_search_button).visibility = View.GONE


        etext!!.setTextColor( Color.parseColor("#000000"))
        etext.setText("Or Search Different Destination")
        etext.background = getDrawable(R.drawable.edit_text_background)
        autocompleteSupportFragment.setPlaceFields(
            listOf(
                Place.Field.NAME,
                Place.Field.ADDRESS,
                Place.Field.PHONE_NUMBER,
                Place.Field.LAT_LNG,
                Place.Field.OPENING_HOURS,
                Place.Field.RATING,
                Place.Field.USER_RATINGS_TOTAL
            )
        )
        autocompleteSupportFragment.setOnPlaceSelectedListener(object : PlaceSelectionListener{
            override fun onPlaceSelected(p0: Place) {
                Log.i(TAG, "Place: ${p0.name}, ${p0.latLng}")
                p0.address?.let { saveRecents(it) }
                val intent = Intent(this@StudentHome, StudentRideReview::class.java)
                intent.putExtra("DESTINATION", p0.address)
                startActivity(intent)

            }

            override fun onError(p0: Status) {
                Log.i(TAG, "An error occurred: $p0")
            }

        })

        listview = findViewById(R.id.recentSearches_listview)
        arrayAdapter = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1,
            recent_searches.toList()
        )
        listview.adapter = arrayAdapter
        listview.setOnItemClickListener { parent, view, position, id ->
            val element = parent.getItemAtPosition(position).toString()
            val intent1 = Intent(this@StudentHome, StudentRideReview::class.java)
            intent1.putExtra("DESTINATION", element)
            startActivity(intent1)
        }
    }

    private val locationListener: LocationListener = object : LocationListener {
        override fun onLocationChanged(location: Location) {
            var currentlocation = LatLng(location.latitude, location.longitude)
            Log.d("LOCATION_LATITUDE", location.latitude.toString())
            Log.d("LOCATION_LONGITUTE", location.longitude.toString())
            val geocoder = Geocoder(this@StudentHome, Locale.getDefault())
            val address = geocoder.getFromLocation(location.latitude, location.longitude, 1)
            findViewById<TextView>(R.id.current_address).text = address[0].getAddressLine(0)
            val mapFragment = supportFragmentManager
                .findFragmentById(R.id.map) as SupportMapFragment?
            (supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?)?.let {
                mapFragment!!.getMapAsync {
                    mMap = it
                    mMap.clear()
                    mMap.addMarker(MarkerOptions().position(currentlocation).title("Current Location"))
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentlocation, 15.0f))
                }
            }
        }
        override fun onProviderEnabled(provider: String) {}
        override fun onProviderDisabled(provider: String) {}
    }

    override fun onMapReady(p0: GoogleMap) {
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.student_home,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.student_view_cancel_ride -> {
                val intent = Intent(this@StudentHome, StudentRideStatus::class.java)
                startActivity(intent)
            }
            R.id.student_edit_profile -> {
                val intent = Intent(this@StudentHome, StudentProfile::class.java)
                startActivity(intent)
            }
            R.id.student_signout -> {
                signout()

            }

        }
        return super.onOptionsItemSelected(item)
    }

    fun signout() {
        val builder: android.app.AlertDialog.Builder = android.app.AlertDialog.Builder(this)

        builder.setTitle("Log Out ?")
        builder.setMessage("Are you sure you want to log out?")

        builder.setPositiveButton(
            "YES",
            DialogInterface.OnClickListener { dialog, which -> // Do nothing but close the dialog
                FirebaseAuth.getInstance().signOut()
                val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                    .requestEmail()
                    .build()
                val mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

                mGoogleSignInClient.signOut().addOnCompleteListener(this)
                {
                    val intent = Intent(this@StudentHome, MainActivity::class.java)
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                    startActivity(intent)
                }
                dialog.dismiss()
                finish()
            })

        builder.setNegativeButton(
            "NO",
            DialogInterface.OnClickListener { dialog, _ -> // Do nothing
                dialog.dismiss()
            })

        builder.create()?.show()
    }

    override fun onResume() {
        super.onResume()
        loadRecents()
        arrayAdapter.notifyDataSetChanged()
        val curr_address = findViewById<TextView>(R.id.current_address)
        curr_address.setOnClickListener {
            if (curr_address.text != "Fetching Location ...") {
                saveRecents(curr_address.text.toString())
                val intent = Intent(this@StudentHome, StudentRideReview::class.java)
                intent.putExtra("DESTINATION", curr_address.text)
                startActivity(intent)
            }else{
                Toast.makeText(this,"Please wait for Location...", Toast.LENGTH_SHORT).show()
            }
        }
    }

    @SuppressLint("CommitPrefEdits")
    fun saveRecents(address: String) {
        val sharedpref = applicationContext.getSharedPreferences("dpsv2",MODE_PRIVATE) ?: return
        val sharedprefeditor = sharedpref.edit()
        val gson = Gson()
        recent_searches.add(address)
        val json = gson.toJson(recent_searches)
        sharedprefeditor.putString("RECENT_SEARCHES", json)
        sharedprefeditor.apply()
        loadRecents()
    }

    fun loadRecents() {
        val sharedpref = applicationContext.getSharedPreferences("dpsv2",MODE_PRIVATE) ?: return
        val gson = Gson()
        val json = sharedpref.getString("RECENT_SEARCHES",null)
        val type  = object : TypeToken<ArrayList<String>>() {}.type
        if (json != null){
            recent_searches = gson.fromJson(json, type)
        }else{
            recent_searches = arrayListOf<String>()
        }
    }
}