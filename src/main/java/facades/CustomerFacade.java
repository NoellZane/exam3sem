package facades;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dtos.BookingDTO;
import dtos.CustomerDTO;
import entities.Booking;
import entities.Role;
import entities.Customer;
import entities.Hotel;
import errorhandling.MissingInputException;
import errorhandling.UserAlreadyExistsException;
import fetch.Fetcher;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeoutException;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import security.errorhandling.AuthenticationException;

/**
 * @author lam@cphbusiness.dk
 */
public class CustomerFacade {

    private static EntityManagerFactory emf;
    private static CustomerFacade instance;
    private static ExecutorService threadPool = Executors.newCachedThreadPool();
    private static Gson GSON  = new GsonBuilder().setPrettyPrinting().create();;

    private CustomerFacade() {
    }

    /**
     *
     * @param _emf
     * @return the instance of this facade.
     */
        private EntityManager getEntityManager() {
        return emf.createEntityManager();
    }
    public static CustomerFacade getUserFacade(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new CustomerFacade();
        }
        return instance;
    }

    public Customer getVeryfiedCustomer(String username, String password) throws AuthenticationException {
        EntityManager em = emf.createEntityManager();
        Customer customer;
        try {
            customer = em.find(Customer.class, username);
            if (customer == null || !customer.verifyPassword(password)) {
                throw new AuthenticationException("Invalid user name or password");
            }
        } finally {
            em.close();
        }
        return customer;
    }

    public CustomerDTO addCustomer(String name, String username, String password, String phone) throws MissingInputException, UserAlreadyExistsException{ //Add person
        EntityManager em = getEntityManager();
            Customer addedCustomer = new Customer(name, username, password, phone);
        if (name.length()== 0||username.length() == 0 || password.length() == 0||phone.length()== 0) { //Checks to see if our inputs are empty
            throw new MissingInputException("Name or username or password or phone is missing");
        }
        try {
            em.getTransaction().begin();
            TypedQuery query = em.createQuery("SELECT c FROM Customer c WHERE c.userName = :username", Customer.class).setParameter("username", username);
           
            List<Customer> customers = query.getResultList(); //Get list of Customers that matches query
            if (customers.size() > 0) { //If Customer size is bigger than zero, that means it exists.
                throw new UserAlreadyExistsException("Customer already exists, please try another username");
            } else {
            addedCustomer.addRole(em.createQuery("SELECT r FROM Role r WHERE r.roleName = :role_name",Role.class).setParameter("role_name", "user").getSingleResult());
            em.persist(addedCustomer);
            }
            em.getTransaction().commit();
        } finally {
            em.close();
        }
        return new CustomerDTO(addedCustomer);
    }
    
    public List<CustomerDTO> getAllUsers() {
        {
            EntityManager em = emf.createEntityManager();
            List<CustomerDTO> userDTOs = new ArrayList<>();
            try {
                em.getTransaction().begin();
                TypedQuery query = em.createQuery("SELECT u FROM User u", Customer.class);
//                TypedQuery query = em.createQuery("SELECT u.userName FROM User u", User.class);
                List<Customer> users = query.getResultList();
                for (Customer user : users) {
                    userDTOs.add(new CustomerDTO(user));
                }
            } finally {
                em.close();
            }
            return userDTOs;
        }
    }
    
    public BookingDTO addBooking(String startDate, int numberOfNights, String customerUsername, int hotelID) throws ExecutionException, TimeoutException, InterruptedException, MissingInputException{
        EntityManager em = getEntityManager();
        Customer customer = em.find(Customer.class, customerUsername);
        Hotel hotel = em.find(Hotel.class, hotelID);
        if(hotel == null){
            hotel = new Hotel(Fetcher.responseParallel(threadPool, GSON)); //Added constructor for hotel that takes a hotelDTO
        }
        Booking addedBooking = new Booking(startDate, numberOfNights, customer, hotel);
        if(startDate.length()== 0){
           throw new MissingInputException("Start date is missing");
        }
          try {
            em.getTransaction().begin();
            
            em.persist(addedBooking);
            em.getTransaction().commit();
            }
         finally {
            em.close();
        }
        return new BookingDTO(addedBooking);
    }
//     public CustomerDTO editUser(CustomerDTO u) throws MissingInputException, UserNotFoundException {
//        EntityManager em = getEntityManager();
//        if (u.getUsername().length() == 0 || u.getPassword().length() == 0) { //Checks to see if our inputs are empty
//            throw new MissingInputException("Username and/or password is missing");
//        }
//        Customer user = em.find(Customer.class, u.getUsername());
//        if (user == null) {
//            throw new UserNotFoundException(String.format("User with username: (%s) not found", u.getUsername()));
//        }
//            user.setUserPass(u.getPassword());
//        try {
//            em.getTransaction().begin();
//            user.setLastEdited(new Date()); //We changed the date of last edited
//            em.merge(user);
//            em.getTransaction().commit();
//        } finally {
//            em.close();
//        }
//        return new CustomerDTO(user);
//    }
//        public void deleteUser(String username, String password) throws UserNotFoundException, AuthenticationException {
//        EntityManager em = getEntityManager();
//        Customer user = instance.getVeryfiedCustomer(username, password);
//        
//        if (user == null || !user.verifyPassword(password)) {
//            throw new UserNotFoundException(String.format("User with username: (%s) not found", user.getUserName()));
//        }
//        try {
//            em.getTransaction().begin();
//           Customer u = em.find(Customer.class, user.getUserName());
//            em.remove(u);
//            em.getTransaction().commit();
//        } finally {
//            em.close();
//        }
////        return new UserDTO(user);
//
//    }
//        public void deleteUserAdmin(String username) throws UserNotFoundException {
//        EntityManager em = getEntityManager();
//        Customer user = em.find(Customer.class, username);
//        if (user == null) {
//            throw new UserNotFoundException(String.format("User with username: (%s) not found", user.getUserName()));
//        }
//        try {
//            em.getTransaction().begin();
//            em.remove(user);
//            em.getTransaction().commit();
//        } finally {
//            em.close();
//        }
////        return new UserDTO(user);
//
//    }
         
         
         
}
