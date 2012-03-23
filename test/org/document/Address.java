/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.document;

import java.io.Serializable;
import java.util.List;

/**
 *
 * @author Valery
 */
public class Address implements Serializable{
   private String state;
   private String City;
   private String street;
   private int house;
   private int flat;
   private List info;

    public Address(String state, String City, String street, int house, int flat) {
        this.state = state;
        this.City = City;
        this.street = street;
        this.house = house;
        this.flat = flat;
    }

    public String getCity() {
        return City;
    }

    public void setCity(String City) {
        this.City = City;
    }

    public int getFlat() {
        return flat;
    }

    public void setFlat(int flat) {
        this.flat = flat;
    }

    public int getHouse() {
        return house;
    }

    public void setHouse(int house) {
        this.house = house;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public List getInfo() {
        return info;
    }

    public void setInfo(List info) {
        this.info = info;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Address other = (Address) obj;
        if ((this.state == null) ? (other.state != null) : !this.state.equals(other.state)) {
            return false;
        }
        if ((this.City == null) ? (other.City != null) : !this.City.equals(other.City)) {
            return false;
        }
        if ((this.street == null) ? (other.street != null) : !this.street.equals(other.street)) {
            return false;
        }
        if (this.house != other.house) {
            return false;
        }
        if (this.flat != other.flat) {
            return false;
        }
        if (this.info != other.info && (this.info == null || !this.info.equals(other.info))) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 73 * hash + (this.state != null ? this.state.hashCode() : 0);
        hash = 73 * hash + (this.City != null ? this.City.hashCode() : 0);
        hash = 73 * hash + (this.street != null ? this.street.hashCode() : 0);
        hash = 73 * hash + this.house;
        hash = 73 * hash + this.flat;
        hash = 73 * hash + (this.info != null ? this.info.hashCode() : 0);
        return hash;
    }

   
}
