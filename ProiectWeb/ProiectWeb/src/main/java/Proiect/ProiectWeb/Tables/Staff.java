package Proiect.ProiectWeb.Tables;

import javax.persistence.*;

@Entity
@Table(name = "staff")
public class Staff {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "staff_name", nullable = false)
    private String staffName;

    @Column(name = "staf_role")
    private String staffRole;
    ///////////////////////////////////

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hotel_id")
    private Hotel staffHotel;

    //////////////////////////////////
    public Staff(){}

    public Staff(String staffName, String staffRole, Hotel staffHotel)
    {
        this.staffName = staffName;
        this.staffRole = staffRole;
        this.staffHotel = staffHotel;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getStaffName() {
        return staffName;
    }

    public void setStaffName(String staffName) {
        this.staffName = staffName;
    }

    public String getStaffRole() {
        return staffRole;
    }

    public void setStaffRole(String staffRole) {
        this.staffRole = staffRole;
    }

    public Hotel getStaffHotel() {
        return staffHotel;
    }

    public void setStaffHotel(Hotel staffHotel) {
        this.staffHotel = staffHotel;
    }

    public void updateHotelStaff(Hotel staffHotel) {
        this.staffHotel = staffHotel;
    }

    public void update (Staff staff) {
        this.staffName = staff.getStaffName();
        this.staffRole = staff.getStaffRole();
    }
}
