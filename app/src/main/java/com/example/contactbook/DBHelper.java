package com.example.contactbook;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

    public static final String DB_NAME = "Phonebook.db";
    public static final String TABLE_NAME = "contacts";
    public static final String COL_NAME = "name";
    public static final String COL_PHONE = "phone";

    public DBHelper(Context context) {
        super(context, DB_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_NAME + " (" +
                COL_NAME + " TEXT, " +
                COL_PHONE + " TEXT PRIMARY KEY)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    // Insert contact
    public boolean insertContact(String name, String phone) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COL_NAME, name);
        cv.put(COL_PHONE, phone);
        long result = db.insert(TABLE_NAME, null, cv);
        return result != -1;
    }

    // Get all contacts
    public Cursor getAllContacts() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
    }

    // Delete contact
    public boolean deleteContact(String phone) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_NAME, "phone = ?", new String[]{phone}) > 0;
    }

    // Update contact name only (used when radio "Name" is selected)
    public boolean updateContact(String phone, String newName) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_NAME, newName);
        int result = db.update(TABLE_NAME, values, COL_PHONE + " = ?", new String[]{phone});
        return result > 0;
    }

    // Update contact phone only (used when radio "Phone" is selected)
    public boolean updateContactActivity(String oldPhone, String newPhone) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_PHONE, newPhone);
        int result = db.update(TABLE_NAME, values, COL_PHONE + " = ?", new String[]{oldPhone});
        return result > 0;
    }

    // Search contact by phone
    public Cursor searchContact(String phone) {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE " + COL_PHONE + " = ?", new String[]{phone});
    }
}
