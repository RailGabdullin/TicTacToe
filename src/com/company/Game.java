package com.company;

import java.util.Scanner;

public class Game {

    private Player player1;
    private Player player2;
    private Map map;

    private Player winner;
    private Scanner scanner = new Scanner(System.in);

    private Player currentPlayer;

    public Game( Map map, Player player1, Player player2) {
        this.player1 = player1;
        this.player2 = player2;
        this.map = map;
    }

    public void chooseWhoFirst (){
        double x = Math.random();
        if (x > 0.5){
            currentPlayer = player1;
        } else {
            currentPlayer = player2;
        }
        System.out.println("Монетка говорит, что первый ход за " + currentPlayer.getName() + ", который играет за " + currentPlayer.getSymbol());
    }

    public void nextStep (){
        int x;
        int y;

        System.out.println("Выполняется функция следующего хода");

        if (currentPlayer.isHuman()){
            System.out.println("Ваш ход: ");
            do{
                x = scanner.nextInt();
                y = scanner.nextInt();
                if (map.isValid(x,y)){
                    if (!map.isFree(x,y)){
                        System.out.println("Эта ячейка занята, выберите другую: ");
                    }
                } else System.out.println("Такой ячейки не существует, выберите другую: ");
            } while (!map.isFree(x, y));
        } else {

            //Генерируем ход в случайную ячеку пока не найдем свободную
            do {
                x = (int) (Math.random() * map.getSize());
                y = (int) (Math.random() * map.getSize());
                System.out.println("Сгенерировали ход автоматически");
            }
            while (!map.isFree(x, y));
        }
        //Ставим символ в свободную ячейку и печатаем новую карту
        map.put(currentPlayer, x, y);
        map.printMap();

        //Передаем ход другому игроку
        if (currentPlayer == player1) {
            currentPlayer = player2;
            System.out.println("Передал ход от 1 к 2");
        } else {
            currentPlayer = player1;
            System.out.println("Передал ход от 2 к 1");
        }
    }

