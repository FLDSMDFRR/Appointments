package pl.test4_api.doctor;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.test4_api.doctor.model.Doctor;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DoctorService {

    private final DoctorRepository doctorRepository;

    public List<Doctor> getAll() {
        return doctorRepository.findAll();
    }
    public Doctor getById(int id) {
        return doctorRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Teacher not found"));
    }

    public void create(Doctor doctor) {
        doctorRepository.save(doctor);
    }
}
