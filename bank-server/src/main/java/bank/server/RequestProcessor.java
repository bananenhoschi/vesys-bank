package bank.server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import bank.Account;
import bank.request.CloseAccountRequest;
import bank.request.CreateAccountRequest;
import bank.request.GetAccountNumbersRequest;
import bank.request.GetAccountRequest;
import bank.request.Request;
import bank.request.TransferRequest;
import bank.response.CloseAccountResponse;
import bank.response.CreateAccountResponse;
import bank.response.GetAccountNumbersResponse;
import bank.response.GetAccountResponse;
import bank.response.Response;
import bank.response.TransferResponse;

public class RequestProcessor implements Runnable {
    private final Socket socket;
    private final RemoteBank bank;
    private final ObjectOutputStream out;
    private final ObjectInputStream in;

    public RequestProcessor(RemoteBank bank, Socket socket) throws IOException {
        this.bank = bank;
        this.socket = socket;
        out = new ObjectOutputStream(socket.getOutputStream());
        in = new ObjectInputStream(socket.getInputStream());

    }

    @Override
    public void run() {
        try {
            while (true) {
                Request req = receiveRequest();
                // TODO anstelle dieses kaskadierten if-Statements K�nnte man auch
                // Polymorphismus verwenden indem man eine auf den Request-Objekten definierte
                // Methode aufruft. => Command Pattern.
                String command = req.getClass().getSimpleName();
                if (req instanceof GetAccountNumbersRequest) {
                    GetAccountNumbersResponse response = new GetAccountNumbersResponse();
                    response.setAccountNumbers(bank.getAccountNumbers());
                    sendResponse(response);
                } else if (req instanceof CreateAccountRequest) {
                    CreateAccountRequest request = (CreateAccountRequest) req;
                    String number = bank.createAccount(request.getOwner());
                    sendResponse(new CreateAccountResponse(number));
                } else if (req instanceof GetAccountRequest) {
                    GetAccountRequest request = (GetAccountRequest) req;
                    Account account = bank.getAccount(request.getNumber());
                    sendResponse(new GetAccountResponse(account));
                } else if (req instanceof CloseAccountRequest) {
                    CloseAccountRequest request = (CloseAccountRequest) req;
                    boolean isAccountClosed = bank.closeAccount(request.getNumber());
                    CloseAccountResponse response = new CloseAccountResponse(isAccountClosed);
                    sendResponse(response);
                } else if (req instanceof TransferRequest) {
                    TransferRequest request = (TransferRequest) req;
                    TransferResponse response = new TransferResponse();

                    try {

                        Account from = bank.getAccount(request.getFrom().getNumber());
                        Account to = bank.getAccount(request.getTo().getNumber());
                        bank.transfer(from, to, request.getAmount());

                        response.setFrom(from);
                        response.setTo(to);

                    } catch (Exception e) {
                        response.setException(e);
                    }
                    sendResponse(response);
                } else {
                    throw new IllegalArgumentException();
                }
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

    public void sendResponse(Response response) throws IOException {
        out.writeObject(response);
        out.flush();
    }

    public Request receiveRequest() throws IOException, ClassNotFoundException {
        return (Request) in.readObject();
    }

}
