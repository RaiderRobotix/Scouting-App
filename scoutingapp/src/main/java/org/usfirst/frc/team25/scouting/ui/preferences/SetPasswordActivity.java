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

    private MaterialEditText newPassswordField;
    private MaterialEditText confirmPasswordField;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setTitle("Change Password");
        setContentView(R.layout.activity_set_password);

        newPassswordField = findViewById(R.id.new_pass_box);
        confirmPasswordField = findViewById(R.id.confirm_new_pass_box);
        Button cancelButton = findViewById(R.id.cancel_button);
        Button setPasswordButton = findViewById(R.id.set_pass_button);

        cancelButton.setOnClickListener(view -> finish());

        setPasswordButton.setOnClickListener(view -> {
            if (newPassswordField.getText().toString().equals("")) {
                newPassswordField.setError("Password cannot be empty");
            } else if (newPassswordField.getText().toString().equals(confirmPasswordField.getText().toString())) {
                Settings.newInstance(getBaseContext()).setPassword(newPassswordField.getText().toString());
                Toast.makeText(SetPasswordActivity.this, "Password changed", Toast.LENGTH_SHORT).show();
                finish();
            } else {
                confirmPasswordField.setError("Passwords mismatch");
            }
        });
    }
}
