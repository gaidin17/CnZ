package Server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    public static void main(String[] args) {
        Server server = new Server();
        server.startServer(999);
    }

    public void startServer(int port) {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Server started and ready for connection on port: " + port);
            while (true) {
                Socket socket = serverSocket.accept();
                new ServerThread(socket).start();


            }
        } catch (IOException ex) {

        }
    }

    public class ServerThread extends Thread {
        Socket socket;

        public ServerThread(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            try (
                    BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    PrintWriter writer = new PrintWriter(socket.getOutputStream(), true)) {
                String message = null;
                while ((message = reader.readLine()) != null) {
                    System.out.println("Client message: " + message);
                    writer.println("response: " + message);
                }
                socket.close();
            } catch (IOException ex) {

            }
        }
    }

    public class ClientHandler extends Thread {
        Socket socket;

        public ClientHandler(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                 PrintWriter writer = new PrintWriter(socket.getOutputStream(), true)) {
                String message = null;
                writer.println("!!!!!!!!!");
                while ((message = reader.readLine()) != null) {
                    System.out.println("Client message: " + message);
                }

            } catch (IOException ex) {

            }
        }
    }
}
