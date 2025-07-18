package com.example.contactbook;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class UpdateContactActivity extends AppCompatActivity {

    EditText editOldPhone, editNewName, editNewPhone;
    RadioGroup radioGroup;
    RadioButton radioName, radioPhone;
    Button btnUpdateContact;
    DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_contact);

        editOldPhone = findViewById(R.id.editOldPhone);
        editNewName = findViewById(R.id.editNewName);
        editNewPhone = findViewById(R.id.editNewPhone);
        radioGroup = findViewById(R.id.radioGroup);
        radioName = findViewById(R.id.radioName);
        radioPhone = findViewById(R.id.radioPhone);
        btnUpdateContact = findViewById(R.id.btnUpdateContact);
        dbHelper = new DBHelper(this);

        // Radio group change logic
        radioGroup.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId == R.id.radioName) {
                editNewName.setVisibility(View.VISIBLE);
                editNewPhone.setVisibility(View.GONE);
                editNewPhone.setText("");
            } else if (checkedId == R.id.radioPhone) {
                editNewPhone.setVisibility(View.VISIBLE);
                editNewName.setVisibility(View.GONE);
                editNewName.setText("");
            }
        });

        btnUpdateContact.setOnClickListener(v -> {
            String oldPhone = editOldPhone.getText().toString().trim();
            String newName = editNewName.getText().toString().trim();
            String newPhone = editNewPhone.getText().toString().trim();

            if (oldPhone.isEmpty()) {
                Toast.makeText(this, "Enter existing phone number", Toast.LENGTH_SHORT).show();
                return;
            }

            if (radioName.isChecked()) {
                if (newName.isEmpty()) {
                    Toast.makeText(this, "Enter new name", Toast.LENGTH_SHORT).show();
                    return;
                }
                boolean updated = dbHelper.updateContact(oldPhone, newName);
                showResult(updated);
            } else if (radioPhone.isChecked()) {
                if (newPhone.isEmpty()) {
                    Toast.makeText(this, "Enter new phone number", Toast.LENGTH_SHORT).show();
                    return;
                }
                boolean updated = dbHelper.updateContactActivity(oldPhone, newPhone);
                showResult(updated);
            } else {
                Toast.makeText(this, "Please select update type", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void showResult(boolean success) {
        if (success) {
            Toast.makeText(this, "Contact Updated", Toast.LENGTH_SHORT).show();
            editOldPhone.setText("");
            editNewName.setText("");
            editNewPhone.setText("");
            radioGroup.clearCheck();
            editNewName.setVisibility(View.GONE);
            editNewPhone.setVisibility(View.GONE);
        } else {
            Toast.makeText(this, "Update failed", Toast.LENGTH_SHORT).show();
        }
    }
}
