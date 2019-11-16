package com.company;

import java.util.Scanner;

public class Player {

    char symbol;
    String name;
    Scanner scanner = new Scanner(System.in);

    public Player() {
        System.out.print("Введите имя игрока: ");
        this.name = scanner.nextLine();
        System.out.println();
        System.out.print("Какими символами он будет играть? ");
        this.symbol = scanner.next().charAt(0);
    }


    public char getSymbol() {
        return symbol;
    }

    public String getName(){
        return name;
    }

    public Result generateXY(){
        int x, y;
        do {
            x = (int) (Math.random() * Game.getMap().getSize());
            y = (int) (Math.random() * Game.getMap().getSize());
            System.out.println("Сгенерировали ход автоматически");
        }
        while (!Game.getMap().isFree(x, y));
        return new Result(x,y);
    }

}
