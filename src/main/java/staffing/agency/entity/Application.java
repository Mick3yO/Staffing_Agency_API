package staffing.agency.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Column;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.Date;

@Entity
@Data
public class Application {
	
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "application_id")
    private Long id;
    
    // Many-to-One relationship with Candidate entity
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "candidate_id")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Candidate candidate;
    
    // Many-to-One relationship with Job entity
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "job_id")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Job job;
    
    @Column(name = "application_date")
    private Date applicationDate;
    
    @Column(name = "status")
    private String status;
      
}
