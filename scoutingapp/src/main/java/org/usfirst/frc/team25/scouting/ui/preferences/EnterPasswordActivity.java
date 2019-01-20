package org.usfirst.frc.team25.scouting.ui.preferences;

import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import com.rengwuxian.materialedittext.MaterialEditText;

import org.usfirst.frc.team25.scouting.R;
import org.usfirst.frc.team25.scouting.data.FileManager;
import org.usfirst.frc.team25.scouting.data.Settings;
import org.usfirst.frc.team25.scouting.ui.views.NoBackgroundPortraitAppCompatActivity;

// Activity in which one password is entered and confirmed, with error text if invalid
public class EnterPasswordActivity extends NoBackgroundPortraitAppCompatActivity {

    Button deleteButton;
    MaterialEditText passwordField;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Delete Files");
        setContentView(R.layout.activity_enter_password);

        Button cancelButton = findViewById(R.id.cancel_delete_button);
        deleteButton = findViewById(R.id.delete_file_button);
        passwordField = findViewById(R.id.delete_password_box);


        cancelButton.setOnClickListener(view -> finish());

        deleteButton.setOnClickListener(view -> {
            String password = passwordField.getText().toString();
            if (Settings.newInstance(getBaseContext()).matchesPassword(password)) {

                FileManager.deleteData(getBaseContext());
                Toast.makeText(EnterPasswordActivity.this, "Scouting data deleted",
                        Toast.LENGTH_SHORT).show();
                finish();
            } else {
                passwordField.setError("Incorrect password");
            }

        });
    }
}
