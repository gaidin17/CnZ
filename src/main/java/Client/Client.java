package Client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Подключается к серверу и передает ему сообщения вводимые пользователем в
 * консоли. Хост сервера указвается первым аргументом, порт вторым. При
 * отсутствии аргументов в качестве адреса порта принимается localhost:9999
 */
public class Client  {
    public Client(String name){
        this.name = name;
    }
    String name;
    private final static String CLIENT_STOP = "exit";
    private String server;
    private int port;
    public static void main(String[] args) {
        Client client1 = new Client(args[0]);
        client1.setConnectionParam("localhost", 999);
        client1.startClient();
    }

    public void startClient() {
        try (Socket socket = new Socket(server, port);
             PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader readerIn = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
            String message = null;
            String response = null;
            int i = 0;
            boolean isGameOver = false;
            while (true) {
                while (!(response = readerIn.readLine()).equals("end")) {
                    System.out.println(response);
                }
                if (isGameOver){
                    break;
                }
                System.out.println("Turn N" + i);
                message = reader.readLine();
                if (message.equals(CLIENT_STOP)) {
                    break;
                }

                writer.println(message);

                while (!(response = readerIn.readLine()).equals("end")) {
                    if (response.contains("gameover")) {
                        isGameOver = true;
                    }
                    System.out.println(response);
                }
                if (isGameOver){
                    break;
                }
                /*if (response.indexOf("The Winner is") >= 0) {
                    break;
                }

                System.out.println("next turn");
                */
                i++;
            }
        }
        catch (IOException ex){

        }
    }

    public void setConnectionParam(String server, int port) {
        this.server = server;
        this.port = port;
    }
}
