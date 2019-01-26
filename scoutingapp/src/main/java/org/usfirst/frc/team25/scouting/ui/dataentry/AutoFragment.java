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

    private ButtonIncDecView cargoToRocket, hatchPanelsToRocket, hatchesToCargoShip, cargoToCargoShip,
            hatchesDropped, cargoDropped;

    private CheckBox reachHabLine , opponentCargoShipLineFoul, hatchesSideCargo, hatchesFrontCargo;


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
            cargoToCargoShip.setValue(prevAuto.getCargoToCargoShip());
            cargoToRocket.setValue(prevAuto.getCargoToRocket());
            hatchPanelsToRocket.setValue(prevAuto.getHatchPanelsToRocket());
            hatchesToCargoShip.setValue(prevAuto.getHatchesToCargoShip());
            hatchesDropped.setValue(prevAuto.getHatchesDropped());
            cargoDropped.setValue(prevAuto.getCargoDropped());
            hatchesSideCargo.setChecked(prevAuto.isHatchesSideCargo());
            hatchesFrontCargo.setChecked(prevAuto.isHatchesFrontCargo());
            reachHabLine.setChecked(prevAuto.isReachHabLine());
            opponentCargoShipLineFoul.setChecked(prevAuto.isOpponentCargoShipLineFoul());
            if (shouldDisableReachAutoLine()) {
                disableReachAutoLine();
            }

        }

    }

    @Override
    public void saveState() {
        entry.setAuto(new Autonomous(cargoToRocket.getValue(), hatchPanelsToRocket.getValue(),
                hatchesToCargoShip.getValue(),
                cargoToCargoShip.getValue(),
                hatchesDropped.getValue(),
                cargoDropped.getValue(), reachHabLine.isChecked(), opponentCargoShipLineFoul.isChecked(),hatchesSideCargo.isChecked(),
                hatchesFrontCargo.isChecked()));

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.fragment_auto, container, false);


        cargoToRocket = view.findViewById(R.id.rocket_cargo_auto);
        cargoToCargoShip = view.findViewById(R.id.cargo_ship_cargo_auto);
        cargoDropped = view.findViewById(R.id.cargo_dropped_auto);
        reachHabLine = view.findViewById(R.id.reach_hab_line);
        hatchesDropped = view.findViewById(R.id.hatches_dropped_auto);
        opponentCargoShipLineFoul = view.findViewById(R.id.opponent_cargo_ship_line);
        hatchesFrontCargo = view.findViewById(R.id.hatches_front_cargo_auto);
        hatchesSideCargo = view.findViewById(R.id.hatches_side_cargo_auto);
        hatchesToCargoShip = view.findViewById(R.id.cargo_ship_hatches_auto);
        hatchPanelsToRocket = view.findViewById(R.id.rocket_hatches_auto);


        Button continueButton = view.findViewById(R.id.auto_continue);


        autoPopulate();


        opponentCargoShipLineFoul.setOnCheckedChangeListener((compoundButton, b) -> {
            if (b) {
                disableReachAutoLine();
            } else if (!shouldDisableReachAutoLine()) {
                enableReachAutoLine();
            }
        });

        hatchPanelsToRocket.incButton.setOnClickListener(view17 -> {
            hatchPanelsToRocket.increment();
            disableReachAutoLine();
        });
        hatchPanelsToRocket.decButton.setOnClickListener(view16 -> {
            hatchPanelsToRocket.decrement();
            if (hatchPanelsToRocket.getValue() < 1 && !shouldDisableReachAutoLine()) {
                enableReachAutoLine();
            }
        });

        hatchesToCargoShip.incButton.setOnClickListener(view17 -> {
            hatchesToCargoShip.increment();
            disableReachAutoLine();
        });
        hatchesToCargoShip.decButton.setOnClickListener(view16 -> {
            hatchesToCargoShip.decrement();
            if (hatchesToCargoShip.getValue() < 1 && !shouldDisableReachAutoLine()) {
                enableReachAutoLine();
            }
        });

        cargoToRocket.incButton.setOnClickListener(view15 -> {
            cargoToRocket.increment();
            disableReachAutoLine();
        });
        cargoToRocket.decButton.setOnClickListener(view14 -> {cargoToRocket.decrement();
            if (cargoToRocket.getValue() < 1 && !shouldDisableReachAutoLine()) {
                enableReachAutoLine();
            }
        });
        cargoToCargoShip.incButton.setOnClickListener(view15 -> {
            cargoToCargoShip.increment();
            disableReachAutoLine();
        });
        cargoToCargoShip.decButton.setOnClickListener(view14 -> {cargoToCargoShip.decrement();
            if (cargoToCargoShip.getValue() < 1 && !shouldDisableReachAutoLine()) {
                enableReachAutoLine();
            }
        });


        continueButton.setOnClickListener(view1 -> {
            if ((hatchesToCargoShip.getValue() > 0 && !hatchesFrontCargo.isChecked() &&
                    !hatchesSideCargo.isChecked())) {
                new AlertDialog.Builder(getActivity())
                        .setTitle("Select where/how many hatches were placed")
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

    private void disableReachAutoLine() {

        reachHabLine.setChecked(true);
        reachHabLine.setEnabled(false);

    }

    private boolean shouldDisableReachAutoLine() {
        return cargoToRocket.getValue() > 0 || cargoToCargoShip.getValue() > 0
                || hatchPanelsToRocket.getValue() > 0 || hatchesSideCargo.isChecked() ||
                hatchesFrontCargo.isChecked() ||  opponentCargoShipLineFoul.isChecked();
    }

    private void enableReachAutoLine() {
        reachHabLine.setEnabled(true);
    }

    @Override
    public void onResume() {
        super.onResume();

        getActivity().setTitle("Add Entry - Sandstorm");
    }

}
