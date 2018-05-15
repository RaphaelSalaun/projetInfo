package com.example.xx_laphoune_xx.projetinfo.model;

/**
 * Created by Xx_LaPhoune_xX on 05/04/2018.
 */

public class User {
    private String mFirstName ;
    private String mLastName;
    private String mAge;
    private String mSexe;
    private float precision;
    private float time;
    private float pressure;
    private int trynumber;


    public User(String name, String surname, String age, String sexe, float precision, float time, float pressure, int trynumber) {
        this.mFirstName = name;
        this.mSexe = sexe;
        this.mAge = age;
        this.precision = precision;
        this.time = time;
        this.mLastName = surname;
        this.trynumber = trynumber;
        this.pressure = pressure;
    }

    public float getPressure() {
        return pressure;
    }

    public void setPressure(float pressure) {
        this.pressure = pressure;
    }

    public void setTrynumber(int trynumber) {
        this.trynumber = trynumber;
    }

    public int getTrynumber() {
        return trynumber;
    }

    public  float getPrecision() {
        return precision;
    }

    public float getTime() {
        return time;
    }

    public void setPrecision(float precision) {
        this.precision = precision;
    }

    public void setTime(float time) {
        this.time = time;
    }

    public String getSexe() {
        return mSexe;
    }

    public void setSexe(String mSexe) {
        this.mSexe = mSexe;
    }

    public String getFirstName() {
        return mFirstName;
    }

    public void setFirstName(String mFirstName) {
        this.mFirstName = mFirstName;
    }

    public String getLastName() {
        return mLastName;
    }

    public void setLastName(String mLastName) {
        this.mLastName = mLastName;
    }

    public String getAge() {
        return mAge;
    }

    public void setAge(String mAge) {
        this.mAge = mAge;
    }

    @Override
    public String toString() {
        return mFirstName + " " + mLastName + " ("+mAge +"ans) "+ " sexe : "+ mSexe + ", precision en pixels :" +
                String.valueOf(precision) +", temps mis (en ms) :" + String.valueOf(time) + "pression = " + pressure + ", essai n°" + trynumber;
    }
}
