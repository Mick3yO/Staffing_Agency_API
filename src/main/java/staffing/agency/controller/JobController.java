package staffing.agency.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import staffing.agency.controller.model.JobDTO;
import staffing.agency.service.StaffingAgencyService;

@RestController
@RequestMapping("/jobs")
public class JobController {
	
	 

    private final StaffingAgencyService staffingAgencyService;

    public JobController(StaffingAgencyService staffingAgencyService) {
        this.staffingAgencyService = staffingAgencyService;
    }

    @PostMapping
    public ResponseEntity<JobDTO> createJob(@RequestBody JobDTO jobDTO) {
        JobDTO createdJob = staffingAgencyService.createJob(jobDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdJob);
    }

    @GetMapping("/{id}")
    public ResponseEntity<JobDTO> getJob(@PathVariable Long id) {
        JobDTO jobDTO = staffingAgencyService.getJob(id);
        return ResponseEntity.ok(jobDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<JobDTO> updateJob(@PathVariable Long id, @RequestBody JobDTO jobDTO) {
        jobDTO.setJobId(id);
        JobDTO updatedJob = staffingAgencyService.updateJob(jobDTO);
        return ResponseEntity.ok(updatedJob);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteJob(@PathVariable Long id) {
        staffingAgencyService.deleteJob(id);
        return ResponseEntity.ok("Job deleted successfully");
    }

    @GetMapping("/jobs")
    public ResponseEntity<List<JobDTO>> getAllJobs() {
        List<JobDTO> jobDTOs = staffingAgencyService.getAllJobs();
        return new ResponseEntity<>(jobDTOs, HttpStatus.OK);
    }
     
    }


   








