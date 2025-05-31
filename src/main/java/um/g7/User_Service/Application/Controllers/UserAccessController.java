package um.g7.User_Service.Application.Controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import um.g7.User_Service.Application.DTOs.UserDTO;
import um.g7.User_Service.Application.DTOs.UserVectorDTO;
import um.g7.User_Service.Domain.Entities.UserEntity;
import um.g7.User_Service.Domain.Entities.UserVector;
import um.g7.User_Service.Domain.Exceptions.AccessDeniedExcep;
import um.g7.User_Service.Domain.Exceptions.DoorNotFoundException;
import um.g7.User_Service.Domain.Exceptions.UserNotFoundException;
import um.g7.User_Service.Domain.Services.UserAccessService;

@RestController
@RequestMapping()
public class UserAccessController {
    
    private final UserAccessService userAccessService;

    public UserAccessController(UserAccessService userAccessService) {
        this.userAccessService = userAccessService;
    }

    @GetMapping("/vector")
    public ResponseEntity<UserDTO> checkVectorAccess(@RequestBody UserVectorDTO userVectorDTO, @RequestParam(value = "doorName") String doorName) throws UserNotFoundException, DoorNotFoundException, AccessDeniedExcep {
        UserEntity userEntity = userAccessService.checkVectorAccess(new UserVector(null, userVectorDTO.getVector()), doorName);

        UserDTO user = new UserDTO(userEntity.getFullName(), userEntity.getCid(), userEntity.getAccessLevel());

        return ResponseEntity.ok(user);
    }

    @GetMapping("/rfid/{rfid}")
    public ResponseEntity<UserDTO> checkRFIDAccess(@PathVariable(name = "rfid") String rfid, @RequestParam(value = "doorName") String doorName) throws UserNotFoundException, DoorNotFoundException, AccessDeniedExcep {
        UserEntity userEntity = userAccessService.checkRFIDAccess(rfid, doorName);

        UserDTO userDTO = new UserDTO(userEntity.getFullName(), userEntity.getCid(), userEntity.getAccessLevel());

        return ResponseEntity.ok(userDTO);
    }
}
