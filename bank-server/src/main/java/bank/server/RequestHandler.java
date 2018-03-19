package bank.server;

import java.io.IOException;

import bank.Account;
import bank.Bank;
import bank.InactiveException;
import bank.OverdrawException;
import bank.Request;
import bank.Response;
import bank.Transfer;

public class RequestHandler {

    private Bank bank;

    public RequestHandler(Bank bank) {
        this.bank = bank;
    }

    public Response handleRequest(Request req) throws IOException, OverdrawException {

        Response response = new Response();

        switch (req.getCommand()) {
        case getaccountnumbers:
            response.setResult(bank.getAccountNumbers());
            break;
        case createaccount:
            String number = bank.createAccount((String) req.getValue()[0]);
            response.setResult(number);
            break;
        case getaccount: {
            Account account = bank.getAccount((String) req.getValue()[0]);
            response.setResult(account != null);
            break;
        }
        case closeaccount:
            boolean isAccountClosed = bank.closeAccount((String) req.getValue()[0]);
            response.setResult(isAccountClosed);
            break;
        case transfer:

            Transfer transfer = (Transfer) req.getValue()[0];
            try {
                Account from = bank.getAccount(transfer.getANumber());
                Account to = bank.getAccount(transfer.getBNumber());
                bank.transfer(from, to, transfer.getAmount());
            } catch (Exception e) {
                response.setException(e);
            }
            break;
        case deposit: {
            Account account = bank.getAccount((String) req.getValue()[0]);
            try {
                account.deposit((double) req.getValue()[1]);
            } catch (IllegalArgumentException | InactiveException e) {
                response.setException(e);
            }
            break;
        }
        case withdraw: {
            Account account = bank.getAccount((String) req.getValue()[0]);
            try {
                account.withdraw((double) req.getValue()[1]);
            } catch (IllegalArgumentException | InactiveException e) {
                response.setException(e);
            }
            break;
        }
        case getowner: {
            Account account = bank.getAccount((String) req.getValue()[0]);
                    response.setResult(account.getOwner());
            break;
        }
        case getbalance: {
            Account account = bank.getAccount((String) req.getValue()[0]);
            response.setResult(account.getBalance());
            break;
        }
        case isactive: {
            Account account = bank.getAccount((String) req.getValue()[0]);
            response.setResult(account.isActive());
            break;
        }
        default:
            throw new IOException("Invalid request type");
        }
        return response;
    }

}
