package org.usfirst.frc.team25.scouting.ui.dataentry;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

import com.rengwuxian.materialedittext.MaterialEditText;

import org.usfirst.frc.team25.scouting.R;
import org.usfirst.frc.team25.scouting.data.FileManager;
import org.usfirst.frc.team25.scouting.data.PostMatch;
import org.usfirst.frc.team25.scouting.data.ScoutEntry;
import org.usfirst.frc.team25.scouting.data.Settings;

import java.util.ArrayList;


public class PostMatchFragment extends Fragment implements EntryFragment {



    ScoutEntry entry;
    MaterialEditText comment;
    ArrayList<CheckBox> quickComments;
    Button finish;

    public static PostMatchFragment getInstance(ScoutEntry entry){
        PostMatchFragment pmf = new PostMatchFragment();
        pmf.setEntry(entry);
        return pmf;
    }

    public void setEntry(ScoutEntry entry) {
        this.entry = entry;
    }
    public ScoutEntry getEntry() {
        return entry;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.fragment_post_match, container, false);
        comment = (MaterialEditText) view.findViewById(R.id.post_comment);

        quickComments = new ArrayList<CheckBox>();
        quickComments.add((CheckBox) view.findViewById(R.id.quick_comment_1_1));
        quickComments.add((CheckBox) view.findViewById(R.id.quick_comment_1_2));
        quickComments.add((CheckBox) view.findViewById(R.id.quick_comment_2_1));
        quickComments.add((CheckBox) view.findViewById(R.id.quick_comment_2_2));
        quickComments.add((CheckBox) view.findViewById(R.id.quick_comment_3_1));
        quickComments.add((CheckBox) view.findViewById(R.id.quick_comment_3_2));
        quickComments.add((CheckBox) view.findViewById(R.id.quick_comment_4_1));
        quickComments.add((CheckBox) view.findViewById(R.id.quick_comment_4_2));
        quickComments.add((CheckBox) view.findViewById(R.id.quick_comment_5_1));
        quickComments.add((CheckBox) view.findViewById(R.id.quick_comment_5_2));
        quickComments.add((CheckBox) view.findViewById(R.id.quick_comment_6_1));
        quickComments.add((CheckBox) view.findViewById(R.id.quick_comment_6_2));
        quickComments.add((CheckBox) view.findViewById(R.id.quick_comment_7_1));
        quickComments.add((CheckBox) view.findViewById(R.id.quick_comment_7_2));
        finish = (Button) view.findViewById(R.id.post_finish);

        autoPopulate();

        finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveState();
                final PostMatch pm = entry.getPostMatch();
                pm.finalizeComment();
                entry.setPostMatch(pm);

                FileManager.saveData(entry, getActivity());

                getActivity().finish();
                Toast.makeText(getActivity().getBaseContext(), "Match data saved", Toast.LENGTH_LONG).show();
            }
        });

        return view;
    }

    public void saveState(){
        entry.setPostMatch(new PostMatch(comment.getText().toString(), quickComments));
    }



    @Override
    public void autoPopulate() {
        if(entry.getPostMatch()!=null){
            ArrayList<CheckBox> checkedComments = entry.getPostMatch().getQuickComments();
            for(int i = 0; i < checkedComments.size(); i++)
                if(checkedComments.get(i).isChecked())
                    quickComments.get(i).setChecked(true);
            comment.setText(entry.getPostMatch().getComment());
        }
    }

    @Override
    public void onResume() {
        getActivity().setTitle("Add Entry - Post Match");
        super.onResume();

    }
}
