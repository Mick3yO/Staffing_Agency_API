package staffing.agency.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import staffing.agency.controller.model.CandidateDTO;
import staffing.agency.entity.Candidate;

@Repository
public interface CandidateDAO extends JpaRepository<Candidate, Long> {
	
	    @Query("SELECT c FROM Candidate c")
	    List<CandidateDTO> getAllCandidates();
}
