package Game;

import ServerClientConstants.PlayersType;

/**
 * Created by Evgeny_Akulenko on 6/8/2016.
 */
public interface Game {
    void makeTurn(PlayersType type, int i, int j);

    String showField();

    PlayersType getWinner();

    boolean isTurnAvailable(int i, int j);

    boolean isNoWinner();
}
