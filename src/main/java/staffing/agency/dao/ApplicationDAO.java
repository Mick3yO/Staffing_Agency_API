package staffing.agency.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import staffing.agency.entity.Application;

public interface ApplicationDAO
		extends JpaRepository<Application, Long> {

}
