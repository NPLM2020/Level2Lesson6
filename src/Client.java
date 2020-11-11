import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicBoolean;

public class Client {
    private final int serverPort = 8888;
    private final String serverHost = "localhost";
    private Socket socket;
    private DataInputStream in;
    private DataOutputStream out;

    public Client() {
        try {

            socket = new Socket(serverHost, serverPort);

            in = new DataInputStream(socket.getInputStream());
            out = new DataOutputStream(socket.getOutputStream());

            new Messenger(in, out, "Client");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Client socket = new Client();
    }
}
