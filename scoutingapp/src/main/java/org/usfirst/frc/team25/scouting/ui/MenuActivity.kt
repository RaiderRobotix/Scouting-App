package org.usfirst.frc.team25.scouting.ui

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.content.res.Configuration
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.os.StrictMode
import android.os.StrictMode.VmPolicy
import android.support.v4.app.ActivityCompat
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import org.usfirst.frc.team25.scouting.R
import org.usfirst.frc.team25.scouting.data.FileManager
import org.usfirst.frc.team25.scouting.data.FileManager.getDataFilename
import org.usfirst.frc.team25.scouting.data.Settings.Companion.newInstance
import org.usfirst.frc.team25.scouting.data.UpdateChecker
import org.usfirst.frc.team25.scouting.ui.dataentry.AddEntryActivity
import org.usfirst.frc.team25.scouting.ui.preferences.SettingsActivity
import org.usfirst.frc.team25.scouting.ui.views.NoBackgroundPortraitAppCompatActivity
import java.io.File
import kotlinx.android.synthetic.main.activity_menu.*


/**
 * The main activity of the application.
 * Responsible for displaying the info bar, game logo, and linking to the four main submenus
 */
class MenuActivity : NoBackgroundPortraitAppCompatActivity() {
	private var statusText: TextView? = null
	//Executes when application is first launched
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		//Manages permissions
		val builder = VmPolicy.Builder()
		StrictMode.setVmPolicy(builder.build())
		//Phone layout has fixed scaling of text and buttons, no Raider Robotix Logo
		if (!isTablet(baseContext)) {
			setContentView(R.layout.activity_menu_phone)
		} else {
			setContentView(R.layout.activity_menu)
		}
		val addEntryButton = add_entry_button
		val shareButton = export_data_button
		val rulesButton = rules_button
		val settingsButton = settings_button
		statusText = current_info_label
		addEntryButton.setOnClickListener {
			//Good model on how to start a new activity
			val i = Intent(this@MenuActivity, AddEntryActivity::class.java)
			startActivity(i)
		}
		shareButton.setOnClickListener {
			try {
				val directory = File(Environment.getExternalStorageDirectory().absolutePath,
						FileManager.DIRECTORY_DATA)
				if (!directory.exists()) {
					directory.mkdir()
				}
				val file = File(directory, getDataFilename(baseContext))
				if (file.length() == 0L) {
					Toast.makeText(baseContext, "Scouting data does not exist",
							Toast.LENGTH_SHORT).show()
				} else {
					val shareIntent = Intent()
					shareIntent.action = Intent.ACTION_SEND
					shareIntent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(file))
					shareIntent.type = "text/plain"
					val chooser = Intent.createChooser(shareIntent, "Export scouting data")
					startActivity(chooser)
				}
			} catch (e: Exception) {
				e.printStackTrace()
			}
		}
		rulesButton.setOnClickListener {
			val i = Intent(this@MenuActivity, RulesActivity::class.java)
			startActivity(i)
		}
		settingsButton.setOnClickListener {
			val i = Intent(this@MenuActivity, SettingsActivity::class.java)
			startActivity(i)
		}
		// Checks for updates from GitHub
		UpdateChecker(this@MenuActivity).execute()
		verifyStoragePermissions(this)
	}

	/**
	 * Determines if the app is running on a tablet or a phone
	 *
	 * @param context Context of the current activity
	 * @return True if the device is a tablet, false otherwise
	 */
	private fun isTablet(context: Context): Boolean { // Note the use of the bitwise AND, rather than the logical AND.=
		val isXlarge = context.resources.configuration.screenLayout and Configuration.SCREENLAYOUT_SIZE_MASK == Configuration.SCREENLAYOUT_SIZE_XLARGE
		val isLarge = context.resources.configuration.screenLayout and Configuration.SCREENLAYOUT_SIZE_MASK == Configuration.SCREENLAYOUT_SIZE_LARGE
		return isXlarge || isLarge
	}

	// Back button disabled to prevent accidental pressing
	override fun onBackPressed() {
		Toast.makeText(this, "Back button currently disabled", Toast.LENGTH_LONG).show()
	}

	/**
	 * Sets the title and info bar upon resuming the activity
	 */
	public override fun onResume() {
		super.onResume()
		updateStatusText()
		title = "Raider Robotix Scouting"
	}

	private fun updateStatusText() {
		val set = newInstance(baseContext)
		var info = set.scoutName + " - " + set.scoutPos + " - Match " + set.matchType + set.matchNum
		if (info.contains("DEFAULT")) {
			info = ""
		}
		statusText!!.text = info
	}

	companion object {
		/**
		 * Checks if the app has permission to write to device storage
		 * If the app does not has permission then the user will be prompted to grant permissions
		 *
		 * @param activity Handle to the current activity, where the prompt appears
		 */
		private fun verifyStoragePermissions(activity: Activity) { // Check if the app has write permission
			val permission = ActivityCompat.checkSelfPermission(activity,
					Manifest.permission.WRITE_EXTERNAL_STORAGE)
			if (permission != PackageManager.PERMISSION_GRANTED) { // App doesn't have permission so prompt the user
				val requestStorageCode = 1
				val storagePermissions = arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE,
						Manifest.permission.WRITE_EXTERNAL_STORAGE)
				ActivityCompat.requestPermissions(activity, storagePermissions, requestStorageCode)
			}
		}
	}
}