package Proiect.ProiectWeb.Service;

import Proiect.ProiectWeb.Repo.*;
import Proiect.ProiectWeb.Tables.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collection;
import java.util.List;
import java.util.Optional;


@Service
public class OnlineService {
    @Autowired
    private RoomsRepository roomsRepository;
    @Autowired
    private HotelRepository hotelRepository;
    @Autowired
    private GuestsRepository guestsRepository;
    @Autowired
    private StatusRepository statusRepository;
    @Autowired
    private ReservationRepository reservationRepository;

    public void makeReservation (String hotelName, long phone, String checkIn, String checkOut,long roomId){
        Guests guest = getGuest(phone);
        Hotel hotel = getHotel(hotelName);
        Rooms room = getRoom(roomId);
        //Status status;

        if(!hotel.getHotelStatus().getStatusType().equals("ok")){
            throw OnlineException.hotelIsFull();
        }
        if(!room.getRoomStatus().getStatusType().equals("ok")){
            throw OnlineException.roomIsOccupied();
        }
        /*
        if(hotel.getHotelCapacity() <= getHotelCurrentCapacity(hotel.getId()) + 1){
            if(getStatuses("full").equals(true)){
                status = getStatuses("full");
                hotel.setHotelStatus(status);
            }
        }*/

        Reservation reservation = new Reservation();
        reservation.setRoomsReservation(room);
        reservation.setCheckIn(checkIn);
        reservation.setCheckOut(checkOut);
        reservation.setReservations(hotel);
        reservation.setGuestReservation(guest);

        try{
            reservation = reservationRepository.save(reservation);
            if(reservation == null){
                throw OnlineException.reservationCouldNotBeSaved();
            }
        }catch (RuntimeException e){
            if(e instanceof OnlineException){
                throw e;
            }else {
                throw OnlineException.reservationCouldNotBeProcessed();
            }
        }
    }

    public List<Reservation> getGuestReservations(long phone){
        Guests guest = getGuest(phone);
        return reservationRepository.findAllByGuestReservation(guest);
    }

    @Transactional
    public void saveGuest(Guests guest){
        Optional<Guests> existingGuest = guestsRepository.findByPhone(guest.getPhone());
        if(existingGuest.isPresent()){
            throw OnlineException.guestWithSamePhoneAlreadyExists();
        }
        try{
            guestsRepository.save(guest);
        } catch (RuntimeException e){
            if (e instanceof OnlineException){
                throw e;
            } else {
                throw OnlineException.guestCouldNotBeSaved();
            }
        }
    }

    @Transactional
    public void removeGuest(long phone){
        try{
            guestsRepository.delete(guestsRepository.findByPhone(phone).get());
        } catch (RuntimeException e){
            if (e instanceof OnlineException){
                throw e;
            } else {
                throw OnlineException.guestCouldNotBeRemoved();
            }
        }
    }

    private Guests getGuest(long phone){
        Optional<Guests> guest = guestsRepository.findByPhone(phone);
        if (guest.isEmpty()){
            throw OnlineException.guestNotFound();
        }
        return guest.get();
    }

    private Hotel getHotel(String hotelName){
        Optional<Hotel> hotel = hotelRepository.findByHotelName(hotelName);
        if (hotel.isEmpty()){
            throw OnlineException.hotelNotFound();
        }
        return hotel.get();
    }

    private Rooms getRoom(long id){
        Optional<Rooms> room = roomsRepository.findById(id);
        if(room.isEmpty()){
            throw OnlineException.roomNotFound();
        }
        return room.get();
    }

    private Status getStatus(String hotelName){
        Optional<Status> status = statusRepository.findByHotelName(hotelName);
        if(status.isEmpty()){
            throw OnlineException.statusesNotFound();
        }
        return status.get();
    }

    private Status getStatuses(String status){
        Collection<Status> statuses = (Collection<Status>) statusRepository.findAll();
        if(statuses.isEmpty()){
            throw OnlineException.statusesNotFound();
        } else {
            for (Status s : statuses){
                if (!s.getStatusType().equals(status)){
                    return s;
                }
            }
        }
        throw OnlineException.statusNotFound();
    }

    private long getHotelCurrentCapacity(long hotelId){
        int currentCapacity = 0;
        Collection<Reservation> reservations = (Collection<Reservation>) reservationRepository.findAll();
        if(reservations.isEmpty()){
            throw OnlineException.reservationNotFound();
        }
        else {
            for (Reservation r : reservations) {
                if (r.getId() == hotelId) {
                    currentCapacity++;
                }
            }
        }
        return currentCapacity;
    }
}
