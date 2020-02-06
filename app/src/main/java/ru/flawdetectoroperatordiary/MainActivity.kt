package ru.flawdetectoroperatordiary

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val schemesList = resources.getStringArray(R.array.titles)

        val adapter = ArrayAdapter<String>(this, R.layout.spinner_item, schemesList)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        val spinner: Spinner = findViewById(R.id.s_schemes_list)
        spinner.adapter = adapter

        val toolbar =
            layoutInflater.inflate(R.layout.toolbar, findViewById(android.R.id.content), false)
        supportActionBar?.customView = toolbar
    }
}
