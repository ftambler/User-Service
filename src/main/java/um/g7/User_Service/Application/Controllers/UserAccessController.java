package um.g7.User_Service.Application.Controllers;

import org.springframework.web.bind.annotation.*;

import um.g7.User_Service.Application.Controllers.DTOs.UserDTO;
import um.g7.User_Service.Application.Controllers.DTOs.UserVectorDTO;
import um.g7.User_Service.Domain.Entities.UserEntity;
import um.g7.User_Service.Domain.Entities.UserVector;
import um.g7.User_Service.Domain.Exceptions.UserNotFoundException;
import um.g7.User_Service.Domain.Services.UserAccessService;

import org.springframework.http.ResponseEntity;

@RestController
@RequestMapping()
public class UserAccessController {
    
    private final UserAccessService userAccessService;

    public UserAccessController(UserAccessService userAccessService) {
        this.userAccessService = userAccessService;
    }

    @GetMapping("/vector")
    public ResponseEntity<UserDTO> checkVectorAccess(@RequestBody UserVectorDTO userVectorDTO) throws UserNotFoundException {
        UserEntity userEntity = userAccessService.checkVectorAccess(new UserVector(null, userVectorDTO.getVector()));

        UserDTO user = new UserDTO(userEntity.getFirstName(), userEntity.getLastName(), userEntity.getCid());

        return ResponseEntity.ok(user);
    }

    @GetMapping("/rfid/{rfid}")
    public ResponseEntity<UserDTO> checkRFIDAccess(@PathVariable(name = "rfid") long rfid) throws UserNotFoundException {
        UserEntity userEntity = userAccessService.checkRFIDAccess(rfid);

        UserDTO userDTO = new UserDTO(userEntity.getFirstName(), userEntity.getLastName(), userEntity.getCid());

        return ResponseEntity.ok(userDTO);
    }
}
