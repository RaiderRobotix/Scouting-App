package org.usfirst.frc.team25.scouting.ui.preferences;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.rengwuxian.materialedittext.MaterialEditText;

import org.usfirst.frc.team25.scouting.R;
import org.usfirst.frc.team25.scouting.data.Settings;
import org.usfirst.frc.team25.scouting.ui.views.NoBackgroundPortraitAppCompatActivity;

// Activity that sets a new password
public class SetPasswordActivity extends NoBackgroundPortraitAppCompatActivity {

    MaterialEditText newPass, confirmPass;
    Button cancel, set;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setTitle("Change Password");
        setContentView(R.layout.activity_set_password);

        newPass = (MaterialEditText) findViewById(R.id.new_pass_box);
        confirmPass = (MaterialEditText) findViewById(R.id.confirm_new_pass_box);
        cancel = (Button) findViewById(R.id.cancel_button);
        set = (Button) findViewById(R.id.set_pass_button);

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        set.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (newPass.getText().toString().equals(""))
                    newPass.setError("Password cannot be empty");

                else if(newPass.getText().toString().equals(confirmPass.getText().toString())){
                    Settings.newInstance(getBaseContext()).setPassword(newPass.getText().toString());
                    Toast.makeText(SetPasswordActivity.this, "Password changed", Toast.LENGTH_SHORT ).show();
                    finish();
                }
                else confirmPass.setError("Passwords mismatch");
            }
        });
    }
}
