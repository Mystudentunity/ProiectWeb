package Proiect.ProiectWeb.Controller;

import Proiect.ProiectWeb.Repo.*;
import Proiect.ProiectWeb.Tables.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.Collection;
import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping("/api")
public class ProjectController {
    @Autowired
    private GuestsRepository guestsRepository;

    @Autowired
    private HotelRepository hotelRepository;

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private StatusRepository statusRepository;

    @Autowired
    private RoomsRepository roomsRepository;

    @Autowired
    private StaffRepository staffRepository;

    @GetMapping(path = "/guests", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Iterable<Guests>> getGuests() {
        Collection<Guests> persons = (Collection<Guests>) guestsRepository.findAll();
        if (!persons.isEmpty()) {
            return ResponseEntity.ok(persons);
        } else {
            return ResponseEntity.noContent().build();
        }
    }

    @GetMapping(value = "/guests/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Guests> getPerson(@PathVariable("id") long id) {
        Optional<Guests> person = guestsRepository.findById(id);
        if (person.isPresent()) {
            return ResponseEntity.ok(person.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping(path = "/guests/{id}")
    public ResponseEntity<Void> changeGuest(@PathVariable("id") long id, @RequestBody Guests entity) {
        Optional<Guests> existingGuest = guestsRepository.findById(id);
        if (existingGuest.isPresent()) {
            existingGuest.get().update(entity);
            guestsRepository.save(existingGuest.get());
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping(path="/guest/{guestId}/reservation/{reservationId}")
    public ResponseEntity<Void> removeGuestReservation(@PathVariable("guestId") long guestId,
                                                       @PathVariable("reservationId") long reservationId) {
        Optional<Guests> guest = guestsRepository.findById(guestId);
        if (guest.isPresent()) {
            Optional<Reservation> room = reservationRepository.findById(reservationId);
            if(room.isPresent()) {
                guest.get().removeGuestReservation(room.get());
                guestsRepository.save(guest.get());
                return ResponseEntity.noContent().build();
            } else {
                return ResponseEntity.badRequest().build();
            }
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping(path="/hotels", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Iterable<Hotel>> getHotels() {
        Collection<Hotel> hotels = (Collection<Hotel>) hotelRepository.findAll();
        if (hotels.isEmpty()) {
            return ResponseEntity.ok(hotels);
        } else {
            return ResponseEntity.noContent().build();
        }
    }

    @GetMapping(value = "/hotels/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Hotel> getHotel(@PathVariable("id") long id) {
        Optional<Hotel> hotel = hotelRepository.findById(id);
        if (hotel.isPresent()) {
            return ResponseEntity.ok(hotel.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping(path="/hotels")
    public ResponseEntity<Void> addHotel(String name, long capacity, int stars, String hotelStatus) {
        Collection<Status> status = (Collection<Status>) statusRepository.findAll();
        if(!status.isEmpty()) {
            for(Status s : status)
                if(s.getStatusType().equals(hotelStatus)) {
                    Hotel hotel = new Hotel(name, capacity, stars, s);
                    hotelRepository.save(hotel);
                    URI uri = WebMvcLinkBuilder.linkTo(ProjectController.class).slash("hotels").slash(hotel.getId()).toUri();
                    return ResponseEntity.created(uri).build();
                }
        } else {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.badRequest().build();
    }

    @PutMapping(path="/hotel/{id}")
    public ResponseEntity<Void> changeHotel(@PathVariable("id") long id, @RequestBody Hotel entity) {
        Optional<Hotel> existingHotel = hotelRepository.findById(id);
        if(existingHotel.isPresent()) {
            existingHotel.get().update(entity);
            hotelRepository.save(existingHotel.get());
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping(path="/hotel/{hotelId}/guests/{guestId}")
    public ResponseEntity<Void> addHotelGuest(@PathVariable("hotelId") long hotelId,
                                              @PathVariable("guestId") long guestId) {
        Optional<Hotel> hotel = hotelRepository.findById(hotelId);
        if (hotel.isPresent()) {
            Optional<Guests> guest = guestsRepository.findById(guestId);
            if(guest.isPresent()) {
                hotel.get().addHotelGuests(guest.get());
                hotelRepository.save(hotel.get());
                return ResponseEntity.noContent().build();
            } else {
                return ResponseEntity.badRequest().build();
            }
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping(path="/hotel/{hotelId}/guests")
    public ResponseEntity<Set<Guests>> getHotelGuests(@PathVariable("hotelId") long hotelId) {
        Optional<Hotel> hotel = hotelRepository.findById(hotelId);
        if (hotel.isPresent()) {
            Set<Guests> guests = hotel.get().getHotelGuests();
            if(!guests.isEmpty()) {
                return ResponseEntity.ok(guests);
            } else {
                return ResponseEntity.noContent().build();
            }
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping(path="/hotel/{hotelId}/guest/{guestId}")
    public ResponseEntity<Void> removeHotelGuest(@PathVariable("hotelId") long hotelId,
                                                 @PathVariable("guestId") long guestId) {
        Optional<Hotel> hotel = hotelRepository.findById(hotelId);
        if (hotel.isPresent()) {
            Optional<Guests> guest = guestsRepository.findById(guestId);
            if(guest.isPresent()) {
                hotel.get().removeHotelGuests(guest.get());
                hotelRepository.save(hotel.get());
                return ResponseEntity.noContent().build();
            } else {
                return ResponseEntity.badRequest().build();
            }
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping(path="/hotel/{hotelId}/rooms/{roomId}")
    public ResponseEntity<Void> addHotelRoom(@PathVariable("hotelId") long hotelId,
                                             @PathVariable("roomId") long roomId) {
        Optional<Hotel> hotel = hotelRepository.findById(hotelId);
        if (hotel.isPresent()) {
            Optional<Rooms> room = roomsRepository.findById(roomId);
            if(room.isPresent()) {
                hotel.get().addRoom(room.get());
                hotelRepository.save(hotel.get());
                return ResponseEntity.noContent().build();
            } else {
                return ResponseEntity.badRequest().build();
            }
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping(path="/hotel/{hotelId}/rooms")
    public ResponseEntity<Set<Rooms>> getHotelRooms(@PathVariable("hotelId") long hotelId) {
        Optional<Hotel> hotel = hotelRepository.findById(hotelId);
        if (hotel.isPresent()) {
            Set<Rooms> rooms = hotel.get().getRooms();
            if(!rooms.isEmpty()) {
                return ResponseEntity.ok(rooms);
            } else {
                return ResponseEntity.noContent().build();
            }
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping(path="/hotel/{hotelId}/room/{roomId}")
    public ResponseEntity<Void> removeHotelRoom(@PathVariable("hotelId") long hotelId,
                                                @PathVariable("roomId") long roomId) {
        Optional<Hotel> hotel = hotelRepository.findById(hotelId);
        if (hotel.isPresent()) {
            Optional<Rooms> room = roomsRepository.findById(roomId);
            if(room.isPresent()) {
                hotel.get().removeRoom(room.get());
                hotelRepository.save(hotel.get());
                return ResponseEntity.noContent().build();
            } else {
                return ResponseEntity.badRequest().build();
            }
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping(path="/rooms", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Iterable<Rooms>> getRooms() {
        Collection<Rooms> rooms = (Collection<Rooms>) roomsRepository.findAll();
        if (rooms.isEmpty()) {
            return ResponseEntity.ok(rooms);
        } else {
            return ResponseEntity.noContent().build();
        }
    }

    @GetMapping(value = "/rooms/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Rooms> getRoom(@PathVariable("id") long id) {
        Optional<Rooms> rooms = roomsRepository.findById(id);
        if (rooms.isPresent()) {
            return ResponseEntity.ok(rooms.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping(path="/rooms")
    public ResponseEntity<Void> addRooms(String type, String roomStatus, long hotelId) {
        Collection<Status> status = (Collection<Status>) statusRepository.findAll();
        Optional<Hotel> hotel = hotelRepository.findById(hotelId);
        if(!status.isEmpty() && hotel.isPresent()) {
            for(Status s : status)
                if(s.getStatusType().equals(roomStatus)) {
                    Rooms rooms = new Rooms(type, s, hotel.get());
                    roomsRepository.save(rooms);
                    URI uri = WebMvcLinkBuilder.linkTo(ProjectController.class).slash("rooms").slash(rooms.getId()).toUri();
                    return ResponseEntity.created(uri).build();
                }
        } else {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.badRequest().build();
    }

    @PutMapping(path="/rooms/{id}")
    public ResponseEntity<Void> changeRoom(@PathVariable("id") long id, @RequestBody Rooms entity) {
        Optional<Rooms> existingRoom = roomsRepository.findById(id);
        if(existingRoom.isPresent()) {
            existingRoom.get().update(entity);
            roomsRepository.save(existingRoom.get());
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping(path="/status", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Iterable<Status>> getStatus() {
        Collection<Status> status = (Collection<Status>) statusRepository.findAll();
        if (status.isEmpty()) {
            return ResponseEntity.ok(status);
        } else {
            return ResponseEntity.noContent().build();
        }
    }

    @GetMapping(value = "/status/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Status> getStatus(@PathVariable("id") long id) {
        Optional<Status> status = statusRepository.findById(id);
        if (status.isPresent()) {
            return ResponseEntity.ok(status.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping(path="/status")
    public ResponseEntity<Void> addStatus(long id, String statusType) {
        Status status = new Status(id, statusType);
        statusRepository.save(status);
        URI uri = WebMvcLinkBuilder.linkTo(ProjectController.class).slash("status").slash(status.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

    @PutMapping(path="/status/{id}")
    public ResponseEntity<Void> changeStatus(@PathVariable("id") long id, @RequestBody Status entity) {
        Optional<Status> existingStatus = statusRepository.findById(id);
        if(existingStatus.isPresent()) {
            existingStatus.get().update(entity);
            statusRepository.save(existingStatus.get());
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping(path="/staff", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Iterable<Staff>> getStaff() {
        Collection<Staff> staff = (Collection<Staff>) staffRepository.findAll();
        if (staff.isEmpty()) {
            return ResponseEntity.ok(staff);
        } else {
            return ResponseEntity.noContent().build();
        }
    }

    @GetMapping(value = "/staff/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Staff> getStaff(@PathVariable("id") long id) {
        Optional<Staff> staff = staffRepository.findById(id);
        if (staff.isPresent()) {
            return ResponseEntity.ok(staff.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping(path="/staff")
    public ResponseEntity<Void> addStaff (String name, String role, long hotelId) {
        Optional<Hotel> hotel = hotelRepository.findById(hotelId);
        if(hotel.isPresent()) {
            Staff staff = new Staff(name, role, hotel.get());
            staffRepository.save(staff);
            URI uri = WebMvcLinkBuilder.linkTo(ProjectController.class).slash("staff").slash(staff.getId()).toUri();
            return ResponseEntity.created(uri).build();
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping(path="/staff/{id}")
    public ResponseEntity<Void> changeStaff(@PathVariable("id") long id, @RequestBody Staff entity) {
        Optional<Staff> existingStaff = staffRepository.findById(id);
        if(existingStaff.isPresent()) {
            existingStaff.get().update(entity);
            staffRepository.save(existingStaff.get());
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping(path = "/staff/{id}")
    public ResponseEntity<Void> removeStaff(@PathVariable("id") long id) {
        Optional<Staff> existingStaff = staffRepository.findById(id);
        if(existingStaff.isPresent()) {
            Hotel hotel = existingStaff.get().getStaffHotel();
            hotel.removeHotelStaff(existingStaff.get());
            hotelRepository.save(hotel);
            staffRepository.delete(existingStaff.get());
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping(path="/staff/{staffId}/hotel")
    public ResponseEntity<Hotel> getStaffHotel(@PathVariable("staffId") long staffId) {
        Optional<Staff> staff = staffRepository.findById(staffId);
        if (staff.isPresent()) {
            Hotel hotel = staff.get().getStaffHotel();
            if(hotel != null) {
                return ResponseEntity.ok(hotel);
            } else {
                return ResponseEntity.noContent().build();
            }
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping(path="/staff/{staffId}/hotel/{hotelId}")
    public ResponseEntity<Void> changeStaffHotel(@PathVariable("staffId") long staffId,
                                                 @PathVariable("hotelId") long hotelId) {
        Optional<Staff> staff = staffRepository.findById(staffId);
        if (staff.isPresent()) {
            Optional<Hotel> hotel = hotelRepository.findById(hotelId);
            if(hotel.isPresent())
            {
                staff.get().updateHotelStaff(hotel.get());
                staffRepository.save(staff.get());
                return ResponseEntity.noContent().build();
            } else {
                return ResponseEntity.badRequest().build();
            }
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
