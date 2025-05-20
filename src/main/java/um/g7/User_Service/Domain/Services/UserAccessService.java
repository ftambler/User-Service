package um.g7.User_Service.Domain.Services;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import um.g7.User_Service.Domain.Entities.Door;
import um.g7.User_Service.Domain.Entities.UserEntity;
import um.g7.User_Service.Domain.Entities.UserRFID;
import um.g7.User_Service.Domain.Entities.UserVector;
import um.g7.User_Service.Domain.Exceptions.AccessDeniedExcep;
import um.g7.User_Service.Domain.Exceptions.DoorNotFound;
import um.g7.User_Service.Domain.Exceptions.UserNotFoundException;
import um.g7.User_Service.Infrastructure.Repositories.DoorRepository;
import um.g7.User_Service.Infrastructure.Repositories.UserRFIDRepository;
import um.g7.User_Service.Infrastructure.Repositories.UserRepository;
import um.g7.User_Service.Infrastructure.Repositories.UserVectorRepository;

@Service
public class UserAccessService {

    private final UserVectorRepository userVectorRepository;
    private final UserRFIDRepository userRFIDRepository;
    private final UserRepository userRepository;
    private final DoorRepository doorRepository;

    public UserAccessService(UserVectorRepository userVectorRepository, UserRFIDRepository userRFIDRepository, UserRepository userRepository, DoorRepository doorRepository) {
        this.userVectorRepository = userVectorRepository;
        this.userRFIDRepository = userRFIDRepository;
        this.userRepository = userRepository;
        this.doorRepository = doorRepository;
    }

    public UserEntity checkVectorAccess(UserVector userVector, String doorName) throws UserNotFoundException, DoorNotFound, AccessDeniedExcep {
        String embedding = Arrays.toString(userVector.getVector());

        List<UUID> mostSimilar = userVectorRepository.findSimilarUsersUnderThreshold(embedding, 10, 1);
        if(mostSimilar.isEmpty()) throw new UserNotFoundException();

        Optional<UserEntity> optUserEntity = userRepository.findById(mostSimilar.getFirst());
        if(optUserEntity.isEmpty()) throw new UserNotFoundException();
        UserEntity userEntity = optUserEntity.get();

        Optional<Door> optDoor = doorRepository.findByName(doorName);
        if(optDoor.isEmpty()) throw new DoorNotFound("Door not found");
        Door door = optDoor.get();

        if(door.getAccessLevel() > userEntity.getAccessLevel()) throw new AccessDeniedExcep("Access Denied");

        return userEntity;
    }

    public void addUserVector(UserVector userVector) {
        userVectorRepository.save(userVector);
    }

    public UserEntity checkRFIDAccess(String rfid, String doorName) throws UserNotFoundException, DoorNotFound, AccessDeniedExcep {

        Optional<UserRFID> optionalUserRFID = userRFIDRepository.findByRfid(rfid);
        if (optionalUserRFID.isEmpty())
            throw new UserNotFoundException();

        Optional<UserEntity> optionalUser = userRepository.findById(optionalUserRFID.get().getUserId());
        if (optionalUser.isEmpty()) throw new UserNotFoundException();
        UserEntity userEntity = optionalUser.get();

        Optional<Door> optDoor = doorRepository.findByName(doorName);
        if(optDoor.isEmpty()) throw new DoorNotFound("Door not found");
        Door door = optDoor.get();

        if(door.getAccessLevel() > userEntity.getAccessLevel()) throw new AccessDeniedExcep("Access Denied");

        return optionalUser.get();
    }

    public void addUserRFID(UserRFID userRFID) {
        userRFIDRepository.save(userRFID);
    }
}
