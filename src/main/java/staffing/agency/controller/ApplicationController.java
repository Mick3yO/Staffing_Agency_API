package staffing.agency.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import staffing.agency.controller.model.ApplicationDTO;
import staffing.agency.service.StaffingAgencyService;

import java.util.List;

@RestController
@RequestMapping("/applications")
public class ApplicationController {

    private final StaffingAgencyService staffingAgencyService;

    public ApplicationController(StaffingAgencyService staffingAgencyService) {
        this.staffingAgencyService = staffingAgencyService;
    }

    @PostMapping
    public ResponseEntity<ApplicationDTO> createApplication(@RequestBody ApplicationDTO applicationDTO) {
        ApplicationDTO savedApplication = staffingAgencyService.createApplication(applicationDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedApplication);
    }

    @GetMapping
    public ResponseEntity<List<ApplicationDTO>> getAllApplications() {
        List<ApplicationDTO> applications = staffingAgencyService.getAllApplications();
        return ResponseEntity.ok(applications);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApplicationDTO> getApplication(@PathVariable Long id) {
        ApplicationDTO application = staffingAgencyService.getApplication(id);
        return ResponseEntity.ok(application);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApplicationDTO> updateApplication(@PathVariable Long id, @RequestBody ApplicationDTO applicationDTO) {
        ApplicationDTO updatedApplication = staffingAgencyService.updateApplication(id, applicationDTO);
        return ResponseEntity.ok(updatedApplication);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteApplication(@PathVariable Long id) {
        staffingAgencyService.deleteApplication(id);
        return ResponseEntity.noContent().build();
    }
}
