package Game;


/**
 * Created by Evgeny_Akulenko on 6/8/2016.
 */
public class TicTacToe implements Game {
    private String[][] gameField = {{" ", " ", " "}, {" ", " ", " "}, {" ", " ", " "}};
    private String winner;
    private boolean isNoWinner;
    private TicTacToeTurnTester ticTacToeTurnTester = new TicTacToeTurnTester(gameField);

    public void makeTurn(String type, int i, int j) {
        gameField[i][j] = type;
        if (ticTacToeTurnTester.testIsWinner(type, i, j)) {
            setWinner(type);
        }
        if (ticTacToeTurnTester.testIsEnd()){
            isNoWinner = true;
        }
    }

    public String getWinner() {
        return this.winner;
    }

    private void setWinner(String type) {
        this.winner = type;
    }

    public boolean isNoWinner(){
        return isNoWinner;
    }

    public boolean isTurnAvailable(int i, int j) {
        if (!(i >= 0 && i < gameField.length) || !(j >= 0 && j < gameField[0].length)) {
            return false;
        }
        if (!gameField[i][j].equals(" ")) {
            return false;
        }
        return true;
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
}
