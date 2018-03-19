/*
 * Copyright (c) 2000-2018 Fachhochschule Nordwestschweiz (FHNW)
 * All Rights Reserved.
 */

package bank.drivers;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.URI;
import java.net.URISyntaxException;

import bank.Bank;
import bank.Request;
import bank.Response;
import bank.server.ProxyBank;
import jdk.incubator.http.HttpClient;
import jdk.incubator.http.HttpRequest;
import jdk.incubator.http.HttpRequest.BodyProcessor;
import jdk.incubator.http.HttpResponse;

public class HttpDriver implements bank.BankDriver {

    private ProxyBank bank = null;
    private Socket s;
    private URI url;

    public HttpDriver() {
        bank = new ProxyBank(this);
    }

    @Override
    public void connect(String[] args) {

        final String host = args[0];
        final String port = args[1];

        try {
            this.url = new URI("http://" + host + ":" + port + "/bank");
        } catch (URISyntaxException e) {
            e.printStackTrace();
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
        HttpClient client = HttpClient.newHttpClient();

        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject(request);

            BodyProcessor body = HttpRequest.BodyProcessor.fromByteArray(baos.toByteArray());

            HttpRequest httpRequest = HttpRequest.newBuilder().uri(url).POST(body).build();

            HttpResponse<byte[]> httpResponse = client.send(httpRequest, HttpResponse.BodyHandler.asByteArray());
            ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(httpResponse.body()));

            Response response = Response.class.cast(ois.readObject());
            return response;

        } catch (ClassNotFoundException | InterruptedException | IOException e) {
            e.printStackTrace();
        }
        
        return null;
    }

}