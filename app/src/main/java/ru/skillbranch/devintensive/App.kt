package ru.skillbranch.devintensive

import android.app.Activity
import android.app.Application
import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate
import ru.skillbranch.devintensive.extensions.log
import ru.skillbranch.devintensive.repositories.PreferencesRepository

class App : Application() {
    companion object {
        private var instance: App? = null
        fun getContext(): Context? = instance?.applicationContext
    }

    init {
        instance = this
    }

    private fun assignActivityLifecycleCallbacks() {
        this.registerActivityLifecycleCallbacks(object : ActivityLifecycleCallbacks {
            override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
                activity.setTheme(R.style.AppTheme)
                log("onActivityCreated")
            }

            override fun onActivityPaused(activity: Activity) {}
            override fun onActivityStarted(activity: Activity) {}
            override fun onActivityDestroyed(activity: Activity) {}
            override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {}
            override fun onActivityStopped(activity: Activity) {}
            override fun onActivityResumed(activity: Activity) {}

        })
    }

    override fun onCreate() {
        super.onCreate()
        assignActivityLifecycleCallbacks()

        log("onCreate")

        PreferencesRepository.getAppTheme().also {
            AppCompatDelegate.setDefaultNightMode(it)
        }
    }

}