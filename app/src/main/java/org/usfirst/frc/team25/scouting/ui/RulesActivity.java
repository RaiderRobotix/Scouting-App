package org.usfirst.frc.team25.scouting.ui;

import android.os.Bundle;
import android.widget.Toast;

import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.ScrollBar;


import org.usfirst.frc.team25.scouting.R;
import org.usfirst.frc.team25.scouting.ui.views.NoBackgroundAppCompatActivity;

// Activity that views the rules menu of the year's game
public class RulesActivity extends NoBackgroundAppCompatActivity {

    //From the PDFView library
    PDFView rulesView;

    //File name of the PDF file, placed in the assets folder
    final String RULES_FILEPATH = "FRC-2016-game-manual.pdf";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rules);


        rulesView = (PDFView) findViewById(R.id.pdfView);

        try {
            rulesView.fromAsset(RULES_FILEPATH)
                .defaultPage(1)
                .showMinimap(false)
                .swipeVertical(true)
                .load();

            //Library object for easy navigation
            ScrollBar scrollBar = (ScrollBar) findViewById(R.id.scrollBar);
            rulesView.setScrollBar(scrollBar);

        }catch(NullPointerException e){
            Toast.makeText(RulesActivity.this, "PDF File not found", Toast.LENGTH_LONG ).show();
            e.printStackTrace();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        setTitle("FIRST Stronghold Rules");
    }
}
