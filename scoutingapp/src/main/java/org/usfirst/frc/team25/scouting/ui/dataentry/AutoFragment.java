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

//    -----Future Reference inc dec set
//    private ButtonIncDecSet cellsScoredBottom, cellsScoredInner, cellsScoredOuter;

    private ButtonIncDecView cellsScoredBottom, cellsScoredInner, cellsScoredOuter,
            cellPickupRpoint, cellPickupTrench, cellsDropped;

    private CheckBox crossInitLine, crossOpponentSector;


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


        cellsScoredBottom = view.findViewById(R.id.power_cells_scored_botom_incdec);
        cellsScoredInner = view.findViewById(R.id.power_cells_scored_inner_incdec);
        cellsScoredOuter = view.findViewById(R.id.power_cells_scored_outer_incdec);
        cellPickupRpoint = view.findViewById(R.id.cell_pickup_rpoint_incdec_auto);
        cellPickupTrench = view.findViewById(R.id.cell_pickup_trench_incdec_auto);
        cellsDropped = view.findViewById(R.id.cells_dropped_auto);
        crossInitLine = view.findViewById(R.id.cross_init_line_checkbox_auto);
        crossOpponentSector = view.findViewById(R.id.crossed_into_opponent_sector_checkbox_auto);

        Button continueButton = view.findViewById(R.id.auto_continue);

//TODO implement changeListeners here
//        opponentCargoShipLineFoul.setOnCheckedChangeListener((compoundButton, b) -> autoEnableCrossHabLine());
//
//        ButtonIncDecSet[] enablingCrossHabLineMetrics = new ButtonIncDecSet[]{rocketHatches,
//                cargoShipHatches, rocketCargo, cargoShipCargo};
//
//        for (ButtonIncDecSet set : enablingCrossHabLineMetrics) {
//            set.incButton.setOnClickListener(view1 -> {
//                set.increment();
//                autoEnableCrossHabLine();
//                if (set.equals(cargoShipHatches)) {
//                    autoEnableCargoShipHatchPlacementSet();
//                }
//
//            });
//            set.decButton.setOnClickListener(view1 -> {
//                set.decrement();
//                autoEnableCrossHabLine();
//                if (set.equals(cargoShipHatches)) {
//                    autoEnableCargoShipHatchPlacementSet();
//                }
//
//            });
//        }
// TODO create AUTO onClick methods


        autoPopulate();

//TODO Create AUTO continue button protection
//        continueButton.setOnClickListener(view1 -> {
//            if (cargoShipHatches.getValue() > 0 && !frontCargoShipHatchCapable.isChecked() &&
//                    !sideCargoShipHatchCapable.isChecked() || cargoDropped.getValue() > 0 && !cargoDroppedCargoShip.isChecked()
//                    && !cargoDroppedRocket.isChecked() || hatchesDropped.getValue() > 0 && !hatchesDroppedCargoShip.isChecked() && !hatchesDroppedRocket.isChecked()) {
//                new AlertDialog.Builder(getActivity())
//                        .setTitle("Select where game pieces were placed/dropped")
//                        .setPositiveButton("OK", (dialogInterface, i) -> {
//
//                        })
//                        .show();
//            } else {
//                saveState();
//                getFragmentManager().beginTransaction()
//                        .replace(android.R.id.content, TeleOpFragment.getInstance(entry), "TELEOP")
//                        .commit();
//            }
//
//        });

        if (entry.getPreMatch().isRobotNoShow()) {
            continueButton.callOnClick();

        }

        return view;
    }
//TODO Auto Enable AUTO views if value/state of others are changed
//    private void autoEnableCrossHabLine() {
//        if (rocketCargo.getValue() > 0 || cargoShipCargo.getValue() > 0
//                || cargoShipHatches.getValue() > 0 || rocketHatches.getValue() > 0 || sideCargoShipHatchCapable.isChecked() ||
//                frontCargoShipHatchCapable.isChecked() || opponentCargoShipLineFoul.isChecked()) {
//            crossHabLine.setChecked(true);
//            crossHabLine.setEnabled(false);
//        } else {
//            crossHabLine.setEnabled(true);
//        }
//    }
//
//    private void autoEnableCargoShipHatchPlacementSet() {
//
//        if (cargoShipHatches.getValue() > 0) {
//            frontCargoShipHatchCapable.setEnabled(true);
//            sideCargoShipHatchCapable.setEnabled(true);
//        } else {
//            frontCargoShipHatchCapable.setEnabled(false);
//            sideCargoShipHatchCapable.setEnabled(false);
//            frontCargoShipHatchCapable.setChecked(false);
//            sideCargoShipHatchCapable.setChecked(false);
//        }
//    }

    @Override
    public void autoPopulate() {
        if (entry.getAutonomous() != null) {

            Autonomous prevAuto = entry.getAutonomous();
            cellsScoredBottom.setValue(prevAuto.getCellsScoredBottom());
            cellsScoredInner.setValue(prevAuto.getCellsScoredInner());
            cellsScoredOuter.setValue(prevAuto.getCellsScoredOuter());
            cellPickupRpoint.setValue(prevAuto.getCellPickupRpoint());
            cellPickupTrench.setValue(prevAuto.getCellPickupTrench());
            cellsDropped.setValue(prevAuto.getCellsDropped());
            crossInitLine.setChecked(prevAuto.isCrossInitLine());
            crossOpponentSector.setChecked(prevAuto.isCrossOpponentSector());

//TODO add autoEnablers here
//            autoEnableCargoShipHatchPlacementSet();
//            autoEnableCargoDroppedLocationSet();
//            autoEnableHatchesDroppedLocationSet();
//            autoEnableCrossHabLine();

        }

    }

    @Override
    public void saveState() {
        entry.setAutonomous(new Autonomous(
                cellsScoredBottom.getValue(),
                cellsScoredInner.getValue(),
                cellsScoredOuter.getValue(),
                cellPickupRpoint.getValue(),
                cellPickupTrench.getValue(),
                cellsDropped.getValue(),

                crossInitLine.isChecked(),
                crossOpponentSector.isChecked()
        ));
    }

    @Override
    public void onResume() {
        super.onResume();

        getActivity().setTitle("Add Entry - Sandstorm");
    }

}
