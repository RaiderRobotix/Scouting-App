package org.usfirst.frc.team25.scouting.ui.dataentry;

import android.app.AlertDialog;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import org.usfirst.frc.team25.scouting.R;
import org.usfirst.frc.team25.scouting.data.models.Autonomous;
import org.usfirst.frc.team25.scouting.data.models.ScoutEntry;
import org.usfirst.frc.team25.scouting.data.models.TeleOp;
import org.usfirst.frc.team25.scouting.ui.UiHelper;
import org.usfirst.frc.team25.scouting.ui.views.ButtonIncDecSet;
import org.usfirst.frc.team25.scouting.ui.views.ButtonIncDecView;

import java.util.ArrayList;
import java.util.HashMap;

import static org.usfirst.frc.team25.scouting.ui.UiHelper.hideKeyboard;


public class TeleOpFragment extends Fragment implements EntryFragment {

    private ScoutEntry entry;
    private RadioButton[] robotFoulButtons, robotDockButtons;
    private RadioGroup robotFoulGroup, robotDockGroup;
    private ButtonIncDecSet coneTopTele, cubeTopTele, coneMidTele, cubeMidTele, coneBttmTele, cubeBttmTele, coneDroppedTele, cubeDroppedTele;
    private CheckBox robotCommitFoul, robotDockAttempt;

    private CheckBox topRow_leftComm_LeftCone,topRow_leftComm_MidCube, topRow_leftComm_RightCone,topRow_midComm_LeftCone,topRow_midComm_MidCube, topRow_midComm_RightCone,
            topRow_rightComm_LeftCone,topRow_rightComm_MidCube, topRow_rightComm_RightCone,midRow_leftComm_LeftCone,midRow_leftComm_MidCube, midRow_leftComm_RightCone,
            midRow_midComm_LeftCone,midRow_midComm_MidCube, midRow_midComm_RightCone, midRow_rightComm_LeftCone,midRow_rightComm_MidCube, midRow_rightComm_RightCone,
            bttmRow_leftComm_LeftCone,bttmRow_leftComm_MidCube, bttmRow_leftComm_RightCone,bttmRow_midComm_LeftCone,bttmRow_midComm_MidCube, bttmRow_midComm_RightCone,
            bttmRow_rightComm_LeftCone,bttmRow_rightComm_MidCube, bttmRow_rightComm_RightCone;

    private CheckBox[] GridButtons = {topRow_leftComm_LeftCone,topRow_leftComm_MidCube, topRow_leftComm_RightCone,topRow_midComm_LeftCone,topRow_midComm_MidCube, topRow_midComm_RightCone,
            topRow_rightComm_LeftCone,topRow_rightComm_MidCube, topRow_rightComm_RightCone,midRow_leftComm_LeftCone,midRow_leftComm_MidCube, midRow_leftComm_RightCone,
            midRow_midComm_LeftCone,midRow_midComm_MidCube, midRow_midComm_RightCone, midRow_rightComm_LeftCone,midRow_rightComm_MidCube, midRow_rightComm_RightCone,
            bttmRow_leftComm_LeftCone,bttmRow_leftComm_MidCube, bttmRow_leftComm_RightCone,bttmRow_midComm_LeftCone,bttmRow_midComm_MidCube, bttmRow_midComm_RightCone,
            bttmRow_rightComm_LeftCone,bttmRow_rightComm_MidCube, bttmRow_rightComm_RightCone};   //
//    private int[] gamePieceHomeGrid = {1,2,1,1,2,1,1,2,1,1,2,1,1,2,1,1,2,1,0,0,0,0,0,0,0,0,0};
//    private int[] bttmRowButtons = {19,20,21,22,23,24,25,26,27};

    //Still a work in progress      CONE = 1 , CUBE = 2 , Unknown  = 0


    public static TeleOpFragment getInstance(ScoutEntry entry) {
        TeleOpFragment tof = new TeleOpFragment();
        tof.setEntry(entry);
        return tof;
    }

    @Override
    public ScoutEntry getEntry() {
        return entry;
    }

    @Override
    public void setEntry(ScoutEntry entry) {
        this.entry = entry;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.fragment_tele_op, container, false);


        coneTopTele = view.findViewById(R.id.cones_scored_top_row_tele);
        coneMidTele = view.findViewById(R.id.cones_scored_mid_row_tele);
        coneBttmTele = view.findViewById(R.id.cones_scored_bttm_row_tele);

        cubeTopTele = view.findViewById(R.id.cubes_scored_top_row_tele);
        cubeMidTele = view.findViewById(R.id.cubes_scored_mid_row_tele);
        cubeBttmTele = view.findViewById(R.id.cubes_scored_bttm_row_tele);

