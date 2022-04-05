package com.example.remoteconfig.utils

import android.util.Log
import android.widget.Toast
import com.example.remoteconfig.R
import com.google.firebase.ktx.Firebase
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings

object RemoteConfig {
    private lateinit var remoteConfig: FirebaseRemoteConfig

        private const val introduction_msg = "Hello i love android Application Development"


    fun init() {
        runConfig()
    }

    fun runConfig() {
        var remoteConfig: FirebaseRemoteConfig = FirebaseRemoteConfig.getInstance()

        val configSettings = FirebaseRemoteConfigSettings.Builder()
            .setMinimumFetchIntervalInSeconds(60)
            .build()

        remoteConfig.setConfigSettingsAsync(configSettings)
        // [END enable_dev_mode]

        remoteConfig.setDefaultsAsync(R.xml.remote_config)
        // [END set_default_values]

        getIntroductoryMessage()
    }


    fun getIntroductoryMessage(){

        // [START fetch_config_with_callback]
        remoteConfig.fetchAndActivate()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val updated = task.result
                    Log.d("TAG", "Config params updated: $updated")
                    getMessage()

                } else {
                    Log.d("TAG", "Config params updated: $")
                }
            }
    }
    fun getMessage(): String = remoteConfig.getString(introduction_msg)


}