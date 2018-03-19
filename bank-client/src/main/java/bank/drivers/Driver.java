/*
 * Copyright (c) 2000-2018 Fachhochschule Nordwestschweiz (FHNW)
 * All Rights Reserved.
 */

package bank.drivers;

import bank.Bank;
import bank.Request;
import bank.Response;
import bank.local.LocalBank;

public class Driver implements bank.BankDriver {
    private Bank bank = null;

    @Override
    public void connect(String[] args) {
        bank = new LocalBank();
        System.out.println("connected...");
    }

    @Override
    public void disconnect() {
        bank = null;
        System.out.println("disconnected...");
    }

    @Override
    public Bank getBank() {
        return bank;
    }

    @Override
    public Response handle(Request request) {
        return null;
    }

}