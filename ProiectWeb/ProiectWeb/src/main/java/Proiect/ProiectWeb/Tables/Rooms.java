package Proiect.ProiectWeb.Tables;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "rooms")
public class Rooms {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "room_type")
    private String roomType;
    ////////////////////////////////

    @ManyToOne(fetch = FetchType.LAZY)
    private Hotel hotelRooms;

    @ManyToOne(fetch = FetchType.LAZY)
    private Status roomStatus;

    ///////////////////////////////////////////////////
    public Rooms(){}

    public Rooms(String roomType, Status roomStatus, Hotel hotelRooms)
    {
        this.roomType = roomType;
        this.roomStatus = roomStatus;
        this.hotelRooms = hotelRooms;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getRoomType() {
        return roomType;
    }

    public void setRoomType(String roomType) {
        this.roomType = roomType;
    }

    public Hotel getHotelRooms() {
        return hotelRooms;
    }

    public void setHotelRooms(Hotel hotelRooms) {
        this.hotelRooms = hotelRooms;
    }

    public Status getRoomStatus() {
        return roomStatus;
    }

    public void setRoomStatus(Status roomStatus) {
        this.roomStatus = roomStatus;
    }

    public void update (Rooms rooms) {
        this.roomType = rooms.getRoomType();
    }
}
