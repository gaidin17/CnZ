package Client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Created by Evgeny_Akulenko on 6/7/2016.
 */
public class SimpleClient {
    private final static String CLIENT_STOP = "exit";
    public static void main(String[] args) {
        String name = args[0];
        try (Socket socket = new Socket("localhost", 999);
             PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader readerIn = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
            String message = null;
            String messageIn = null;
            while (true) {
                message = reader.readLine();
                writer.println(name + " : " + message);
                System.out.println(readerIn.readLine());
            }
        }
        catch (IOException ex){

        }
    }
}
