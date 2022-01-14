package Proiect.ProiectWeb.Repo;

import Proiect.ProiectWeb.Tables.Guests;
import Proiect.ProiectWeb.Tables.Reservation;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReservationRepository extends CrudRepository<Reservation, Long> {
    List<Reservation> findAllByGuestReservation(Guests guest);
}
