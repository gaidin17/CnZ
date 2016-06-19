package MessengerUtils;

import java.io.PrintWriter;

/**
 * Created by Евгений on 18.06.2016.
 */
public class MessageReciever {
    PrintWriter writer;
    public MessageReciever(PrintWriter writer){
        this.writer = writer;
    }

    public void sendMessage(String message, String responseEnd) {
        writer.println(message);
        writer.println(responseEnd);
    }
}
