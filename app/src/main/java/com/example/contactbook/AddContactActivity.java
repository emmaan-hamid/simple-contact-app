package com.example.contactbook;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class AddContactActivity extends AppCompatActivity {

    EditText etName, etPhone;
    Button btnAddToDB;
    DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contact);

        etName = findViewById(R.id.etName);
        etPhone = findViewById(R.id.etPhone);
        btnAddToDB = findViewById(R.id.btnAddToDB);
        dbHelper = new DBHelper(this);

        btnAddToDB.setOnClickListener(view -> {
            String name = etName.getText().toString().trim();
            String phone = etPhone.getText().toString().trim();

            if (name.isEmpty() || phone.isEmpty()) {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            } else {
                boolean inserted = dbHelper.insertContact(name, phone);
                if (inserted) {
                    Toast.makeText(this, "Contact Added!", Toast.LENGTH_SHORT).show();
                    etName.setText("");
                    etPhone.setText("");
                } else {
                    Toast.makeText(this, "Failed to add contact. Number might already exist.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
