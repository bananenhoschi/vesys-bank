package bank.server.socket;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import bank.Command;
import bank.Request;
import bank.Response;
import bank.server.RemoteBank;
import bank.server.RequestHandler;

public class RequestProcessor implements Runnable {
    private final Socket socket;
    private final RemoteBank bank;
    private final ObjectOutputStream out;
    private final ObjectInputStream in;
    private final RequestHandler requestHandler;

    public RequestProcessor(RemoteBank bank, Socket socket) throws IOException {
        this.bank = bank;
        this.socket = socket;
        out = new ObjectOutputStream(socket.getOutputStream());
        in = new ObjectInputStream(socket.getInputStream());
        requestHandler = new RequestHandler(this.bank);

    }

    @Override
    public void run() {
        try {
            while (true) {
                Request req = receiveRequest();
                Response response = requestHandler.handleRequest(req);
                sendResponse(response);
            }
        } catch (IOException e) {
            System.err.println(e.getMessage());
            throw new RuntimeException(e);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                System.err.println(e.getMessage());
            }
        }

    }

    private Command getCommand(String simpleName) {
        return Command.valueOf(simpleName.substring(0, simpleName.indexOf("Request", 0)).toLowerCase());
    }

    public void sendResponse(Response response) throws IOException {
        out.writeObject(response);
        out.flush();
    }

    public Request receiveRequest() throws IOException, ClassNotFoundException {
        return (Request) in.readObject();
    }

}
