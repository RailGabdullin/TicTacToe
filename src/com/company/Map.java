package com.company;

public class Map {

    private int mapSize;

    private char [][] map;

    private char player1;
    private char player2;

    private char noSymbol;

    public Map(int mapSize, Player player1, Player player2){
        this.mapSize = mapSize;
        this.player1 = player1.getSymbol();
        this.player2 = player2.getSymbol();
        this.noSymbol = '*';
        map = new char[mapSize][mapSize];

        for (int i = 0; i < mapSize; i++) {
            for (int j = 0; j < mapSize; j++) {
                map[i][j] = noSymbol;
            }

        }
    }
    public void printMap(){
        System.out.print("  ");
        for (int j = 0; j < mapSize; j++) {
            System.out.print(j);
            System.out.print(" ");
        }
        System.out.println();
        for (int i = 0; i < mapSize; i++) {
            System.out.print(i);
            for (int j = 0; j < mapSize; j++) {
                System.out.print(" ");
                System.out.print(map[i][j]);
            }
            System.out.println();
        }
    }

    public boolean isFree (int x, int y) {
        return isValid(x, y) && map[x][y] == noSymbol;
    }

    public int getSize() {
        return mapSize;
    }

    public void put (Player player, int x , int y){
        map[x][y] = player.getSymbol();
    }

    public char getMapXY(int x, int y) {
        return map[x][y];
    }

    public char getNoSymbol() {
        return noSymbol;
    }

    public boolean isValid(int x, int y){
        return x >= 0 && x <= mapSize - 1 && y >= 0 && y <= mapSize - 1;
    }


}
