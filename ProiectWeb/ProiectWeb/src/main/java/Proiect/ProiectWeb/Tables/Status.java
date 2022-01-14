package Proiect.ProiectWeb.Tables;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "status")
public class Status {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "status_type")
    private String statusType;
    ///////////////////////////////////////////////

    @OneToMany(mappedBy = "hotelStatus")
    private Set<Hotel> hotelName;

    ///////////////////////////////////////////////
    public Status(){}

    public Status(Long id, String statusType)
    {
        this.id = id;
        this.statusType = statusType;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getStatusType() {
        return statusType;
    }

    public void setStatusType(String statusType) {
        this.statusType = statusType;
    }

    public Set<Hotel> getHotelName() {
        return hotelName;
    }

    public void setHotelName(Set<Hotel> statusHotel) {
        this.hotelName = statusHotel;
    }

    public void update (Status status) {
        this.statusType = status.getStatusType();
    }
}
