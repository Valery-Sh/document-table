/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.document;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Valery
 */
public class Person implements Serializable{
    private String s1;
    protected String s2;
    public String s3;
    
    private String firstName;
    private String lastName;
    private Date  birthDay;
    private int sex;
    private List family;  

    private List listOfList;  
    
    //@Embedded
    
    private transient Order order;
    
    public Person() {
       this.firstName = "Bill";
       this.lastName = "Down";
       this.birthDay = null;
       this.sex = 1;
    }
    
    public Person(String firstName,String lastName, Date birthDay, int sex) {
       this.firstName = firstName;
       this.lastName = lastName;
       this.birthDay = birthDay;
       this.sex = sex;
       
    }
    
    //@Embedded
    public Order getOrder() {
        return this.order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }
    
    public Date getBirthDay() {
        return birthDay;
    }

    public void setBirthDay(Date birthDay) {
        this.birthDay = birthDay;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public List getFamily() {
        return family;
    }

    public void setFamily(List family) {
        this.family = family;
    }

    public List getListOfList() {
        return listOfList;
    }

    public void setListOfList(List listOfList) {
        this.listOfList = listOfList;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Person other = (Person) obj;
        if ((this.firstName == null) ? (other.firstName != null) : !this.firstName.equals(other.firstName)) {
            return false;
        }
        if ((this.lastName == null) ? (other.lastName != null) : !this.lastName.equals(other.lastName)) {
            return false;
        }
        if (this.birthDay != other.birthDay && (this.birthDay == null || !this.birthDay.equals(other.birthDay))) {
            return false;
        }
        if (this.sex != other.sex) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        return hash;
    }
    
}
