package Proiect.ProiectWeb.Repo;

import Proiect.ProiectWeb.Tables.Staff;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StaffRepository extends CrudRepository<Staff, Long> {
}