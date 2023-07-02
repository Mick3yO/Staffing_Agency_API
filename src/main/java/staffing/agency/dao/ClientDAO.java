package staffing.agency.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import staffing.agency.entity.Client;

public interface ClientDAO
		extends JpaRepository<Client, Long> {

}
