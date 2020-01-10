package ru.skillbranch.devintensive

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import ru.skillbranch.devintensive.extensions.log_d

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        log_d("test")
    }
}

