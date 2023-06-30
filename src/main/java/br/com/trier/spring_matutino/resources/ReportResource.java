/*package br.com.trier.spring_matutino.resources;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.trier.spring_matutino.services.AppointmentService;


@RestController
@RequestMapping("/reports")
public class ReportResource {

    @Autowired
    private ReportService reportService;

    @Autowired
    private AppointmentService appointmentService;

    @Secured({"ROLE_USER"})
    @GetMapping("/appointment-report")
    public ResponseEntity<List<AppointmentReportDTO>> generateAppointmentReport() {
        List<AppointmentReportDTO> appointmentReportDTOs = reportService.generateAppointmentReport(appointmentService.getAllAppointments());
        return ResponseEntity.ok(appointmentReportDTOs);
    }
}*/
