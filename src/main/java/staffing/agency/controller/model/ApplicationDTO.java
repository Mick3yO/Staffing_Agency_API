package staffing.agency.controller.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApplicationDTO {
    private Long applicationId;
    private Long candidateId;
    private String candidateName;
    private Long jobId;
    private String jobDescription;
    private String applicationStatus;
    private Date applicationDate;
}
