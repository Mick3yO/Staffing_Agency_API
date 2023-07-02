package staffing.agency.service;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.EntityManager;
import staffing.agency.controller.model.ApplicationDTO;
import staffing.agency.controller.model.CandidateDTO;
import staffing.agency.controller.model.ClientDTO;
import staffing.agency.controller.model.JobDTO;
import staffing.agency.dao.ApplicationDAO;
import staffing.agency.dao.CandidateDAO;
import staffing.agency.dao.ClientDAO;
import staffing.agency.dao.JobDAO;
import staffing.agency.entity.Application;
import staffing.agency.entity.Candidate;
import staffing.agency.entity.Client;
import staffing.agency.entity.Job;
import staffing.agency.service.impl.JobServiceImpl;

@Service
public class StaffingAgencyService {

    @Autowired
    private CandidateDAO candidateDAO;

    @Autowired
    private ClientDAO clientDAO;
    
 
    private final JobServiceImpl jobService;

    @Autowired
    public StaffingAgencyService(JobServiceImpl jobService) {
        this.jobService = jobService;
    }

    @Autowired
    private JobDAO jobDAO;
    
    @Autowired
    private ApplicationDAO applicationDAO;
    
    @Autowired
    EntityManager entityManager;
   
    // Candidate Management

    @Transactional
    public CandidateDTO createCandidate(CandidateDTO candidateDTO) {
        Candidate candidate = new Candidate();
        candidate.setCandidateName(candidateDTO.getName());
        candidate.setEmail(candidateDTO.getEmail());
        candidate.setEducation(candidateDTO.getEducation());
        candidate.setExperience(candidateDTO.getExperience());
        
        Candidate savedCandidate = candidateDAO.save(candidate);
        return mapCandidateToDTO(savedCandidate);
    }

    @Transactional
    public CandidateDTO getCandidate(Long id) {
        Candidate candidate = candidateDAO.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Candidate with id " + id + " not found"));

        return mapCandidateToDTO(candidate);
    }

    @Transactional
    public CandidateDTO updateCandidate(CandidateDTO candidateDTO) {
        Candidate candidate = candidateDAO.findById(candidateDTO.getCandidateId())
            .orElseThrow(() -> new NoSuchElementException("Candidate not found"));

        candidate.setCandidateName(candidateDTO.getName());
        candidate.setEmail(candidateDTO.getEmail());
        candidate.setEducation(candidateDTO.getEducation());
        candidate.setExperience(candidateDTO.getExperience());
        Candidate savedCandidate = candidateDAO.save(candidate);

        return mapCandidateToDTO(savedCandidate);
    }

    @Transactional
    public String deleteCandidate(Long id) {
        candidateDAO.deleteById(id);
        return "Candidate deleted successfully";
    }


    public List<CandidateDTO> getAllCandidates() {
        List<Candidate> candidates = candidateDAO.findAll();
        return candidates.stream()
                .map(this::mapCandidateToDTO)
                .collect(Collectors.toList());
    }

    // Job Management

    @Transactional
    public JobDTO createJob(JobDTO jobDTO) {
        Job job = new Job();
        job.setTitle(jobDTO.getTitle());
        job.setDescription(jobDTO.getDescription());
        job.setRequirements(jobDTO.getRequirements());
        job.setLocation(jobDTO.getLocation());
        job.setPay(jobDTO.getPay());

        // Set client
        Client client = clientDAO.findById(jobDTO.getClientId())
                .orElseThrow(() -> new NoSuchElementException("Client with id " + jobDTO.getClientId() + " not found"));
        job.setClient(client);

        Job savedJob = jobDAO.save(job);
        return mapJobToDTO(savedJob);
    }
    
    public void deleteJob(Long id) {
        jobService.deleteJob(id);
    }

