package fetch;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dtos.HotelDTO;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import javax.ws.rs.PathParam;
import utils.HttpUtils;

/**
 *
 * @author Noell Zane
 */
public class Fetcher {
    final static String HOTEL_SERVER_ALL = "http://exam.cphdat.dk:8000//hotel/all";
    final static String HOTEL_SERVER_BY_ID = "http://exam.cphdat.dk:8000//hotel/";

    
    
    
    public static HotelDTO responseSequentialHotelByID(@PathParam("id") int id) throws IOException {
        Gson GSON = new GsonBuilder().setPrettyPrinting().create();
        
        String hotelJSON = HttpUtils.fetchData(HOTEL_SERVER_BY_ID+id);
        HotelDTO hotelDTO = GSON.fromJson(hotelJSON, HotelDTO.class);
  
        return hotelDTO;
    }
    public static HotelDTO[] responseSequentialAllHotels() throws IOException {
        Gson GSON = new GsonBuilder().setPrettyPrinting().create();
        
        String hotelJSON = HttpUtils.fetchData(HOTEL_SERVER_ALL);
        HotelDTO[] hotelDTO = GSON.fromJson(hotelJSON, HotelDTO[].class);
  
        return hotelDTO;
    }
      public static HotelDTO responseParallelHotelByID(ExecutorService threadPool, final Gson GSON,@PathParam("id") int id) throws ExecutionException, TimeoutException, InterruptedException{
      
      Callable<HotelDTO> hotelTask = new Callable<HotelDTO>(){
          @Override
          public HotelDTO call() throws IOException {
              String hotel = HttpUtils.fetchData(HOTEL_SERVER_BY_ID+id);
              System.out.println(hotel);
 
        HotelDTO theHotel = GSON.fromJson(hotel, HotelDTO.class);
        return theHotel;
          }
      };
      
      Future<HotelDTO> futureHotel = threadPool.submit(hotelTask);
      HotelDTO hotel = futureHotel.get(5, TimeUnit.SECONDS); 
      return hotel;
  }
      
  public static HotelDTO[] responseParallelAllHotels(ExecutorService threadPool, final Gson GSON) throws ExecutionException, TimeoutException, InterruptedException{
      
      Callable<HotelDTO[]> hotelTask = new Callable<HotelDTO[]>(){
          @Override
          public HotelDTO[] call() throws IOException {
              String hotel = HttpUtils.fetchData(HOTEL_SERVER_ALL);
              System.out.println(hotel);
 
        HotelDTO[] allHotels = GSON.fromJson(hotel, HotelDTO[].class);
        return allHotels;
          }
      };
      
      Future<HotelDTO[]> futureHotel = threadPool.submit(hotelTask);
      HotelDTO[] hotel = futureHotel.get(5, TimeUnit.SECONDS); 
      return hotel;
  }

}
