package Server;

import Game.Game;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.concurrent.SynchronousQueue;

/**
 * Created by Evgeny_Akulenko on 6/8/2016.
 */
public class ServerThreadForFirstClient extends Thread {
    private Socket socket;
    private SynchronousQueue<Game> queue;
    private Game game;

    public ServerThreadForFirstClient(Socket socket, SynchronousQueue<Game> queue, Game game) {
        this.game = game;
        this.queue = queue;
        this.socket = socket;
    }

    public void run() {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter writer = new PrintWriter(socket.getOutputStream(), true)) {
            int i = 0;
            while (true) {
                System.out.println("first client step N:" + i);
                if (i != 0) {
                    try {
                        game = queue.take();
                    } catch (InterruptedException ex) {

                    }
                }
                if (game.getWinner() != null) {
                    writer.println("Oops...you lose!");
                    writer.println(game.showField());
                    writer.println("gameover");
                    writer.println("end");
                    break;
                } else {
                    writer.println(game.showField());
                    writer.println("end");
                }

                System.out.println("Waiting for input FirstClient turn");
                String stringTurn = reader.readLine();
                String[] turn = stringTurn.split(",");
                game.makeTurn("X", Integer.parseInt(turn[0]), Integer.parseInt(turn[1]));
                try {
                    writer.println(game.showField());
                    queue.put(game);
                    if (game.getWinner() != null) {
                        writer.println("Great!!! You WIN!!!");
                        writer.println("gameover");
                        writer.println("end");
                        break;
                    } else {
                        writer.println("Awaiting...");
                        writer.println("end");
                    }

                } catch (InterruptedException ex) {
                    break;
                }
                i++;


            }
            socket.close();
        } catch (IOException ex) {

        } finally {
            try {
                socket.close();
            } catch (IOException ex) {

            }

        }
    }
}
