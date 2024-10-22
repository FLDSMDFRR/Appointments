package pl.test4_api.patient;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class PatientController {

    private final PatientService patientService;

}
