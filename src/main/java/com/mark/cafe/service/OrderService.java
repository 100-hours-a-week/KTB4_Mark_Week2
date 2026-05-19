package com.mark.cafe.service;

import com.mark.cafe.menus.MenuItem;

import java.util.Random;

public class OrderService {
    private final Random random = new Random();
    private final MenuItem[] menuItems;

    public OrderService(MenuItem[] menuItems){
        this.menuItems = menuItems;
    }

    public MenuItem receiveOrder(){
        int menuSelect = random.nextInt(menuItems.length);
        return menuItems[menuSelect];
    }




}
