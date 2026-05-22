package com.mark.cafe.actions;

import java.util.List;

public interface Menus {
    String getMenuName();
    int getMenuPrice();
    List<Recipes> getMenuSteps();
}
