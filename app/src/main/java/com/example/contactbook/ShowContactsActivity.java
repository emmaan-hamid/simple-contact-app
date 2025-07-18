package com.example.contactbook;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;

public class ShowContactsActivity extends AppCompatActivity {

    ListView listViewContacts;
    DBHelper dbHelper;
    ArrayList<String> contactsList;
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_contacts);

        listViewContacts = findViewById(R.id.listViewContacts);
        dbHelper = new DBHelper(this);

        loadContacts();
    }

    private void loadContacts() {
        contactsList = new ArrayList<>();
        Cursor cursor = dbHelper.getAllContacts();

        if (cursor.getCount() == 0) {
            Toast.makeText(this, "No contacts found", Toast.LENGTH_SHORT).show();
            return;
        }

        while (cursor.moveToNext()) {
            String name = cursor.getString(0);
            String phone = cursor.getString(1);
            contactsList.add("Name: " + name + "\nPhone: " + phone);
        }

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, contactsList);
        listViewContacts.setAdapter(adapter);
    }
}
