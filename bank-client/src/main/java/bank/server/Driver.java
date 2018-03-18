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

    private SocketBankProxy bank = null;
    private Socket s;
    private ObjectInputStream inputstream;
    private ObjectOutputStream outputstream;

    public Driver() {
        bank = new SocketBankProxy(this);
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

    public <T> T sendRequestAndReceiveResult(Request request, Class<T> clazz) throws IOException {

        try {
            if (s.isClosed()) {
                System.out.println("closed");
                return null;
            }

            outputstream.writeObject(request);
            outputstream.flush();

            T response = clazz.cast(inputstream.readObject());

            return response;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

}