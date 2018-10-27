package com.example.runtimethemedemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_main.sb_theme

class MainActivity : AppCompatActivity() {

    private var pref: PrefManager? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        pref = PrefManager.getInstance(this)
        setAppTheme() // set theme based on pref.
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSwitch() // set switch name/check

        sb_theme.setOnCheckedChangeListener { _, isChecked ->
            run {
                if (isChecked) {
                    sb_theme.text = getString(R.string.on)
                    PrefManager.getInstance(this)?.setThemeMode(true)
                    setTheme(R.style.AppThemeDark)
                    recreate()
                } else {
                    PrefManager.getInstance(this)?.setThemeMode(false)
                    sb_theme.text = getString(R.string.off)
                    setTheme(R.style.AppTheme)
                    recreate()

                }
            }
        }
    }

    private fun setSwitch() {
        if (pref != null && pref?.getThemeMode()!!) {
            sb_theme.text = getString(R.string.on)
            sb_theme.isChecked = true
        } else {
            sb_theme.text = getString(R.string.off)
            sb_theme.isChecked = false
        }
    }

    private fun setAppTheme() {
        if(pref != null && pref?.getThemeMode()!!) {
            setTheme(R.style.AppThemeDark)
            window.decorView.systemUiVisibility = 0
        }
        else {
            setTheme(R.style.AppTheme)
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        }
    }
}
