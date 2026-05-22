package com.mark.cafe.entity;

import java.util.concurrent.atomic.AtomicInteger;

public class Player {
    private int money;
    private AtomicInteger rating;

    public Player(int money, int rating) {
        this.money = money;
        this.rating = new AtomicInteger(rating);
    }

    public void earnMoney(int price){
        money += price;
    }

    public void loseMoney(int price){
        money -= price;
    }

    public void upRating(){
        rating.incrementAndGet();
    }

    public void downRating(){
        rating.decrementAndGet();
    }

    public int getRating(){
        return rating.get();
    }

    public int getMoney(){
        return money;
    }

}
