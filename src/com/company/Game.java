package com.company;

import java.util.Scanner;

public class Game {

    private Player player1;
    private Player player2;
    private Map map;

    private Player winner;
    private Scanner scanner = new Scanner(System.in);

    private Player currentPlayer;

    Game(Map map, Player player1, Player player2) {
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
        int x = 0;
        int y = 0;

        //Если игрок человек.
        //Считываем вводимые X и Y, проверяем корректность данных.
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

            //Если игрок - глупый компьютер компьютер
        } else if (!currentPlayer.isSmart()){

            //Генерируем ход в случайную ячеку пока не найдем свободную
            do {
                x = (int) (Math.random() * map.getSize());
                y = (int) (Math.random() * map.getSize());
                System.out.println("Сгенерировали ход автоматически");
            }
            while (!map.isFree(x, y));
        }

        //"Умный" компьютер
        //Ценность ячейки оценивается как количество линий, которые через нее проходят (вертикали, горизонталь и одна либо две диагонали)
        // + число "дружественных" символов, уже проставленных в этой линии. Однако, если в линии уже есть хотя бы один вражеский смивол,
        // то ценность этой линии в ценности ячейки не засчитывается.
        // Ценность рассчитывается для каждой ячейки и записывается в альтернативную карту ценностей ячеек. Она совпадает по размеру с игровым
        // полем.
        // Для хода компьютер выбирает ячейку с наибольшей ценностью.

