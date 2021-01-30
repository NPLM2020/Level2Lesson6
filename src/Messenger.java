import java.io.*;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicBoolean;

public class Messenger {

    private final String exitMessage = "-exit";

    AtomicBoolean isDrop = new AtomicBoolean(false);
    private DataInputStream in;
    private DataOutputStream out;
    private String name;

    public Messenger(DataInputStream in, DataOutputStream out, String name) {
        this.in = in;
        this.out = out;
        this.name = name;
        try {
            start();
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    private void start() throws IOException {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Please type a message in the line below...");

        new Thread(() -> {
            try {
                while (true) {
                    String incomingMessage = in.readUTF();
                    if (incomingMessage.contains(exitMessage)) {
                        if (isDrop.get()) break;
                        out.writeUTF(exitMessage);
                        isDrop.set(true);
                        System.out.println("Session is ended. Press Enter to exit.");
                        break;
                    }
                    System.out.println(incomingMessage);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();

        while (true) {
            String message =  scanner.nextLine();
            if (isDrop.get()) {
                break;
            }
            out.writeUTF(name + ": " + message);
            if (message.contains(exitMessage)) {
                isDrop.set(true);
                break;
            }
        }

    }

}


