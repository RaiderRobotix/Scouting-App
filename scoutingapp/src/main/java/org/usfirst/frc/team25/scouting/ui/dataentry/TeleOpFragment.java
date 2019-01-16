package org.usfirst.frc.team25.scouting.ui.dataentry;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import org.usfirst.frc.team25.scouting.R;
import org.usfirst.frc.team25.scouting.data.Settings;
import org.usfirst.frc.team25.scouting.data.models.ScoutEntry;
import org.usfirst.frc.team25.scouting.data.models.TeleOp;
import org.usfirst.frc.team25.scouting.ui.views.ButtonIncDecInt;
import org.usfirst.frc.team25.scouting.ui.views.ButtonTimer;


public class TeleOpFragment extends Fragment implements EntryFragment {

    private final RadioButton[] climbOtherRobotType = new RadioButton[5];
    private final int[] redLeftFieldConfig = {R.drawable.red_left_rrr, R.drawable.red_left_rlr,
            R.drawable.red_left_lrl, R.drawable.red_left_lll};
    private final int[] blueLeftFieldConfig = {R.drawable.blue_left_rrr, R.drawable.blue_left_rlr,
            R.drawable.blue_left_lrl, R.drawable.blue_left_lll};
    private ScoutEntry entry;
    private ImageView fieldImage;
    private Button continueButton;
    private ButtonIncDecInt ownSwitchCubes;
    private ButtonIncDecInt scaleCubes;
    private ButtonIncDecInt opponentSwtichCubes;
    private ButtonIncDecInt exchangeCubes;
    private ButtonIncDecInt cubesDropped;
    private ButtonIncDecInt climbsAssisted;
    private CheckBox attemptRumgClimb;
    private CheckBox successRungClimb;
    private CheckBox parked;
    private CheckBox climbsOtherRobots;
    private ButtonTimer firstCubeTime;
    private ButtonTimer cycleTime;
    private ButtonTimer timerIncAmount;
    private EditText climbOtherRobotTypeOtherField;
    private int fieldConfigIndex = 0;
    private RadioGroup otherRobotTypeGroup;


    private Settings set;


    public TeleOpFragment() {
        // Required empty public constructor
    }

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

        ownSwitchCubes = view.findViewById(R.id.own_switch_tele);
        scaleCubes = view.findViewById(R.id.scale_tele);
        opponentSwtichCubes = view.findViewById(R.id.opponent_switch_tele);
        attemptRumgClimb = view.findViewById(R.id.attempt_rung_climb);
        successRungClimb = view.findViewById(R.id.success_rung_climb);
        continueButton = view.findViewById(R.id.tele_continue);
        fieldImage = view.findViewById(R.id.field_config_image);
        firstCubeTime = view.findViewById(R.id.first_cube_time);
        exchangeCubes = view.findViewById(R.id.exchange_tele);
        cubesDropped = view.findViewById(R.id.cubes_dropped_tele);
        cycleTime = view.findViewById(R.id.cycle_time);
        parked = view.findViewById(R.id.park_platform);
        climbsAssisted = view.findViewById(R.id.climbs_assisted);
        climbsOtherRobots = view.findViewById(R.id.climb_other_robot);
        climbOtherRobotType[0] = view.findViewById(R.id.ramp_bot_type);
        climbOtherRobotType[1] = view.findViewById(R.id.robot_rung_type);
        climbOtherRobotType[2] = view.findViewById(R.id.iron_cross_type);
        climbOtherRobotType[3] = view.findViewById(R.id.single_lift_type);
        climbOtherRobotType[4] = view.findViewById(R.id.other_type);
        otherRobotTypeGroup = view.findViewById(R.id.climb_other_robot_type_group);
        climbOtherRobotTypeOtherField = view.findViewById(R.id.other_robot_type_text);
        timerIncAmount = view.findViewById(R.id.timer_manual_inc);

        timerIncAmount.startStopButton.setVisibility(View.GONE);


        set = Settings.newInstance(getActivity());


