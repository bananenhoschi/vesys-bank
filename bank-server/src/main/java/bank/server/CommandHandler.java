package bank.server;

import java.io.IOException;

import bank.Account;
import bank.Bank;
import bank.Command;
import bank.InactiveException;
import bank.OverdrawException;
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

public class CommandHandler {

    private Bank bank;

    public CommandHandler(Bank bank) {
        this.bank = bank;
    }
    
    
    public Response handleCommand(Command command, Request req) throws IOException, OverdrawException {
        
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
                account.withdraw(((WithdrawRequest) req).getAmount());
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
        return response;
    }

}
