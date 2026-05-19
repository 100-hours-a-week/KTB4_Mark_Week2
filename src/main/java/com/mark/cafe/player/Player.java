package com.mark.cafe.player;

public class Player {
    private int money;

    public Player(int money) {
        this.money = money;
    }

    public void earnMoney(int price){
        money += price;
    }

    public void loseMoney(int price){
        money -= price;
    }

    public int getMoney(){
        return money;
    }

}
