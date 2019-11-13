package com.company;

public class Player {

    private char symbol;
    private String name;

    private boolean isHuman;

    public Player(char symbol, String name, boolean isHuman) {
        this.symbol = symbol;
        this.name = name;
        this.isHuman = isHuman;
    }

    public char getSymbol() {
        return symbol;
    }

    public String getName(){
        return name;
    }

    public boolean isHuman() {
        return isHuman;
    }
}
