package um.g7.User_Service.Domain.Services;

import java.util.Optional;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import um.g7.User_Service.Domain.Entities.Door;
import um.g7.User_Service.Domain.Exceptions.InvalidCredentials;
import um.g7.User_Service.Infrastructure.Repositories.DoorRepository;

@Service
public class DoorService {

    private final JwtService jwtService;
    
    private final DoorRepository doorRepository;

    private final PasswordEncoder passwordEncoder;

    public DoorService(JwtService jwtService, DoorRepository doorRepository) {
        this.jwtService = jwtService;
        this.doorRepository = doorRepository;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    public void addDoor(Door door) {
        doorRepository.save(door);
    }

    public String connect(String doorName, String passcode) throws InvalidCredentials {
        Optional<Door> optDoor = doorRepository.findByName(doorName);        
        if(optDoor.isEmpty()) 
            throw new InvalidCredentials("Invalid Door Credentials");
        Door door = optDoor.get();

        if(!passwordEncoder.matches(passcode, door.getPasscode()))
            throw new InvalidCredentials("Invalid Door Credentials");

        return jwtService.generateDoorToken(doorName);

    }

}
