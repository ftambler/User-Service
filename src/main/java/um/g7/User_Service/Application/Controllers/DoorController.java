package um.g7.User_Service.Application.Controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import um.g7.User_Service.Application.DTOs.DoorCredentialsDTO;
import um.g7.User_Service.Application.DTOs.TokenDTO;
import um.g7.User_Service.Domain.Exceptions.InvalidCredentials;
import um.g7.User_Service.Domain.Services.DoorService;

@RestController
@RequestMapping("/doors")
public class DoorController {
    
    private final DoorService doorService;
    
    public DoorController(DoorService doorService) {
        this.doorService = doorService;
    }

    @PostMapping("/connect")
    public ResponseEntity<TokenDTO> connect(@RequestBody DoorCredentialsDTO doorCredentials) throws InvalidCredentials {
        String token = doorService.connect(doorCredentials.getDoorName(), doorCredentials.getPasscode());

        return ResponseEntity.ok(new TokenDTO(token));
    }

}
