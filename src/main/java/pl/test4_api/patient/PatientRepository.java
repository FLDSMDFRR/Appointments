package pl.test4_api.patient;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.test4_api.patient.model.Patient;

public interface PatientRepository extends JpaRepository<Patient, Integer> {

}
