package com.company;

import java.util.Scanner;


public class Main {

    private static char playerChar1 = 'X';
    private static char playerChar2 = '0';
    private static int mapSize = 4;
    private static Player player1;
    private static Player player2;

    private static Game game;
    public static void main(String[] args) {


        game = new Game();

        game.getMap().printMap();
        game.chooseWhoFirst();
        while (true){
            if (!game.checkWinner() && !game.checkTie()){
                game.nextStep();
            } else {
                if (game.checkWinner()){
                    System.out.println("Победил " + game.getWinner().getName() + ", который играет за " + game.getWinner().getSymbol());} else {
                    System.out.println("Ничья");
                }
                break;
            }
        }

    }
}