        coneDroppedTele = view.findViewById(R.id.cones_dropped_tele);
        cubeDroppedTele = view.findViewById(R.id.cubes_dropped_tele);

        robotDockButtons = new RadioButton[2];
        robotDockButtons[0] = view.findViewById(R.id.docked_button);
        robotDockButtons[1] = view.findViewById(R.id.docked_engaged_button);

        robotDockAttempt = view.findViewById(R.id.robot_attempt_dock);
        robotDockGroup = view.findViewById(R.id.robot_dock_group);

        robotCommitFoul = view.findViewById(R.id.robot_commit_foul);

        robotFoulGroup = view.findViewById(R.id.robotFoulGroup);

        robotFoulButtons = new RadioButton[2];
        robotFoulButtons[0] = view.findViewById(R.id.robot_foul);
        robotFoulButtons[1] = view.findViewById(R.id.robot_techFoul);


//        GridButtons = new CheckBox[27];
//        //Row 1
//        GridButtons[0] = view.findViewById(R.id.checkBox1); // Cone
//        GridButtons[1] = view.findViewById(R.id.checkBox2); // Cube
//        GridButtons[2] = view.findViewById(R.id.checkBox3); // Cone
//        GridButtons[3] = view.findViewById(R.id.checkBox4); // Cone
//        GridButtons[4] = view.findViewById(R.id.checkBox5); // Cube
//        GridButtons[5] = view.findViewById(R.id.checkBox6); // Cone
//        GridButtons[6] = view.findViewById(R.id.checkBox7); // Cone
//        GridButtons[7] = view.findViewById(R.id.checkBox8); // Cube
//        GridButtons[8] = view.findViewById(R.id.checkBox9); // Cone
//        //Row 2
//        GridButtons[9] = view.findViewById(R.id.checkBox10);
//        GridButtons[10] = view.findViewById(R.id.checkBox11);
//        GridButtons[11] = view.findViewById(R.id.checkBox12);
//        GridButtons[12] = view.findViewById(R.id.checkBox13);
//        GridButtons[13] = view.findViewById(R.id.checkBox14);
//        GridButtons[14] = view.findViewById(R.id.checkBox15);
//        GridButtons[15] = view.findViewById(R.id.checkBox16);
//        GridButtons[16] = view.findViewById(R.id.checkBox17);
//        GridButtons[17] = view.findViewById(R.id.checkBox18);
//        //Row 3
//        GridButtons[18] = view.findViewById(R.id.checkBox19);
//        GridButtons[19] = view.findViewById(R.id.checkBox20);
//        GridButtons[20] = view.findViewById(R.id.checkBox21);
//        GridButtons[21] = view.findViewById(R.id.checkBox22);
//        GridButtons[22] = view.findViewById(R.id.checkBox23);
//        GridButtons[23] = view.findViewById(R.id.checkBox24);
//        GridButtons[24] = view.findViewById(R.id.checkBox25);
//        GridButtons[25] = view.findViewById(R.id.checkBox26);
//        GridButtons[26] = view.findViewById(R.id.checkBox27);

        //Row 1
        topRow_leftComm_LeftCone = view.findViewById(R.id.checkBox1); // Cone
        topRow_leftComm_MidCube = view.findViewById(R.id.checkBox2); // Cube
        topRow_leftComm_RightCone = view.findViewById(R.id.checkBox3); // Cone
        topRow_midComm_LeftCone = view.findViewById(R.id.checkBox4); // Cone
        topRow_midComm_MidCube = view.findViewById(R.id.checkBox5); // Cube
        topRow_midComm_RightCone = view.findViewById(R.id.checkBox6); // Cone
        topRow_rightComm_LeftCone = view.findViewById(R.id.checkBox7); // Cone
        topRow_rightComm_MidCube = view.findViewById(R.id.checkBox8); // Cube
        topRow_rightComm_RightCone = view.findViewById(R.id.checkBox9); // Cone
        //Row 2
        midRow_leftComm_LeftCone = view.findViewById(R.id.checkBox10); // Cone
        midRow_leftComm_MidCube = view.findViewById(R.id.checkBox11); // Cube
        midRow_leftComm_RightCone = view.findViewById(R.id.checkBox12); // Cone
        midRow_midComm_LeftCone = view.findViewById(R.id.checkBox13); // Cone
        midRow_midComm_MidCube = view.findViewById(R.id.checkBox14); // Cube
        midRow_midComm_RightCone = view.findViewById(R.id.checkBox15); // Cone
        midRow_rightComm_LeftCone = view.findViewById(R.id.checkBox16); // Cone
        midRow_rightComm_MidCube = view.findViewById(R.id.checkBox17); // Cube
        midRow_rightComm_RightCone = view.findViewById(R.id.checkBox18); // Cone
        //Row 3
        bttmRow_leftComm_LeftCone = view.findViewById(R.id.checkBox19); // Cone
        bttmRow_leftComm_MidCube = view.findViewById(R.id.checkBox20); // Cube
        bttmRow_leftComm_RightCone = view.findViewById(R.id.checkBox21); // Cone
        bttmRow_midComm_LeftCone = view.findViewById(R.id.checkBox22); // Cone
        bttmRow_midComm_MidCube = view.findViewById(R.id.checkBox23); // Cube
        bttmRow_midComm_RightCone = view.findViewById(R.id.checkBox24); // Cone
        bttmRow_rightComm_LeftCone = view.findViewById(R.id.checkBox25); // Cone
        bttmRow_rightComm_MidCube = view.findViewById(R.id.checkBox26); // Cube
        bttmRow_rightComm_RightCone = view.findViewById(R.id.checkBox27); // Cone



