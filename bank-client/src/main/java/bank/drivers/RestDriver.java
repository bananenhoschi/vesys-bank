package bank.drivers;
/*
 * Copyright (c) 2000-2018 Fachhochschule Nordwestschweiz (FHNW)
 * All Rights Reserved.
 */



import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import bank.Bank;
import bank.BankDriver;
import bank.Request;
import bank.Response;
import bank.server.ProxyBank;

public class RestDriver implements BankDriver {

    private ProxyBank bank = null;
    private Socket s;
    private ObjectInputStream inputstream;
    private ObjectOutputStream outputstream;

    public RestDriver() {
        bank = new ProxyBank(this);
    }

    @Override
    public void connect(String[] args) {

        final String host = args[0];
        final String port = args[1];

        try {
            s = new Socket(host, Integer.parseInt(port));
            outputstream = new ObjectOutputStream(s.getOutputStream());
            inputstream = new ObjectInputStream(s.getInputStream());
        } catch (NumberFormatException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void disconnect() {
        try {
            s.close();
        } catch (IOException e) {
        }
        System.out.println("disconnected...");
    }

    @Override
    public Bank getBank() {
        return bank;
    }

    @Override
    public Response handle(Request request) {
        try {
            if (s.isClosed()) {
                System.out.println("closed");
                return null;
            }

            outputstream.writeObject(request);
            outputstream.flush();

            Response response = Response.class.cast(inputstream.readObject());

            return response;
        } catch (ClassNotFoundException | IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}