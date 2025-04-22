package um.g7.User_Service.Domain.Services;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import um.g7.User_Service.Domain.Entities.UserEntity;
import um.g7.User_Service.Domain.Entities.UserRFID;
import um.g7.User_Service.Domain.Entities.UserVector;
import um.g7.User_Service.Domain.Exceptions.UserNotFoundException;
import um.g7.User_Service.Infrastructure.Repositories.UserRFIDRepository;
import um.g7.User_Service.Infrastructure.Repositories.UserRepository;
import um.g7.User_Service.Infrastructure.Repositories.UserVectorRepository;

@Service
public class UserAccessService {

    private final UserVectorRepository userVectorRepository;
    private final UserRFIDRepository userRFIDRepository;
    private final UserRepository userRepository;

    public UserAccessService(UserVectorRepository userVectorRepository, UserRFIDRepository userRFIDRepository, UserRepository userRepository) {
        this.userVectorRepository = userVectorRepository;
        this.userRFIDRepository = userRFIDRepository;
        this.userRepository = userRepository;
    }

    public UserEntity checkVectorAccess(UserVector userVector) throws UserNotFoundException {
        String embedding = Arrays.toString(userVector.getVector());

        List<UUID> mostSimilar = userVectorRepository.findSimilarUsersUnderThreshold(embedding, 10, 1);
        if(mostSimilar.isEmpty()) throw new UserNotFoundException();

        Optional<UserEntity> userEntity = userRepository.findById(mostSimilar.getFirst());
        if(userEntity.isEmpty()) throw new UserNotFoundException();

        return userEntity.get();
    }

    public void addUserVector(UserVector userVector) {
        userVectorRepository.save(userVector);
    }

    public UserEntity checkRFIDAccess(String rfid) throws UserNotFoundException {

        Optional<UserRFID> optionalUserRFID = userRFIDRepository.findByRfid(rfid);
        if (optionalUserRFID.isEmpty())
            throw new UserNotFoundException();

        Optional<UserEntity> optionalUser = userRepository.findById(optionalUserRFID.get().getUserId());
        if (optionalUser.isEmpty())
            throw new UserNotFoundException();

        return optionalUser.get();
    }

    public void addUserRFID(UserRFID userRFID) {
        userRFIDRepository.save(userRFID);
    }
}
