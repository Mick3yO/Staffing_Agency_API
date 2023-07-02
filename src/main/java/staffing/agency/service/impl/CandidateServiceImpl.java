package staffing.agency.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import staffing.agency.controller.model.CandidateDTO;
import staffing.agency.dao.ApplicationDAO;
import staffing.agency.dao.CandidateDAO;
import staffing.agency.entity.Application;
import staffing.agency.entity.Candidate;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CandidateServiceImpl {

    private final CandidateDAO candidateDAO;
    
    @Autowired
    private ApplicationDAO applicationDAO;


    @Autowired
    public CandidateServiceImpl(CandidateDAO candidateDAO) {
        this.candidateDAO = candidateDAO;
    }

    // Add a new candidate to the database
    public Candidate addCandidate(Candidate candidate) {
        return candidateDAO.save(candidate);
    }

    // Retrieve all candidates from the database
    public List<Candidate> getCandidates() {
        return candidateDAO.findAll();
    }

    // Retrieve a candidate by their ID from the database
    public Candidate getCandidateById(Long id) {
        Optional<Candidate> optionalCandidate = candidateDAO.findById(id);
        return optionalCandidate.orElseThrow(() -> new RuntimeException("No candidate found with id: " + id));
    }

    // Update an existing candidate in the database
    public Candidate updateCandidate(Candidate candidate) {
        return candidateDAO.save(candidate);
    }

    // Delete a candidate from the database by their ID
    public void deleteCandidate(Long id) {
        candidateDAO.deleteById(id);
    }
    
    // Convert Candidate entity to CandidateDTO
    private CandidateDTO toDTO(Candidate candidate) {
        CandidateDTO candidateDTO = new CandidateDTO();
        candidateDTO.setCandidateId(candidate.getCandidateId());
        candidateDTO.setName(candidate.getCandidateName());
        candidateDTO.setEmail(candidate.getEmail());
        candidateDTO.setEducation(candidate.getEducation());
        candidateDTO.setExperience(candidate.getExperience());

        // Convert the set of Application IDs to a set of Longs
        Set<Long> applicationIds = candidate.getApplications().stream()
                                            .map(Application::getId) 
                                            .collect(Collectors.toSet());
        candidateDTO.setApplicationIds(applicationIds);

        return candidateDTO;
    }

    // Convert CandidateDTO to Candidate entity
    private Candidate toEntity(CandidateDTO candidateDTO) {
        Candidate candidate = new Candidate();

        candidate.setCandidateName(candidateDTO.getName());
        candidate.setEmail(candidateDTO.getEmail());
        candidate.setEducation(candidateDTO.getEducation());
        candidate.setExperience(candidateDTO.getExperience());

        // Convert the set of Application IDs to a set of Application entities and set it to candidate
        if (candidateDTO.getApplicationIds() != null) {
            Set<Application> applications = candidateDTO.getApplicationIds().stream()
                .map(applicationDAO::findById)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toSet());
            candidate.setApplications(applications);
        }

        return candidate;
    }

    // Add a new candidate using CandidateDTO
    public CandidateDTO addCandidate(CandidateDTO candidateDTO) {
        Candidate candidate = toEntity(candidateDTO); // Convert DTO to entity
        candidate = candidateDAO.save(candidate);
        return toDTO(candidate); // Convert entity to DTO before returning
    }

    // Update an existing candidate using CandidateDTO
    public CandidateDTO updateCandidate(CandidateDTO candidateDTO) {
        Candidate candidate = toEntity(candidateDTO); // Convert DTO to entity
        candidate = candidateDAO.save(candidate);
        return toDTO(candidate); // Convert entity to DTO before returning
    }
}

