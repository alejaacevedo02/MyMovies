package com.alejandraacevedo.mymovies.activities

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.location.Geocoder
import android.location.Location
import android.os.Build
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.alejandraacevedo.mymovies.DEFAULT_COUNTRY_CODE
import com.alejandraacevedo.mymovies.EXTRA_MOVIE
import com.alejandraacevedo.mymovies.adapter.MoviesAdapter
import com.alejandraacevedo.mymovies.R
import com.alejandraacevedo.mymovies.databinding.ActivityMainBinding
import com.alejandraacevedo.mymovies.model.Movie
import com.alejandraacevedo.mymovies.service.MovieDbClient
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import kotlinx.coroutines.launch
import kotlinx.coroutines.suspendCancellableCoroutine
import timber.log.Timber
import kotlin.coroutines.resume

class MainActivity : AppCompatActivity() {
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private val moviesAdapter = MoviesAdapter(emptyList()) { movie ->
        navigateTo(movie)
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private val requestPermissionLauncher = registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
        doRequestPopularMovies(isGranted)
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        binding.moviesRv.adapter = moviesAdapter

        requestPermissionLauncher.launch(Manifest.permission.ACCESS_COARSE_LOCATION)
    }

    private fun doRequestPopularMovies(isLocationGranted: Boolean) {
        lifecycleScope.launch {
            val apiKey = getString(R.string.api_key)
            val region = getRegion(isLocationGranted)
            val popularMovies = MovieDbClient.service.listPopularMovies(apiKey, region)
            moviesAdapter.movies = popularMovies.results
            moviesAdapter.notifyDataSetChanged()
        }
    }

    @SuppressLint("MissingPermission")
    private suspend fun getRegion(isLocationGranted: Boolean): String = suspendCancellableCoroutine { continuation ->
        if (isLocationGranted) {
            fusedLocationClient.lastLocation.addOnCompleteListener {
                continuation.resume(getRegionFromLocation(it.result))
            }
        } else {
            continuation.resume(DEFAULT_COUNTRY_CODE)
        }
    }

    private fun getRegionFromLocation(location: Location?): String {
        if (location == null) {
            return DEFAULT_COUNTRY_CODE
        }
        val geocoder = Geocoder(this)
        val result = geocoder.getFromLocation(location.latitude, location.longitude, 1)
        return result.firstOrNull()?.countryCode ?: DEFAULT_COUNTRY_CODE
    }

    private fun navigateTo(movie: Movie) {
        val intent = Intent(this, DetailActivity::class.java)
        intent.putExtra(EXTRA_MOVIE, movie)
        startActivity(intent)
    }

    override fun onDestroy() {
        super.onDestroy()
        Timber.d("onDestroy")
    }
}