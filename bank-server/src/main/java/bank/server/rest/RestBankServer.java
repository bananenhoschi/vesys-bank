package bank.server.rest;

import java.io.IOException;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;

import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;

import bank.server.RemoteBank;
import bank.server.rest.resources.AccountResource;
import bank.server.rest.resources.BankResource;

public class RestBankServer {

    private RemoteBank bank;

    private RestBankServer(int port) throws IOException {

        bank = new RemoteBank();

        String baseurl = "http://localhost:" + port;

        ResourceConfig rc = new ResourceConfig();
        rc.register(BankResource.class);
        rc.register(AccountResource.class);

        HttpServer httpServer = GrizzlyHttpServerFactory.createHttpServer(URI.create(baseurl), rc, true);

        System.out.println("Started Server on port " + port);

    }

    public static void main(String[] args) throws IOException {
        new RestBankServer(8990);
    }

}
