package staffing.agency.controller.model;

import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CandidateDTO {
    private Long candidateId;
    private String name;
    private String email;
    private String education;
    private String experience;
    private Set<Long> applicationIds;

}



   

