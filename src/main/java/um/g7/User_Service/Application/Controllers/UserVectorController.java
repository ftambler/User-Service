package um.g7.User_Service.Application.Controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import um.g7.User_Service.Application.Controllers.DTOs.UserVectorDTO;
import um.g7.User_Service.Domain.Entities.UserVector;
import um.g7.User_Service.Domain.Services.UserVectorService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping(path = "/vector")
public class UserVectorController {
    
    private final UserVectorService userVectorService;

    public UserVectorController(UserVectorService userVectorService) {
        this.userVectorService = userVectorService;
    }

    @GetMapping("")
    public ResponseEntity<Boolean> hasAccess(@RequestBody UserVectorDTO userVectorDTO) {
        // System.out.println(userVectorDTO.toString());
        return ResponseEntity.ok(userVectorService.hasAccess(new UserVector(null, userVectorDTO.getVector())));
    }


    //TEMP (el add es desde el kaska)
    @PostMapping("")
    public ResponseEntity<UserVector> addUserVector(@RequestBody UserVectorDTO userVectorDTO) {
        // userVectorDTO.setVector(new float[768]);
        UserVector userVector = new UserVector(userVectorDTO.getUserId(), userVectorDTO.getVector());
        
        return ResponseEntity.ok(userVectorService.addUserVector(userVector));
    }
    

    
    

}
