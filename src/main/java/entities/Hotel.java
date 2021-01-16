package entities;

import dtos.HotelDTO;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;


@Entity
@Table(name = "hotel")
@NamedQuery(name = "Hotel.deleteAllRows", query = "DELETE from Hotel")
public class Hotel implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @NotNull

    private Integer id;
    @Column(name = "name", length = 50)
    private String name;
    @Column(name = "address", length = 50)
    private String address;
    @Column(name = "city", length = 50)
    private String city;
    @Column(name = "phone", length = 25)
    private String phone;
    @Column(name = "price", length = 10)
    private String price;
    @Column(name = "content")
    private String content;
    @Column(name = "url",length = 100)
    private String url;
    
    @OneToMany(mappedBy = "hotel", cascade = CascadeType.PERSIST)
    private List<Booking> bookings = new ArrayList();
    
    public Hotel() {
    }
        
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Hotel(Integer id, String name, String address,String city, String phone, String price, String content, String url) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.city = city;
        this.phone = phone;
        this.price = price;
        this.content = content;
        this.url = url;
    }
    
   public Hotel(HotelDTO dto){
        this.id = dto.getId();
        this.name = dto.getName();
        this.address = dto.getAddress();
        this.city = dto.getTitle(); //Title is the name of city in the api
        this.phone = dto.getPhone();
        this.price = dto.getPrice();
        this.content = dto.getContent();
        this.url = dto.getUrl(); 
   }

    
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public List<Booking> getBookings() {
        return bookings;
    }

    public void addBooking(Booking booking){
        bookings.add(booking);
    }
    
    
    
    

   
}
