package com.project.android.qr;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DBManager {
    private DatabaseHelper dbHelper;

    private Context context;

    private SQLiteDatabase database;

    public DBManager(Context c) {
        context = c;
    }

    public DBManager open() throws SQLException {
        dbHelper = new DatabaseHelper(context);
        database = dbHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        dbHelper.close();
    }

    public void insert(ContactModel contact) {
        ContentValues contentValue = new ContentValues();
        contentValue.put(DatabaseHelper.COLUMN_NAME, contact.getName());
        contentValue.put(DatabaseHelper.COLUMN_EMAIL, contact.getEmail());
        contentValue.put(DatabaseHelper.COLUMN_STREET, contact.getStreet());
        contentValue.put(DatabaseHelper.COLUMN_CITY, contact.getCity());
        contentValue.put(DatabaseHelper.COLUMN_STATE, contact.getState());
        contentValue.put(DatabaseHelper.COLUMN_COUNTRY, contact.getCountry());
        contentValue.put(DatabaseHelper.COLUMN_POSTCODE, contact.getPostcode());
        contentValue.put(DatabaseHelper.COLUMN_CELL, contact.getCell());
        contentValue.put(DatabaseHelper.COLUMN_DOB, contact.getDob());
        contentValue.put(DatabaseHelper.COLUMN_PHONE, contact.getPhone());
        contentValue.put(DatabaseHelper.COLUMN_UID, contact.getUid());
        contentValue.put(DatabaseHelper.COLUMN_IMAGE_LARGE, contact.getImg_lg());
        contentValue.put(DatabaseHelper.COLUMN_IMAGE_MEDIUM, contact.getImg_md());
        contentValue.put(DatabaseHelper.COLUMN_IMAGE_THUMBNAIL, contact.getImg_sm());
        contentValue.put(DatabaseHelper.COLUMN_NATURE, contact.getNat());
        database.insert(DatabaseHelper.TABLE_NAME, null, contentValue);
    }

    @SuppressLint("Range")
    public List<ContactModel> fetch() {
        List<ContactModel> modelList = new ArrayList<>();
        Cursor cursor = database.rawQuery("SELECT * FROM " + DatabaseHelper.TABLE_NAME, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    int _id = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COLUMN_ID));
                    String name = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_NAME));
                    String street = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_STREET));
                    String city = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_CITY));
                    String state = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_STATE));
                    String country = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_COUNTRY));
                    String postcode = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_POSTCODE));
                    String cell = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_CELL));
                    String dob = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_DOB));
                    String phone = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_PHONE));
                    String email = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_EMAIL));
                    String uid = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_UID));
                    String img_lg = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_IMAGE_LARGE));
                    String img_md = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_IMAGE_MEDIUM));
                    String img_sm = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_IMAGE_THUMBNAIL));
                    String nat = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_NATURE));
                    ContactModel model = new ContactModel();
                    model.set_id(_id);
                    model.setUid(uid);
                    model.setName(name);
                    model.setEmail(email);
                    model.setStreet(street);
                    model.setCity(city);
                    model.setState(state);
                    model.setCountry(country);
                    model.setPostcode(postcode);
                    model.setCell(cell);
                    model.setDob(dob);
                    model.setPhone(phone);
                    model.setImg_lg(img_lg);
                    model.setImg_md(img_md);
                    model.setImg_sm(img_sm);
                    model.setNat(nat);
                    modelList.add(model);
                } while (cursor.moveToNext());
            }
        }

        return modelList;
    }

//    public int update(long _id, String name, String desc) {
//        ContentValues contentValues = new ContentValues();
//        contentValues.put(DatabaseHelper.SUBJECT, name);
//        contentValues.put(DatabaseHelper.DESC, desc);
//        int i = database.update(DatabaseHelper.TABLE_NAME, contentValues, DatabaseHelper._ID + " = " + _id, null);
//        return i;
//    }

//    public void delete(long _id) {
//        database.delete(DatabaseHelper.TABLE_NAME, DatabaseHelper._ID + "=" + _id, null);
//    }
}

class DatabaseHelper extends SQLiteOpenHelper {
    // Table Name
    public static final String TABLE_NAME = "contacts";

    // Table Columns
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_NAME = "_name";
    public static final String COLUMN_EMAIL = "_email";
    public static final String COLUMN_DOB = "_dob";
    public static final String COLUMN_STREET = "_street";
    public static final String COLUMN_CITY = "_city";
    public static final String COLUMN_STATE = "_state";
    public static final String COLUMN_COUNTRY = "_country";
    public static final String COLUMN_POSTCODE = "_postcode";
    public static final String COLUMN_PHONE = "_phone";
    public static final String COLUMN_CELL = "_cell";
    public static final String COLUMN_UID = "_uid";
    public static final String COLUMN_IMAGE_LARGE = "_img_lg";
    public static final String COLUMN_IMAGE_MEDIUM = "_img_md";
    public static final String COLUMN_IMAGE_THUMBNAIL = "_img_sm";
    public static final String COLUMN_NATURE = "_nat";

    // Database Information
    static final String DB_NAME = "user.db";

    // database version
    static final int DB_VERSION = 1;

    // Creating table query
    private static final String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + "(" +
            COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COLUMN_UID + " TEXT NOT NULL, " +
            COLUMN_NAME + " TEXT NOT NULL, " +
            COLUMN_EMAIL + " TEXT NOT NULL, " +
            COLUMN_DOB + " TEXT NOT NULL, " +
            COLUMN_STREET + " TEXT NOT NULL, " +
            COLUMN_CITY + " TEXT NOT NULL, " +
            COLUMN_STATE + " TEXT NOT NULL, " +
            COLUMN_COUNTRY + " TEXT NOT NULL, " +
            COLUMN_POSTCODE + " TEXT NOT NULL, " +
            COLUMN_PHONE + " TEXT NOT NULL, " +
            COLUMN_CELL + " TEXT NOT NULL, " +
            COLUMN_IMAGE_LARGE + " TEXT NOT NULL, " +
            COLUMN_IMAGE_MEDIUM + " TEXT NOT NULL, " +
            COLUMN_IMAGE_THUMBNAIL + " TEXT NOT NULL, " +
            COLUMN_NATURE + " TEXT NOT NULL);";

    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
}
