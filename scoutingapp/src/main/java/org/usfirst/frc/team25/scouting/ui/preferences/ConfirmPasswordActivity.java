package org.usfirst.frc.team25.scouting.ui.preferences;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import org.usfirst.frc.team25.scouting.R;
import org.usfirst.frc.team25.scouting.data.Settings;

/**
 * Activity that prompts users to enter the old password before setting a new one
 * This activity follows the same layout and behavior of the EnterPasswordActivity, only with
 * different button labels and confirm/deleteButton button behavior
 *
 * @see EnterPasswordActivity
 */
public class ConfirmPasswordActivity extends EnterPasswordActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Change Password");
        findViewById(R.id.delete_warning).setVisibility(View.INVISIBLE);
        TextView title = findViewById(R.id.enter_password_label);


        title.setText(R.string.confirm_old_pass);
        deleteButton.setText(R.string.confirm_button);

        deleteButton.setOnClickListener(view -> {
            String password = passwordField.getText().toString();

            if (Settings.newInstance(getBaseContext()).matchesPassword(password)) {
                finish();
                Intent i = new Intent(getBaseContext(), SetPasswordActivity.class);
                startActivity(i);
            } else {
                passwordField.setError("Incorrect password");
            }

        });
    }
}
