package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dtos.HotelDTO;
import fetch.Fetcher;
import java.io.IOException;
import java.util.concurrent.ExecutionException;
import utils.EMF_Creator;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeoutException;
import javax.persistence.EntityManagerFactory;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("fetch")
public class APIResource {

    private static final EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory();
    private static final ExecutorService es = Executors.newCachedThreadPool();
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static String cachedReponse;
            
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public String demo() {
        return "{\"msg\":\"Hello World\"}";
    }
    
    @GET
    @Path("sequential")
    @Produces({MediaType.APPLICATION_JSON})
    public String fetchSequential() throws IOException{
        HotelDTO result = Fetcher.responseSequential();
        String resultJSON = GSON.toJson(result);
        cachedReponse = resultJSON;
        return resultJSON;
    }
    
    @GET
    @Path("parallel")
    @Produces({MediaType.APPLICATION_JSON})
    public String fetchParallel() throws IOException, ExecutionException, InterruptedException, TimeoutException{
        HotelDTO result = Fetcher.responseParallel(es, GSON);
      String resultJSON = GSON.toJson(result);
        cachedReponse = resultJSON;
        return resultJSON;
    }
    
    @GET
    @Path("cached")
    @Produces({MediaType.APPLICATION_JSON})
    public String getCachedResponse(){
    HotelDTO hotelDTO = GSON.fromJson(cachedReponse, HotelDTO.class);
    return GSON.toJson(hotelDTO);    
    }
}
