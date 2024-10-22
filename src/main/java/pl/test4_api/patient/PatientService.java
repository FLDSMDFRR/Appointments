package pl.test4_api.patient;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.test4_api.patient.model.Patient;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PatientService {

    private final PatientRepository patientRepository;

    public List<Patient> getAll() {
        return patientRepository.findAll();
    }

    public Patient getById(int id) {
        return patientRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Teacher not found"));
    }

}
