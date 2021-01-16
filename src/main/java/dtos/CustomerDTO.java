package dtos;
import entities.Customer;

/**
 *
 * @author Noell Zane
 */
public class CustomerDTO {
    private String name;
    private String username;
    private String password;
    private String phone;

    public CustomerDTO(String name, String username, String password, String phone) {
        this.name = name;
        this.username = username;
        this.password = password;
        this.phone = phone;
    }
    
    
    
    public CustomerDTO(Customer c) {
        this.name = c.getName();
        this.username = c.getUserName();
        this.password = c.getUserPass();
        this.phone = c.getPhone();
    }

    public CustomerDTO(String username) {
        this.username = username;
    }



    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
    
    
    
}
