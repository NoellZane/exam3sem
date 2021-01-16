package entities;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name = "creditcard")
@NamedQuery(name = "Creditcard.deleteAllRows", query = "DELETE from Creditcard")
public class Creditcard implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Column(name = "type", length = 16)
    private String type;

    @Id
    @Column(name = "card_number", length = 16)
    private String cardnumber;
    
    @Column(name = "expiration_date", length = 16)
    private String expirationDate;
    
    @Column(name = "name_on_card", length = 50)
    private String nameOnCard;

    @JoinColumn(name = "username")
    @ManyToOne  
    private Customer customer;

    public Creditcard() {
    }

    public Creditcard(String type, String cardnumber, String expirationDate, String nameOnCard, Customer customer) {
        this.type = type;
        this.cardnumber = cardnumber;
        this.expirationDate = expirationDate;
        this.nameOnCard = nameOnCard;
        this.customer = customer;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCardnumber() {
        return cardnumber;
    }

    public void setCardnumber(String cardnumber) {
        this.cardnumber = cardnumber;
    }

    public String getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(String expirationDate) {
        this.expirationDate = expirationDate;
    }

    public String getNameOnCard() {
        return nameOnCard;
    }

    public void setNameOnCard(String nameOnCard) {
        this.nameOnCard = nameOnCard;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
    
    

}