    @Transactional
    public JobDTO updateJob(JobDTO jobDTO) {
        Job job = jobDAO.findById(jobDTO.getJobId())
                .orElseThrow(NoSuchElementException::new);

        job.setTitle(jobDTO.getTitle());
        job.setDescription(jobDTO.getDescription());
        job.setRequirements(jobDTO.getRequirements());
        job.setLocation(jobDTO.getLocation());
        job.setPay(jobDTO.getPay());

     // Update the client for the job
        Client client = clientDAO.findById(jobDTO.getClientId())
                .orElseThrow(() -> new NoSuchElementException("Client with id " + jobDTO.getClientId() + " not found"));
        job.setClient(client);

        Job savedJob = jobDAO.save(job);
        return mapJobToDTO(savedJob);
    }

 
    public List<JobDTO> getAllJobs() {
        List<Job> jobs = jobDAO.findAll();
        return jobs.stream()
                .map(this::mapJobToDTO)
                .collect(Collectors.toList());
    }
    
    public JobDTO getJob(Long id) {
        Job job = jobDAO.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Job with id " + id + " not found"));

        return mapJobToDTO(job);
    }


    // Client Management

    @Transactional
    public ClientDTO createClient(ClientDTO clientDTO) {
        Client client = new Client();
        client.setName(clientDTO.getName());
        client.setContactDetails(clientDTO.getContactDetails());
        client.setIndustry(clientDTO.getIndustry());
     // Retrieve the job IDs from the DTO and fetch the corresponding jobs
        Set<Long> jobIds = clientDTO.getJobIds();
        if (jobIds != null && !jobIds.isEmpty()) {
            Set<Job> jobs = jobIds.stream()
                .map(jobDAO::findById)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toSet());
            client.setJobs(jobs);
        }

