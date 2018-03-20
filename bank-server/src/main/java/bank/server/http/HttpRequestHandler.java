package bank.server.http;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import bank.Bank;
import bank.OverdrawException;
import bank.Request;
import bank.Response;
import bank.server.RequestHandler;

public class HttpRequestHandler implements HttpHandler {

    private Bank bank;

    public HttpRequestHandler(Bank bank) {
        this.bank = bank;
    }

    @Override
    public void handle(HttpExchange t) throws IOException {

        try {
            ObjectInputStream ois = new ObjectInputStream(t.getRequestBody());
            Request request = (Request) ois.readObject();

            RequestHandler handler = new RequestHandler(bank);
            Response response = handler.handleRequest(request);

            t.sendResponseHeaders(200, 0);
            ObjectOutputStream oos = new ObjectOutputStream(t.getResponseBody());
            oos.writeObject(response);
            oos.close();

        } catch (ClassNotFoundException | OverdrawException e) {
            e.printStackTrace();
        }
    }

}
