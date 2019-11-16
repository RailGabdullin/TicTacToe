package com.company;

import java.util.Scanner;

public class Game {

    private static Player player1;
    private static Player player2;
    private static Map map;

    private Player winner;
    private Scanner scanner = new Scanner(System.in);

    private static Player currentPlayer;

    public Game() {

        insertPlayerSettings();
        map = new Map(player1, player2);
    }

    private void insertPlayerSettings (){
        System.out.println("Первый игрок - человек или компьютер?");
        System.out.print("1 - человек / 2 - компьютер");
        System.out.println();
        switch (scanner.nextInt()){
            case (1):
                player1 = new Human();
                break;
            case (2):
                System.out.println("Умный компьютер?");
                System.out.print("1 - да / 2 - нет");
                System.out.println();
                switch (scanner.nextInt()){
                    case (1):
                        player1 = new SmartPC();
                        break;
                    case (2):
                        player1 = new Player();
                        break;
                }
                break;
            default:
                player1 = new Player();
                break;
        }
        System.out.println("Второй игрок - человек или компьютер?");
        System.out.print("1 - человек / 2 - компьютер");
        System.out.println();
        switch (scanner.nextInt()){
            case (1):
                player2 = new Human();
                break;
            case (2):
                System.out.println("Умный компьютер?");
                System.out.print("1 - да / 2 - нет");
                System.out.println();
                switch (scanner.nextInt()){
                    case (1):
                        player2 = new SmartPC();
                        break;
                    case (2):
                        player2 = new Player();
                        break;
                }
                break;
            default:
                player2 = new Player();
                break;
        }
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

        printXY(currentPlayer.generateXY());

//        //________________
//
//        //Если игрок человек.
//        //Считываем вводимые X и Y, проверяем корректность данных.
//        if (currentPlayer.isHuman()){
//            System.out.println("Ваш ход: ");
//            do{
//                if (map.isValid(x,y)){
//                    if (!map.isFree(x,y)){
//                        System.out.println("Эта ячейка занята, выберите другую: ");
//                    }
//                } else System.out.println("Такой ячейки не существует, выберите другую: ");
//            } while (!map.isFree(x, y));
//
//            //Если игрок - глупый компьютер компьютер
//        } else if (!currentPlayer.isSmart()){
//
//            //Генерируем ход в случайную ячеку пока не найдем свободную
//            do {
//                x = (int) (Math.random() * map.getSize());
//                y = (int) (Math.random() * map.getSize());
//                System.out.println("Сгенерировали ход автоматически");
//            }
//            while (!map.isFree(x, y));
//        }
//
//        //"Умный" компьютер
//        //Ценность ячейки оценивается как количество линий, которые через нее проходят (вертикали, горизонталь и одна либо две диагонали)
//        // + число "дружественных" символов, уже проставленных в этой линии. Однако, если в линии уже есть хотя бы один вражеский смивол,
//        // то ценность этой линии в ценности ячейки не засчитывается.
//        // Ценность рассчитывается для каждой ячейки и записывается в альтернативную карту ценностей ячеек. Она совпадает по размеру с игровым
//        // полем.
//        // Для хода компьютер выбирает ячейку с наибольшей ценностью.
//
//        if (!currentPlayer.isHuman() && currentPlayer.isSmart()){
//
//            System.out.println("Умный ход");
//
//            int [][] valueMap = new int[map.getSize()][map.getSize()];
//
//            //Первым проходом расставили ценность ячейкам по дефолту, как будто на поле еще не стоит ни одного символа.
//            //Все ячейки получают по 2 балла: по баллу за вертикаль и диагональ. Потом те из них, кто в диагонали получают + один
//            //балл за диагональ. Потом та ячейка, которая находится на пересечении диагоналей получает + еще один балл за дополнительную
//            //диагональ.
//            for (int i = 0; i < map.getSize(); i++) {
//                for (int j = 0; j < map.getSize(); j++) {
//                    valueMap[i][j] = 2;
//                    if (i == j || j == map.getSize() - i - 1){
//                        valueMap [i][j] = 3;
//                    }
//                    if (i == j && i == map.getSize() - i - 1){
//                        valueMap[i][j] = 4;
//                    }
//                }
//            }
//
//            //ОТЛАДКА: ВЫВОД ТАБЛИЦЫ ЦЕННОСТИ
//            //____________
//            System.out.println("Smart step 1");
//            System.out.print("  ");
//            for (int j = 0; j < valueMap.length; j++) {
//                System.out.print(j);
//                System.out.print(" ");
//            }
//            System.out.println();
//            for (int i = 0; i < valueMap.length; i++) {
//                System.out.print(i);
//                for (int j = 0; j < valueMap.length; j++) {
//                    System.out.print(" ");
//                    System.out.print(valueMap[i][j]);
//                }
//                System.out.println();
//            }
//            System.out.println("*******");
//            //___________
//
//            int numberOfFriendlySymbolsHor;
//            boolean isThereEnemySymbolHor;
//            int numberOfFriendlySymbolsVer;
//            boolean isThereEnemySymbolVer;
//            int numberOfFriendlySymbolsDiagonalDirect = 0;
//            boolean isThereEnemySymbolDiagonalDirect = false;
//            int numberOfFriendlySymbolsDiagonalRevers = 0;
//            boolean isThereEnemySymbolDiagonalRevers = false;
//
//            for (int i = 0; i < map.getSize(); i++) {
//
//                numberOfFriendlySymbolsHor = 0;
//                isThereEnemySymbolHor = false;
//                numberOfFriendlySymbolsVer = 0;
//                isThereEnemySymbolVer = false;
//
//                //Считаем число дружественных символов
//                //и проверяем наличие символов противника
//                //Число дружестенных символов в лайне записываем в счетчик umberOfFriendlySymbols,
//                //При обнаружении врага в лайне переключаем флаг isThereEnemySymbol из false в true.
//                //В основном цикле только считаем необходимые переменные для добавочного валью. Прибавим его к ячейкам отдельным
//                //циклом
//                for (int j = 0; j < map.getSize(); j++) {
//
//                    //Считаем вертикали
//                    if (map.getMapXY(i, j) == currentPlayer.getSymbol()) {
//                        numberOfFriendlySymbolsHor++;
//                    }
//                    if (map.getMapXY(i, j) == getEnemy(currentPlayer).getSymbol()) {
//                        isThereEnemySymbolHor = true;
//                    }
//
//                    //Считаем горизонтали
//                    if (map.getMapXY(j, i) == currentPlayer.getSymbol()) {
//                        numberOfFriendlySymbolsVer++;
//                    }
//                    if (map.getMapXY(j, i) == getEnemy(currentPlayer).getSymbol()) {
//                        isThereEnemySymbolVer = true;
//                    }
//
//                }
//
//                //Цикл прибавляет уже итоговую сумму вертикалей и диагоналей ко всем ячекам в лайне либо уменьшаяет
//                // ценность на единицу, если в лайне есть символ противника. Делать это пока линя
//                //до конца не посчитана невозможно. Поэтому сначала заканчиваем предыдущий цикл подсчета добавочного валью, а в этом
//                //цикле прибавляем это валью ко всем ячейкам в этом лайне.
//                for (int j = 0; j < map.getSize(); j++) {
//                    if (!isThereEnemySymbolHor) {
//                        valueMap[i][j] = valueMap[i][j] + numberOfFriendlySymbolsHor;
//                    } else {
//                        valueMap[i][j] = valueMap[i][j] - 1;
//                    }
//
//                    if (!isThereEnemySymbolVer){
//                        valueMap[j][i] = valueMap[j][i] + numberOfFriendlySymbolsVer;
//                    } else {
//                        valueMap[j][i] = valueMap[j][i] - 1;
//                    }
//                }
//
//                //Также проходим прямую диагональ.
//                if(map.getMapXY(i , i) == currentPlayer.getSymbol()){
//                    numberOfFriendlySymbolsDiagonalDirect++;
//                }
//                if(map.getMapXY(i , i) == getEnemy(currentPlayer).getSymbol()){
//                    isThereEnemySymbolDiagonalDirect = true;
//                }
//
//                //Обратная диагональ
//                if(map.getMapXY(i , map.getSize() - 1 - i) == currentPlayer.getSymbol()){
//                    numberOfFriendlySymbolsDiagonalRevers++;
//                }
//                if(map.getMapXY(i , map.getSize() - 1 - i) == getEnemy(currentPlayer).getSymbol()){
//                    isThereEnemySymbolDiagonalRevers = true;
//                }
//
//            }
//
//            //Прибавляем добавочные валью диагоналей имеющимся валью ячеек
//            for (int i = 0; i < map.getSize(); i++) {
//                if (!isThereEnemySymbolDiagonalDirect) {
//                    valueMap[i][i] = valueMap[i][i] + numberOfFriendlySymbolsDiagonalDirect;
//                } else {
//                    valueMap[i][i] = valueMap[i][i] - 1;
//                }
//
//                if (!isThereEnemySymbolDiagonalRevers) {
//                    valueMap[i][map.getSize() - 1 - i] = valueMap[i][map.getSize() - 1 - i] + numberOfFriendlySymbolsDiagonalRevers;
//                } else {
//                    valueMap[i][map.getSize() - 1 - i] = valueMap[i][map.getSize() - 1 - i] - 1;
//                }
//
//            }
//
//            //ОТЛАДКА: ВЫВОД ТАБЛИЦЫ ЦЕННОСТИ
//            //____________
//            System.out.println("Smart step 2");
//            System.out.print("  ");
//            for (int j = 0; j < valueMap.length; j++) {
//                System.out.print(j);
//                System.out.print(" ");
//            }
//            System.out.println();
//            for (int i = 0; i < valueMap.length; i++) {
//                System.out.print(i);
//                for (int j = 0; j < valueMap.length; j++) {
//                    System.out.print(" ");
//                    System.out.print(valueMap[i][j]);
//                }
//                System.out.println();
//            }
//            System.out.println("*******");
//            //___________
//
//            //Еще раз пройдемся по игровому полю, чтобы обнулить в карте ценностей уже занятые ячейки, так как туда
//            //сходит все равно не получится
//            for (int i = 0; i < map.getSize(); i++) {
//                for (int j = 0; j < map.getSize(); j++) {
//                    if (map.getMapXY(i , j) != map.getNoSymbol()){
//                        valueMap[i][j] = 0;
//                    }
//                }
//            }
//
//            //ОТЛАДКА: ВЫВОД ТАБЛИЦЫ ЦЕННОСТИ
//            //____________
//            System.out.println("Smart step 3");
//            System.out.print("  ");
//            for (int j = 0; j < valueMap.length; j++) {
//                System.out.print(j);
//                System.out.print(" ");
//            }
//            System.out.println();
//            for (int i = 0; i < valueMap.length; i++) {
//                System.out.print(i);
//                for (int j = 0; j < valueMap.length; j++) {
//                    System.out.print(" ");
//                    System.out.print(valueMap[i][j]);
//                }
//                System.out.println();
//            }
//            System.out.println("*******");
//            //___________
//
//            //Теперь пройдемся по valueMap и найдем ячейку с максимальным значением ценности
//            int maxValue = 0;
//            int xMaxValue = 0;
//            int yMaxValue = 0;
//            for (int i = 0; i < valueMap.length; i++) {
//                for (int j = 0; j < valueMap.length; j++) {
//                    if (maxValue < valueMap[i][j]){
//                        maxValue = valueMap[i][j];
//                        xMaxValue = i;
//                        yMaxValue = j;
//                    }
//                }
//            }
//
//            //И осталось найденное значение подставить в x и y, которые потом пропечатаются
//            x = xMaxValue;
//            y = yMaxValue;
//
//
//        }
//
//        //Передаем координаты ячейки в которую хотим сходить и печатаем новую карту
//        printXY(x,y);
//
//        //______________

        //Передаем ход другому игроку
        switchCurrentPlayer();
    }

    public boolean checkWinner(){


        //В цикле считаем число свопадающих символов отдельно в вертикалях, горизонталях и двух диагоналях.
        //Если где-то символов становится столько же сколько размер карты, значит строка полная - выигрыш. Переключаем флаг наличия
        //победителя в true и возвращаем в качестве результата функции. Также меняем значение переменной winner на владельца
        //победных символов
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
                }
            }


        }

        return result;
    }

    //Проверяем наличие нечьи. Если есть свободные ячейки - значит не ничья

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

    //Передает ход следующему игроку. Если текущий игрой - игрок1, то текущий игрок - игрок2. Иначе текщий игрок - игрок1

    private void switchCurrentPlayer(){

        currentPlayer = (currentPlayer == player1) ? player2 : player1;
    }
    //Узнаем кто соперник

    public static Player getEnemy( Player player){
        return (player == player1) ? player2 : player1;
    }
    public void printXY (Result result){
        map.put(currentPlayer, result.getX(), result.getY());
        map.printMap();
    }

    public static Map getMap() {
        return map;
    }

    public static Player getCurrentPlayer() {
        return currentPlayer;
    }
}
