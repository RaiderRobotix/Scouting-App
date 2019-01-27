package org.usfirst.frc.team25.scouting.ui.dataentry;

import android.app.AlertDialog;
import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
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

    private ButtonIncDecView rocketCargo, rocketHatches, cargoShipHatches, cargoShipCargo,
            hatchesDropped, cargoDropped;

    private CheckBox reachHabLine, opponentCargoShipLineFoul, sideCargoShipHatchCapable,
            frontCargoShipHatchCapable;


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
            cargoShipCargo.setValue(prevAuto.getCargoToCargoShip());
            rocketCargo.setValue(prevAuto.getCargoToRocket());
            rocketHatches.setValue(prevAuto.getHatchPanelsToRocket());
            cargoShipHatches.setValue(prevAuto.getHatchesToCargoShip());
            hatchesDropped.setValue(prevAuto.getHatchesDropped());
            cargoDropped.setValue(prevAuto.getCargoDropped());
            sideCargoShipHatchCapable.setChecked(prevAuto.isHatchesSideCargo());
            frontCargoShipHatchCapable.setChecked(prevAuto.isHatchesFrontCargo());
            reachHabLine.setChecked(prevAuto.isReachHabLine());
            opponentCargoShipLineFoul.setChecked(prevAuto.isOpponentCargoShipLineFoul());
            if (shouldDisableReachHabLine()) {
                disableReachHabLine();
            }

        }

    }

    @Override
    public void saveState() {
        entry.setAuto(new Autonomous(rocketCargo.getValue(), rocketHatches.getValue(),
                cargoShipHatches.getValue(),
                cargoShipCargo.getValue(),
                hatchesDropped.getValue(),
                cargoDropped.getValue(), reachHabLine.isChecked(),
                opponentCargoShipLineFoul.isChecked(), sideCargoShipHatchCapable.isChecked(),
                frontCargoShipHatchCapable.isChecked()));

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.fragment_auto, container, false);


        rocketCargo = view.findViewById(R.id.rocket_cargo_auto);
        cargoShipCargo = view.findViewById(R.id.cargo_ship_cargo_auto);
        cargoDropped = view.findViewById(R.id.cargo_dropped_auto);
        reachHabLine = view.findViewById(R.id.reach_hab_line);
        hatchesDropped = view.findViewById(R.id.hatches_dropped_auto);
        opponentCargoShipLineFoul = view.findViewById(R.id.opponent_cargo_ship_line);
        frontCargoShipHatchCapable = view.findViewById(R.id.hatches_front_cargo_auto);
        sideCargoShipHatchCapable = view.findViewById(R.id.hatches_side_cargo_auto);
        cargoShipHatches = view.findViewById(R.id.cargo_ship_hatches_auto);
        rocketHatches = view.findViewById(R.id.rocket_hatches_auto);


        Button continueButton = view.findViewById(R.id.auto_continue);


        autoPopulate();
        disableHatchPlacement();


        opponentCargoShipLineFoul.setOnCheckedChangeListener((compoundButton, b) -> {
            if (b) {
                disableReachHabLine();
            } else if (!shouldDisableReachHabLine()) {
                enableReachHabLine();
            }
        });

        rocketHatches.incButton.setOnClickListener(view17 -> {
            rocketHatches.increment();
            disableReachHabLine();
        });
        rocketHatches.decButton.setOnClickListener(view16 -> {
            rocketHatches.decrement();
            if (rocketHatches.getValue() < 1 && !shouldDisableReachHabLine()) {
                enableReachHabLine();
            }
        });

        cargoShipHatches.incButton.setOnClickListener(view17 -> {
            cargoShipHatches.increment();
            disableReachHabLine();
            enableHatchPlacement();
        });
        cargoShipHatches.decButton.setOnClickListener(view16 -> {
            cargoShipHatches.decrement();
            if (cargoShipHatches.getValue() < 1 && !shouldDisableReachHabLine()) {
                enableReachHabLine();
            }
        });

        rocketCargo.incButton.setOnClickListener(view15 -> {
            rocketCargo.increment();
            disableReachHabLine();
        });
        rocketCargo.decButton.setOnClickListener(view14 -> {
            rocketCargo.decrement();
            if (rocketCargo.getValue() < 1 && !shouldDisableReachHabLine()) {
                enableReachHabLine();
            }
        });
        cargoShipCargo.incButton.setOnClickListener(view15 -> {
            cargoShipCargo.increment();
            disableReachHabLine();
        });
        cargoShipCargo.decButton.setOnClickListener(view14 -> {
            cargoShipCargo.decrement();
            if (cargoShipCargo.getValue() < 1 && !shouldDisableReachHabLine()) {
                enableReachHabLine();
            }
        });


        continueButton.setOnClickListener(view1 -> {
            if (cargoShipHatches.getValue() > 0 && !frontCargoShipHatchCapable.isChecked() &&
                    !sideCargoShipHatchCapable.isChecked()) {
                new AlertDialog.Builder(getActivity())
                        .setTitle("Select where hatches were placed")
                        .setMessage("Front and/or side of cargo ship")
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

    private void disableReachHabLine() {

        reachHabLine.setChecked(true);
        reachHabLine.setEnabled(false);

    }

    private boolean shouldDisableReachHabLine() {
        return rocketCargo.getValue() > 0 || cargoShipCargo.getValue() > 0
                || rocketHatches.getValue() > 0 || sideCargoShipHatchCapable.isChecked() ||
                frontCargoShipHatchCapable.isChecked() || opponentCargoShipLineFoul.isChecked();
    }

    private void enableReachHabLine() {
        reachHabLine.setEnabled(true);
    }

    private void enableHatchPlacement() {
        frontCargoShipHatchCapable.setEnabled(true);
        sideCargoShipHatchCapable.setEnabled(true);
    }

    private void disableHatchPlacement() {
        frontCargoShipHatchCapable.setEnabled(false);
        sideCargoShipHatchCapable.setEnabled(false);
    }

    @Override
    public void onResume() {
        super.onResume();

        getActivity().setTitle("Add Entry - Sandstorm");
    }

}
