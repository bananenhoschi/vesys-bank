package bank.server.rest.resources;

import java.io.IOException;
import java.util.Set;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import bank.Account;
import bank.InactiveException;
import bank.OverdrawException;

@Path("bank")
public class BankResource {

    @POST
    @Path("accounts/new/{owner}")
    public String createAccount(@PathParam("owner") String owner) throws IOException {
        // TODO Auto-generated method stub
        return null;
    }

    @POST
    @Path("accounts/{number}/close")
    public boolean closeAccount(@PathParam("number") String number) throws IOException {
        // TODO Auto-generated method stub
        return false;
    }

    @GET
    @Path("accounts")
    public Set<String> getAccountNumbers() throws IOException {
        // TODO Auto-generated method stub
        return null;
    }

    @POST
    @Path("accounts/{number}")
    public Account getAccount(@PathParam("number") String number) throws IOException {
        // TODO Auto-generated method stub
        return null;
    }

    @POST
    @Path("transfer/{numberA}-{numberB}-{amount}")
    public void transfer(@PathParam("numberA") String numberA, @PathParam("numberB") String numberB,
            @PathParam("amount") double amount)
            throws IOException, IllegalArgumentException, OverdrawException, InactiveException {
        // TODO Auto-generated method stub

    }

}
