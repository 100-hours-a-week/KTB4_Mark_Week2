package com.mark.cafe.entity;

import com.mark.cafe.menus.MenuItem;

public class Order {
    private MenuItem menu;
    private long orderTime;

    public Order(MenuItem menu, long orderTime){
        this.menu = menu;
        this.orderTime = orderTime;
    }

    public MenuItem getMenu(){
        return menu;
    }

    public long getOrderTime(){
        return orderTime;
    }

}
