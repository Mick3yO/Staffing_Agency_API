package staffing.agency.controller.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class JobDTO {
    private Long jobId;
    private Long clientId;
    private String title;
    private String description;
    private String requirements;
    private String location;
    private String pay;
    
 
}


