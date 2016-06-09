package Server;


import Game.GameServer;

import java.net.Socket;


/**
 * Created by Evgeny_Akulenko on 6/8/2016.
 */
public class ServerThread extends Thread {

    private Socket socket;
    private GameServer gameServer;


    public ServerThread(Socket socket, GameServer gameServer) {
        this.socket = socket;
        this.gameServer = gameServer;
    }

    public void run() {
        gameServer.setSocket(socket);
        gameServer.startGame();
    }
}
