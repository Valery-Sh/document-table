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
public class PersonWithReadOnlyFields implements Serializable{
    
    private String firstName;
    private String lastName;
    private Date  birthDay;
    private int sex;
    private List family;  

    private List listOfList;  
    
    private Map personProps;
    
    //@Embedded
    
    private transient Order order;
    private OrderWithReadOnlyFields orderWithReadOnlyFields;   
    public PersonWithReadOnlyFields() {
       this.firstName = "Bill";
       this.lastName = "Down";
       this.birthDay = null;
       this.sex = 1;
       personProps = new HashMap();
    }
    
    public PersonWithReadOnlyFields(String firstName,String lastName, Date birthDay, int sex) {
       this.firstName = firstName;
       this.lastName = lastName;
       this.birthDay = birthDay;
       this.sex = sex;
       personProps = new HashMap();       
       
    }
    
    //@Embedded
    public OrderWithReadOnlyFields getOrderWithReadOnlyFields() {
        return this.orderWithReadOnlyFields;
    }

    public void setOrderWithReadOnlyFields(OrderWithReadOnlyFields order) {
        this.orderWithReadOnlyFields = order;
    }

    public Order getOrder() {
        return order;
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

/*    public void setSex(int sex) {
        this.sex = sex;
    }
*/
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


    
}
