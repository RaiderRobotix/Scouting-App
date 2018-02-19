package org.usfirst.frc.team25.scouting.ui.preferences;

import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import com.rengwuxian.materialedittext.MaterialEditText;

import org.usfirst.frc.team25.scouting.R;
import org.usfirst.frc.team25.scouting.data.Settings;
import org.usfirst.frc.team25.scouting.ui.views.NoBackgroundPortraitAppCompatActivity;

// Activity that sets a new password
public class SetPasswordActivity extends NoBackgroundPortraitAppCompatActivity {

    private MaterialEditText newPass;
    private MaterialEditText confirmPass;
    private Button cancel;
    private Button set;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setTitle("Change Password");
        setContentView(R.layout.activity_set_password);

        newPass = findViewById(R.id.new_pass_box);
        confirmPass = findViewById(R.id.confirm_new_pass_box);
        cancel = findViewById(R.id.cancel_button);
        set = findViewById(R.id.set_pass_button);

        cancel.setOnClickListener(view -> finish());

        set.setOnClickListener(view -> {
            if (newPass.getText().toString().equals(""))
                newPass.setError("Password cannot be empty");

            else if(newPass.getText().toString().equals(confirmPass.getText().toString())){
                Settings.newInstance(getBaseContext()).setPassword(newPass.getText().toString());
                Toast.makeText(SetPasswordActivity.this, "Password changed", Toast.LENGTH_SHORT ).show();
                finish();
            }
            else confirmPass.setError("Passwords mismatch");
        });
    }
}
