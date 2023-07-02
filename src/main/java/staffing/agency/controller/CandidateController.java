package staffing.agency.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import staffing.agency.controller.model.CandidateDTO;
import staffing.agency.service.StaffingAgencyService;

@RestController
@RequestMapping("/candidates")
public class CandidateController {

    private final StaffingAgencyService staffingAgencyService;

    public CandidateController(StaffingAgencyService staffingAgencyService) {
        this.staffingAgencyService = staffingAgencyService;
    }

    @PostMapping
    public ResponseEntity<CandidateDTO> createCandidate(@RequestBody CandidateDTO candidateDTO) {
        CandidateDTO createdCandidate = staffingAgencyService.createCandidate(candidateDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdCandidate);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CandidateDTO> getCandidate(@PathVariable Long id) {
        CandidateDTO candidateDTO = staffingAgencyService.getCandidate(id);
        return ResponseEntity.ok(candidateDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CandidateDTO> updateCandidate(@PathVariable Long id, @RequestBody CandidateDTO candidateDTO) {
        candidateDTO.setCandidateId(id);
        CandidateDTO updatedCandidate = staffingAgencyService.updateCandidate(candidateDTO);
        return ResponseEntity.ok(updatedCandidate);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCandidate(@PathVariable Long id) {
        String responseMessage = staffingAgencyService.deleteCandidate(id);
        return ResponseEntity.ok(responseMessage);
    }

    @GetMapping
    public ResponseEntity<List<CandidateDTO>> getAllCandidates() {
        List<CandidateDTO> allCandidates = staffingAgencyService.getAllCandidates();
        return ResponseEntity.ok(allCandidates);
    }
}
   