        Button continueButton = view.findViewById(R.id.tele_continue);


        robotDockAttempt.setOnCheckedChangeListener((compoundButton, b) -> {

            if (b) {
                for(RadioButton button : robotDockButtons) {
                    button.setEnabled(true);
                }

            } else {
                for(RadioButton button : robotDockButtons) {
                    button.setEnabled(false);
                    button.setChecked(false);
                }
            }
        });

        robotCommitFoul.setOnCheckedChangeListener((compoundButton, b) -> {

            if (b) {
                for(RadioButton button : robotFoulButtons) {
                    button.setEnabled(true);
                }

            } else {
                for(RadioButton button : robotFoulButtons) {
                    button.setEnabled(false);
                    button.setChecked(false);
                }
            }
        });

        autoPopulate();
        try{
            continueButton.setOnClickListener(view1 -> {
                hideKeyboard(getActivity());

                boolean proceed = true;


                if (proceed) {
                    saveState();
                    System.out.println("Passed");
                    getFragmentManager()
                            .beginTransaction()
                            .replace(android.R.id.content, PostMatchFragment.getInstance(entry), "POST")
                            .commit();
                }
            }); } catch (Exception e){
            e.printStackTrace();
        }

        if (entry.getPreMatch().isRobotNoShow()) {
            continueButton.callOnClick();
        }

