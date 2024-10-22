package pl.test4_api.appointment;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import pl.test4_api.appointment.model.Appointment;
import pl.test4_api.appointment.model.dto.AppointmentDto;
import pl.test4_api.common.Reason;
import pl.test4_api.common.exception.TermUnavailableException;
import pl.test4_api.doctor.DoctorService;
import pl.test4_api.patient.PatientService;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/appointments")

public class AppointmentController {

    private final AppointmentService appointmentService;
    private final DoctorService doctorService;
    private final PatientService patientService;

    @GetMapping
    public String listAppointments(@RequestParam(value = "sort", required = false) String sort,
                                   @RequestParam(value = "order", required = false, defaultValue = "asc") String direction,
                                   @RequestParam(value = "doctorId", required = false) Integer doctorId,
                                   @RequestParam(value = "patientId", required = false) Integer patientId,
                                   @RequestParam(value = "dateFrom", required = false) String dateFrom,
                                   @RequestParam(value = "dateTo", required = false) String dateTo,
                                   @RequestParam(value = "reason", required = false) Reason reason,
                                   Model model) {

        List<Appointment> appointments = appointmentService.filterAppointments(doctorId, patientId, dateFrom, dateTo, reason);

        appointments = appointmentService.sortAppointments(appointments, sort, direction);

        model.addAttribute("appointments", appointments);
        model.addAttribute("doctors", doctorService.getAll());
        model.addAttribute("patients", patientService.getAll());
        model.addAttribute("reasons", Reason.values());

        model.addAttribute("doctorId", doctorId);
        model.addAttribute("patientId", patientId);
        model.addAttribute("dateFrom", dateFrom);
        model.addAttribute("dateTo", dateTo);
        model.addAttribute("sort", sort);
        model.addAttribute("order", direction);
        model.addAttribute("reason", reason);

        return "appointment/list";
    }

    @GetMapping("/create")
    public String getCreateForm(Model model) {
        prepareCreateForm(model);
        return "appointment/form";
    }

    @PostMapping("/create")
    public String create(Appointment appointment,
                         @RequestParam int doctorId,
                         @RequestParam int patientId,
                         Model model) {
        try {
            appointmentService.create(appointment, doctorId, patientId);
            model.addAttribute("message", "Wizyta dodana pomyślnie!");
        } catch (TermUnavailableException e) {
            prepareCreateForm(model);
            return "appointment/form";
        }
        return "redirect:/appointments";
    }

    @GetMapping(params = "reason")
    @ResponseBody
    public List<AppointmentDto> getAllByReason(@RequestParam("reason") Reason reason) {
        return appointmentService.getAllByReason(reason).stream()
                .map(AppointmentDto::fromEntity)
                .toList();
    }

    private void prepareCreateForm(Model model) {
        model.addAttribute("doctors", doctorService.getAll());
        model.addAttribute("patients", patientService.getAll());
        model.addAttribute("reasons", Reason.values());
    }
}
