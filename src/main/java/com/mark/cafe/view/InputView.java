package com.mark.cafe.view;

import java.util.Scanner;


public class InputView {
    private final Scanner sc;

    public InputView(Scanner sc){
        this.sc = sc;
    }

    public String readLine(){
        return sc.nextLine();
    }

    public void closeScanner(){
        sc.close();
    }
}
