package Proiect.ProiectWeb.Tables;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "hotel")
public class Hotel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "hotel_name", nullable = false)
    private String hotelName;

    @Column(name = "hotel_capacity", nullable = false)
    private long hotelCapacity;

    @Column(name = "stars")
    private int stars;
    ////////////////////////////////////////////////////////////

    @OneToMany(mappedBy = "hotelRooms")
    private Set<Rooms> rooms;

    @OneToMany(mappedBy = "reservations")
    private Set<Reservation> hotelReservations;

    @ManyToOne(fetch = FetchType.LAZY)
    private Status hotelStatus;

    @OneToMany(mappedBy = "staffHotel")
    private Set<Staff> hotelStaff;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "hotel_guests",
            joinColumns = @JoinColumn(name = "hotel_id"),
            inverseJoinColumns = @JoinColumn(name = "guests_id"))
    private Set<Guests> hotelGuests;

    /////////////////////////////////////////////////////////////////
    public Hotel(){}

    public Hotel(String hotelName,Long hotelCapacity, int stars, Status hotelStatus){
        this.hotelName = hotelName;
        this.hotelCapacity = hotelCapacity;
        this.stars = stars;
        this.hotelStatus = hotelStatus;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getHotelName() {
        return hotelName;
    }

    public void setHotelName(String hotelName) {
        this.hotelName = hotelName;
    }

    public long getHotelCapacity() {
        return hotelCapacity;
    }

    public void setHotelCapacity(long hotelCapacity) {
        this.hotelCapacity = hotelCapacity;
    }

    public int getStars() {
        return stars;
    }

    public void setStars(int stars) {
        this.stars = stars;
    }

    public Set<Rooms> getRooms() {
        return rooms;
    }

    public void setRooms(Set<Rooms> rooms) {
        this.rooms = rooms;
    }

    public Status getHotelStatus() {
        return hotelStatus;
    }

    public void setHotelStatus(Status hotelStatus) {
        this.hotelStatus = hotelStatus;
    }

    public Set<Guests> getHotelGuests() {
        return hotelGuests;
    }

    public void addHotelGuests(Guests guest){
        hotelGuests.add(guest);
    }

    public void removeHotelGuests(Guests guest){
        hotelGuests.remove(guest);
    }

    public void addRoom(Rooms room){
        rooms.add(room);
    }

    public void removeRoom(Rooms room){
        rooms.remove(room);
    }

    public void setHotelGuests(Set<Guests> hotelGuests) {
        this.hotelGuests = hotelGuests;
    }

    public Set<Reservation> getHotelReservations() {
        return hotelReservations;
    }

    public void setHotelReservations(Set<Reservation> hotelReservations) {
        this.hotelReservations = hotelReservations;
    }

    public Set<Staff> getHotelStaff() {
        return hotelStaff;
    }

    public void setHotelStaff(Set<Staff> hotelStaff) {
        this.hotelStaff = hotelStaff;
    }

    public void removeHotelStaff(Staff staff){
        hotelStaff.remove(staff);
    }

    public void update (Hotel hotel) {
        this.hotelName = hotel.getHotelName();
        this.stars = hotel.getStars();
    }
}
