package org.usfirst.frc.team25.scouting.ui.dataentry;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.TextView;

import org.usfirst.frc.team25.scouting.R;
import org.usfirst.frc.team25.scouting.data.models.ScoutEntry;
import org.usfirst.frc.team25.scouting.ui.views.NoBackgroundPortraitAppCompatActivity;

/**
 * Container for fragments for match data input
 */
public class AddEntryActivity extends NoBackgroundPortraitAppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_entry);

        ScoutEntry entry = new ScoutEntry();
        getFragmentManager()
                .beginTransaction()
                .replace(android.R.id.content, PreMatchFragment.getInstance(entry), "PREMATCH")
                .commit();
    }


    @Override
    public void onBackPressed() {

        PreMatchFragment prematch = (PreMatchFragment) getFragmentManager().findFragmentByTag(
                "PREMATCH");
        AutoFragment auto = (AutoFragment) getFragmentManager().findFragmentByTag("AUTO");
        TeleOpFragment teleop = (TeleOpFragment) getFragmentManager().findFragmentByTag("TELEOP");
        PostMatchFragment postmatch = (PostMatchFragment) getFragmentManager().findFragmentByTag(
                "POST");

        if (prematch != null && prematch.isVisible()) {

            // Essentially the Snackbar is shown, but its color is changed first
            Snackbar backWarn = Snackbar.make(findViewById(R.id.add_entry), "Back button " +
                    "disabled", Snackbar.LENGTH_LONG)
                    .setAction("DISCARD DATA", view -> finish())
                    .setActionTextColor(getResources().getColor(R.color.raider_accent_yellow));

            View view = backWarn.getView();
            TextView tv = view.findViewById(android.support.design.R.id.snackbar_text);
            tv.setTextColor(Color.WHITE);
            backWarn.show();

        } else if (auto != null && auto.isVisible()) {
            auto.saveState();
            getFragmentManager()
                    .beginTransaction()
                    .replace(android.R.id.content, PreMatchFragment.getInstance(auto.getEntry()),
                            "PREMATCH")
                    .commit();

        } else if (teleop != null && teleop.isVisible()) {
            teleop.saveState();
            getFragmentManager()
                    .beginTransaction()
                    .replace(android.R.id.content, AutoFragment.getInstance(teleop.getEntry()),
                            "AUTO")
                    .commit();

        } else if (postmatch != null && postmatch.isVisible()) {
            postmatch.saveState();
            getFragmentManager()
                    .beginTransaction()
                    .replace(android.R.id.content,
                            TeleOpFragment.getInstance(postmatch.getEntry()), "TELEOP")
                    .commit();

        }

    }

}
