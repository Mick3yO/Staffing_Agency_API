package staffing.agency.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityNotFoundException;
import staffing.agency.controller.model.ClientDTO;
import staffing.agency.dao.ClientDAO;
import staffing.agency.dao.JobDAO;
import staffing.agency.entity.Client;
import staffing.agency.entity.Job;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ClientServiceImpl {

    private final ClientDAO clientDAO;
    private final JobDAO jobDAO;

    @Autowired
    public ClientServiceImpl(ClientDAO clientDAO, JobDAO jobDAO) { 
        this.clientDAO = clientDAO;
        this.jobDAO = jobDAO;  
    }

    // Retrieve all clients from the database
    public List<Client> getClients() {
        return clientDAO.findAll();
    }

    // Retrieve a client by their ID from the database
    public Client getClientById(Long id) {
        Optional<Client> optionalClient = clientDAO.findById(id);
        return optionalClient.orElseThrow(() -> new EntityNotFoundException("Client not found with id: " + id));
    }

    // Delete a client from the database by their ID
    public void deleteClient(Long id) {
        clientDAO.deleteById(id);
    }

    // Add a new client using ClientDTO
    public ClientDTO addClient(ClientDTO clientDTO) {
        Client client = toEntity(clientDTO); // Convert DTO to entity
        client = clientDAO.save(client);
        return toDTO(client); // Convert entity to DTO before returning
    }

    // Update an existing client using ClientDTO
    public ClientDTO updateClient(ClientDTO clientDTO) {
        Client client = toEntity(clientDTO); // Convert DTO to entity
        client = clientDAO.save(client);
        return toDTO(client); // Convert entity to DTO before returning
    }

    // Convert Client entity to ClientDTO
    private ClientDTO toDTO(Client client) {
        ClientDTO clientDTO = new ClientDTO();
        clientDTO.setClientId(client.getClientId());
        clientDTO.setName(client.getName());
        clientDTO.setContactDetails(client.getContactDetails());
        clientDTO.setIndustry(client.getIndustry());
        
        // Convert the set of Job IDs to a set of Longs and set it to clientDTO
        Set<Long> jobIds = client.getJobs().stream()
                                 .map(Job::getJobId)
                                 .collect(Collectors.toSet());
        clientDTO.setJobIds(jobIds);

        return clientDTO;
    }

    // Convert ClientDTO to Client entity
    private Client toEntity(ClientDTO clientDTO) {
        Client client = new Client();

        client.setName(clientDTO.getName());
        client.setContactDetails(clientDTO.getContactDetails());
        client.setIndustry(clientDTO.getIndustry());
        
        // Convert the set of Job IDs to a set of Job entities and set it to client
        if (clientDTO.getJobIds() != null) {
            Set<Job> jobs = clientDTO.getJobIds().stream()
                                     .map(jobDAO::findById)
                                     .filter(Optional::isPresent)
                                     .map(Optional::get)
                                     .collect(Collectors.toSet());
            client.setJobs(jobs);
        }

        return client;
    }

}

