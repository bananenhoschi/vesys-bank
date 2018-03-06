package bank.server;

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

public class RequestProcessor implements Runnable {
    private final Socket socket;
    private final SocketBank bank;
    private final ObjectOutputStream out;
    private final ObjectInputStream in;

    public RequestProcessor(SocketBank bank, Socket socket) throws IOException {
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
                Command command = getCommand(req.getClass().getSimpleName());
                Response response = null;
                switch (command) {
                case getaccountnumbers:
                    response = new GetAccountNumbersResponse();
                    ((GetAccountNumbersResponse) response).setAccountNumbers(bank.getAccountNumbers());
                    break;
                case createaccount:
                    String number = bank.createAccount(((CreateAccountRequest) req).getOwner());
                    response = new CreateAccountResponse(number);
                    break;
                case getaccount: {
                    Account account = bank.getAccount(((GetAccountRequest) req).getNumber());
                    response = new GetAccountResponse(account != null);
                    break;
                }
                case closeaccount:
                    boolean isAccountClosed = bank.closeAccount(((CloseAccountRequest) req).getNumber());
                    response = new CloseAccountResponse(isAccountClosed);
                    break;
                case transfer:

                    response = new TransferResponse();

                    try {
                        Account from = bank.getAccount(((TransferRequest) req).getFrom());
                        Account to = bank.getAccount(((TransferRequest) req).getTo());
                        bank.transfer(from, to, ((TransferRequest) req).getAmount());
                        ((TransferResponse) response).setSuccess(true);

                    } catch (Exception e) {
                        ((TransferResponse) response).setThrowable(e);
                    }
                    break;
                case deposit: {
                    Account account = bank.getAccount(((DepositRequest) req).getNumber());
                    response = new DepositResponse();
                    try {
                        account.deposit(((DepositRequest) req).getAmount());
                        ((DepositResponse) response).setSuccess(true);
                    } catch (IllegalArgumentException | InactiveException e) {
                        ((DepositResponse) response).setThrowable(e);
                    }
                    break;
                }
                case withdraw: {
                    Account account = bank.getAccount(((WithdrawRequest) req).getNumber());
                    response = new WithdrawResponse();
                    try {
                        account.deposit(((WithdrawRequest) req).getAmount());
                        ((WithdrawResponse) response).setSuccess(true);
                    } catch (IllegalArgumentException | InactiveException e) {
                        ((WithdrawResponse) response).setThrowable(e);
                    }
                    break;
                }
                case getowner: {
                    Account account = bank.getAccount(((GetOwnerRequest) req).getNumber());
                    response = new GetOwnerResponse(account.getOwner());
                    break;
                }
                case getbalance: {
                    Account account = bank.getAccount(((GetBalanceRequest) req).getNumber());
                    response = new GetBalanceResponse(account.getBalance());
                    break;
                }
                case isactive: {
                    Account account = bank.getAccount(((IsActiveRequest) req).getNumber());
                    response = new IsActiveResponse(account.isActive());
                    break;
                }
                default:
                    throw new IOException("Invalid request type");
                }
                sendResponse(response);
            }
        } catch (

        IOException e) {
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
