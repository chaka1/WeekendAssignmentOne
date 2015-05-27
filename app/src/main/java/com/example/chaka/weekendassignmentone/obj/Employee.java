package com.example.chaka.weekendassignmentone.obj;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

/**
 * Created by Chaka on 22/05/2015.
 */
public class Employee implements Parcelable {
    int _id;
    String _name;
    String _login;
    String _ni;
    String _passport;
    String _gender;
    String _country;
    String _password;
    Date _dateOfBirth;
    Boolean _manager;
    Bitmap _image;

    public Employee() {
        this._manager = false;
    }


    public Employee(String _name, String _ni, String _passport, String _gender,
                    String _country, String _password) {
        this._name = _name;
        this._ni = _ni;
        this._passport = _passport;
        this._gender = _gender;
        this._country = _country;
        this._password = _password;
        this._manager = false;
    }

    public Employee(String _name, String _ni, String _passport, String _gender,
                    String _country, String _password, Bitmap _image) {
        this._name = _name;
        this._ni = _ni;
        this._passport = _passport;
        this._gender = _gender;
        this._country = _country;
        this._password = _password;
        this._image = _image;
        this._manager = true;
    }

    public Employee(String _name, String _ni, String _passport, String _gender,
                    String _country, String _password, Boolean _manager, Bitmap _image) {
        this._name = _name;
        this._ni = _ni;
        this._passport = _passport;
        this._gender = _gender;
        this._country = _country;
        this._password = _password;
        this._manager = _manager;
        this._image = _image;
    }

    public Employee(int _id, String _name, String _ni, String _passport, String _gender,
                    String _country, String _password, Boolean _manager, Bitmap _image) {
        this._id = _id;
        this._name = _name;
        this._ni = _ni;
        this._passport = _passport;
        this._gender = _gender;
        this._country = _country;
        this._password = _password;
        this._manager = _manager;
        this._image = _image;
    }



    public Employee(int _id, String _name, String _login, String _ni, String _passport,
                    String _gender, String _country, String _password, Boolean _manager, Bitmap _image) {
        this._id = _id;
        this._name = _name;
        this._login = _login;
        this._ni = _ni;
        this._passport = _passport;
        this._gender = _gender;
        this._country = _country;
        this._password = _password;
        this._manager = _manager;
        this._image = _image;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String get_name() {
        return _name;
    }

    public void set_name(String _name) {
        this._name = _name;
    }

    public String get_login() {
        return _login;
    }

    public void set_login(String _login) {
        this._login = _login;
    }

    public String get_ni() {
        return _ni;
    }

    public void set_ni(String _ni) {
        this._ni = _ni;
    }

    public String get_passport() {
        return _passport;
    }

    public void set_passport(String _passport) {
        this._passport = _passport;
    }

    public String get_gender() {
        return _gender;
    }

    public void set_gender(String _gender) {
        this._gender = _gender;
    }

    public String get_country() {
        return _country;
    }

    public void set_country(String _country) {
        this._country = _country;
    }

    public String get_password() {
        return _password;
    }

    public void set_password(String _password) {
        this._password = _password;
    }

    public Boolean get_manager() {
        return _manager;
    }

    public void set_manager(Boolean _manager) {
        this._manager = _manager;
    }

    public Bitmap get_image() {
        return _image;
    }

    public void set_image(Bitmap _image) {
        this._image = _image;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(_id);

        dest.writeString(_name);
        dest.writeString(_ni);
        dest.writeString(_passport);
        dest.writeString(_gender);
        dest.writeString(_country);
        dest.writeString(_password);
        dest.writeString(String.valueOf(_manager));

        _image.writeToParcel(dest, flags);

    }

    /*
     * Retrieving Employee data from Parcel object
     * This constructor is invoked by the method createFromParcel(Parcel source) of
     * the object CREATOR
     */
    public Employee(Parcel in) {
        this._id = in.readInt();
        this._name = in.readString();
        this._ni = in.readString();
        this._passport = in.readString();
        this._gender = in.readString();
        this._country = in.readString();
        this._password = in.readString();
        this._manager = Boolean.valueOf(in.readString());
        this._image = Bitmap.CREATOR.createFromParcel(in);
        //this.image = in.readString();
    }

    public static final Parcelable.Creator<Employee> CREATOR = new Parcelable.Creator<Employee>() {

        @Override
        public Employee createFromParcel(Parcel source) {
            return new Employee(source);
        }

        @Override
        public Employee[] newArray(int size) {
            return new Employee[size];
        }
    };
}
