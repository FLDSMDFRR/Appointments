package pl.test4_api.appointment;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.test4_api.appointment.model.Appointment;
import pl.test4_api.appointment.model.dto.AppointmentDto;
import pl.test4_api.common.Reason;
import pl.test4_api.common.exception.TermUnavailableException;
import pl.test4_api.doctor.DoctorRepository;
import pl.test4_api.doctor.model.Doctor;
import pl.test4_api.patient.PatientRepository;
import pl.test4_api.patient.model.Patient;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AppointmentService {

    private final AppointmentRepository appointmentRepository;

    private final DoctorRepository doctorRepository;

    private final PatientRepository patientRepository;

    public List<Appointment> getAll() {
        return appointmentRepository.findAll();
    }

    @Transactional
    public void create(Appointment appointment, int doctorId, int patientId) {
        int duration = appointment.getDuration();

        LocalDateTime date = appointment.getDate();
        if (date.isBefore(LocalDateTime.now())) {
            throw new TermUnavailableException("Appointment cannot be planned in past");
        }
        Doctor doctor = doctorRepository.findWithLockingById(doctorId)
                .orElseThrow(() -> new EntityNotFoundException("Doctor not found"));
        if (appointmentRepository.existsByDoctorAndDateAfterAndDateBefore(doctor, date.minusMinutes(duration), date.plusMinutes(duration))) {
            throw new TermUnavailableException("The doctor is busy for the selected date!");
        }
        Patient patient = patientRepository.findById(patientId)
                .orElseThrow(() -> new EntityNotFoundException("Patient not found"));
        appointment.setDoctor(doctor);
        appointment.setPatient(patient);
        appointment.setDate(date);
        appointment.setDuration(duration);
        appointmentRepository.save(appointment);
    }

    public List<Appointment> getAllByReason(Reason reason) {
        return appointmentRepository.findAllByReasonsContaining(reason);
    }

    public List<Appointment> filterAppointments(Integer doctorId, Integer patientId, String dateFrom, String dateTo) {
        List<Appointment> appointments = appointmentRepository.findAll();

        if (doctorId != null) {
            Doctor doctor = doctorRepository.findById(doctorId)
                    .orElseThrow(() -> new EntityNotFoundException("Nie znaleziono lekarza"));
            appointments = appointments.stream()
                    .filter(a -> a.getDoctor().equals(doctor))
                    .collect(Collectors.toList());
        }

        if (patientId != null) {
            Patient patient = patientRepository.findById(patientId)
                    .orElseThrow(() -> new EntityNotFoundException("Nie znaleziono pacjenta"));
            appointments = appointments.stream()
                    .filter(a -> a.getPatient().equals(patient))
                    .collect(Collectors.toList());
        }

        try {
            if (dateFrom != null && !dateFrom.isEmpty() && dateTo != null && !dateTo.isEmpty()) {
                LocalDateTime from = LocalDateTime.parse(dateFrom);
                LocalDateTime to = LocalDateTime.parse(dateTo);
                appointments = appointments.stream()
                        .filter(a -> !a.getDate().isBefore(from) && !a.getDate().isAfter(to))
                        .collect(Collectors.toList());
            }
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Niepoprawny format daty", e);
        }

        return appointments;
    }

    public List<Appointment> sortAppointments(List<Appointment> appointments, String sortField, String direction) {
        if (sortField != null && !sortField.isEmpty()) {
            Comparator<Appointment> comparator = null;

            switch (sortField) {
                case "date":
                    comparator = Comparator.comparing(Appointment::getDate);
                    break;
                case "doctor":
                    comparator = Comparator.comparing(a -> a.getDoctor().getLastName());
                    break;
                case "patient":
                    comparator = Comparator.comparing(a -> a.getPatient().getLastName());
                    break;
                case "duration":
                    comparator = Comparator.comparing(Appointment::getDuration);
                    break;
                default:
                    break;
            }

            if (comparator != null) {
                if ("desc".equalsIgnoreCase(direction)) {
                    comparator = comparator.reversed();
                }
                appointments = appointments.stream()
                        .sorted(comparator)
                        .collect(Collectors.toList());
            }
        }
        return appointments;
    }
}
