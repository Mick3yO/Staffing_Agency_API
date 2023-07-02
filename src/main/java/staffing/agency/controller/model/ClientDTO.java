package staffing.agency.controller.model;

import java.util.HashSet;
import java.util.Set;
import lombok.Data;

@Data
public class ClientDTO {
    private Long clientId;
    private String name;
    private Set<Long> jobIds = new HashSet<>();
    private String contactDetails;
    private String industry;  
    
}

