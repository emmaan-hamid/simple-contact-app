package com.example.contactbook;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class DeleteContactActivity extends AppCompatActivity {

    EditText editPhoneDelete;
    Button btnDeleteContact;
    DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_contact);

        editPhoneDelete = findViewById(R.id.editPhoneDelete);
        btnDeleteContact = findViewById(R.id.btnDeleteContact);
        dbHelper = new DBHelper(this);

        btnDeleteContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String phone = editPhoneDelete.getText().toString().trim();

                if (phone.isEmpty()) {
                    Toast.makeText(DeleteContactActivity.this, "Enter a phone number", Toast.LENGTH_SHORT).show();
                } else {
                    boolean deleted = dbHelper.deleteContact(phone);
                    if (deleted) {
                        Toast.makeText(DeleteContactActivity.this, "Contact Deleted", Toast.LENGTH_SHORT).show();
                        editPhoneDelete.setText("");
                    } else {
                        Toast.makeText(DeleteContactActivity.this, "No contact found with this phone", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
}
