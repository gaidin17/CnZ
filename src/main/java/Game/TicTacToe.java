package Game;


import ServerClientConstants.PlayersType;

/**
 * Created by Evgeny_Akulenko on 6/8/2016.
 */
public class TicTacToe implements Game {
    private String[][] gameField = {{" ", " ", " "}, {" ", " ", " "}, {" ", " ", " "}};
    private PlayersType winner;
    private boolean isNoWinner;
    private TicTacToeTurnTester ticTacToeTurnTester = new TicTacToeTurnTester(gameField);

    public void makeTurn(PlayersType playersType, int i, int j) {
        String type = playersType.toString();
        gameField[i][j] = type;
        if (ticTacToeTurnTester.testIsWinner(playersType, i, j)) {
            setWinner(playersType);
        }
        if (ticTacToeTurnTester.testIsEnd()) {
            isNoWinner = true;
        }
    }

    public PlayersType getWinner() {
        return winner;
    }

    private void setWinner(PlayersType type) {
        this.winner = type;
    }

    public boolean isNoWinner() {
        return isNoWinner;
    }

    public boolean isTurnAvailable(int i, int j) {
        if (!(i >= 0 && i < gameField.length) || !(j >= 0 && j < gameField[0].length)) {
            return false;
        }
        return gameField[i][j].equals(" ");
    }

    public String showField() {
        StringBuilder stringBuffer = new StringBuilder();
        for (String[] aGameField : gameField) {
            for (String anAGameField : aGameField) {
                stringBuffer.append(anAGameField);
                stringBuffer.append("|");
            }
            stringBuffer.append("\n");
        }
        return stringBuffer.toString();
    }
}
