package com.mario.countrycatalog.models.Corona;

import java.util.Date;
import java.util.List;

public class CoronaInformation {
    public String ID;
    public String Message;
    public Global Global;
    public List<Country> Countries;
    public Date Date;

    public String getiD() {
        return ID;
    }

    public void setiD(String ID) {
        this.ID = ID;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String Message) {
        this.Message = Message;
    }

    public Global getGlobal() {
        return Global;
    }

    public void setGlobal(Global Global) {
        this.Global = Global;
    }

    public List<Country> getCountries() {
        return Countries;
    }

    public void setCountries(List<Country> Countries) {
        this.Countries = Countries;
    }

    public Date getDate() {
        return Date;
    }

    public void setDate(Date Date) {
        this.Date = Date;
    }
}
