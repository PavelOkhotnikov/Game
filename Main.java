package com.company;

import java.util.Scanner;

public class Main {

    private static final int fieldSize = 3; //размер поля (чем выше значение, тем сложнее)
    private static String[] field = new String[fieldSize * fieldSize];
    private static int PlayerNum = 0; // текущий игрок
    public static void main(String[] args) throws Exception {
        int iTmp = 0;
        Scanner sc = new Scanner(System.in);
        for (int i = 0; i < fieldSize * fieldSize; i++)
            field[i] = Integer.toString(++iTmp); //инициализируем поле
        while (!isGameEnd()) {
            nextPlayer();
            while (true) {
                System.out.println("\nХод игрока " + PlayerNum);
                showField();
                System.out.print("Наберите число, куда вы хотите вставить " + (1 == PlayerNum ? "крестик" : "нолик") + ": ");
                if (sc.hasNextInt()) { // проверяем, есть ли в потоке целое число
                    iTmp = sc.nextInt() - 1; // считывает целое число с потока ввода и сохраняем в переменную
                    if (isValidInput(iTmp))
                        break;
                }
                System.out.println("Вы ввели неправильное число. Повторите ввод");
                sc.next();
            }
            try {
                putX(iTmp); // Вставляем на поле крестик или нолик
            } catch (Exception e) {
                System.out.println("Что-то пошло не так ;(");
            }
        }
        showField();
    }
    private static boolean isValidInput(int iIn) {
        if (iIn >= fieldSize * fieldSize) return false;
        if (iIn < 0) return false;
        switch (getX(iIn)) {
            case 'O':
            case 'X':
                return false;
        }
        return true; //Введенное число должно быть по размеру поля и поле должно быть в этом месте еще не заполнено
    }
    private static void nextPlayer() {
        PlayerNum = (byte) (1 == PlayerNum ? 2 : 1); //задаём номер следующего игрока
    }
    private static boolean isGameEnd() {
        int i, j;
        boolean bRowWin = false, bColWin = false;

        // Проверка победы на строках и столбиках
        for (i = 0; i < fieldSize; i++) {
            bRowWin = true; //строки
            bColWin = true; //столбики
            for (j = 0; j < fieldSize-1; j++) {
                bRowWin &= (getXY(i,j).charAt(0) == getXY(i,j+1).charAt(0));
                bColWin &= (getXY(j,i).charAt(0) == getXY(j+1,i).charAt(0));
            }
            if (bColWin || bRowWin) {
                System.out.println("Победил игрок " + PlayerNum);
                return true;
            }
        }
        // Проверка победы по диагоналям
        bRowWin = true;
        bColWin = true;
        for (i=0; i<fieldSize-1; i++) {
            bRowWin &= (getXY(i,i).charAt(0) == getXY(i+1,i+1).charAt(0));
            bColWin &= (getXY(i, fieldSize-i-1).charAt(0) == getXY(i+1, fieldSize-i-2).charAt(0));
        }
        if (bColWin || bRowWin) {
            System.out.println("Победил игрок " + PlayerNum);
            return true;
        }
        for (i = 0; i < fieldSize * fieldSize; i++) {
            switch (getX(i)) {
                case 'O':
                case 'X':
                    break;
                default:
                    return false;
            }
        }
        if (fieldSize*fieldSize <= i) {
            System.out.println("Ничья. Кончились ходы.");
            return true;
        }
        return false; // Продолжаем игру
    }
    private static String getXY(int x, int y) {
        return field[x * fieldSize + y]; //значение координаты на полн
    }
    private static char getX(int x) {
        return field[x].charAt(0); // тоже значение координаты на поле
    }
    private static void putX(int x) {
        field[x] = 1 == PlayerNum ? "X" : "O"; //вставляем крестик или нолик
    }
    private static void showField() {
        for (int i = 0; i < fieldSize; i++) {
            for (int j = 0; j < fieldSize; j++) {
                System.out.printf("%4s", getXY(i, j));
            }
            System.out.print("\n");
        }
    }
}
