/*
 * Copyright (c) 2000-2018 Fachhochschule Nordwestschweiz (FHNW)
 * All Rights Reserved.
 */

package bank.server;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;

import bank.Bank;
import bank.request.CreateAccountRequest;

public class Driver implements bank.BankDriver {
	private Bank bank = null;

	public Driver() {
		bank = new RemoteBank();
	}

	@Override
	public void connect(String[] args) {
		try (Socket s = new Socket(args[0], Integer.parseInt(args[1]))){
			ObjectOutputStream outputStream = new ObjectOutputStream(s.getOutputStream());
			outputStream.writeObject(new CreateAccountRequest("Hans"));
		} catch (NumberFormatException | IOException e) {
			e.printStackTrace();
		}
		System.out.println("connected...");
	}

	@Override
	public void disconnect() {
		System.out.println("disconnected...");
	}

	@Override
	public Bank getBank() {
		return bank;
	}

}