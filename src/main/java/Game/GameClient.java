package Game;

import java.net.Socket;

/**
 * Created by Evgeny_Akulenko on 6/9/2016.
 */
public interface GameClient {
    void startGame(Socket socket);
}
