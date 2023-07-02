package staffing.agency.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import staffing.agency.dao.ApplicationDAO;
import staffing.agency.entity.Application;

import java.util.List;
import java.util.Optional;

@Service
public class ApplicationServiceImpl {

    private final ApplicationDAO applicationDAO;

    @Autowired
    public ApplicationServiceImpl(ApplicationDAO applicationDAO) {
        this.applicationDAO = applicationDAO;
    }

    // Add a new application to the database
    public Application addApplication(Application application) {
        return applicationDAO.save(application);
    }

    // Retrieve all applications from the database
    public List<Application> getApplications() {
        return applicationDAO.findAll();
    }

    // Retrieve an application by its ID from the database
    public Application getApplicationById(Long id) {
        Optional<Application> optionalApplication = applicationDAO.findById(id);
        return optionalApplication.orElseThrow(() -> new RuntimeException("No application found with id: " + id));
    }

    // Update an existing application in the database
    public Application updateApplication(Application application) {
        return applicationDAO.save(application);
    }

    // Delete an application from the database by its ID
    public void deleteApplication(Long id) {
        applicationDAO.deleteById(id);
    }
}