        if (!currentPlayer.isHuman() && currentPlayer.isSmart()){

            System.out.println("Умный ход");

            int [][] valueMap = new int[map.getSize()][map.getSize()];

            //Первым проходом расставили ценность ячейкам по дефолту, как будто на поле еще не стоит ни одного символа.
            //Все ячейки получают по 2 балла: по баллу за вертикаль и диагональ. Потом те из них, кто в диагонали получают + один
            //балл за диагональ. Потом та ячейка, которая находится на пересечении диагоналей получает + еще один балл за дополнительную
            //диагональ.
            for (int i = 0; i < map.getSize(); i++) {
                for (int j = 0; j < map.getSize(); j++) {
                    valueMap[i][j] = 2;
                    if (i == j || j == map.getSize() - i - 1){
                        valueMap [i][j] = 3;
                    }
                    if (i == j && i == map.getSize() - i - 1){
                        valueMap[i][j] = 4;
                    }
                }
            }

            int numberOfFriendlySymbolsHor;
            int isThereEnemySymbolHor;
            int numberOfFriendlySymbolsVer;
            int isThereEnemySymbolVer;
            int numberOfFriendlySymbolsDiagonalDirect = 0;
            int isThereEnemySymbolDiagonalDirect = 1;
            int numberOfFriendlySymbolsDiagonalReves = 0;
            int isThereEnemySymbolDiagonalRever = 1;

            for (int i = 0; i < map.getSize(); i++) {

                //Считаем число дружественных символов
                //и проверяем наличие символов противника
                //Число дружестенных символов в лайне записываем в счетчик umberOfFriendlySymbols,
                //При обнаружении врага в лайне переключаем флаг isThereEnemySymbol из 1 в 0.

                numberOfFriendlySymbolsHor = 0;
                isThereEnemySymbolHor = 1;

                numberOfFriendlySymbolsVer = 0;
                isThereEnemySymbolVer = 1;

                for (int j = 0; j < map.getSize(); j++) {

                    //Считаем вертикали
                    if(map.getMapXY(i,j) == currentPlayer.getSymbol()){
                        numberOfFriendlySymbolsHor++;
                    }
                    if(map.getMapXY(i,j) == getEnemy(currentPlayer).getSymbol()){
                        isThereEnemySymbolHor = 0;
                    }

                    //Считаем горизонтали
                    if(map.getMapXY(j , i) == currentPlayer.getSymbol()){
                        numberOfFriendlySymbolsVer++;
                    }
                    if(map.getMapXY(j , i) == getEnemy(currentPlayer).getSymbol()){
                        isThereEnemySymbolVer = 0;
                    }


                    //Прибавляем результат к дефолтному валью в ячейках valueMap. Если враг обнаружен в лайне, то переключаем флаг isThereEnemySymbol из 1 в 0
                    //Умножаем получиное число дружественных символов numberOfFriendlySymbols на флаг. Таким образом, если враг обнаружен,
                    //то их произведение будет давать ноль и ничего не прибавится. Если врага не будет, то произведение
                    //счетчика с единицей никак не изменится и просто прибавится к валью.
                    //Таким образом избежали здесь еще одного if-а.

                    //Пришлось сделать здесь еще один цикл, так как нам нужно прибавлять уже итоговую сумму ко всем ячекам в лайне. Делать это пока линя
                    //до конца не посчитана невозможно. Поэтому сначала заканчиваем цикл подсчета добавочного валью, а в другом
                    //цикле прибавляем это валью ко всем ячейкам в этом лайне.
                }
                for (int j = 0; j < map.getSize(); j++) {
                    valueMap[i][j] = valueMap[i][j] + (numberOfFriendlySymbolsHor * isThereEnemySymbolHor);
                    valueMap[j][i] = valueMap[j][i] + (numberOfFriendlySymbolsVer * isThereEnemySymbolVer);


                }

                //Также проходим прямую диагональ.
                //В основном цикле только считаем необходимые переменные для добавочного валью. Прибавим его к ячейкам отдельным
                //циклом
                if(map.getMapXY(i , i) == currentPlayer.getSymbol()){
                    numberOfFriendlySymbolsDiagonalDirect++;
                }
                if(map.getMapXY(i , i) == getEnemy(currentPlayer).getSymbol()){
                    isThereEnemySymbolDiagonalDirect = 0;
                }

                //Обратная диагональ
                if(map.getMapXY(i , map.getSize() - 1 - i) == currentPlayer.getSymbol()){
                    numberOfFriendlySymbolsDiagonalReves++;
                }
                if(map.getMapXY(i , map.getSize() - 1 - i) == getEnemy(currentPlayer).getSymbol()){
                    isThereEnemySymbolDiagonalRever = 0;
                }

            }

            //Прибавляем добавочные валью диагоналей имеющимся валью ячеек
            for (int i = 0; i < map.getSize(); i++) {
                valueMap[i][i] = valueMap[i][i] + (numberOfFriendlySymbolsDiagonalDirect * isThereEnemySymbolDiagonalDirect);
                valueMap[i][map.getSize() - 1 - i] = valueMap[i][map.getSize() - 1 - i] + (numberOfFriendlySymbolsDiagonalReves * isThereEnemySymbolDiagonalRever);
            }

            //Еще раз пройдемся по игровому полю, чтобы обнулить в карте ценностей уже заполненные ячейки, так как туда
            //сходит все равно не получится
            for (int i = 0; i < map.getSize(); i++) {
                for (int j = 0; j < map.getSize(); j++) {
                    if (map.getMapXY(i , j) != map.getNoSymbol()){
                        valueMap[i][j] = 0;
                    }
                }
            }

            //Теперь пройдемся по valueMap и найдем ячейку с максимальным значением ценности
            int maxValue = 0;
            int xMaxValue = 0;
            int yMaxValue = 0;
            for (int i = 0; i < valueMap.length; i++) {
                for (int j = 0; j < valueMap.length; j++) {
                    if (maxValue < valueMap[i][j]){
                        maxValue = valueMap[i][j];
                        xMaxValue = i;
                        yMaxValue = j;
                    }
                }
            }

            //И осталось найденное значение подставить в x и y, которые потом пропечатаются
            x = xMaxValue;
            y = yMaxValue;

        }

        //Передаем координаты ячейки в которую хотим сходить и печатаем новую карту
        printXY(x,y);

        //Передаем ход другому игроку
        switchCurrentPlayer();
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
    public Player getEnemy( Player player){
        return (player == player1) ? player2 : player1;
    }

    public void printXY (int x, int y){
        map.put(currentPlayer, x, y);
        map.printMap();
    }


}
