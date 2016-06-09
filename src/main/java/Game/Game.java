package Game;

/**
 * Created by Evgeny_Akulenko on 6/8/2016.
 */
public interface Game  {
    void makeTurn(String type, int i, int j);
    String showField();
    String getWinner();
    boolean isTurnAvailable(int i, int j);
    boolean isNoWinner();
}
