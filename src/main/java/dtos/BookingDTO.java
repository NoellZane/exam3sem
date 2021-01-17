
package dtos;

import entities.Booking;

/**
 *
 * @author Noell Zane
 */
public class BookingDTO {
    private int ID;
    private String startDate;
    private int numberOfNights;
    private String customerUsername;
    private int hotelID;

    public BookingDTO(int bookingID, String startDate, int numberOfNights, String customerUsername, int hotelID) {
        this.ID = bookingID;
        this.startDate = startDate;
        this.numberOfNights = numberOfNights;
        this.customerUsername = customerUsername;
        this.hotelID = hotelID;
    }


    
    public BookingDTO(Booking booking){
        this.ID = booking.getId();
        this.startDate = booking.getStartDate();
        this.numberOfNights = booking.getNumberOfNights();
        this.customerUsername = booking.getCustomer().getUserName();
        this.hotelID = booking.getHotel().getId();
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

    public String getCustomerUsername() {
        return customerUsername;
    }

    public void setCustomerUsername(String customerUsername) {
        this.customerUsername = customerUsername;
    }

    public int getHotelID() {
        return hotelID;
    }

    public void setHotelID(int hotelID) {
        this.hotelID = hotelID;
    }
    
    
    
}
