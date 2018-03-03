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
	private RemoteBank bank;

	public RequestProcessor(RemoteBank bank, Socket socket) {
		this.bank = bank;
		this.socket = socket;
	}

	@Override
	public void run() {
		try {
			while (true) {
				Request req = receiveRequest();
				if (req instanceof GetAccountNumbersRequest) {
					GetAccountNumbersResponse response = new GetAccountNumbersResponse();
					response.setAccountNumbers(bank.getAccountNumbers());
					sendResponse(response);
				} else if (req instanceof CreateAccountRequest) {
					CreateAccountRequest request = (CreateAccountRequest) req;
					String number = bank.createAccount(request.getOwner());
					CreateAccountResponse response = new CreateAccountResponse();
					response.setNumber(number);
					sendResponse(response);
				} else if (req instanceof GetAccountRequest) {
					GetAccountRequest request = (GetAccountRequest) req;
					Account account = bank.getAccount(request.getNumber());
					GetAccountResponse response = new GetAccountResponse();
					response.setAccount(account);
					sendResponse(response);
				} else if (req instanceof CloseAccountRequest) {
					CloseAccountRequest request = (CloseAccountRequest) req;
					boolean isAccountClosed = bank.closeAccount(request.getNumber());
					CloseAccountResponse response = new CloseAccountResponse();
					response.setAccountClosed(isAccountClosed);
					sendResponse(response);
				} else if (req instanceof TransferRequest) {
					TransferRequest request = (TransferRequest) req;
					bank.transfer(request.getFrom(), request.getTo(), request.getAmount());
					TransferResponse response = new TransferResponse();
					sendResponse(response);
				}else {
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
		ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
		out.writeObject(response);
		out.flush();
	}

	public Request receiveRequest() throws IOException, ClassNotFoundException {
		ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
		return (Request) in.readObject();
	}

}
