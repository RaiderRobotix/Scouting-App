package org.usfirst.frc.team25.scouting.ui.dataentry;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RelativeLayout;

import org.usfirst.frc.team25.scouting.R;
import org.usfirst.frc.team25.scouting.data.models.Autonomous;
import org.usfirst.frc.team25.scouting.data.models.ScoutEntry;
import org.usfirst.frc.team25.scouting.ui.views.ButtonIncDecView;

import static org.usfirst.frc.team25.scouting.ui.UiHelper.hideKeyboard;

public class AutoFragment extends Fragment implements EntryFragment {

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
        RelativeLayout cells_scored_auto = view.findViewById(R.id.layout_auto);
        for (int child_index=0;child_index<cells_scored_auto.getChildCount();child_index++) {
            View child = cells_scored_auto.getChildAt(child_index);
                if (child instanceof ButtonIncDecView) {
                    ButtonIncDecView child_view = (ButtonIncDecView) child;
                    child_view.incButton.setOnClickListener(view1 -> {
                        child_view.increment();
                        autoEnableCrossInitationLine();
                    });
                    child_view.decButton.setOnClickListener(view1 -> {
                        child_view.decrement();
                        autoEnableCrossInitationLine();
                    });
                }
        }

        cellsScoredBottom = view.findViewById(R.id.power_cells_scored_botom_incdec);
        cellsScoredInner = view.findViewById(R.id.power_cells_scored_inner_incdec);
        cellsScoredOuter = view.findViewById(R.id.power_cells_scored_outer_incdec);
        cellPickupRpoint = view.findViewById(R.id.cell_pickup_rpoint_incdec_auto);
        cellPickupTrench = view.findViewById(R.id.cell_pickup_trench_incdec_auto);
        cellsDropped = view.findViewById(R.id.cells_dropped_auto);
        crossInitLine = view.findViewById(R.id.cross_init_line_checkbox_auto);
        crossOpponentSector = view.findViewById(R.id.crossed_into_opponent_sector_checkbox_auto);

        Button continueButton = view.findViewById(R.id.auto_continue);


        autoPopulate();

        continueButton.setOnClickListener(view1 -> {
            saveState();
            getFragmentManager().beginTransaction()
                    .replace(android.R.id.content, TeleOpFragment.getInstance(entry), "TELEOP")
                    .commit();
        });

        if (entry.getPreMatch().getNoShow()) {
            continueButton.callOnClick();

        }

        return view;
    }

    private void autoEnableCrossInitationLine() {
        if (crossOpponentSector.isChecked() || cellsScoredBottom.getValue() > 0
                || cellsScoredInner.getValue() > 0 || cellsScoredOuter.getValue() > 0
                || cellPickupRpoint.getValue() > 0 || cellPickupTrench.getValue() > 0) {
            crossInitLine.setChecked(true);
            crossInitLine.setEnabled(false);
        } else {
            crossInitLine.setEnabled(true);
        }
    }

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
            crossInitLine.setChecked(prevAuto.getCrossInitLine());
            crossOpponentSector.setChecked(prevAuto.getCrossOpponentSector());

            autoEnableCrossInitationLine();

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

        getActivity().setTitle("Add Entry - Autonomous");
        hideKeyboard(getActivity());
    }

}
