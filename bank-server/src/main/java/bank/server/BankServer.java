package bank.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class BankServer {

    private RemoteBank bank;

    // private final static Logger LOG = LogManager.getLogger(BankServer.class);

    private BankServer(int port) throws IOException {

        bank = new RemoteBank();

        try (ServerSocket server = new ServerSocket(port)) {
            System.out.println("Started Server on port " + port);

            while (true) {
                Socket s = server.accept();
                Thread t = new Thread(new RequestProcessor(bank, s));
                t.start();
            }
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }

    }

    public static void main(String[] args) throws IOException {
        new BankServer(8989);
    }

}
