package Game;

import ServerClientConstants.ResponseEnd;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Created by Evgeny_Akulenko on 6/9/2016.
 */
public class TicTacToeGameClient implements GameClient {
    private final Logger logger = LoggerFactory.getLogger(TicTacToeGameClient.class);

    public void startGame(Socket socket) {
        try (PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader readerIn = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
            String response;
            boolean isExit = false;
            while (true) {
                label:
                while (true) {
                    response = readerIn.readLine();
                    if (response != null) {
                        switch (response) {
                            case ResponseEnd.GAME_FIELD:
                            case ResponseEnd.MESSAGE_END:
                                break label;
                            case ResponseEnd.NEXT_TURN:
                            case ResponseEnd.INPUT_ERROR:
                                System.in.read(new byte[System.in.available()]);
                                String string = reader.readLine();
                                writer.println(string);
                                if (string.equals(ResponseEnd.CLIENT_STOP)) {
                                    isExit = true;
                                }
                                break label;
                            case ResponseEnd.GAME_OVER:
                                isExit = true;
                                break label;
                        }
                        System.out.println(response);
                    }
                }
                if (isExit) {
                    break;
                }
            }
        } catch (IOException ex) {
            logger.error("Error: ", ex);
        }
    }
}
