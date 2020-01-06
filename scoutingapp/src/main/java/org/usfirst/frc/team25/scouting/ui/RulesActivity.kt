package org.usfirst.frc.team25.scouting.ui

import android.os.Bundle
import android.widget.Toast
import com.github.barteksc.pdfviewer.PDFView
import org.usfirst.frc.team25.scouting.R
import org.usfirst.frc.team25.scouting.ui.RulesActivity
import org.usfirst.frc.team25.scouting.ui.views.NoBackgroundPortraitAppCompatActivity

/**
 * Allows users to view the rules and fouls cheatsheet of the current year's game
 */
class RulesActivity : NoBackgroundPortraitAppCompatActivity() {
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_rules)
		val rulesView = findViewById<PDFView>(R.id.pdfView)
		try {
			rulesView.fromAsset(getString(R.string.cheatsheet_filename))
					.defaultPage(1)
					.showMinimap(false)
					.swipeVertical(true)
					.load()
		} catch (e: NullPointerException) {
			Toast.makeText(this@RulesActivity, "PDF File not found", Toast.LENGTH_LONG)
					.show()
			e.printStackTrace()
		}
	}

	override fun onResume() {
		super.onResume()
		title = getString(R.string.game_name) + " Rules"
	}
}