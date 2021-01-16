package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dtos.BookingDTO;
import dtos.CustomerDTO;
import entities.Customer;
import errorhandling.MissingInputException;
import errorhandling.UserAlreadyExistsException;
import facades.CustomerFacade;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;
import javax.annotation.security.RolesAllowed;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import javax.ws.rs.Consumes;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.SecurityContext;
import utils.EMF_Creator;

/**
 * @author lam@cphbusiness.dk
 */
@Path("info")
public class CustomerResource {
    
    private static final EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory();
    @Context
    private UriInfo context;
    private static final CustomerFacade facade = CustomerFacade.getUserFacade(EMF);
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    @Context
    SecurityContext securityContext;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getInfoForAll() {
        return "{\"msg\":\"Hello anonymous\"}";
    }

    //Just to verify if the database is setup
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("all")
    public String allCustomers() {

        EntityManager em = EMF.createEntityManager();
        try {
            TypedQuery<Customer> query = em.createQuery ("select u from User u",entities.Customer.class);
            List<Customer> users = query.getResultList();
            return "[" + users.size() + "]";
        } finally {
            em.close();
        }
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("user")
    @RolesAllowed("user")
    public String getFromUser() {
        String thisuser = securityContext.getUserPrincipal().getName();
        return "{\"msg\": \"Hello to User: " + thisuser + "\"}";
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("admin")
    @RolesAllowed("admin")
    public String getFromAdmin() {
        String thisuser = securityContext.getUserPrincipal().getName();
        return "{\"msg\": \"Hello to (admin) User: " + thisuser + "\"}";
    }
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces({MediaType.APPLICATION_JSON})
    public String addCustomer(String customer) throws MissingInputException, UserAlreadyExistsException {
        CustomerDTO c = GSON.fromJson(customer, CustomerDTO.class);
        CustomerDTO cAdded = facade.addCustomer(c.getName(),c.getUsername(),c.getPassword(),c.getPhone());
        return GSON.toJson(cAdded);
    }
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces({MediaType.APPLICATION_JSON})
    @Path("booking")
    @RolesAllowed("user")
    public String addBooking(String booking) throws ExecutionException, TimeoutException, InterruptedException, MissingInputException{
        BookingDTO b = GSON.fromJson(booking, BookingDTO.class);
        BookingDTO bAdded = facade.addBooking(b.getStartDate(), b.getNumberOfNights(), b.getCustomerUsername(), b.getHotelID());
        return GSON.toJson(bAdded);

    }
    
    
//    
//    @PUT
//    @Produces({MediaType.APPLICATION_JSON})
//    @Consumes({MediaType.APPLICATION_JSON})
//    @Path("{username}")
//    @RolesAllowed({"user", "admin"})
//
//    public String editUser(@PathParam("username") String username, String user) throws MissingInputException, UserNotFoundException {
//        CustomerDTO u = GSON.fromJson(user, CustomerDTO.class);
//        CustomerDTO uToEdit = new CustomerDTO(u.getUsername(),u.getPassword());
//        CustomerDTO editedUser = facade.editUser(uToEdit);
//        return GSON.toJson(editedUser);
//    }
//    
//    @DELETE
//    @Produces({MediaType.APPLICATION_JSON})
//    @Path("{username}")
//    @RolesAllowed("user")
//
//    public String deleteUser(@PathParam("username") String username, String password) throws UserNotFoundException, AuthenticationException {
//        facade.deleteUser(username,password);
//        return "{\"status\":\"deleted\"}";
//    }
//    
//    @DELETE
//    @Produces({MediaType.APPLICATION_JSON})
//    @Path("admin/{username}")
//    @RolesAllowed("admin")
//    public String deletePerson(@PathParam("username") String username) throws UserNotFoundException {
//        facade.deleteUserAdmin(username);
//        return "{\"status\":\"deleted\"}";
//    }
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("allusers")
    @RolesAllowed("admin")
    public String getAllUsers() {
        return GSON.toJson(facade.getAllUsers());
    }
}