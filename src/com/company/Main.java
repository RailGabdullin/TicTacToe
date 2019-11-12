package com.company;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Scanner;


public class Main {

    private static char playerChar1 = 'X';
    private static char playerChar2 = '0';
    private static int mapSize = 3;
    private static Player player1;
    private static Player player2;
    private static Map map;
    private static Game game;

    public static void main(String[] args) {

        System.out.println("Введите имя первого игрока");

        Scanner scanner = new Scanner(System.in);

        player1 = new Player(playerChar1, scanner.nextLine());


        System.out.println("Введите имя второго игрока");
        Scanner scanner2 = new Scanner(System.in);
        player2 = new Player(playerChar2, scanner2.nextLine());

        map = new Map(mapSize, player1, player2);
        game = new Game(map, player1,player2);

        map.printMap();
        game.chooseWhoFirst();
        boolean onGame = true;
        while (onGame){
            if (!game.checkWinner() && !game.checkTie()){
                game.nextStep();
            } else {
                if (game.checkWinner()){
                    System.out.println("Победил " + game.getWinner().getName() + ", который играет за " + game.getWinner().getSymbol());} else {
                    System.out.println("Ничья");
                }
                onGame = false;
            }
        }
    }
}