        firstCubeTime.setIncDecAmount(set.getTimerManualInc());
        cycleTime.setIncDecAmount(set.getTimerManualInc());

        timerIncAmount.setValue(set.getTimerManualInc());

        climbOtherRobotTypeOtherField.setEnabled(false);

        climbOtherRobotType[4].setOnCheckedChangeListener((compoundButton, b) -> {
            if (b) {
                for (int i = 0; i < 4; i++)
                    climbOtherRobotType[i].setChecked(false);
                climbOtherRobotTypeOtherField.setEnabled(true);
            }
        });

        cycleTime.setOnClickListener(view15 -> cycleTime.setIncDecAmount(timerIncAmount.getValue()));

        firstCubeTime.setOnClickListener(view14 -> firstCubeTime.setIncDecAmount(timerIncAmount.getValue()));

        for (int i = 0; i < climbOtherRobotType.length - 1; i++)
            climbOtherRobotType[i].setOnClickListener(view13 -> {
                climbOtherRobotType[climbOtherRobotType.length - 1].setChecked(false);
                climbOtherRobotTypeOtherField.setText("");
                climbOtherRobotTypeOtherField.setEnabled(false);
            });

        if (set.getLeftAlliance().equals("Red Alliance"))
            fieldImage.setImageResource(redLeftFieldConfig[0]);
        else
            fieldImage.setImageResource(blueLeftFieldConfig[0]);

        fieldImage.setOnClickListener(view12 -> {
            if (set.getLeftAlliance().equals("Red Alliance"))
                fieldImage.setImageResource(redLeftFieldConfig[++fieldConfigIndex % 4]);
            else
                fieldImage.setImageResource(blueLeftFieldConfig[++fieldConfigIndex % 4]);
        });

        successRungClimb.setOnCheckedChangeListener((compoundButton, b) -> {
            if (b) {
                parked.setChecked(false);
                parked.setEnabled(false);
                disableOtherRobotTypeGroup();
                climbsOtherRobots.setEnabled(false);
                climbsOtherRobots.setChecked(false);
            } else {
                parked.setEnabled(true);
                climbsOtherRobots.setEnabled(true);
            }
        });

        attemptRumgClimb.setOnCheckedChangeListener((compoundButton, b) -> {
            if (b && !climbsOtherRobots.isChecked())
                successRungClimb.setEnabled(true);
            else {
                successRungClimb.setEnabled(false);
                successRungClimb.setChecked(false);
            }
        });

        climbsOtherRobots.setOnCheckedChangeListener((compoundButton, b) -> {
            if (b) {
                enableOtherRobotTypeGroup();
                successRungClimb.setEnabled(false);
                successRungClimb.setChecked(false);
                parked.setChecked(false);
                parked.setEnabled(false);
            } else {
                disableOtherRobotTypeGroup();
                parked.setEnabled(true);
                if (attemptRumgClimb.isChecked())
                    successRungClimb.setEnabled(true);
            }
        });