    public boolean checkWinner(){

//        //Ща будет хардкод, переделать
//        if (
//                //Проверяем горизонтали
//                (map.getMapXY(0,0) == player1.getSymbol()) && (map.getMapXY(0,0) == map.getMapXY(0,1) && map.getMapXY(0,1) == map.getMapXY(0,2))||
//                (map.getMapXY(1,0) == player1.getSymbol()) && (map.getMapXY(1,0) == map.getMapXY(1,1) && map.getMapXY(1,1) == map.getMapXY(1,2))||
//                (map.getMapXY(2,0) == player1.getSymbol()) && (map.getMapXY(2,0) == map.getMapXY(2,1) && map.getMapXY(2,1) == map.getMapXY(2,2))||
//
//                        //Проверяем вертикали
//                (map.getMapXY(0,0) == player1.getSymbol()) && (map.getMapXY(0,0) == map.getMapXY(1,0) && map.getMapXY(1,0) == map.getMapXY(2,0))||
//                (map.getMapXY(0,1) == player1.getSymbol()) && (map.getMapXY(0,1) == map.getMapXY(1,1) && map.getMapXY(1,1) == map.getMapXY(2,1))||
//                (map.getMapXY(0,2) == player1.getSymbol()) && (map.getMapXY(0,2) == map.getMapXY(1,2) && map.getMapXY(1,2) == map.getMapXY(2,2))||
//
//                        //Проверяем диагонали
//                (map.getMapXY(0,0) == player1.getSymbol()) && (map.getMapXY(0,0) == map.getMapXY(1,1) && map.getMapXY(1,1) == map.getMapXY(2,2))||
//                (map.getMapXY(2,2) == player1.getSymbol()) && (map.getMapXY(2,2) == map.getMapXY(1,1) && map.getMapXY(1,1) == map.getMapXY(0,0))
//
//                ){
//            winner = player1;
//            return true;
//        } else if (
//            //Проверяем горизонтали
//            (map.getMapXY(0,0) == player2.getSymbol()) && (map.getMapXY(0,0) == map.getMapXY(0,1) && map.getMapXY(0,1) == map.getMapXY(0,2))||
//                    (map.getMapXY(1,0) == player2.getSymbol()) && (map.getMapXY(1,0) == map.getMapXY(1,1) && map.getMapXY(1,1) == map.getMapXY(1,2))||
//                    (map.getMapXY(2,0) == player2.getSymbol()) && (map.getMapXY(2,0) == map.getMapXY(2,1) && map.getMapXY(2,1) == map.getMapXY(2,2))||
//
//                    //Проверяем вертикали
//                    (map.getMapXY(0,0) == player2.getSymbol()) && (map.getMapXY(0,0) == map.getMapXY(1,0) && map.getMapXY(1,0) == map.getMapXY(2,0))||
//                    (map.getMapXY(0,1) == player2.getSymbol()) && (map.getMapXY(0,1) == map.getMapXY(1,1) && map.getMapXY(1,1) == map.getMapXY(2,1))||
//                    (map.getMapXY(0,2) == player2.getSymbol()) && (map.getMapXY(0,2) == map.getMapXY(1,2) && map.getMapXY(1,2) == map.getMapXY(2,2))||
//
//                    //Проверяем диагонали
//                    (map.getMapXY(0,0) == player2.getSymbol()) && (map.getMapXY(0,0) == map.getMapXY(1,1) && map.getMapXY(1,1) == map.getMapXY(2,2))||
//                    (map.getMapXY(2,2) == player2.getSymbol()) && (map.getMapXY(2,2) == map.getMapXY(1,1) && map.getMapXY(1,1) == map.getMapXY(0,0))
//                ){
//            winner = player2;
//            return true;
//        } else { return false; }

        int countOfSameSymbolsHorizontal;
        int countOfSameSymbolsVertical;
        int countOfSameSymbolsDiagonal1 = 0;
        int countOfSameSymbolsDiagonal2 = 0;
        boolean result = false;

        for (int i = 0; i < map.getSize(); i++) {
            countOfSameSymbolsHorizontal = 0;
            countOfSameSymbolsVertical = 0;
            for (int j = 0; j < map.getSize(); j++) {

                //Горизонтали
                if (j < map.getSize() - 1 &&
                map.getMapXY(i, j) == map.getMapXY(i, j+1)&&
                        !map.isFree(i,j)
                        ){
                    countOfSameSymbolsHorizontal ++;
                    if (countOfSameSymbolsHorizontal == map.getSize() - 1){
                        result = true;
                        winner = getPlayer(map.getMapXY(i,j));
                        System.out.println("Гор " + i + " " + j + " " + countOfSameSymbolsHorizontal);
                    }
                }

                //Вертикали
                if (j < map.getSize() - 1 &&
                        map.getMapXY(j, i) == map.getMapXY(j + 1, i)&&
                        !map.isFree(j,i)
                        ){
                    countOfSameSymbolsVertical ++;
                    if (countOfSameSymbolsVertical == map.getSize() - 1){
                        result = true;
                        winner = getPlayer(map.getMapXY(j,i));
                        System.out.println("Верт " + j + " " + i + " " + countOfSameSymbolsVertical);
                    }
                }

            }

            //Диагонали
            //Прямая
            if (i < map.getSize() - 1&&
                    map.getMapXY(i, i) == map.getMapXY(i+1, i+1)&&
                    !map.isFree(i,i)
                    ){
                countOfSameSymbolsDiagonal1 ++;
                if (countOfSameSymbolsDiagonal1 == map.getSize() - 1){
                    result = true;
                    winner = getPlayer(map.getMapXY(i,i));
                    System.out.println("Дпр " + i + " " + i);
                }
            }

            //Обратная
            if (i < map.getSize() - 1&&
                    map.getMapXY(i, map.getSize() - 1 - i) == map.getMapXY(i + 1, map.getSize() - i - 2)
                    &&
                    !map.isFree(i,map.getSize() - 1-i)){
                countOfSameSymbolsDiagonal2 ++;
                if (countOfSameSymbolsDiagonal2 == map.getSize() - 1){
                    result = true;
                    winner = getPlayer(map.getMapXY(i,map.getSize() - 1 - i));
                    System.out.println("Добр " + i + " " + i);
                }
            }


        }

        return result;
    }

    public boolean checkTie(){
        boolean result = true;
        for (int i = 0; i < map.getSize(); i++) {
            for (int j = 0; j < map.getSize(); j++) {
                if (map.getMapXY(i,j) == map.getNoSymbol()) {
                    result = false;
                }
            }
        }
        return result;
    }

    public Player getWinner() {
        return winner;
    }

    public Player getPlayer (char symbol){
        if (player1.getSymbol() == symbol){
            return player1;
        } else if (player2.getSymbol() == symbol) {return player2; } else return null;
    }


}
