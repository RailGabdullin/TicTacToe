package com.company;


public class Human extends Player {

    public Human() {
    }

    @Override
    public Result generateXY() {
        int x,y;
        System.out.println("Ваш ход: ");
        do{
            x = scanner.nextInt();
            y = scanner.nextInt();
            if (Game.getMap().isValid(x,y)){
                if (!Game.getMap().isFree(x,y)){
                    System.out.println("Эта ячейка занята, выберите другую: ");
                }
            } else System.out.println("Такой ячейки не существует, выберите другую: ");
        } while (!Game.getMap().isFree(x, y));
        return new Result(x,y);
    }

}
