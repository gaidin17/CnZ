package Game;

import ServerClientConstants.PlayersType;

/**
 * Created by Evgeny_Akulenko on 6/9/2016.
 */
class TicTacToeTurnTester {

    private String[][] gameField;
    TicTacToeTurnTester(String[][] gamefield){
        this.gameField = gamefield;
    }

    boolean testIsWinner(PlayersType lastType, int lastI, int lastJ) {
        if (checkHorizontalLine(lastType, lastI, lastJ)) {
            return true;
        } else if (checkVerticalLine(lastType, lastI, lastJ)) {
            return true;
        } else if (checkDiagonalLine(lastType, lastI, lastJ)) {
            return true;
        }
        return false;
    }

    boolean testIsEnd() {
        int countEmpty = 0;
        for (String[] aGameField : gameField) {
            for (String anAGameField : aGameField) {
                if (anAGameField.equals(" ")) {
                    countEmpty++;
                }
            }
        }
        return countEmpty == 0;

    }

    private boolean checkHorizontalLine(PlayersType lastType, int lastI, int lastJ) {
        int count = 0;
        int j = lastJ;
        while (j < gameField.length) {
            if (lastType.toString().equals(gameField[lastI][j])) {
                count++;
            }
            j++;
        }
        j = lastJ - 1;
        while (lastI >= 0 && j >= 0) {
            if (lastType.toString().equals(gameField[lastI][j])) {
                count++;
            }
            j--;
        }
        return (count == gameField.length);
    }

    private boolean checkVerticalLine(PlayersType lastType, int lastI, int lastJ) {
        int count = 0;
        int i = lastI;
        while (lastJ < gameField.length && i < gameField.length) {
            if (lastType.toString().equals(gameField[i][lastJ])) {
                count++;
            }
            i++;
        }
        i = lastI - 1;
        while (lastJ >= 0 && i >= 0) {
            if (lastType.toString().equals(gameField[i][lastJ])) {
                count++;
            }
            i--;
        }
        if (count == gameField.length) {
            System.out.println("Vertical " + count);
        }
        return (count == gameField[0].length);
    }

    private boolean checkDiagonalLine(PlayersType lastType, int lastI, int lastJ) {
        int count = 0;
        int i = lastI;
        int j = lastJ;
        while (j < gameField[0].length && i < gameField.length) {
            if (lastType.toString().equals(gameField[i][j])) {
                count++;
            }
            j++;
            i++;
        }
        i = lastI - 1;
        j = lastJ - 1;
        while (j >= 0 && i >= 0) {
            if (lastType.toString().equals(gameField[i][j])) {
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
            if (lastType.toString().equals(gameField[i][j])) {
                count++;
            }
            j++;
            i--;
        }
        i = lastI + 1;
        j = lastJ - 1;
        while (j >= 0 && i < gameField.length) {
            if (lastType.toString().equals(gameField[i][j])) {
                count++;
            }
            j--;
            i++;
        }
        return (count == gameField[0].length);
    }
}
