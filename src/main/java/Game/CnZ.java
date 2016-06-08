package Game;

import java.io.BufferedReader;

/**
 * Created by Evgeny_Akulenko on 6/8/2016.
 */
public class CnZ implements Game {
    private final static String TYPE_O = "O";
    private final static String TYPE_X = "X";
    private String[][] gameField = {{" ", " ", " "},{" ", " ", " "},{" ", " ", " "}};
    private String winner;

    public void makeTurn(String type, int i, int j) {
        if (!(i >= 0 && i < 3) || !(j >= 0 && j < 3)) {
            throw new IllegalArgumentException();
        }
        /*
        if (!type.equals(TYPE_X)) {
            throw new IllegalArgumentException();
        } else if (!type.equals(TYPE_O)) {
            throw new IllegalArgumentException();
        }*/

        gameField[i][j] = type;

        if (testIsEnd(type, i, j)) {
            setWinner(type);
        }
    }

    public String getWinner() {
        return this.winner;
    }

    private boolean testIsEnd(String lastType, int lastI, int lastJ) {
        if (checkHorizontalLine(lastType, lastI, lastJ)) {
            return true;
        } else if (checkVerticalLine(lastType, lastI, lastJ)) {
            return true;
        } else if (checkDiagonalLine(lastType, lastI, lastJ)) {
            return true;
        }
        return false;
    }

    private void setWinner(String type) {
        this.winner = type;
    }

    //check horizontal line
    private boolean checkHorizontalLine(String lastType, int lastI, int lastJ) {
        int count = 0;
        int i = lastI;
        int j = lastJ;
        while (j < gameField.length) {
            if (lastType.equals(gameField[i][j])) {
                count++;
            }
            j++;
        }
        j = lastJ - 1;
        while (i >= 0 && j >= 0) {
            if (lastType.equals(gameField[i][j])) {
                count++;
            }
            j--;
        }
        if (count == gameField[0].length) {
            System.out.println("Horizontal: " + count);
        }
        return (count == gameField.length);
    }

    //check vertical line
    private boolean checkVerticalLine(String lastType, int lastI, int lastJ) {
        int count = 0;
        int i = lastI;
        int j = lastJ;
        while (j < gameField.length && i < gameField.length) {
            if (lastType.equals(gameField[i][j])) {
                count++;
            }
            i++;
        }
        i = lastI - 1;
        while (j >= 0 && i >= 0) {
            if (lastType.equals(gameField[i][j])) {
                count++;
            }
            i--;
        }
        if (count == gameField.length) {
            System.out.println("Vertical " + count);
        }
        return (count == gameField[0].length);
    }

    //check diagonal line
    private boolean checkDiagonalLine(String lastType, int lastI, int lastJ) {
        int count = 0;
        int i = lastI;
        int j = lastJ;
        while (j < gameField[0].length && i < gameField.length) {
            if (lastType.equals(gameField[i][j])) {
                count++;
            }
            j++;
            i++;
        }
        i = lastI - 1;
        j = lastJ - 1;
        while (j >= 0 && i >= 0) {
            if (lastType.equals(gameField[i][j])) {
                count++;
            }
            j--;
            i--;
        }
        if (count == gameField[0].length) {
            return true;
        }
        count = 0;
        i = lastI;
        j = lastJ;
        while (j < gameField.length && i >= 0) {
            if (lastType.equals(gameField[i][j])) {
                count++;
            }
            j++;
            i--;
        }
        i = lastI + 1;
        j = lastJ - 1;
        while (j >= 0 && i < gameField.length) {
            if (lastType.equals(gameField[i][j])) {
                count++;
            }
            j--;
            i++;
        }
        return (count == gameField[0].length);
    }

    public String showField() {
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0; i < gameField.length; i++) {
            for (int j = 0; j < gameField[i].length; j++) {
                stringBuffer.append(gameField[i][j]);
                stringBuffer.append("|");
            }
            stringBuffer.append("\n");
        }
        return stringBuffer.toString();
    }

    public static void main(String[] args) {
        CnZ cnz = new CnZ();
        cnz.gameField[1][0] = "X";
        cnz.gameField[1][1] = "X";
        cnz.makeTurn("X", 1, 2);
        System.out.println(cnz.showField());
        System.out.println(cnz.winner);
    }
}
