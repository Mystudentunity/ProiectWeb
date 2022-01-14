package Proiect.ProiectWeb.Tables;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "reservation")
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name="checkIn", nullable = false)
    @JsonFormat(pattern = "yyyy-MM-dd", shape = JsonFormat.Shape.STRING)
    private String checkIn;

    @Column(name="checkOut", nullable = false)
    @JsonFormat(pattern = "yyyy-MM-dd", shape = JsonFormat.Shape.STRING)
    private String checkOut;
    ///////////////////////////////////////////////////////////

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hotel_id")
    private Hotel reservations;

    @OneToOne()
    @JoinColumn(name = "room_reservation", referencedColumnName = "id")
    private Rooms roomsReservation;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "guest_reservation")
    private Guests guestReservation;

    //////////////////////////////////////////////////////////
    public Reservation(){}

    public Reservation(Long id, String checkIn, String checkOut)
    {
        this.id = id;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCheckIn() {
        return checkIn;
    }

    public void setCheckIn(String checkIn) {
        this.checkIn = checkIn;
    }

    public String getCheckOut() {
        return checkOut;
    }

    public void setCheckOut(String checkOut) {
        this.checkOut = checkOut;
    }

    public Hotel getReservations() {
        return reservations;
    }

    public void setReservations(Hotel reservations) {
        this.reservations = reservations;
    }

    public Rooms getRoomsReservation() {
        return roomsReservation;
    }

    public void setRoomsReservation(Rooms roomsReservation) {
        this.roomsReservation = roomsReservation;
    }

    public Guests getGuestReservation() {
        return guestReservation;
    }

    public void setGuestReservation(Guests guestReservation) {
        this.guestReservation = guestReservation;
    }

    public void update (Reservation reservation) {
        this.checkIn = reservation.getCheckIn();
        this.checkOut = reservation.getCheckOut();
    }
}
