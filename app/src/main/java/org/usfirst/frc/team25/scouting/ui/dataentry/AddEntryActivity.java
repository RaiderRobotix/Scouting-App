package org.usfirst.frc.team25.scouting.ui.dataentry;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.TextView;

import org.usfirst.frc.team25.scouting.R;
import org.usfirst.frc.team25.scouting.data.models.ScoutEntry;
import org.usfirst.frc.team25.scouting.data.Settings;
import org.usfirst.frc.team25.scouting.ui.views.NoBackgroundAppCompatActivity;

/**
 * Container for fragments for match data input
 */
public class AddEntryActivity extends NoBackgroundAppCompatActivity {

    ScoutEntry entry;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_entry);
        entry = new ScoutEntry();
        getFragmentManager()
                .beginTransaction()
                .replace(android.R.id.content, PrematchFragment.getInstance(entry), "PREMATCH")
                .commit();
    }


    @Override
    public void onBackPressed() {
        PrematchFragment pre =(PrematchFragment) getFragmentManager().findFragmentByTag("PREMATCH");
        AutoFragment auto = (AutoFragment) getFragmentManager().findFragmentByTag("AUTO");
        TeleOpFragment tele = (TeleOpFragment) getFragmentManager().findFragmentByTag("TELEOP");
        PostMatchFragment post = (PostMatchFragment) getFragmentManager().findFragmentByTag("POST");

        if(pre!=null && pre.isVisible()){
            Snackbar backWarn = Snackbar.make(findViewById(R.id.add_entry), "Back button disabled", Snackbar.LENGTH_LONG) // Essentially the Snackbar is shown, but its color is changed first
                    .setAction("DISCARD DATA", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            finish();
                        }
                    })
                    .setActionTextColor(getResources().getColor(R.color.raider_accent_yellow));
            View view = backWarn.getView();
            TextView tv = (TextView) view.findViewById(android.support.design.R.id.snackbar_text);
            tv.setTextColor(Color.WHITE);
            backWarn.show();

        }

        else if(auto!=null && auto.isVisible()){
            auto.saveState();
            getFragmentManager()
                    .beginTransaction()
                    .replace(android.R.id.content, PrematchFragment.getInstance(auto.getEntry()), "PREMATCH")
                    .commit();

        }

        else if(tele!=null && tele.isVisible()){
            tele.saveState();
            getFragmentManager()
                    .beginTransaction()
                    .replace(android.R.id.content, AutoFragment.getInstance(tele.getEntry()), "AUTO")
                    .commit();

        }

        else if(post!=null && post.isVisible()){
            post.saveState();
            getFragmentManager()
                    .beginTransaction()
                    .replace(android.R.id.content, TeleOpFragment.getInstance(post.getEntry()), "TELEOP")
                    .commit();

        }

    }

    @Override
    public void finish() {
        super.finish();
        Settings set = Settings.newInstance(getBaseContext());
        set.setMatchNum(set.getMatchNum()+1);

    }
}
