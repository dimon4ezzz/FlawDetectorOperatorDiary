package ru.flawdetectoroperatordiary

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import ru.flawdetectoroperatordiary.scheme4.Scheme4
import ru.flawdetectoroperatordiary.scheme5a.Scheme5a
import ru.flawdetectoroperatordiary.scheme5d.Scheme5d
import ru.flawdetectoroperatordiary.scheme5e.Scheme5e
import ru.flawdetectoroperatordiary.scheme5g.Scheme5g
import ru.flawdetectoroperatordiary.scheme5v.Scheme5v
import ru.flawdetectoroperatordiary.utils.DefaultFragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val schemesList = resources.getStringArray(R.array.titles)

        val adapter = ArrayAdapter<String>(this, R.layout.spinner_item, schemesList)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        val spinner: Spinner = findViewById(R.id.s_schemes_list)
        spinner.adapter = adapter
        spinner.onItemSelectedListener = defaultOnItemSelectedListener

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)


    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.action_clear) {
            // TODO send to Fragment some Bundle
        }

        return super.onOptionsItemSelected(item)
    }

    private val defaultOnItemSelectedListener = object : AdapterView.OnItemSelectedListener {
        override fun onNothingSelected(parent: AdapterView<*>?) {
            // TODO make something smart
        }

        override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
            when (id) {
                0L -> gotoFragment(Scheme4())
                1L -> gotoFragment(Scheme5a())
                3L -> gotoFragment(Scheme5v())
                4L -> gotoFragment(Scheme5g())
                5L -> gotoFragment(Scheme5d())
                6L -> gotoFragment(Scheme5e())
            }
        }

    }

    private fun gotoFragment(fragment: DefaultFragment) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment, fragment)
            .commit()
    }
}
