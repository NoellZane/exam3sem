package entities;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


@Entity
@Table(name = "booking")
@NamedQuery(name = "Booking.deleteAllRows", query = "DELETE from Booking")
public class Booking implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @Column(name = "start_date", length = 16)
    private String startDate;
    @Column(name = "number_of_nights", length = 25)
    private int numberOfNights;
    
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "dateCreated")
    private java.util.Date dateCreated;
    
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "lastEdited")
    private java.util.Date lastEdited;
    
    @JoinColumn(name = "username")
    @ManyToOne
    private Customer customer;
    
    @JoinColumn(name = "hotel_id")
    @ManyToOne
    private Hotel hotel;
    
    
      
    public Booking() {
    }
        
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Booking(String startDate, int numberOfNights, Customer customer, Hotel hotel) {
        this.startDate = startDate;
        this.numberOfNights = numberOfNights;
        this.dateCreated = new Date();
        this.lastEdited = new Date();
        this.customer = customer;
        this.hotel = hotel;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public int getNumberOfNights() {
        return numberOfNights;
    }

    public void setNumberOfNights(int numberOfNights) {
        this.numberOfNights = numberOfNights;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public Date getLastEdited() {
        return lastEdited;
    }

    public void setLastEdited(Date lastEdited) {
        this.lastEdited = lastEdited;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Hotel getHotel() {
        return hotel;
    }

    public void setHotel(Hotel hotel) {
        this.hotel = hotel;
    }
    
}
