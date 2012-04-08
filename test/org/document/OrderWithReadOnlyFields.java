/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.document;

import java.io.Serializable;

/**
 *
 * @author Valery
 */
public class OrderWithReadOnlyFields implements Serializable{
    private Long orderNum;
    public int orderId;
    
    public final String info = "This is a final property";
    
    public OrderWithReadOnlyFields(Long orderNum, int orderId) {
        this.orderId = orderId;
        this.orderNum = orderNum;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        System.out.println("Unsupported operation for a final field");
    }
    
    public Long getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(Long orderNum) {
        this.orderNum = orderNum;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    
    
}

