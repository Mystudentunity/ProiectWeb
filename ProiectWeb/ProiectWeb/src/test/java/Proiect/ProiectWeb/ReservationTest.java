package Proiect.ProiectWeb;

import Proiect.ProiectWeb.Repo.*;
import Proiect.ProiectWeb.Service.OnlineException;
import Proiect.ProiectWeb.Service.OnlineService;
import Proiect.ProiectWeb.Tables.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ReservationTest {

    @InjectMocks
    private OnlineService onlineService;

    @Mock
    private RoomsRepository roomsRepository;
    @Mock
    private HotelRepository hotelRepository;
    @Mock
    private GuestsRepository guestsRepository;

    private static Optional<Guests> guest;
    private static Optional<Rooms> room;
    private static Optional<Hotel> hotel;
    private static Optional<Status> status;
    private static Optional<Reservation> reservation;

    @BeforeAll
    public static void setup(){
        guest = Optional.of(new Guests(1L, "Mihai", "Pep", 766));
        status = Optional.of(new Status(1L, "ok"));
        hotel = Optional.of(new Hotel("Maria", 3L, 5, status.get()));
        room = Optional.of(new Rooms("double", status.get(), hotel.get()));
        reservation = Optional.of(new Reservation(1L, "2000-01-01", "2000-01-03"));
    }

    @Test
    public void testInvalidGuest(){
        when(guestsRepository.findByPhone(guest.get().getPhone())).thenReturn(Optional.empty());
        OnlineException exception = assertThrows(OnlineException.class,
                () -> onlineService.makeReservation(hotel.get().getHotelName(), guest.get().getPhone(), reservation.get().getCheckIn(),
                        reservation.get().getCheckOut(), room.get().getId()));
        assertEquals(OnlineException.ErrorCode.GUEST_NOT_FOUND, exception.getError());
    }

    @Test
    public void testInvalidHotel(){
        when((guestsRepository.findByPhone(guest.get().getPhone()))).thenReturn(guest);
        when(hotelRepository.findByHotelName(hotel.get().getHotelName())).thenReturn(Optional.empty());
        OnlineException exception = assertThrows(OnlineException.class,
                () -> onlineService.makeReservation(hotel.get().getHotelName(), guest.get().getPhone(), reservation.get().getCheckIn(),
                        reservation.get().getCheckOut(), room.get().getId()));
        assertEquals(OnlineException.ErrorCode.HOTEL_NOT_FOUND, exception.getError());
    }

    @Test
    public void testInvalidRoom(){
        when((guestsRepository.findByPhone(guest.get().getPhone()))).thenReturn(guest);
        when(hotelRepository.findByHotelName(hotel.get().getHotelName())).thenReturn(hotel);
        when(roomsRepository.findById(room.get().getId())).thenReturn(Optional.empty());
        OnlineException exception = assertThrows(OnlineException.class,
                () -> onlineService.makeReservation(hotel.get().getHotelName(), guest.get().getPhone(), reservation.get().getCheckIn(),
                        reservation.get().getCheckOut(), room.get().getId()));
        assertEquals(OnlineException.ErrorCode.ROOM_NOT_FOUND, exception.getError());
    }
}
