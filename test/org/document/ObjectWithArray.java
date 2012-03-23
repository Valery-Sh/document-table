/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.document;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author Valery
 */
public class ObjectWithArray implements Serializable{
    private String[] stringArray;
    private int[] intArray;
    private List[] listArray;
    private Address[] addressArray;
    private String[][] stringStringArray;
    
    private List list;

    public ObjectWithArray() {
    }

    public Address[] getAddressArray() {
        return addressArray;
    }

    public void setAddressArray(Address[] addressArray) {
        this.addressArray = addressArray;
    }

    public int[] getIntArray() {
        return intArray;
    }

    public void setIntArray(int[] intArray) {
        this.intArray = intArray;
    }

    public List[] getListArray() {
        return listArray;
    }

    public void setListArray(List[] listArray) {
        this.listArray = listArray;
    }

    public String[] getStringArray() {
        return stringArray;
    }

    public void setStringArray(String[] stringArray) {
        this.stringArray = stringArray;
    }

    public String[][] getStringStringArray() {
        return stringStringArray;
    }

    public void setStringStringArray(String[][] stringStringArray) {
        this.stringStringArray = stringStringArray;
    }

    public List getList() {
        return list;
    }

    public void setList(List list) {
        this.list = list;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final ObjectWithArray other = (ObjectWithArray) obj;
        if (!Arrays.deepEquals(this.stringArray, other.stringArray)) {
            return false;
        }
        if (!Arrays.equals(this.intArray, other.intArray)) {
            return false;
        }
        if (!Arrays.deepEquals(this.listArray, other.listArray)) {
            return false;
        }
        if (!Arrays.deepEquals(this.addressArray, other.addressArray)) {
            return false;
        }
        if (!Arrays.deepEquals(this.stringStringArray, other.stringStringArray)) {
            return false;
        }
        if (this.list != other.list && (this.list == null || !this.list.equals(other.list))) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 31 * hash + Arrays.deepHashCode(this.stringArray);
        hash = 31 * hash + Arrays.hashCode(this.intArray);
        hash = 31 * hash + Arrays.deepHashCode(this.listArray);
        hash = 31 * hash + Arrays.deepHashCode(this.addressArray);
        hash = 31 * hash + Arrays.deepHashCode(this.stringStringArray);
        hash = 31 * hash + (this.list != null ? this.list.hashCode() : 0);
        return hash;
    }

    
    
}