        return view;


    }



    @Override
    public void autoPopulate() {
        if (entry.getTeleOp() != null) {
            TeleOp tele = entry.getTeleOp();

            robotDockAttempt.setChecked(tele.isDockAttempt());
            robotCommitFoul.setChecked(tele.isRobotCommitedFoul());
            coneTopTele.setValue(tele.getConeTop());
            cubeTopTele.setValue(tele.getCubeTop());
            coneMidTele.setValue(tele.getConeMid());
            cubeMidTele.setValue(tele.getCubeMid());
            coneBttmTele.setValue(tele.getConeBttm());
            cubeBttmTele.setValue(tele.getCubeBttm());
            coneDroppedTele.setValue(tele.getConeDropped());
            cubeDroppedTele.setValue(tele.getCubeDropped());
            topRow_leftComm_LeftCone.setChecked(tele.getTopRow_leftComm_LeftCone());
            topRow_leftComm_MidCube.setChecked(tele.getTopRow_leftComm_MidCube());
            topRow_leftComm_RightCone.setChecked(tele.getTopRow_leftComm_RightCone());
            topRow_midComm_LeftCone.setChecked(tele.getTopRow_midComm_LeftCone());
            topRow_midComm_MidCube.setChecked(tele.getTopRow_midComm_MidCube());
            topRow_midComm_RightCone.setChecked(tele.getTopRow_midComm_RightCone());
            topRow_rightComm_LeftCone.setChecked(tele.getTopRow_rightComm_LeftCone());
            topRow_rightComm_MidCube.setChecked(tele.getTopRow_rightComm_MidCube());
            topRow_rightComm_RightCone.setChecked(tele.getTopRow_rightComm_RightCone());

            midRow_leftComm_LeftCone.setChecked(tele.getMidRow_leftComm_LeftCone());
            midRow_leftComm_MidCube.setChecked(tele.getMidRow_leftComm_MidCube());
            midRow_leftComm_RightCone.setChecked(tele.getMidRow_leftComm_RightCone());
            midRow_midComm_LeftCone.setChecked(tele.getMidRow_midComm_LeftCone());
            midRow_midComm_MidCube.setChecked(tele.getMidRow_midComm_MidCube());
            midRow_midComm_RightCone.setChecked(tele.getMidRow_midComm_RightCone());
            midRow_rightComm_LeftCone.setChecked(tele.getMidRow_rightComm_LeftCone());
            midRow_rightComm_MidCube.setChecked(tele.getMidRow_rightComm_MidCube());
            midRow_rightComm_RightCone.setChecked(tele.getMidRow_rightComm_RightCone());

            bttmRow_leftComm_LeftCone.setChecked(tele.getBttmRow_leftComm_LeftCone());
            bttmRow_leftComm_MidCube.setChecked(tele.getBttmRow_leftComm_MidCube());
            bttmRow_leftComm_RightCone.setChecked(tele.getBttmRow_leftComm_RightCone());
            bttmRow_midComm_LeftCone.setChecked(tele.getBttmRow_midComm_LeftCone());
            bttmRow_midComm_MidCube.setChecked(tele.getBttmRow_midComm_MidCube());
            bttmRow_midComm_RightCone.setChecked(tele.getBttmRow_midComm_RightCone());
            bttmRow_rightComm_LeftCone.setChecked(tele.getBttmRow_rightComm_LeftCone());
            bttmRow_rightComm_MidCube.setChecked(tele.getBttmRow_rightComm_MidCube());
            bttmRow_rightComm_RightCone.setChecked(tele.getBttmRow_rightComm_RightCone());


            for (RadioButton button : robotDockButtons) {
                if (button.getText().toString().equals(tele.getDockStatus())) {
                    button.setChecked(true);
                }
            }

            for (RadioButton button : robotFoulButtons) {
                if (button.getText().toString().equals(tele.getFoulType())) {
                    button.setChecked(true);
                }
            }


//            HashMap<CheckBox, Boolean> GridValuesAutoPopulate = tele.getGridVals();
//            for(CheckBox checkBoxValue: GridButtons){
//                checkBoxValue.setChecked(GridValuesAutoPopulate.get(checkBoxValue));
//                //checkBoxValue.set()
//            }





        }
    }

    @Override
    public void saveState() {

//       HashMap<CheckBox, Boolean> GridValues = new HashMap<CheckBox, Boolean>();
//
//        //boolean[][] GridValues = new boolean[3][9];
//        //ArrayList<Boolean> GridValues = new ArrayList<Boolean>();
//        for(CheckBox checkBoxValue: GridButtons){
//            GridValues.put(checkBoxValue, checkBoxValue.isChecked());
//            //GridValues.add(checkBoxValue.isChecked());
//        }

        String dockStatus = "";
        for (RadioButton button : robotDockButtons) {
            if (button.isChecked()) {
                dockStatus = (String) button.getText();
                break;
            }
        }

        String foulType = "";
        for (RadioButton button : robotFoulButtons) {
            if (button.isChecked()) {
                foulType = (String) button.getText();
                break;
            }
        }



        entry.setTeleOp(new TeleOp(coneTopTele.getValue(),cubeTopTele.getValue(),coneMidTele.getValue(),
                cubeMidTele.getValue(), coneBttmTele.getValue(), cubeBttmTele.getValue(), coneDroppedTele.getValue(),
                cubeDroppedTele.getValue(),robotDockAttempt.isChecked(),dockStatus, robotCommitFoul.isChecked(),foulType,
                topRow_leftComm_LeftCone.isChecked(),topRow_leftComm_MidCube.isChecked(), topRow_leftComm_RightCone.isChecked(),topRow_midComm_LeftCone.isChecked(),
                topRow_midComm_MidCube.isChecked(), topRow_midComm_RightCone.isChecked(), topRow_rightComm_LeftCone.isChecked(),topRow_rightComm_MidCube.isChecked(),
                topRow_rightComm_RightCone.isChecked(),

                midRow_leftComm_LeftCone.isChecked(),midRow_leftComm_MidCube.isChecked(), midRow_leftComm_RightCone.isChecked(),
                midRow_midComm_LeftCone.isChecked(),midRow_midComm_MidCube.isChecked(), midRow_midComm_RightCone.isChecked(),
                midRow_rightComm_LeftCone.isChecked(), midRow_rightComm_MidCube.isChecked(), midRow_rightComm_RightCone.isChecked(),

                bttmRow_leftComm_LeftCone.isChecked(),bttmRow_leftComm_MidCube.isChecked(), bttmRow_leftComm_RightCone.isChecked(),
                bttmRow_midComm_LeftCone.isChecked(),bttmRow_midComm_MidCube.isChecked(), bttmRow_midComm_RightCone.isChecked(),
                bttmRow_rightComm_LeftCone.isChecked(),bttmRow_rightComm_MidCube.isChecked(), bttmRow_rightComm_RightCone.isChecked()
        ));


    }

    @Override
    public void onResume() {
        super.onResume();
        getActivity().setTitle("Add Entry - Tele-Op");
        hideKeyboard(getActivity());
    }

}