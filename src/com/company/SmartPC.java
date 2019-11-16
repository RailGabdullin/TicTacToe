package com.company;

public class SmartPC extends Player {

    public SmartPC() {
    }

    @Override
    public Result generateXY() {
        int x, y;
        //"Умный" компьютер
        //Ценность ячейки оценивается как количество линий, которые через нее проходят (вертикали, горизонталь и одна либо две диагонали)
        // + число "дружественных" символов, уже проставленных в этой линии. Однако, если в линии уже есть хотя бы один вражеский смивол,
        // то ценность этой линии в ценности ячейки не засчитывается.
        // Ценность рассчитывается для каждой ячейки и записывается в альтернативную карту ценностей ячеек. Она совпадает по размеру с игровым
        // полем.
        // Для хода компьютер выбирает ячейку с наибольшей ценностью.

        System.out.println("Умный ход");

        int[][] valueMap = new int[Game.getMap().getSize()][Game.getMap().getSize()];

        //Первым проходом расставили ценность ячейкам по дефолту, как будто на поле еще не стоит ни одного символа.
        //Все ячейки получают по 2 балла: по баллу за вертикаль и диагональ. Потом те из них, кто в диагонали получают + один
        //балл за диагональ. Потом та ячейка, которая находится на пересечении диагоналей получает + еще один балл за дополнительную
        //диагональ.
        for (int i = 0; i < Game.getMap().getSize(); i++) {
            for (int j = 0; j < Game.getMap().getSize(); j++) {
                valueMap[i][j] = 2;
                if (i == j || j == Game.getMap().getSize() - i - 1) {
                    valueMap[i][j] = 3;
                }
                if (i == j && i == Game.getMap().getSize() - i - 1) {
                    valueMap[i][j] = 4;
                }
            }
        }

        //ОТЛАДКА: ВЫВОД ТАБЛИЦЫ ЦЕННОСТИ
        //____________
        System.out.println("Smart step 1");
        System.out.print("  ");
        for (int j = 0; j < valueMap.length; j++) {
            System.out.print(j);
            System.out.print(" ");
        }
        System.out.println();
        for (int i = 0; i < valueMap.length; i++) {
            System.out.print(i);
            for (int j = 0; j < valueMap.length; j++) {
                System.out.print(" ");
                System.out.print(valueMap[i][j]);
            }
            System.out.println();
        }
        System.out.println("*******");
        //___________

        int numberOfFriendlySymbolsHor;
        boolean isThereEnemySymbolHor;
        int numberOfFriendlySymbolsVer;
        boolean isThereEnemySymbolVer;
        int numberOfFriendlySymbolsDiagonalDirect = 0;
        boolean isThereEnemySymbolDiagonalDirect = false;
        int numberOfFriendlySymbolsDiagonalRevers = 0;
        boolean isThereEnemySymbolDiagonalRevers = false;

        for (int i = 0; i < Game.getMap().getSize(); i++) {

            numberOfFriendlySymbolsHor = 0;
            isThereEnemySymbolHor = false;
            numberOfFriendlySymbolsVer = 0;
            isThereEnemySymbolVer = false;

            //Считаем число дружественных символов
            //и проверяем наличие символов противника
            //Число дружестенных символов в лайне записываем в счетчик umberOfFriendlySymbols,
            //При обнаружении врага в лайне переключаем флаг isThereEnemySymbol из false в true.
            //В основном цикле только считаем необходимые переменные для добавочного валью. Прибавим его к ячейкам отдельным
            //циклом
            for (int j = 0; j < Game.getMap().getSize(); j++) {

                //Считаем вертикали
                if (Game.getMap().getMapXY(i, j) == Game.getCurrentPlayer().getSymbol()) {
                    numberOfFriendlySymbolsHor++;
                }
                if (Game.getMap().getMapXY(i, j) == Game.getEnemy(Game.getCurrentPlayer()).getSymbol()) {
                    isThereEnemySymbolHor = true;
                }

                //Считаем горизонтали
                if (Game.getMap().getMapXY(j, i) == Game.getCurrentPlayer().getSymbol()) {
                    numberOfFriendlySymbolsVer++;
                }
                if (Game.getMap().getMapXY(j, i) == Game.getEnemy(Game.getCurrentPlayer()).getSymbol()) {
                    isThereEnemySymbolVer = true;
                }

            }

            //Цикл прибавляет уже итоговую сумму вертикалей и диагоналей ко всем ячекам в лайне либо уменьшаяет
            // ценность на единицу, если в лайне есть символ противника. Делать это пока линя
            //до конца не посчитана невозможно. Поэтому сначала заканчиваем предыдущий цикл подсчета добавочного валью, а в этом
            //цикле прибавляем это валью ко всем ячейкам в этом лайне.
            for (int j = 0; j < Game.getMap().getSize(); j++) {
                if (!isThereEnemySymbolHor) {
                    valueMap[i][j] = valueMap[i][j] + numberOfFriendlySymbolsHor;
                } else {
                    valueMap[i][j] = valueMap[i][j] - 1;
                }

                if (!isThereEnemySymbolVer) {
                    valueMap[j][i] = valueMap[j][i] + numberOfFriendlySymbolsVer;
                } else {
                    valueMap[j][i] = valueMap[j][i] - 1;
                }
            }

            //Также проходим прямую диагональ.
            if (Game.getMap().getMapXY(i, i) == Game.getCurrentPlayer().getSymbol()) {
                numberOfFriendlySymbolsDiagonalDirect++;
            }
            if (Game.getMap().getMapXY(i, i) == Game.getEnemy(Game.getCurrentPlayer()).getSymbol()) {
                isThereEnemySymbolDiagonalDirect = true;
            }

            //Обратная диагональ
            if (Game.getMap().getMapXY(i, Game.getMap().getSize() - 1 - i) == Game.getCurrentPlayer().getSymbol()) {
                numberOfFriendlySymbolsDiagonalRevers++;
            }
            if (Game.getMap().getMapXY(i, Game.getMap().getSize() - 1 - i) == Game.getEnemy(Game.getCurrentPlayer()).getSymbol()) {
                isThereEnemySymbolDiagonalRevers = true;
            }

        }

        //Прибавляем добавочные валью диагоналей имеющимся валью ячеек
        for (int i = 0; i < Game.getMap().getSize(); i++) {
            if (!isThereEnemySymbolDiagonalDirect) {
                valueMap[i][i] = valueMap[i][i] + numberOfFriendlySymbolsDiagonalDirect;
            } else {
                valueMap[i][i] = valueMap[i][i] - 1;
            }

            if (!isThereEnemySymbolDiagonalRevers) {
                valueMap[i][Game.getMap().getSize() - 1 - i] = valueMap[i][Game.getMap().getSize() - 1 - i] + numberOfFriendlySymbolsDiagonalRevers;
            } else {
                valueMap[i][Game.getMap().getSize() - 1 - i] = valueMap[i][Game.getMap().getSize() - 1 - i] - 1;
            }

        }

        //ОТЛАДКА: ВЫВОД ТАБЛИЦЫ ЦЕННОСТИ
        //____________
        System.out.println("Smart step 2");
        System.out.print("  ");
        for (int j = 0; j < valueMap.length; j++) {
            System.out.print(j);
            System.out.print(" ");
        }
        System.out.println();
        for (int i = 0; i < valueMap.length; i++) {
            System.out.print(i);
            for (int j = 0; j < valueMap.length; j++) {
                System.out.print(" ");
                System.out.print(valueMap[i][j]);
            }
            System.out.println();
        }
        System.out.println("*******");
        //___________

        //Еще раз пройдемся по игровому полю, чтобы обнулить в карте ценностей уже занятые ячейки, так как туда
        //сходит все равно не получится
        for (int i = 0; i < Game.getMap().getSize(); i++) {
            for (int j = 0; j < Game.getMap().getSize(); j++) {
                if (Game.getMap().getMapXY(i, j) != Game.getMap().getNoSymbol()) {
                    valueMap[i][j] = 0;
                }
            }
        }

        //ОТЛАДКА: ВЫВОД ТАБЛИЦЫ ЦЕННОСТИ
        //____________
        System.out.println("Smart step 3");
        System.out.print("  ");
        for (int j = 0; j < valueMap.length; j++) {
            System.out.print(j);
            System.out.print(" ");
        }
        System.out.println();
        for (int i = 0; i < valueMap.length; i++) {
            System.out.print(i);
            for (int j = 0; j < valueMap.length; j++) {
                System.out.print(" ");
                System.out.print(valueMap[i][j]);
            }
            System.out.println();
        }
        System.out.println("*******");
        //___________

        //Теперь пройдемся по valueMap и найдем ячейку с максимальным значением ценности
        int maxValue = 0;
        int xMaxValue = 0;
        int yMaxValue = 0;
        for (int i = 0; i < valueMap.length; i++) {
            for (int j = 0; j < valueMap.length; j++) {
                if (maxValue < valueMap[i][j]) {
                    maxValue = valueMap[i][j];
                    xMaxValue = i;
                    yMaxValue = j;
                }
            }
        }

        //И осталось найденное значение подставить в x и y, которые потом пропечатаются
        x = xMaxValue;
        y = yMaxValue;

        return new Result(x,y);
    }
}
