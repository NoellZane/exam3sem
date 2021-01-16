package fetch;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dtos.HotelDTO;
import dtos.BookingDTO;
import java.io.IOException;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import utils.HttpUtils;

/**
 *
 * @author Noell Zane
 */
public class Fetcher {
    final static String HOTEL_SERVER = "https://exam.cphdat.dk/hotel/all";

    
    public static HotelDTO responseSequential() throws IOException {
        Gson GSON = new GsonBuilder().setPrettyPrinting().create();
        
        String hotelJSON = HttpUtils.fetchData(HOTEL_SERVER);
        HotelDTO hotelDTO = GSON.fromJson(hotelJSON, HotelDTO.class);
  
        return hotelDTO;
    }
    
  public static HotelDTO responseParallel(ExecutorService threadPool, final Gson GSON) throws ExecutionException, TimeoutException, InterruptedException{
      
      Callable<HotelDTO> hotelTask = new Callable<HotelDTO>(){
          @Override
          public HotelDTO call() throws IOException {
              String hotel = HttpUtils.fetchData(HOTEL_SERVER);
              HotelDTO hotelDTO = GSON.fromJson(hotel, HotelDTO.class);
              return hotelDTO;

          }
      };
      
      Future<HotelDTO> futureHotel = threadPool.submit(hotelTask);
      HotelDTO hotel = futureHotel.get(5, TimeUnit.SECONDS); 
      return hotel;
  }
}
