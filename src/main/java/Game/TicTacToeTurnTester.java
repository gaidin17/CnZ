package Game;

/**
 * Created by Evgeny_Akulenko on 6/9/2016.
 */
public class TicTacToeTurnTester {

    String[][] gameField;
    TicTacToeTurnTester(String[][] gamefield){
        this.gameField = gamefield;
    }

    protected boolean testIsWinner(String lastType, int lastI, int lastJ) {
        if (checkHorizontalLine(lastType, lastI, lastJ)) {
            return true;
        } else if (checkVerticalLine(lastType, lastI, lastJ)) {
            return true;
        } else if (checkDiagonalLine(lastType, lastI, lastJ)) {
            return true;
        }
        return false;
    }

    protected boolean testIsEnd() {
        int countEmpty = 0;
        for (int i =0; i < gameField.length; i++) {
            for (int j = 0; j< gameField[i].length; j++) {
                if (gameField[i][j].equals(" ")) {
                    countEmpty++;
                }
            }
        }
        if (countEmpty == 0) {
            return true;
        }
        return false;

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
}
