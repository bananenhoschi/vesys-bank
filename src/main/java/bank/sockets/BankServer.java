package bank.sockets;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import bank.Bank;
import bank.sockets.request.CreateAccountRequest;

public class BankServer {

	private static ServerSocket server;
	private static Bank bank;

	public static void main(String[] args) {
		try {
			server = new ServerSocket(8989);
			bank = new RemoteBank();
		} catch (IOException e) {
			System.out.println(e);
		}

		while (true) {
			try {
				Socket s = server.accept();

				ObjectInputStream ois = new ObjectInputStream(s.getInputStream());

				Thread t = new Thread(() -> {

					try {
						Object o = ois.readObject();

						if (o instanceof CreateAccountRequest) {
							CreateAccountRequest request = (CreateAccountRequest) o;
							bank.createAccount(request.getOwner());
							System.out.println("Server: create account");
						}

					} catch (ClassNotFoundException | IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				});
				t.start();
			} catch (IOException e) {
				e.printStackTrace();
			}

		}
	}

}
