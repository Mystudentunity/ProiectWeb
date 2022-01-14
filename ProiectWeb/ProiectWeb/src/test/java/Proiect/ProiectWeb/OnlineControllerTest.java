package Proiect.ProiectWeb;

import Proiect.ProiectWeb.Controller.OnlineController;
import Proiect.ProiectWeb.Repo.*;
import Proiect.ProiectWeb.Tables.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(controllers = OnlineController.class)
@EnableTransactionManagement
@ComponentScan
public class OnlineControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RoomsRepository roomsRepository;
    @MockBean
    private HotelRepository hotelRepository;
    @MockBean
    private GuestsRepository guestsRepository;
    @MockBean
    private StatusRepository statusRepository;
    @MockBean
    private StaffRepository staffRepository;
    @MockBean
    private ReservationRepository reservationRepository;

    private static Guests guest;
    private static Rooms room;
    private static Hotel hotel;
    private static Status statusType;
    private static Reservation reservation;

    @BeforeAll
    public static void setup(){
        guest = new Guests(1L, "Andrei", "Duta", 133);
        statusType = new Status(1L, "ok");
        hotel = new Hotel( "Constanta", 5L, 4, statusType);
        room = new Rooms("single", statusType, hotel);
        reservation = new Reservation(1L, "2000-01-24", "2000-02-01");
    }

   /* @Test
    public void testGetReservations() throws Exception {
        String endpoint = "/api/reservations?phone=%d";
        mockMvc.perform(get(String.format(endpoint, guest.getPhone()))).andDo(print()).andExpect(status().isOk());
        }*/
}
