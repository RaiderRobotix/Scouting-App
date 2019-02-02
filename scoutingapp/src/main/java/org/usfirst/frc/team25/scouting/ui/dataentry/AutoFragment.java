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

    private ButtonIncDecView rocketCargo, rocketHatches, cargoShipHatches, cargoShipCargo,
            hatchesDropped, cargoDropped;

    private CheckBox reachHabLine, opponentCargoShipLineFoul, sideCargoShipHatchCapable,
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
        cargoShipCargo = view.findViewById(R.id.cargo_ship_cargo_auto);
        cargoDropped = view.findViewById(R.id.cargo_dropped_auto);
        reachHabLine = view.findViewById(R.id.reach_hab_line);
        hatchesDropped = view.findViewById(R.id.hatches_dropped_auto);
        opponentCargoShipLineFoul = view.findViewById(R.id.opponent_cargo_ship_line);
        frontCargoShipHatchCapable = view.findViewById(R.id.hatches_front_cargo_auto);
        sideCargoShipHatchCapable = view.findViewById(R.id.hatches_side_cargo_auto);
        cargoShipHatches = view.findViewById(R.id.cargo_ship_hatches_auto);
        rocketHatches = view.findViewById(R.id.rocket_hatches_auto);
        cargoDroppedCargoShip = view.findViewById(R.id.cargo_dropped_cargo_ship);
        cargoDroppedRocket = view.findViewById(R.id.cargo_dropped_rocket);
        hatchesDroppedCargoShip = view.findViewById(R.id.hatches_dropped_cargo_ship);
        hatchesDroppedRocket = view.findViewById(R.id.hatches_dropped_rocket);


        Button continueButton = view.findViewById(R.id.auto_continue);



        autoPopulate();
        Constants();


        opponentCargoShipLineFoul.setOnCheckedChangeListener((compoundButton, b) -> {
            reachHabLineState();
        });

        rocketHatches.incButton.setOnClickListener(view17 -> {
            rocketHatches.increment();
            disableReachHabLine();
        });
        rocketHatches.decButton.setOnClickListener(view16 -> {
            rocketHatches.decrement();
            reachHabLineState();
        });

        cargoShipHatches.incButton.setOnClickListener(view17 -> {
            cargoShipHatches.increment();
            disableReachHabLine();
            enableHatchPlacement();
        });
        cargoShipHatches.decButton.setOnClickListener(view16 -> {
            cargoShipHatches.decrement();
            reachHabLineState();
            enableHatchPlacement();
        });

        rocketCargo.incButton.setOnClickListener(view15 -> {
            rocketCargo.increment();
            disableReachHabLine();
        });
        rocketCargo.decButton.setOnClickListener(view14 -> {
            rocketCargo.decrement();
            reachHabLineState();
        });
        cargoShipCargo.incButton.setOnClickListener(view15 -> {
            cargoShipCargo.increment();
            disableReachHabLine();
        });
        cargoShipCargo.decButton.setOnClickListener(view14 -> {
            cargoShipCargo.decrement();
            reachHabLineState();
        });

        hatchesDropped.incButton.setOnClickListener(view15 -> {
            hatchesDropped.increment();
            hatchesDroppedLocation();
        });

        hatchesDropped.decButton.setOnClickListener(view15 -> {
            hatchesDropped.decrement();
            hatchesDroppedLocation();
        });
        cargoDropped.incButton.setOnClickListener(view15 -> {
            cargoDropped.increment();
            cargoDroppedLocation();
        });

        cargoDropped.decButton.setOnClickListener(view15 -> {
            cargoDropped.decrement();
            cargoDroppedLocation();
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

        if (entry.getPreMatch().isRobotNoShow()) {
            continueButton.callOnClick();

        }

        return view;
    }

    @Override
    public void autoPopulate() {
        if (entry.getAuto() != null) {

            Autonomous prevAuto = entry.getAuto();
            cargoShipCargo.setValue(prevAuto.getCargoShipCargo());
            rocketCargo.setValue(prevAuto.getRocketCargo());
            rocketHatches.setValue(prevAuto.getRocketHatches());
            cargoShipHatches.setValue(prevAuto.getCargoShipHatches());
            hatchesDropped.setValue(prevAuto.getHatchesDropped());
            cargoDropped.setValue(prevAuto.getCargoDropped());
            sideCargoShipHatchCapable.setChecked(prevAuto.isSideCargoShipHatchCapable());
            frontCargoShipHatchCapable.setChecked(prevAuto.isFrontCargoShipHatchCapable());
            reachHabLine.setChecked(prevAuto.isReachHabLine());
            opponentCargoShipLineFoul.setChecked(prevAuto.isOpponentCargoShipLineFoul());
            cargoDroppedCargoShip.setChecked(prevAuto.isCargoDroppedCargoShip());
            cargoDroppedRocket.setChecked(prevAuto.isCargoDroppedRocket());
            hatchesDroppedCargoShip.setChecked(prevAuto.isHatchesDroppedCargoShip());
            hatchesDroppedRocket.setChecked(prevAuto.isHatchesDroppedRocket());

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
                frontCargoShipHatchCapable.isChecked(), cargoDroppedCargoShip.isChecked(),
                cargoDroppedRocket.isChecked(), hatchesDroppedRocket.isChecked(),
                hatchesDroppedCargoShip.isChecked()));
    }

    private void disableReachHabLine() {

        reachHabLine.setChecked(true);
        reachHabLine.setEnabled(false);

    }

    private void Constants() {
        cargoDroppedCargoShip.setEnabled(false);
        cargoDroppedRocket.setEnabled(false);
        hatchesDroppedCargoShip.setEnabled(false);
        hatchesDroppedRocket.setEnabled(false);
        sideCargoShipHatchCapable.setEnabled(false);
        frontCargoShipHatchCapable.setEnabled(false);
        reachHabLine.setChecked(false);
    }

    private void reachHabLineState() {
        if (rocketCargo.getValue() > 0 || cargoShipCargo.getValue() > 0
                || rocketHatches.getValue() > 0 || sideCargoShipHatchCapable.isChecked() ||
                frontCargoShipHatchCapable.isChecked() || opponentCargoShipLineFoul.isChecked()) {
            reachHabLine.setChecked(true);
            reachHabLine.setEnabled(false);
        } else {
            reachHabLine.setEnabled(true);
            reachHabLine.setChecked(false);
        }
    }

    private void enableHatchPlacement() {

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

    private void hatchesDroppedLocation() {
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

    private void cargoDroppedLocation() {
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
    public void onResume() {
        super.onResume();

        getActivity().setTitle("Add Entry - Sandstorm");
    }

}
