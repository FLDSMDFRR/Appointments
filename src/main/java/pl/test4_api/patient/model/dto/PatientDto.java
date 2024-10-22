package pl.test4_api.patient.model.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import pl.test4_api.patient.model.Patient;

import java.util.Objects;

@Getter
@Setter
@Builder
public class PatientDto {
    private int id;
    private String firstName;
    private String lastName;

    public static PatientDto fromEntity(Patient patient) {
        return PatientDto.builder()
                .id(patient.getId())
                .firstName(patient.getFirstName())
                .lastName(patient.getLastName())
                .build();
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        PatientDto that = (PatientDto) object;
        return id == that.id && Objects.equals(firstName, that.firstName) && Objects.equals(lastName, that.lastName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, lastName);
    }
}
