package entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.mindrot.jbcrypt.BCrypt;

@Entity
@Table(name = "customers")
public class Customer implements Serializable {

    private static final long serialVersionUID = 1L;
    @Basic(optional = false)
    @NotNull
    @Column(name = "name", length = 50)
    private String name;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "user_name", length = 25)
    private String userName;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "user_pass")
    private String userPass;
    @Basic(optional = false)
    @NotNull
    @Column(name = "phone", length = 25)
    private String phone;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "dateCreated")
    private java.util.Date dateCreated;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "lastEdited")
    private java.util.Date lastEdited;
    @JoinTable(name = "user_roles", joinColumns = {
        @JoinColumn(name = "user_name", referencedColumnName = "user_name")}, inverseJoinColumns = {
        @JoinColumn(name = "role_name", referencedColumnName = "role_name")})
    @ManyToMany
    private List<Role> roleList = new ArrayList<>();
    
    @OneToMany(mappedBy = "customer", cascade = CascadeType.REMOVE)
    private List<Booking> bookings = new ArrayList();

    @OneToMany(mappedBy = "customer", cascade = CascadeType.PERSIST)
    private List<Creditcard> creditcards = new ArrayList();

    public List<String> getRolesAsStrings() {
        if (roleList.isEmpty()) {
            return null;
        }
        List<String> rolesAsStrings = new ArrayList<>();
        roleList.forEach((role) -> {
            rolesAsStrings.add(role.getRoleName());
        });
        return rolesAsStrings;
    }

    public Customer() {
    }

    public boolean verifyPassword(String pw) {
//        return(pw.equals(userPass));
//Check password using Bcrypt.
        return (BCrypt.checkpw(pw, this.userPass));
    }

    public Customer(String name, String userName, String userPass, String phone) {
        this.name = name;
        this.userName = userName;
        this.userPass = BCrypt.hashpw(userPass, BCrypt.gensalt(12));
        this.phone = phone;
        this.dateCreated = new Date();
        this.lastEdited = new Date();
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

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPass() {
        return this.userPass;
    }

    public void setUserPass(String userPass) {
        this.userPass = BCrypt.hashpw(userPass, BCrypt.gensalt(12));//Hash password using BCrypt and gensalt. to generate salt.
    }

    public List<Role> getRoleList() {
        return roleList;
    }

    public void setRoleList(List<Role> roleList) {
        this.roleList = roleList;
    }

    public void addRole(Role userRole) {
        roleList.add(userRole);
    }

    public List<Booking> getBookings() {
        return bookings;
    }

    public List<Creditcard> getCreditcards() {
        return creditcards;
    }

    public void addBooking(Booking booking) {
        bookings.add(booking);
    }

    public void addCreditcard(Creditcard creditcard) {
        creditcards.add(creditcard);
    }

    public void setLastEdited(Date lastEdited) {
        this.lastEdited = lastEdited;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public Date getLastEdited() {
        return lastEdited;
    }

}
