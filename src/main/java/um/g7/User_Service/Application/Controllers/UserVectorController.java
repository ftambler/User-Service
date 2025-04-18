package um.g7.User_Service.Application.Controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import um.g7.User_Service.Application.Controllers.DTOs.UserDTO;
import um.g7.User_Service.Application.Controllers.DTOs.UserVectorDTO;
import um.g7.User_Service.Domain.Entities.UserEntity;
import um.g7.User_Service.Domain.Entities.UserVector;
import um.g7.User_Service.Domain.Exceptions.UserNotFoundException;
import um.g7.User_Service.Domain.Services.UserVectorService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping(path = "/vector")
public class UserVectorController {
    
    private final UserVectorService userVectorService;

    public UserVectorController(UserVectorService userVectorService) {
        this.userVectorService = userVectorService;
    }

    @GetMapping("")
    public ResponseEntity<UserDTO> hasAccess(@RequestBody UserVectorDTO userVectorDTO) throws UserNotFoundException {
        UserEntity userEntity = userVectorService.hasAccess(new UserVector(null, userVectorDTO.getVector()));

        UserDTO user = new UserDTO(userEntity.getFirstName(), userEntity.getLastName(), userEntity.getCid());

        return ResponseEntity.ok(user);
    }  

}
