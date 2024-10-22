package pl.test4_api.doctor;

import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import pl.test4_api.doctor.model.Doctor;

import java.util.Optional;

public interface DoctorRepository extends JpaRepository<Doctor, Integer> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    Optional<Doctor> findWithLockingById(int id);


}
