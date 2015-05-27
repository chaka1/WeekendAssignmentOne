package com.example.chaka.weekendassignmentone.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.chaka.weekendassignmentone.obj.Employee;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Chaka on 22/05/2015.
 */
public class DatabaseHelper extends SQLiteOpenHelper{


    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "employeesManager";

    // Contacts table name
    private static final String TABLE_CONTACTS = "employee";

    private static final String TABLE_LOGIN = "login";

    // Contacts Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_NI = "ni";
    private static final String KEY_PASSPORT = "passport";
    private static final String KEY_GENDER = "gender";
    private static final String KEY_COUNTRY = "country";
    private static final String KEY_MANAGER = "manager";
    private static final String KEY_PASSWORD = "password";
    private static final String KEY_IMAGE = "image";


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_CONTACTS + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_NAME + " TEXT,"
                + KEY_NI + " TEXT,"+ KEY_PASSPORT + " TEXT,"+ KEY_GENDER + " TEXT," + KEY_COUNTRY +
                " TEXT," + KEY_MANAGER + " INT, "+ KEY_PASSWORD + " TEXT, " + KEY_IMAGE + " BLOB " + ")";
        db.execSQL(CREATE_CONTACTS_TABLE);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CONTACTS);

        // Create tables again
        onCreate(db);
    }

    /**
     * All CRUD(Create, Read, Update, Delete) Operations
     */

    // Adding new employee
    public void addEmployee(Employee employee) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, employee.get_name()); // Employee Name
        values.put(KEY_NI, employee.get_ni()); // Employee NI
        values.put(KEY_PASSPORT, employee.get_passport()); // Employee Passport
        values.put(KEY_GENDER, employee.get_gender()); // Employee Gender
        values.put(KEY_COUNTRY, employee.get_country());
        values.put(KEY_MANAGER,employee.get_manager());
        values.put(KEY_PASSWORD,employee.get_password());
        values.put(KEY_IMAGE, DbBitmapUtility.getBytes(employee.get_image()));


        // Inserting Row
        db.insert(TABLE_CONTACTS, null, values);
        db.close(); // Closing database connection
    }

    // Getting single employee
    public Employee getEmployee(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_CONTACTS, new String[] { KEY_ID,
                        KEY_NAME, KEY_NI, KEY_PASSPORT, KEY_GENDER , KEY_COUNTRY, KEY_MANAGER, KEY_PASSWORD,KEY_IMAGE}, KEY_ID + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        Employee employee = new Employee();
        employee.set_id(Integer.parseInt(cursor.getString(0)));
        employee.set_name(cursor.getString(1));
        employee.set_ni(cursor.getString(2));
        employee.set_passport(cursor.getString(3));
        employee.set_gender(cursor.getString(4));
        employee.set_country(cursor.getString(5));
        employee.set_manager(Boolean.parseBoolean(cursor.getString(6)));
        employee.set_password(cursor.getString(7));
        employee.set_image(DbBitmapUtility.getImage(cursor.getBlob(8)));
        // return employee
        return employee;
    }

    // Getting All Employees
    public List<Employee> getAllEmployees() {
        List<Employee> employeeList = new ArrayList<Employee>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_CONTACTS;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Employee employee = new Employee();
                employee.set_id(Integer.parseInt(cursor.getString(0)));
                employee.set_name(cursor.getString(1));
                employee.set_ni(cursor.getString(2));
                employee.set_passport(cursor.getString(3));
                employee.set_gender(cursor.getString(4));
                employee.set_country(cursor.getString(5));
                employee.set_manager(Boolean.parseBoolean(cursor.getString(6)));
                employee.set_password(cursor.getString(7));
                employee.set_image(DbBitmapUtility.getImage(cursor.getBlob(8)));
                // Adding employee to list
                employeeList.add(employee);
            } while (cursor.moveToNext());
        }

        // return employee list
        return employeeList;
    }

    // Updating single employee
    public int updateEmployee(Employee employee) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, employee.get_name()); // Employee Name
        values.put(KEY_NI, employee.get_ni()); // Employee NI
        values.put(KEY_PASSPORT, employee.get_passport()); // Employee Passport
        values.put(KEY_GENDER, employee.get_gender()); // Employee Gender
        values.put(KEY_COUNTRY, employee.get_country());
        values.put(KEY_MANAGER,employee.get_manager());

        values.put(KEY_PASSWORD,employee.get_password());
        values.put(KEY_IMAGE, DbBitmapUtility.getBytes(employee.get_image()));// Employee Gender

        // updating row
        return db.update(TABLE_CONTACTS, values, KEY_ID + " = ?",
                new String[] { String.valueOf(employee.get_id()) });
    }

    // Deleting single employee
    public void deleteEmployee(Employee employee) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_CONTACTS, KEY_ID + " = ?",
                new String[]{String.valueOf(employee.get_id())});
        db.close();
    }


    // Getting employees Count
    public int getEmployeesCount() {
        String countQuery = "SELECT  * FROM " + TABLE_CONTACTS;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();

        // return count
        return cursor.getCount();
    }


    public Employee tryLogin(String username, String password){
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_CONTACTS, new String[] { KEY_ID,
                        KEY_NAME, KEY_NI, KEY_PASSPORT, KEY_GENDER , KEY_COUNTRY, KEY_MANAGER, KEY_PASSWORD,KEY_IMAGE},
                KEY_NAME + "=? and " + KEY_PASSWORD + "=?",
                new String[] { username,password}, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();

            Employee employee = new Employee();
            employee.set_id(Integer.parseInt(cursor.getString(0)));
            employee.set_name(cursor.getString(1));
            employee.set_ni(cursor.getString(2));
            employee.set_passport(cursor.getString(3));
            employee.set_gender(cursor.getString(4));
            employee.set_country(cursor.getString(5));
            employee.set_manager(Boolean.parseBoolean(cursor.getString(6)));
            employee.set_password(cursor.getString(7));
            employee.set_image(DbBitmapUtility.getImage(cursor.getBlob(8)));
            employee.set_manager(true);
            // return employee
            return employee;
        }
        return null;

    }
}
