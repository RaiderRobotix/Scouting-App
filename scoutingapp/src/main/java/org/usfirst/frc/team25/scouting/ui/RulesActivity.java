package org.usfirst.frc.team25.scouting.ui;

import android.os.Bundle;
import android.widget.Toast;

import com.github.barteksc.pdfviewer.PDFView;

import org.usfirst.frc.team25.scouting.R;
import org.usfirst.frc.team25.scouting.ui.views.NoBackgroundPortraitAppCompatActivity;

/**
 * Allows users to view the rules and fouls cheatsheet of the current year's game
 */
public class RulesActivity extends NoBackgroundPortraitAppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rules);

        PDFView rulesView = findViewById(R.id.pdfView);

        try {
            rulesView.fromAsset(getString(R.string.cheatsheet_filename))
                    .defaultPage(1)
                    .showMinimap(false)
                    .swipeVertical(true)
                    .load();

        } catch (NullPointerException e) {

            Toast.makeText(RulesActivity.this, "PDF File not found", Toast.LENGTH_LONG)
                    .show();
            e.printStackTrace();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        setTitle(getString(R.string.game_name) + " Rules");
    }
}
