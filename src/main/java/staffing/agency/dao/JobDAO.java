package staffing.agency.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import staffing.agency.entity.Job;

public interface JobDAO extends JpaRepository<Job, Long> {

}
