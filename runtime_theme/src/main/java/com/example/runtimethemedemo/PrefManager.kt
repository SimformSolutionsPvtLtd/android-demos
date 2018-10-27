package com.example.runtimethemedemo

import android.content.Context
import android.content.SharedPreferences

/**
* Created by Sanat Shukla on 27/10/18.
*/
class PrefManager(context: Context) {
	private val sharedPreferences: SharedPreferences = context.getSharedPreferences("RuntimeTheme", Context.MODE_PRIVATE)

	companion object {
		private var prefManager: PrefManager? = null

		/**
		* getInstance of PreferenceManager
		*/
		fun getInstance(context: Context): PrefManager? {
			if (prefManager == null)
				prefManager = PrefManager(context)
			return prefManager
		}
	}

	/**
	 * set theme mode
	 */
	fun setThemeMode(mode: Boolean) {
		sharedPreferences.edit().putBoolean("isNightMode", mode).apply()
	}

	/**
	 * get theme mode
	 */
	fun getThemeMode(): Boolean {
		return sharedPreferences.getBoolean("isNightMode", false)
	}
}
