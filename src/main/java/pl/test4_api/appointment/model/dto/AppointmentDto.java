package pl.test4_api.appointment.model.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import pl.test4_api.appointment.model.Appointment;
import pl.test4_api.common.Reason;
import pl.test4_api.doctor.model.Doctor;
import pl.test4_api.patient.model.Patient;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Set;

@Getter
@Setter
@Builder
public class AppointmentDto {
    private int id;
    private Doctor doctor;
    private Patient patient;
    private LocalDateTime date;
    private int duration;
    private Set<Reason> reasons;

    public static AppointmentDto fromEntity(Appointment appointment) {
        return AppointmentDto.builder()
                .id(appointment.getId())
                .doctor(appointment.getDoctor())
                .patient(appointment.getPatient())
                .date(appointment.getDate())
                .duration(appointment.getDuration())
                .reasons(appointment.getReasons())
                .build();
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        AppointmentDto that = (AppointmentDto) object;
        return id == that.id && duration == that.duration && Objects.equals(doctor, that.doctor) && Objects.equals(patient, that.patient) && Objects.equals(date, that.date) && Objects.equals(reasons, that.reasons);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, doctor, patient, date, duration, reasons);
    }
}
