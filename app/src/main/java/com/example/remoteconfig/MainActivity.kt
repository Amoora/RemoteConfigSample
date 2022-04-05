package com.example.remoteconfig

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import com.example.remoteconfig.databinding.ActivityMainBinding
import com.example.remoteconfig.utils.RemoteConfig
import com.google.firebase.ktx.Firebase
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings


class MainActivity : AppCompatActivity() {
    private lateinit var remoteConfig: FirebaseRemoteConfig


    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        runConfig()

    }

    fun runConfig() {
        remoteConfig = FirebaseRemoteConfig.getInstance()

        val configSettings = FirebaseRemoteConfigSettings.Builder()
            .setMinimumFetchIntervalInSeconds(60)
            .build()

        remoteConfig.setConfigSettingsAsync(configSettings)
        // [END enable_dev_mode]
        remoteConfig.setDefaultsAsync(R.xml.remote_config)

        getIntroductoryMessage()
    }

    fun getIntroductoryMessage(){

        // [START fetch_config_with_callback]
        remoteConfig.fetchAndActivate()
            .addOnCompleteListener { task ->
                val msg = task.result
                if (task.isSuccessful) {

                    Log.d("msg", "All set up: $msg")

                } else {
                    Log.d("msg", "Config params updated: $msg")
                }

            }
        binding.introductoryTitleTV.text = remoteConfig.getString(INTRO_TITLE)
        binding.introductoryTV.text = remoteConfig.getString(INTRO_MSG)
    }

    companion object {
        private const val INTRO_TITLE= "introduction_title"
        private const val  INTRO_MSG= "introduction_msg"
    }
}