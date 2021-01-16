package fetch;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dtos.ChuckDTO;
import dtos.CustomDTO;
import dtos.DogDTO;
import dtos.JokeDTO;
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
    final static String CHUCK_SERVER = "https://api.chucknorris.io/jokes/random";
    final static String DAD_SERVER = "https://icanhazdadjoke.com";
    final static String DOG_SERVER = "https://dog.ceo/api/breeds/image/random";
    
    public static String responseSequential() throws IOException {
        Gson GSON = new GsonBuilder().setPrettyPrinting().create();
        
        String chuckJSON = HttpUtils.fetchData(CHUCK_SERVER);
        ChuckDTO chuckDTO = GSON.fromJson(chuckJSON, ChuckDTO.class);
        
        String jokeJSON = HttpUtils.fetchData(DAD_SERVER);
        JokeDTO jokeDTO = GSON.fromJson(jokeJSON, JokeDTO.class);
        
        String dogJSON = HttpUtils.fetchData(DOG_SERVER);
        DogDTO dogDTO = GSON.fromJson(dogJSON, DogDTO.class);
        
        CustomDTO customDTO = new CustomDTO(chuckDTO.getValue(),CHUCK_SERVER, jokeDTO.getJoke(),DAD_SERVER, dogDTO.getMessage(),DOG_SERVER);
        
        String customJSON = GSON.toJson(customDTO);
        return customJSON;
    }
    
  public static String responseParallel(ExecutorService threadPool, final Gson GSON) throws ExecutionException, TimeoutException, InterruptedException{
      
      Callable<ChuckDTO> chuckTask = new Callable<ChuckDTO>(){
          @Override
          public ChuckDTO call() throws IOException {
              String chuck = HttpUtils.fetchData(CHUCK_SERVER);
              ChuckDTO chuckDTO = GSON.fromJson(chuck, ChuckDTO.class);
              return chuckDTO;

          }
      };
      
      Callable<JokeDTO> jokeTask = new Callable<JokeDTO>(){
          @Override
          public JokeDTO call() throws IOException {
              String joke = HttpUtils.fetchData(DAD_SERVER);
              JokeDTO jokeDTO = GSON.fromJson(joke, JokeDTO.class);
              return jokeDTO;

          }
      };
      
      Callable<DogDTO> dogTask = new Callable<DogDTO>(){
          @Override
          public DogDTO call() throws IOException {
              String dog = HttpUtils.fetchData(DOG_SERVER);
              DogDTO dogDTO = GSON.fromJson(dog, DogDTO.class);
              return dogDTO;

          }
      };
      
      Future<ChuckDTO> futureChuck = threadPool.submit(chuckTask);
      Future<JokeDTO> futureJoke = threadPool.submit(jokeTask);
      Future<DogDTO> futureDog = threadPool.submit(dogTask);
      
      ChuckDTO chuck = futureChuck.get(5, TimeUnit.SECONDS);
      JokeDTO joke = futureJoke.get(5, TimeUnit.SECONDS);
      DogDTO dog = futureDog.get(5, TimeUnit.SECONDS);
      
      CustomDTO customDTO = new CustomDTO(chuck.getValue(),CHUCK_SERVER, joke.getJoke(),DAD_SERVER, dog.getMessage(),DOG_SERVER);
      String customJSON = GSON.toJson(customDTO);
      return customJSON;
  }
}
