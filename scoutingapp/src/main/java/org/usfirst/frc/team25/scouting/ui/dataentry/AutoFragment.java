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
import org.usfirst.frc.team25.scouting.ui.views.ButtonIncDecSet;
import org.usfirst.frc.team25.scouting.ui.views.ButtonIncDecView;

public class AutoFragment extends Fragment implements EntryFragment {

    private ButtonIncDecSet rocketCargo, rocketHatches, cargoShipCargo, cargoShipHatches;

    private ButtonIncDecView hatchesDropped, cargoDropped;

    private CheckBox crossHabLine, opponentCargoShipLineFoul, sideCargoShipHatchCapable,
            frontCargoShipHatchCapable, cargoDroppedCargoShip, cargoDroppedRocket,
            hatchesDroppedCargoShip, hatchesDroppedRocket;


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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.fragment_auto, container, false);


        rocketCargo = view.findViewById(R.id.rocket_cargo_auto);
        rocketHatches = view.findViewById(R.id.rocket_hatches_auto);
        cargoShipCargo = view.findViewById(R.id.cargo_ship_cargo_auto);
        cargoDropped = view.findViewById(R.id.cargo_dropped_auto);
        crossHabLine = view.findViewById(R.id.cross_hab_line);
        hatchesDropped = view.findViewById(R.id.hatches_dropped_auto);
        opponentCargoShipLineFoul = view.findViewById(R.id.opponent_cargo_ship_line);
        frontCargoShipHatchCapable = view.findViewById(R.id.hatches_front_cargo_auto);
        sideCargoShipHatchCapable = view.findViewById(R.id.hatches_side_cargo_auto);
        cargoShipHatches = view.findViewById(R.id.cargo_ship_hatches_auto);
        cargoDroppedCargoShip = view.findViewById(R.id.cargo_dropped_cargo_ship);
        cargoDroppedRocket = view.findViewById(R.id.cargo_dropped_rocket);
        hatchesDroppedCargoShip = view.findViewById(R.id.hatches_dropped_cargo_ship);
        hatchesDroppedRocket = view.findViewById(R.id.hatches_dropped_rocket);


        Button continueButton = view.findViewById(R.id.auto_continue);


        opponentCargoShipLineFoul.setOnCheckedChangeListener((compoundButton, b) -> autoEnableCrossHabLine());

        ButtonIncDecSet[] enablingCrossHabLineMetrics = new ButtonIncDecSet[]{rocketHatches,
                cargoShipHatches, rocketCargo, cargoShipCargo};

        for (ButtonIncDecSet set : enablingCrossHabLineMetrics) {
            set.incButton.setOnClickListener(view1 -> {
                set.increment();
                autoEnableCrossHabLine();
                if (set.equals(cargoShipHatches)) {
                    autoEnableCargoShipHatchPlacementSet();
                }

            });
            set.decButton.setOnClickListener(view1 -> {
                set.decrement();
                autoEnableCrossHabLine();
                if (set.equals(cargoShipHatches)) {
                    autoEnableCargoShipHatchPlacementSet();
                }

            });
        }

        hatchesDropped.incButton.setOnClickListener(view1 -> {
            hatchesDropped.increment();
            autoEnableHatchesDroppedLocationSet();
        });

        hatchesDropped.decButton.setOnClickListener(view1 -> {
            hatchesDropped.decrement();
            autoEnableHatchesDroppedLocationSet();
        });

        cargoDropped.incButton.setOnClickListener(view1 -> {
            cargoDropped.increment();
            autoEnableCargoDroppedLocationSet();
        });

        cargoDropped.decButton.setOnClickListener(view1 -> {
            cargoDropped.decrement();
            autoEnableCargoDroppedLocationSet();
        });


        autoPopulate();

        continueButton.setOnClickListener(view1 -> {
            if (cargoShipHatches.getValue() > 0 && !frontCargoShipHatchCapable.isChecked() &&
                    !sideCargoShipHatchCapable.isChecked() || cargoDropped.getValue() > 0 && !cargoDroppedCargoShip.isChecked()
                    && !cargoDroppedRocket.isChecked() || hatchesDropped.getValue() > 0 && !hatchesDroppedCargoShip.isChecked() && !hatchesDroppedRocket.isChecked()) {
                new AlertDialog.Builder(getActivity())
                        .setTitle("Select where game pieces were placed/dropped")
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

        if (entry.getPreMatch().isRobotNoShow()) {
            continueButton.callOnClick();

        }

        return view;
    }

    private void autoEnableCrossHabLine() {
        if (rocketCargo.getValue() > 0 || cargoShipCargo.getValue() > 0
                || cargoShipHatches.getValue() > 0 || rocketHatches.getValue() > 0 || sideCargoShipHatchCapable.isChecked() ||
                frontCargoShipHatchCapable.isChecked() || opponentCargoShipLineFoul.isChecked()) {
            crossHabLine.setChecked(true);
            crossHabLine.setEnabled(false);
        } else {
            crossHabLine.setEnabled(true);
        }
    }

    private void autoEnableCargoShipHatchPlacementSet() {

        if (cargoShipHatches.getValue() > 0) {
            frontCargoShipHatchCapable.setEnabled(true);
            sideCargoShipHatchCapable.setEnabled(true);
        } else {
            frontCargoShipHatchCapable.setEnabled(false);
            sideCargoShipHatchCapable.setEnabled(false);
            frontCargoShipHatchCapable.setChecked(false);
            sideCargoShipHatchCapable.setChecked(false);
        }
    }

    private void autoEnableHatchesDroppedLocationSet() {
        if (hatchesDropped.getValue() > 0) {
            hatchesDroppedCargoShip.setEnabled(true);
            hatchesDroppedRocket.setEnabled(true);
        } else {
            hatchesDroppedCargoShip.setEnabled(false);
            hatchesDroppedRocket.setEnabled(false);
            hatchesDroppedCargoShip.setChecked(false);
            hatchesDroppedRocket.setChecked(false);
        }
    }

    private void autoEnableCargoDroppedLocationSet() {
        if (cargoDropped.getValue() > 0) {
            cargoDroppedRocket.setEnabled(true);
            cargoDroppedCargoShip.setEnabled(true);
        } else {
            cargoDroppedRocket.setEnabled(false);
            cargoDroppedCargoShip.setEnabled(false);
            cargoDroppedRocket.setChecked(false);
            cargoDroppedCargoShip.setChecked(false);
        }
    }

    @Override
    public void autoPopulate() {
        if (entry.getAutonomous() != null) {

            Autonomous prevAuto = entry.getAutonomous();
            cargoShipCargo.setValue(prevAuto.getCargoShipCargo());
            rocketCargo.setValue(prevAuto.getRocketCargo());
            rocketHatches.setValue(prevAuto.getRocketHatches());
            cargoShipHatches.setValue(prevAuto.getCargoShipHatches());
            hatchesDropped.setValue(prevAuto.getHatchesDropped());
            cargoDropped.setValue(prevAuto.getCargoDropped());
            sideCargoShipHatchCapable.setChecked(prevAuto.isSideCargoShipHatchCapable());
            frontCargoShipHatchCapable.setChecked(prevAuto.isFrontCargoShipHatchCapable());
            crossHabLine.setChecked(prevAuto.isCrossHabLine());
            opponentCargoShipLineFoul.setChecked(prevAuto.isOpponentCargoShipLineFoul());
            cargoDroppedCargoShip.setChecked(prevAuto.isCargoDroppedCargoShip());
            cargoDroppedRocket.setChecked(prevAuto.isCargoDroppedRocket());
            hatchesDroppedCargoShip.setChecked(prevAuto.isHatchesDroppedCargoShip());
            hatchesDroppedRocket.setChecked(prevAuto.isHatchesDroppedRocket());

            autoEnableCargoShipHatchPlacementSet();
            autoEnableCargoDroppedLocationSet();
            autoEnableHatchesDroppedLocationSet();

            autoEnableCrossHabLine();

        }

    }

    @Override
    public void saveState() {
        entry.setAutonomous(new Autonomous(rocketCargo.getValue(), rocketHatches.getValue(),
                cargoShipHatches.getValue(),
                cargoShipCargo.getValue(),
                hatchesDropped.getValue(),
                cargoDropped.getValue(), crossHabLine.isChecked(),
                opponentCargoShipLineFoul.isChecked(), sideCargoShipHatchCapable.isChecked(),
                frontCargoShipHatchCapable.isChecked(), cargoDroppedCargoShip.isChecked(),
                cargoDroppedRocket.isChecked(), hatchesDroppedRocket.isChecked(),
                hatchesDroppedCargoShip.isChecked()));
    }

    @Override
    public void onResume() {
        super.onResume();

        getActivity().setTitle("Add Entry - Sandstorm");
    }

}
