/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.document;

import java.io.Serializable;


public class Order implements Serializable{
    private Long orderNum;
    public int orderId;
    
    public Order(Long orderNum, int orderId) {
        this.orderId = orderId;
        this.orderNum = orderNum;
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

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Order other = (Order) obj;
        if (this.orderId != other.orderId) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 97 * hash + (this.orderNum != null ? this.orderNum.hashCode() : 0);
        hash = 97 * hash + this.orderId;
        return hash;
    }
    
}
