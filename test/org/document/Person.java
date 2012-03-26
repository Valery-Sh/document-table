/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.document;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    
    private Map personProps;
    
    //@Embedded
    
    private transient Order order;
    
    public Person() {
       this.firstName = "Bill";
       this.lastName = "Down";
       this.birthDay = null;
       this.sex = 1;
       personProps = new HashMap();
    }
    
    public Person(String firstName,String lastName, Date birthDay, int sex) {
       this.firstName = firstName;
       this.lastName = lastName;
       this.birthDay = birthDay;
       this.sex = sex;
       personProps = new HashMap();       
       
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

    public Map getPersonProps() {
        return personProps;
    }

    public void setPersonProps(Map personProps) {
        this.personProps = personProps;
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
        if (this.family != other.family && (this.family == null || !this.family.equals(other.family))) {
            return false;
        }
        if (this.listOfList != other.listOfList && (this.listOfList == null || !this.listOfList.equals(other.listOfList))) {
            return false;
        }
        if (this.personProps != other.personProps && (this.personProps == null || !this.personProps.equals(other.personProps))) {
            return false;
        }
        if (this.order != other.order && (this.order == null || !this.order.equals(other.order))) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 89 * hash + (this.firstName != null ? this.firstName.hashCode() : 0);
        hash = 89 * hash + (this.lastName != null ? this.lastName.hashCode() : 0);
        hash = 89 * hash + (this.birthDay != null ? this.birthDay.hashCode() : 0);
        hash = 89 * hash + this.sex;
        hash = 89 * hash + (this.family != null ? this.family.hashCode() : 0);
        hash = 89 * hash + (this.listOfList != null ? this.listOfList.hashCode() : 0);
        hash = 89 * hash + (this.personProps != null ? this.personProps.hashCode() : 0);
        hash = 89 * hash + (this.order != null ? this.order.hashCode() : 0);
        return hash;
    }

    
}
