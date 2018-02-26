/*
 * Copyright (c) 2000-2018 Fachhochschule Nordwestschweiz (FHNW)
 * All Rights Reserved.
 */

package bank.sockets;

import bank.Bank;

public class Driver implements bank.BankDriver {
	private Bank bank = null;
	
	public Driver() {
		bank = new RemoteBank();
	}

	@Override
	public void connect(String[] args) {
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