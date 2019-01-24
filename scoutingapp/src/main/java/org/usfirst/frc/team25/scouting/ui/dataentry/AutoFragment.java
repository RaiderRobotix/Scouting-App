package org.usfirst.frc.team25.scouting.ui.dataentry;

import android.app.AlertDialog;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;

import org.usfirst.frc.team25.scouting.R;
import org.usfirst.frc.team25.scouting.data.models.Autonomous;
import org.usfirst.frc.team25.scouting.data.models.ScoutEntry;
import org.usfirst.frc.team25.scouting.ui.views.ButtonIncDecView;

public class AutoFragment extends Fragment implements EntryFragment {

    private ButtonIncDecView ownSwitchCubes, ownScaleCubes, exchangeCubes, powerCubePilePickup,
            switchAdjacentPickup, cubesDropped;
    private CheckBox reachAutoLine, cubesOpponentPlate, opponentSwitchPlate,
            opponentScalePlate, nullTerritoryFoul;

    private ScoutEntry entry;


    public static AutoFragment getInstance(ScoutEntry entry) {
        AutoFragment autoFragment = new AutoFragment();
        autoFragment.setEntry(entry);
        return autoFragment;
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
    public void autoPopulate() {
        if (entry.getAuto() != null) {

            Autonomous prevAuto = entry.getAuto();
            enableOpponentPlateLocationCheckboxes(prevAuto.isCubeDropOpponentScalePlate() || prevAuto.isCubeDropOpponentSwitchPlate());
            cubesOpponentPlate.setChecked(prevAuto.isCubeDropOpponentScalePlate() || prevAuto.isCubeDropOpponentSwitchPlate());
            ownSwitchCubes.setValue(prevAuto.getSwitchCubes());
            ownScaleCubes.setValue(prevAuto.getScaleCubes());
            exchangeCubes.setValue(prevAuto.getExchangeCubes());
            reachAutoLine.setChecked(prevAuto.isAutoLineCross());
            powerCubePilePickup.setValue(prevAuto.getPowerCubePilePickup());
            switchAdjacentPickup.setValue(prevAuto.getSwitchAdjacentPickup());
            cubesDropped.setValue(prevAuto.getCubesDropped());
            opponentSwitchPlate.setChecked(prevAuto.isCubeDropOpponentSwitchPlate());
            opponentScalePlate.setChecked(prevAuto.isCubeDropOpponentScalePlate());
            nullTerritoryFoul.setChecked(prevAuto.isNullTerritoryFoul());
            if (shouldDisableReachAutoLine()) {
                disableReachAutoLine();
            }

        }

    }

    @Override
    public void saveState() {
        entry.setAuto(new Autonomous(ownSwitchCubes.getValue(), ownScaleCubes.getValue(),
                exchangeCubes.getValue(),
                powerCubePilePickup.getValue(),
                switchAdjacentPickup.getValue(),
                cubesDropped.getValue(), reachAutoLine.isChecked(), nullTerritoryFoul.isChecked(),
                opponentSwitchPlate.isChecked(), opponentScalePlate.isChecked()));

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.fragment_auto, container, false);

        ownScaleCubes = view.findViewById(R.id.hatch_panels_delivered);
        ownSwitchCubes = view.findViewById(R.id.cargo_delivered);
        exchangeCubes = view.findViewById(R.id.cargo_dropped);
        reachAutoLine = view.findViewById(R.id.reach_HAB_line);
        powerCubePilePickup = view.findViewById(R.id.power_cube_pile_pickup_auto);
        switchAdjacentPickup = view.findViewById(R.id.six_switch_pickup_auto);
        cubesDropped = view.findViewById(R.id.hatches_dropped);
        cubesOpponentPlate = view.findViewById(R.id.cubes_wrong_plate_auto);
        opponentScalePlate = view.findViewById(R.id.scale_wrong_plate_auto);
        opponentSwitchPlate = view.findViewById(R.id.switch_wrong_plate_auto);
        nullTerritoryFoul = view.findViewById(R.id.opponent_cargo_ship_line);

        Button continueButton = view.findViewById(R.id.auto_continue);


        autoPopulate();


        nullTerritoryFoul.setOnCheckedChangeListener((compoundButton, b) -> {
            if (b) {
                disableReachAutoLine();
            } else if (!shouldDisableReachAutoLine()) {
                enableReachAutoLine();
            }
        });

        ownScaleCubes.incButton.setOnClickListener(view17 -> {
            ownScaleCubes.increment();
            disableReachAutoLine();
        });
        ownScaleCubes.decButton.setOnClickListener(view16 -> {
            ownScaleCubes.decrement();
            if (ownScaleCubes.getValue() < 1 && !shouldDisableReachAutoLine()) {
                enableReachAutoLine();
            }
        });


        ownSwitchCubes.incButton.setOnClickListener(view15 -> {
            ownSwitchCubes.increment();
            disableReachAutoLine();
        });
        ownSwitchCubes.decButton.setOnClickListener(view14 -> {
            ownSwitchCubes.decrement();
            if (ownSwitchCubes.getValue() < 1 && !shouldDisableReachAutoLine()) {
                enableReachAutoLine();
            }
        });

        switchAdjacentPickup.incButton.setOnClickListener(view13 -> {
            switchAdjacentPickup.increment();
            disableReachAutoLine();
        });
        switchAdjacentPickup.decButton.setOnClickListener(view12 -> {
            switchAdjacentPickup.decrement();
            if (switchAdjacentPickup.getValue() < 1 && !shouldDisableReachAutoLine()) {
                enableReachAutoLine();
            }
        });

        cubesOpponentPlate.setOnCheckedChangeListener((compoundButton, b) -> {
            if (b) {
                disableReachAutoLine();
                enableOpponentPlateLocationCheckboxes(true);
            } else if (!shouldDisableReachAutoLine()) {
                enableReachAutoLine();
                enableOpponentPlateLocationCheckboxes(false);
            } else {
                enableOpponentPlateLocationCheckboxes(false);
            }
        });


        continueButton.setOnClickListener(view1 -> {
            if (cubesOpponentPlate.isChecked() && !(opponentSwitchPlate.isChecked()
                    || opponentScalePlate.isChecked())) {
                new AlertDialog.Builder(getActivity())
                        .setTitle("Select where the robot dropped cubes on its opponents' plate(s)")
                        .setMessage("Scale and/or own switch")
                        .setPositiveButton("OK", (dialogInterface, i) -> {

                        })
                        .show();
            } else {
                saveState();

                getFragmentManager().beginTransaction()
                        .replace(android.R.id.content, TeleOpFragment.getInstance(entry), "TELEOP")
                        .commit();
            }

        });

        return view;
    }

    private void disableReachAutoLine() {

        reachAutoLine.setChecked(true);
        reachAutoLine.setEnabled(false);

    }

    private boolean shouldDisableReachAutoLine() {
        return ownSwitchCubes.getValue() > 0 || ownScaleCubes.getValue() > 0 || switchAdjacentPickup
                .getValue() > 0 || cubesOpponentPlate.isChecked() || nullTerritoryFoul.isChecked();
    }

    private void enableReachAutoLine() {
        reachAutoLine.setEnabled(true);
    }

    private void enableOpponentPlateLocationCheckboxes(boolean enable) {

        if (enable) {// checked
            opponentSwitchPlate.setEnabled(true);
            opponentScalePlate.setEnabled(true);
        } else {
            opponentSwitchPlate.setEnabled(false);
            opponentScalePlate.setEnabled(false);
            opponentScalePlate.setChecked(false);
            opponentSwitchPlate.setChecked(false);
        }
    }

    @Override
    public void onResume() {
        super.onResume();

        getActivity().setTitle("Add Entry - Sandstorm");
    }

}
