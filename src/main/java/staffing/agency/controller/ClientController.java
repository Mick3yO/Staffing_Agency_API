package staffing.agency.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import staffing.agency.controller.model.ClientDTO;
import staffing.agency.service.StaffingAgencyService;

import java.util.List;

@RestController
@RequestMapping("/clients")
public class ClientController {

    private final StaffingAgencyService staffingAgencyService;

    public ClientController(StaffingAgencyService staffingAgencyService) {
        this.staffingAgencyService = staffingAgencyService;
    }

    @PostMapping
    public ResponseEntity<ClientDTO> createClient(@RequestBody ClientDTO clientDTO) {
    	  System.out.println(clientDTO); 
        ClientDTO savedClient = staffingAgencyService.createClient(clientDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedClient);
    }

    @GetMapping
    public ResponseEntity<List<ClientDTO>> getAllClients() {
        List<ClientDTO> clients = staffingAgencyService.getAllClients();
        return ResponseEntity.ok(clients);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClientDTO> getClient(@PathVariable Long id) {
        ClientDTO client = staffingAgencyService.getClient(id);
        return ResponseEntity.ok(client);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClientDTO> updateClient(@PathVariable Long id, @RequestBody ClientDTO clientDTO) {
        ClientDTO updatedClient = staffingAgencyService.updateClient(id, clientDTO); 
        return ResponseEntity.ok(updatedClient);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteClient(@PathVariable Long id) {
        staffingAgencyService.deleteClient(id);
        return ResponseEntity.noContent().build();
    }
}

