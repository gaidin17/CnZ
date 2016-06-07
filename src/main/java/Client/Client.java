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
public class Client extends Thread {
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
        client1.run();
    }

    public void run() {
        System.out.println(name);
        try (Socket socket = new Socket(server, port);
             PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader readerIn = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
            String message = null;
            while (!(message = reader.readLine()).equals(CLIENT_STOP)) {
                writer.println(name + " : " + message);
                System.out.println(readerIn.readLine());
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
