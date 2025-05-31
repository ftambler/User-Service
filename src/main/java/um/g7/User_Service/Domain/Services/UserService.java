package um.g7.User_Service.Domain.Services;

import org.springframework.stereotype.Service;
import um.g7.User_Service.Domain.Entities.UserEntity;
import um.g7.User_Service.Domain.Entities.UserRFID;
import um.g7.User_Service.Domain.Entities.UserVector;
import um.g7.User_Service.Domain.Exceptions.UserNotFoundException;
import um.g7.User_Service.Infrastructure.Repositories.UserRFIDRepository;
import um.g7.User_Service.Infrastructure.Repositories.UserRepository;
import um.g7.User_Service.Infrastructure.Repositories.UserVectorRepository;

import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {
    
    private final UserRepository userRepository;
    private final UserRFIDRepository userRFIDRepository;
    private final UserVectorRepository userVectorRepository;
    
    public UserService(UserRepository userRepository, UserRFIDRepository userRFIDRepository, UserVectorRepository userVectorRepository) {
        this.userRepository = userRepository;
        this.userRFIDRepository = userRFIDRepository;
        this.userVectorRepository = userVectorRepository;
    }

    public void addUser(UserEntity user) {
        userRepository.save(user);
    }

    public void deleteUser(UUID userId) throws UserNotFoundException {
        Optional<UserEntity> optionalUser = userRepository.findById(userId);

        if (optionalUser.isEmpty())
            throw new UserNotFoundException();

        Optional<UserRFID> optionalUserRFID = userRFIDRepository.findById(userId);

        if (optionalUserRFID.isPresent())
            userRFIDRepository.deleteById(userId);

        Optional<UserVector> optionalUserVector = userVectorRepository.findById(userId);

        if (optionalUserVector.isPresent())
            userVectorRepository.deleteById(userId);

        userRepository.deleteById(userId);
    }
}
