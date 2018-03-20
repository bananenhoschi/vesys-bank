package bank.server.http;

import java.io.IOException;
import java.net.InetSocketAddress;

import com.sun.net.httpserver.HttpServer;

import bank.server.RemoteBank;

public class HttpBankServer {

    private RemoteBank bank;

    private HttpBankServer(int port) throws IOException {

        bank = new RemoteBank();

        try {
            HttpServer httpServer = HttpServer.create(new InetSocketAddress(port), 0);
            httpServer.createContext("/", new RootHandler());
            httpServer.createContext("/bank", new HttpRequestHandler(bank));

            httpServer.setExecutor(null);
            httpServer.start();

            System.out.println("Started Server on port " + port);

        } catch (IOException e) {
            System.err.println(e.getMessage());
        }

    }

    public static void main(String[] args) throws IOException {
        new HttpBankServer(8990);
    }

}
