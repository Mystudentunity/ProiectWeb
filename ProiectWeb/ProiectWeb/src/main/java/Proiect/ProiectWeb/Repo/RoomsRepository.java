package Proiect.ProiectWeb.Repo;

import Proiect.ProiectWeb.Tables.Rooms;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoomsRepository extends CrudRepository<Rooms, Long> {
}