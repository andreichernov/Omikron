package de.wombatsoftware.Omikron.rest;

import javax.ejb.Stateless;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.function.Function;

/**
 * @author Daniel Sachse
 * @date 10.10.13 15:15
 */

@Path("/delegate/")
@Stateless
public class DelegateResource {
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getStocks(@QueryParam("stocks") @DefaultValue("100") int stocks) {
        CalculateNAV calculateNAV = new CalculateNAV(StockFetcher::getStockPrice);

        return Response.ok(calculateNAV.compute("ORCL", stocks)).build();
    }

    @GET
    @Path("/test/")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getStocksTestcase(@QueryParam("stocks") @DefaultValue("100") int stocks, @QueryParam("price") @DefaultValue("33.33") double price) {
        CalculateNAV calculateNAV = new CalculateNAV(ticker -> price);

        return Response.ok(calculateNAV.compute("ORCL", stocks)).build();
    }
}

class CalculateNAV {
    private Function<String, Double> priceFinder;

    public CalculateNAV(Function<String, Double> priceFinder) {
        this.priceFinder = priceFinder;
    }

    public double compute(String ticker, int stocks) {
        return stocks * priceFinder.apply(ticker);
    }
}

class StockFetcher {
    public static double getStockPrice(String ticker) {
        System.out.println("Do real work and get the stock price...");
        return 35.55;
    }
}