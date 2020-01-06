package org.usfirst.frc.team25.scouting.ui.dataentry

import android.graphics.Color
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.view.View
import android.widget.TextView
import org.usfirst.frc.team25.scouting.R
import org.usfirst.frc.team25.scouting.data.models.ScoutEntry
import org.usfirst.frc.team25.scouting.ui.views.NoBackgroundPortraitAppCompatActivity

/**
 * Container for fragments for match data input
 */
class AddEntryActivity : NoBackgroundPortraitAppCompatActivity() {
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_add_entry)
		val entry = ScoutEntry(null, null, null, null)
		fragmentManager
				.beginTransaction()
				.replace(android.R.id.content, PreMatchFragment.Companion.getInstance(entry), "PREMATCH")
				.commit()
	}

	override fun onBackPressed() {
		val prematch = fragmentManager.findFragmentByTag(
				"PREMATCH") as PreMatchFragment
		val auto = fragmentManager.findFragmentByTag("AUTO") as AutoFragment
		val teleop = fragmentManager.findFragmentByTag("TELEOP") as TeleOpFragment
		val postmatch = fragmentManager.findFragmentByTag(
				"POST") as PostMatchFragment
		if (prematch != null && prematch.isVisible) { // Essentially the Snackbar is shown, but its color is changed first
			val backWarn = Snackbar.make(findViewById(R.id.add_entry), "Back button " +
					"disabled", Snackbar.LENGTH_LONG)
					.setAction("DISCARD DATA") { view: View? -> finish() }
					.setActionTextColor(resources.getColor(R.color.raider_accent_yellow))
			val view = backWarn.view
			val tv = view.findViewById<TextView>(android.support.design.R.id.snackbar_text)
			tv.setTextColor(Color.WHITE)
			backWarn.show()
		} else if (auto != null && auto.isVisible) {
			auto.saveState()
			fragmentManager
					.beginTransaction()
					.replace(android.R.id.content, PreMatchFragment.Companion.getInstance(auto.getEntry()),
							"PREMATCH")
					.commit()
		} else if (teleop != null && teleop.isVisible) {
			teleop.saveState()
			fragmentManager
					.beginTransaction()
					.replace(android.R.id.content, AutoFragment.Companion.getInstance(teleop.entry),
							"AUTO")
					.commit()
		} else if (postmatch != null && postmatch.isVisible) {
			postmatch.saveState()
			fragmentManager
					.beginTransaction()
					.replace(android.R.id.content,
							TeleOpFragment.Companion.getInstance(postmatch.entry), "TELEOP")
					.commit()
		}
	}
}