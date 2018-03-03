/*
 * Copyright (c) 2000-2018 Fachhochschule Nordwestschweiz (FHNW)
 * All Rights Reserved.
 */

package bank.server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import bank.Bank;
import bank.request.Request;

public class Driver implements bank.BankDriver {

	private RemoteBank bank = null;
	private Socket s;

	public Driver() {
		bank = new RemoteBank(this);
	}

	@Override
	public void connect(String[] args) {

		final String host = args[0];
		final String port = args[1];

		try {
			s = new Socket(host, Integer.parseInt(port));
		} catch (NumberFormatException | IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void disconnect() {
		try {
			s.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("disconnected...");
	}

	@Override
	public Bank getBank() {
		return bank;
	}

	public <T> T sendRequestAndReceiveResult(Request request, Class<T> clazz) throws IOException {

		try {
			if (s.isClosed()) {
				System.out.println("closed");
				return null;
			}
			ObjectOutputStream outputstream = new ObjectOutputStream(s.getOutputStream());
			outputstream.writeObject(request);
			outputstream.flush();

			ObjectInputStream inputstream = new ObjectInputStream(s.getInputStream());
			T response = clazz.cast(inputstream.readObject());

			return response;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}

}