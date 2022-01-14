package Proiect.ProiectWeb.Tables;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table
public class Guests {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "phone", nullable = false)
    private long phone;
    /////////////////////////////////////////////////////

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "guest_reservation",
            joinColumns = @JoinColumn(name = "reservation_id"),
            inverseJoinColumns = @JoinColumn(name = "guest_id"))
    private Set<Guests> guestReservation;

    ////////////////////////////////////////////////////
    public Guests(){}

    public Guests(Long id, String firstName, String lastName, long phone){
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public long getPhone() {
        return phone;
    }

    public void setPhone(long phone) {
        this.phone = phone;
    }

    public Set<Guests> getGuestReservation() {
        return guestReservation;
    }

    public void setGuestReservation(Set<Guests> guestReservation) {
        this.guestReservation = guestReservation;
    }

    public void removeGuestReservation(Reservation reservation){
        guestReservation.remove(reservation);
    }

    public void update (Guests guests) {
        this.firstName = guests.getFirstName();
        this.lastName = guests.getLastName();
        this.phone = guests.getPhone();
    }

}
