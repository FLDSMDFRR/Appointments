package pl.test4_api.doctor.model.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import pl.test4_api.doctor.model.Doctor;

import java.util.Objects;

@Getter
@Setter
@Builder
public class DoctorDto {
    private int id;
    private String firstName;
    private String lastName;

    public static DoctorDto fromEntity(Doctor doctor) {
        return DoctorDto.builder()
                .id(doctor.getId())
                .firstName(doctor.getFirstName())
                .lastName(doctor.getLastName())
                .build();
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        DoctorDto doctorDto = (DoctorDto) object;
        return id == doctorDto.id && Objects.equals(firstName, doctorDto.firstName) && Objects.equals(lastName, doctorDto.lastName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, lastName);
    }
}
