package bank.server.socket;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import bank.Account;
import bank.Command;
import bank.InactiveException;
import bank.request.CloseAccountRequest;
import bank.request.CreateAccountRequest;
import bank.request.DepositRequest;
import bank.request.GetAccountRequest;
import bank.request.GetBalanceRequest;
import bank.request.GetOwnerRequest;
import bank.request.IsActiveRequest;
import bank.request.Request;
import bank.request.TransferRequest;
import bank.request.WithdrawRequest;
import bank.response.CloseAccountResponse;
import bank.response.CreateAccountResponse;
import bank.response.DepositResponse;
import bank.response.GetAccountNumbersResponse;
import bank.response.GetAccountResponse;
import bank.response.GetBalanceResponse;
import bank.response.GetOwnerResponse;
import bank.response.IsActiveResponse;
import bank.response.Response;
import bank.response.TransferResponse;
import bank.response.WithdrawResponse;
import bank.server.CommandHandler;
import bank.server.RemoteBank;

public class RequestProcessor implements Runnable {
    private final Socket socket;
    private final RemoteBank bank;
    private final ObjectOutputStream out;
    private final ObjectInputStream in;
    private final CommandHandler commandHandler;

    public RequestProcessor(RemoteBank bank, Socket socket) throws IOException {
        this.bank = bank;
        this.socket = socket;
        out = new ObjectOutputStream(socket.getOutputStream());
        in = new ObjectInputStream(socket.getInputStream());
        commandHandler = new CommandHandler(this.bank);

    }

    @Override
    public void run() {
        try {
            while (true) {
                Request req = receiveRequest();
                Command command = getCommand(req.getClass().getSimpleName());
                Response response = commandHandler.handleCommand(command, req);
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
