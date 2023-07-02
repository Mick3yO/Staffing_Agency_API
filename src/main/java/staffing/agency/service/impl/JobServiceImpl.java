 package staffing.agency.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityNotFoundException;
import staffing.agency.controller.model.JobDTO;
import staffing.agency.dao.JobDAO;
import staffing.agency.entity.Job;

import java.util.List;
import java.util.Optional;

@Service
public class JobServiceImpl {

    private final JobDAO jobDAO;
    
    @Autowired
    public JobServiceImpl(JobDAO jobDAO) {
        this.jobDAO = jobDAO;
    }

    // Add a new job to the database
    public Job addJob(Job job) {
        return jobDAO.save(job);
    }

    // Retrieve all jobs from the database
    public List<Job> getJobs() {
        return jobDAO.findAll();
    }

    // Retrieve a job by its ID from the database
    public Job getJobById(Long id) {
        Optional<Job> optionalJob = jobDAO.findById(id);
        return optionalJob.orElseThrow(() -> new EntityNotFoundException("Job not found with id: " + id));
    }

    // Update an existing job in the database
    public Job updateJob(Job job) {
        return jobDAO.save(job);
    }

    // Delete a job from the database by its ID
    public void deleteJob(Long id) {
        jobDAO.deleteById(id);
    }
    
    // Add a new job using JobDTO
    public JobDTO addJob(JobDTO jobDTO) {
        Job job = toEntity(jobDTO);
        Job savedJob = jobDAO.save(job);
        return toDTO(savedJob);
    }
    
    // Convert JobDTO to Job entity
    private Job toEntity(JobDTO jobDTO) {
        Job job = new Job();
        job.setTitle(jobDTO.getTitle());
        job.setDescription(jobDTO.getDescription());
        job.setRequirements(jobDTO.getRequirements());
        job.setLocation(jobDTO.getLocation());
        job.setPay(jobDTO.getPay());
        return job;
    }

    // Convert Job entity to JobDTO
    private JobDTO toDTO(Job job) {
        JobDTO jobDTO = new JobDTO();
        jobDTO.setJobId(job.getJobId());
        jobDTO.setTitle(job.getTitle());
        jobDTO.setDescription(job.getDescription());
        jobDTO.setRequirements(job.getRequirements());
        jobDTO.setLocation(job.getLocation());
        jobDTO.setPay(job.getPay());
        return jobDTO;
    }

    public interface JobRepository extends JpaRepository<Job, Long> {
        List<Job> findByClientId(Long clientId);
    }

}

    
    
    
    
    

