package Proiect.ProiectWeb.Repo;

import Proiect.ProiectWeb.Tables.Guests;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GuestsRepository extends CrudRepository<Guests, Long> {
    Optional<Guests> findByPhone(long phone);
}