package Game;

import java.net.Socket;

/**
 * Created by Evgeny_Akulenko on 6/9/2016.
 */
public interface GameServer {
    void setSocket(Socket socket);
    void startGame();
}