        Client savedClient = clientDAO.save(client);
        return mapClientToDTO(savedClient);
    }


    public ClientDTO getClient(Long id) {
        Client client = clientDAO.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Client with id " + id + " not found"));

        return mapClientToDTO(client);
    }

    @Transactional
    public ClientDTO updateClient(Long id, ClientDTO clientDTO) {
        // Logging the received name to check it, part of my troubleshooting. But I left it.
        System.out.println("Received name: " + clientDTO.getName());

        Client client = clientDAO.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Client with id " + id + " not found"));

        client.setName(clientDTO.getName());
        client.setContactDetails(clientDTO.getContactDetails());
        client.setIndustry(clientDTO.getIndustry());

        Client savedClient = clientDAO.save(client);

        // Flushing the changes manually
        // I was doing some troubleshooting and I just left it. 
        entityManager.flush();

        return mapClientToDTO(savedClient);
    }


    @Transactional
    public void deleteClient(Long id) {
        clientDAO.deleteById(id);
    }

    public List<ClientDTO> getAllClients() {
        List<Client> clients = clientDAO.findAll();
        return clients.stream()
                .map(this::mapClientToDTO)
                .collect(Collectors.toList());
    }

    // Application Management


    @Transactional
    public ApplicationDTO createApplication(ApplicationDTO applicationDTO) {
    	// Map the DTO to an Application entity
        Application application = mapDtoToApplication(applicationDTO);
        Application savedApplication = applicationDAO.save(application);
        return mapApplicationToDTO(savedApplication);
    }

    public ApplicationDTO getApplication(Long id) {
        Application application = applicationDAO.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Application with id " + id + " not found"));

        return mapApplicationToDTO(application);
    }

    @Transactional
    public ApplicationDTO updateApplication(Long id, ApplicationDTO applicationDTO) {
        Application existingApplication = applicationDAO.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Application with id " + id + " not found"));
        
        Application applicationToUpdate = mapDtoToApplication(applicationDTO);
        applicationToUpdate.setId(existingApplication.getId());
        
        Application updatedApplication = applicationDAO.save(applicationToUpdate);
        return mapApplicationToDTO(updatedApplication);
    }

    @Transactional
    public void deleteApplication(Long id) {
        applicationDAO.deleteById(id);
    }

    public List<ApplicationDTO> getAllApplications() {
        List<Application> applications = applicationDAO.findAll();
        return applications.stream()
                .map(this::mapApplicationToDTO)
                .collect(Collectors.toList());
    }

    // Helper methods to map entities to DTOs
    private CandidateDTO mapCandidateToDTO(Candidate candidate) {
    	// Map a Candidate entity to a CandidateDTO object
        CandidateDTO candidateDTO = new CandidateDTO();
        candidateDTO.setCandidateId(candidate.getCandidateId());
        candidateDTO.setName(candidate.getCandidateName());
        candidateDTO.setEmail(candidate.getEmail());
        candidateDTO.setEducation(candidate.getEducation());
        candidateDTO.setExperience(candidate.getExperience());

     // Map the Application IDs from the Candidate's applications
        Set<Long> applicationIds = Optional.ofNullable(candidate.getApplications())
            .orElse(Collections.emptySet())
            .stream()
            .map(Application::getId) 
            .collect(Collectors.toSet());

        candidateDTO.setApplicationIds(applicationIds);
        
        return candidateDTO;
    }


    private JobDTO mapJobToDTO(Job job) {
        JobDTO jobDTO = new JobDTO();
        jobDTO.setJobId(job.getJobId());
        jobDTO.setClientId(job.getClient().getClientId());
        jobDTO.setTitle(job.getTitle());
        jobDTO.setDescription(job.getDescription());
        jobDTO.setRequirements(job.getRequirements());
        jobDTO.setLocation(job.getLocation());
        jobDTO.setPay(job.getPay());

        return jobDTO;
    }
 
    private ClientDTO mapClientToDTO(Client client) {
        ClientDTO clientDTO = new ClientDTO();
        clientDTO.setClientId(client.getClientId());
        clientDTO.setName(client.getName());
        clientDTO.setContactDetails(client.getContactDetails());
        clientDTO.setIndustry(client.getIndustry());

        if (client.getJobs() != null) {
            clientDTO.setJobIds(client.getJobs().stream().map(Job::getJobId).collect(Collectors.toSet()));
        }

        return clientDTO;
    }

    

    public ClientDTO updateClient(ClientDTO clientDTO) {
        Client client = clientDAO.findById(clientDTO.getClientId())
            .orElseThrow(() -> new NoSuchElementException("Client not found"));

        client.setName(clientDTO.getName());
        client.setContactDetails(clientDTO.getContactDetails());
        client.setIndustry(clientDTO.getIndustry());
        
        // Update the associated jobIds if needed
        Set<Long> updatedJobIds = clientDTO.getJobIds();
        if (updatedJobIds != null) {
            Set<Job> updatedJobs = new HashSet<>();
            for (Long jobId : updatedJobIds) {
                Job job = jobDAO.findById(jobId)
                    .orElseThrow(() -> new NoSuchElementException("Job not found: " + jobId));
                updatedJobs.add(job);
            }
            client.setJobs(updatedJobs);
        }

        // Save the updated client entity
        Client savedClient = clientDAO.save(client);
        
        // Return the updated clientDTO
        return mapClientToDTO(savedClient);
    }
    private ApplicationDTO mapApplicationToDTO(Application application) {
    	// Map an Application entity to an ApplicationDTO object
        ApplicationDTO applicationDTO = new ApplicationDTO();
        applicationDTO.setApplicationId(application.getId());
        applicationDTO.setCandidateId(application.getCandidate().getCandidateId());
        applicationDTO.setCandidateName(application.getCandidate().getCandidateName());
        applicationDTO.setJobId(application.getJob().getJobId());
        applicationDTO.setJobDescription(application.getJob().getDescription());
        applicationDTO.setApplicationStatus(application.getStatus());
        applicationDTO.setApplicationDate(application.getApplicationDate());

        return applicationDTO;
    }


    private Application mapDtoToApplication(ApplicationDTO applicationDTO) {
    	// Map an ApplicationDTO object to an Application entity
        Application application = new Application();
        Candidate candidate = candidateDAO.findById(applicationDTO.getCandidateId())
                .orElseThrow(() -> new NoSuchElementException("Candidate with id " + applicationDTO.getCandidateId() + " not found"));
        Job job = jobDAO.findById(applicationDTO.getJobId())
                .orElseThrow(() -> new NoSuchElementException("Job with id " + applicationDTO.getJobId() + " not found"));
        
        application.setCandidate(candidate);
        application.setJob(job);
        application.setStatus(applicationDTO.getApplicationStatus());
        application.setApplicationDate(applicationDTO.getApplicationDate());

        return application;
    }
    
}
