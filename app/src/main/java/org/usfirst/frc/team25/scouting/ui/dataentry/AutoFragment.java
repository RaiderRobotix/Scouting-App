package org.usfirst.frc.team25.scouting.ui.dataentry;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import org.usfirst.frc.team25.scouting.R;
import org.usfirst.frc.team25.scouting.data.Settings;
import org.usfirst.frc.team25.scouting.data.models.Autonomous;
import org.usfirst.frc.team25.scouting.data.models.ScoutEntry;
import org.usfirst.frc.team25.scouting.ui.views.ButtonIncDecInt;

public class AutoFragment extends Fragment implements  EntryFragment{

    ButtonIncDecInt ownSwitchCubes, ownScaleCubes, exchangeCubes, powerCubePilePickup,
        switchAdjacentPickup, cubesDropped;
    CheckBox reachAutoLine, cubesOpponentPlate, opponentSwitchPlate,
            opponentScalePlate, nullTerritoryFoul;
    Button continueButton;

    ScoutEntry entry;



    @Override
    public ScoutEntry getEntry() {
        return entry;
    }

    public static AutoFragment getInstance(ScoutEntry entry){
        AutoFragment af = new AutoFragment();
        af.setEntry(entry);
        return af;
    }

    public AutoFragment() {
        // Required empty public constructor

    }

    public boolean shouldDisableReachAutoLine(){
        return ownSwitchCubes.getValue()>0||ownScaleCubes.getValue()>0||switchAdjacentPickup
                .getValue()>0||cubesOpponentPlate.isChecked()||nullTerritoryFoul.isChecked();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.fragment_auto, container, false);

        ownScaleCubes = view.findViewById(R.id.own_scale_auto);
        ownSwitchCubes = view.findViewById(R.id.own_switch_auto);
        exchangeCubes = view.findViewById(R.id.exchange_auto);
        reachAutoLine = view.findViewById(R.id.reach_auto_line);
        powerCubePilePickup = view.findViewById(R.id.power_cube_pile_pickup_auto);
        switchAdjacentPickup = view.findViewById(R.id.six_switch_pickup_auto);
        cubesDropped = view.findViewById(R.id.cubes_dropped_auto);
        cubesOpponentPlate = view.findViewById(R.id.cubes_wrong_plate_auto);
        opponentScalePlate = view.findViewById(R.id.scale_wrong_plate_auto);
        opponentSwitchPlate = view.findViewById(R.id.switch_wrong_plate_auto);
        nullTerritoryFoul = view.findViewById(R.id.null_territory_auto_foul);

        continueButton = (Button) view.findViewById(R.id.auto_continue);


        autoPopulate();




        nullTerritoryFoul.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b)
                    disableReachAutoLine();
                else if(!shouldDisableReachAutoLine())
                    enableReachAutoLine();
            }
        });

        ownScaleCubes.incButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ownScaleCubes.increment();
                disableReachAutoLine();
            }
        });
        ownScaleCubes.decButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ownScaleCubes.decrement();
                if(ownScaleCubes.getValue()<1&&!shouldDisableReachAutoLine())
                    enableReachAutoLine();
            }
        });


        ownSwitchCubes.incButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ownSwitchCubes.increment();
                disableReachAutoLine();
            }
        });
        ownSwitchCubes.decButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ownSwitchCubes.decrement();
                if(ownSwitchCubes.getValue()<1&&!shouldDisableReachAutoLine())
                    enableReachAutoLine();
            }
        });

        switchAdjacentPickup.incButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switchAdjacentPickup.increment();
                disableReachAutoLine();
            }
        });
        switchAdjacentPickup.decButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switchAdjacentPickup.decrement();
                if(switchAdjacentPickup.getValue()<1&&!shouldDisableReachAutoLine())
                    enableReachAutoLine();
            }
        });

        cubesOpponentPlate.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    disableReachAutoLine();
                    enableOpponentPlateLocationCheckboxes(true);
                }
                else if(!shouldDisableReachAutoLine()) {
                    enableReachAutoLine();
                    enableOpponentPlateLocationCheckboxes(false);
                }
                else enableOpponentPlateLocationCheckboxes(false);
            }
        });



        continueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(cubesOpponentPlate.isChecked()&&!(opponentSwitchPlate.isChecked()
                        ||opponentScalePlate.isChecked())){
                    new AlertDialog.Builder(getActivity())
                            .setTitle("Select where the robot dropped cubes on its opponents' plate(s)")
                            .setMessage("Scale and/or own switch")
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {

                                }
                            })
                            .show();
                }
                else {
                    saveState();

                    getFragmentManager().beginTransaction()
                            .replace(android.R.id.content, TeleOpFragment.getInstance(entry), "TELEOP")
                            .commit();
                }

            }
        });

        return view;
    }


    public void disableReachAutoLine(){

        reachAutoLine.setChecked(true);
        reachAutoLine.setEnabled(false);

    }

    public void enableReachAutoLine(){
        reachAutoLine.setEnabled(true);
    }

    public void enableOpponentPlateLocationCheckboxes(boolean enable){

        if(enable) {// checked
            opponentSwitchPlate.setEnabled(true);
            opponentScalePlate.setEnabled(true);
        }
        else{
            opponentSwitchPlate.setEnabled(false);
            opponentScalePlate.setEnabled(false);
            opponentScalePlate.setChecked(false);
            opponentSwitchPlate.setChecked(false);
        }
    }

    @Override
    public void saveState() {
        Settings set = Settings.newInstance(getActivity());
        entry.setAuto(new Autonomous(ownSwitchCubes.getValue(), ownScaleCubes.getValue(),
                exchangeCubes.getValue(),
                powerCubePilePickup.getValue(),
                switchAdjacentPickup.getValue(),
                cubesDropped.getValue(), reachAutoLine.isChecked(), nullTerritoryFoul.isChecked(),
                opponentSwitchPlate.isChecked(), opponentScalePlate.isChecked()));

    }



    @Override
    public void setEntry(ScoutEntry entry) {
        this.entry = entry;
    }

    @Override
    public void onResume() {
        super.onResume();

        getActivity().setTitle("Add Entry - Autonomous");

    }

    @Override
    public void autoPopulate() {
        if(entry.getAuto()!=null){



            Autonomous prevAuto = entry.getAuto();
            enableOpponentPlateLocationCheckboxes(prevAuto.isCubeDropOpponentScalePlate()||prevAuto.isCubeDropOpponentSwitchPlate());
            cubesOpponentPlate.setChecked(prevAuto.isCubeDropOpponentScalePlate()||prevAuto.isCubeDropOpponentSwitchPlate());
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
            if(shouldDisableReachAutoLine())
                disableReachAutoLine();

        }

    }

}
