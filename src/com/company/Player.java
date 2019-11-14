package com.company;

public class Player {

    private char symbol;
    private String name;
    private boolean isHuman;
    private boolean isSmart = false;

    public Player(char symbol, String name, boolean isHuman) {
        this.symbol = symbol;
        this.name = name;
        this.isHuman = isHuman;
    }

    public Player(char symbol, String name, boolean isHuman, boolean isSmart) {
        this.symbol = symbol;
        this.name = name;
        this.isHuman = isHuman;
        this.isSmart = isSmart;
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

    public boolean isSmart() {
        return isSmart;
    }
}
