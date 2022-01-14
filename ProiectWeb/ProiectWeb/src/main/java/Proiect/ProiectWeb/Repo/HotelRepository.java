package Proiect.ProiectWeb.Repo;

import Proiect.ProiectWeb.Tables.Hotel;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface HotelRepository extends CrudRepository<Hotel, Long> {
    Optional<Hotel> findByHotelName(String hotelName);
}