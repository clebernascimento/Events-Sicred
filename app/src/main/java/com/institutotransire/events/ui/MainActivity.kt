package com.institutotransire.events.ui

import android.content.res.Configuration
import android.os.Bundle
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.institutotransire.events.R
import com.institutotransire.events.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var mainBinding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar!!.hide()
        adjustFontScale(resources.configuration)
        mainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        components()

    }

    /**
     * Metodo para inicializar os componentes
     */
    fun components() {
        fragments()
    }

    /**
     * Metodo para iniciar Fragmento Home
     */
    fun fragments() {
        val fm = supportFragmentManager
        val ft = fm.beginTransaction()
        ft.add(R.id.container, MainFragment.newInstance(), "main")
        ft.commit()
    }

    /**
     * Metodo para iniciar o Fragmento
     */
    fun openFragment(fragment: Fragment?) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.container, fragment!!, "dashboard")
        transaction.addToBackStack(null)
        transaction.commit()
    }

    /**
     * Metodo para Ajustar o size do dispositivo
     */
    fun adjustFontScale(configuration: Configuration) {
        configuration.fontScale = 1.0.toFloat()
        val metrics = resources.displayMetrics
        val wm = getSystemService(WINDOW_SERVICE) as WindowManager
        wm.defaultDisplay.getMetrics(metrics)
        metrics.scaledDensity = configuration.fontScale * metrics.density
        baseContext.resources.updateConfiguration(configuration, metrics)
    }
}