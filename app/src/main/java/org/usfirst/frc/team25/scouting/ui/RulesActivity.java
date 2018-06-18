package org.usfirst.frc.team25.scouting.ui;

import android.os.Bundle;
import android.widget.Toast;

import com.github.barteksc.pdfviewer.PDFView;


import org.usfirst.frc.team25.scouting.Constants;
import org.usfirst.frc.team25.scouting.R;
import org.usfirst.frc.team25.scouting.ui.views.NoBackgroundPortraitAppCompatActivity;

// Activity that views the rules menu of the year's game
public class RulesActivity extends NoBackgroundPortraitAppCompatActivity {

    //From the PDFView library
    private PDFView rulesView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rules);


        rulesView = findViewById(R.id.pdfView);

        try {
            rulesView.fromAsset(Constants.RULES_FILEPATH)
                .defaultPage(1)
                .showMinimap(false)
                .swipeVertical(true)
                .load();

        }catch(NullPointerException e){

            Toast.makeText(RulesActivity.this, "PDF File not found", Toast.LENGTH_LONG ).show();
            e.printStackTrace();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        setTitle(Constants.GAME_NAME + " Rules");
    }
}
