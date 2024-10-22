package pl.test4_api.doctor;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import pl.test4_api.doctor.model.Doctor;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/doctors")
public class DoctorController {

    private final DoctorService doctorService;

    @GetMapping
    public String getList(Model model) {
        model.addAttribute("doctors", doctorService.getAll());
        return "doctor/list";
    }

    @GetMapping("/create")
    public String getCreateForm() {
        return "doctor/form";
    }

    @PostMapping("/create")
    public String create(Doctor doctor, Model model) {
        doctorService.create(doctor);
        model.addAttribute("message", "Nauczyciel dodany pomyślnie!");
        return "redirect:/doctors";
    }

}
