package pl.test4_api.appointment;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import pl.test4_api.appointment.model.Appointment;
import pl.test4_api.common.Reason;
import pl.test4_api.doctor.model.Doctor;

import java.time.LocalDateTime;
import java.util.List;

public interface AppointmentRepository extends JpaRepository<Appointment, Integer>, JpaSpecificationExecutor<Appointment> {

    boolean existsByDoctorAndDateAfterAndDateBefore(Doctor doctor, LocalDateTime from, LocalDateTime to);

    List<Appointment> findAllByReasonsContaining(Reason reason);

}
