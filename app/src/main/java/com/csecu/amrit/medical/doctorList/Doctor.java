package com.csecu.amrit.medical.doctorList;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Amrit on 27-12-2017.
 */

public class Doctor implements Parcelable {
    public static final Creator<Doctor> CREATOR = new Creator<Doctor>() {
        @Override
        public Doctor createFromParcel(Parcel in) {
            return new Doctor(in);
        }

        @Override
        public Doctor[] newArray(int size) {
            return new Doctor[size];
        }
    };
    String id;
    String name, password, contact, sex, special, qualification, chamber, days, hours, fee;
    String registration, imageName, token;
    Bitmap image;

    protected Doctor(Parcel in) {
        id = in.readString();
        name = in.readString();
        password = in.readString();
        contact = in.readString();
        sex = in.readString();
        special = in.readString();
        qualification = in.readString();
        chamber = in.readString();
        days = in.readString();
        hours = in.readString();
        fee = in.readString();
        registration = in.readString();
        imageName = in.readString();
        token = in.readString();
        image = in.readParcelable(Bitmap.class.getClassLoader());
    }

    public Doctor() {

    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(name);
        dest.writeString(password);
        dest.writeString(contact);
        dest.writeString(sex);
        dest.writeString(special);
        dest.writeString(qualification);
        dest.writeString(chamber);
        dest.writeString(days);
        dest.writeString(hours);
        dest.writeString(fee);
        dest.writeString(registration);
        dest.writeString(imageName);
        dest.writeString(token);
        dest.writeParcelable(image, flags);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getSpecial() {
        return special;
    }

    public void setSpecial(String special) {
        this.special = special;
    }

    public String getQualification() {
        return qualification;
    }

    public void setQualification(String qualification) {
        this.qualification = qualification;
    }

    public String getChamber() {
        return chamber;
    }

    public void setChamber(String chamber) {
        this.chamber = chamber;
    }

    public String getDays() {
        return days;
    }

    public void setDays(String days) {
        this.days = days;
    }

    public String getHours() {
        return hours;
    }

    public void setHours(String hours) {
        this.hours = hours;
    }

    public String getFee() {
        return fee;
    }

    public void setFee(String fee) {
        this.fee = fee;
    }

    public String getRegistration() {
        return registration;
    }

    public void setRegistration(String registration) {
        this.registration = registration;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }
}