        continueButton.setOnClickListener(view1 -> {
            hideKeyboard();
            if (climbsOtherRobots.isChecked() && !(climbOtherRobotType[0].isChecked() || climbOtherRobotType[1].isChecked()
                    || climbOtherRobotType[2].isChecked() || climbOtherRobotType[3].isChecked() ||
                    (climbOtherRobotType[4].isChecked() && !climbOtherRobotTypeOtherField.getText().toString().isEmpty()))) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Select or fill in type of robot climbed on")
                        .setCancelable(false)
                        .setPositiveButton("OK", (dialog, id) -> {
                            //do things
                        });
                AlertDialog alert = builder.create();
                alert.show();
            } else {
                saveState();
                getFragmentManager()
                        .beginTransaction()
                        .replace(android.R.id.content, PostMatchFragment.getInstance(entry), "POST")
                        .commit();

            }
        });


        return view;
    }

    private void enableOtherRobotTypeGroup() {
        for (RadioButton button : climbOtherRobotType)
            button.setEnabled(true);
    }

    private void disableOtherRobotTypeGroup() {
        for (RadioButton button : climbOtherRobotType) {
            button.setEnabled(false);
            button.setChecked(false);
        }
        climbOtherRobotTypeOtherField.setEnabled(false);
        climbOtherRobotTypeOtherField.setText("");
    }

    @Override
    public void onStop() {
        set.setTimerManualInc(timerIncAmount.getValue());
        Log.i("tag", "stopped");
        super.onStop();
    }

    public void hideKeyboard() {
        try {
            InputMethodManager inputManager =
                    (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            inputManager.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken()
                    , InputMethodManager.HIDE_NOT_ALWAYS);
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void autoPopulate() {
        if (entry.getTeleOp() != null) {
            TeleOp tele = entry.getTeleOp();
            firstCubeTime.setValue(tele.getFirstCubeTime());
            cycleTime.setValue(tele.getCycleTime());
            ownSwitchCubes.setValue(tele.getOwnSwitchCubes());
            scaleCubes.setValue(tele.getScaleCubes());
            opponentSwtichCubes.setValue(tele.getOpponentSwitchCubes());
            exchangeCubes.setValue(tele.getExchangeCubes());
            cubesDropped.setValue(tele.getCubesDropped());
            climbsAssisted.setValue(tele.getClimbsAssisted());
            parked.setChecked(tele.isParked());
            attemptRumgClimb.setChecked(tele.isAttemptRungClimb());
            successRungClimb.setChecked(tele.isSuccessfulRungClimb());
            climbsOtherRobots.setChecked(tele.isOtherRobotClimb());

            if (!tele.getOtherRobotClimbType().equals("")) {
                boolean otherChecked = true;
                for (RadioButton button : climbOtherRobotType)
                    if (button.getText().equals(tele.getOtherRobotClimbType())) {
                        button.setChecked(true);
                        otherChecked = false;
                    }
                if (otherChecked) {
                    climbOtherRobotType[climbOtherRobotType.length - 1].setChecked(true);
                    Log.i("tag", tele.getOtherRobotClimbType());
                    climbOtherRobotTypeOtherField.setText(tele.getOtherRobotClimbType());
                }
            }

            String[] fieldLayoutValues = {"RRR", "RLR", "LRL", "LLL"};

            fieldConfigIndex = 0;

            for (int i = 0; i < fieldLayoutValues.length; i++)
                if (fieldLayoutValues[i].equals(tele.getFieldLayout()))
                    fieldConfigIndex = i;

            if (set.getLeftAlliance().equals("Red Alliance"))
                fieldImage.setImageResource(redLeftFieldConfig[fieldConfigIndex]);
            else
                fieldImage.setImageResource(blueLeftFieldConfig[fieldConfigIndex]);

        }
    }

    @Override
    public void saveState() {
        String climbOtherRobotTypeStr = "";
        for (int i = 0; i < climbOtherRobotType.length - 1; i++)
            if (climbOtherRobotType[i].isChecked())
                climbOtherRobotTypeStr = (String) climbOtherRobotType[i].getText();
        if (climbOtherRobotType[climbOtherRobotType.length - 1].isChecked())
            climbOtherRobotTypeStr = climbOtherRobotTypeOtherField.getText().toString();

        //Always from red driver POV, depicting red plates
        String[] fieldLayoutValues = {"RRR", "RLR", "LRL", "LLL"};

        entry.setTeleOp(new TeleOp(firstCubeTime.getValue(), cycleTime.getValue(),
                ownSwitchCubes.getValue(),
                scaleCubes.getValue(), opponentSwtichCubes.getValue(), exchangeCubes.getValue(),
                cubesDropped.getValue(),
                climbsAssisted.getValue(), parked.isChecked(), attemptRumgClimb.isChecked(),
                successRungClimb.isChecked(),
                climbsOtherRobots.isChecked(), climbOtherRobotTypeStr,
                fieldLayoutValues[fieldConfigIndex % 4]));

    }

    @Override
    public void onResume() {
        super.onResume();
        getActivity().setTitle("Add Entry - Tele-Op");
        autoPopulate();
    }
}
