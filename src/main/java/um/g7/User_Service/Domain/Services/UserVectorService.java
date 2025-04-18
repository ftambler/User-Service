package um.g7.User_Service.Domain.Services;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import um.g7.User_Service.Domain.Entities.UserEntity;
import um.g7.User_Service.Domain.Entities.UserVector;
import um.g7.User_Service.Domain.Exceptions.UserNotFoundException;
import um.g7.User_Service.Infrastructure.Repositories.UserRepository;
import um.g7.User_Service.Infrastructure.Repositories.UserVectorRepository;

@Service
public class UserVectorService {

    private final UserVectorRepository userVectorRepository;
    private final UserRepository userRepository;

    public UserVectorService(UserVectorRepository userVectorRepository, UserRepository userRepository) {
        this.userVectorRepository = userVectorRepository;
        this.userRepository = userRepository;
    }

    public UserEntity hasAccess(UserVector userVector) throws UserNotFoundException {
        String embedding = Arrays.toString(userVector.getVector());

        List<UUID> mostSimilar = userVectorRepository.findSimilarUsersUnderThreshold(embedding, 10, 1);
        if(mostSimilar.isEmpty()) throw new UserNotFoundException();

        Optional<UserEntity> userEntity = userRepository.findById(mostSimilar.get(0)); 
        if(userEntity.isEmpty()) throw new UserNotFoundException();

        return userEntity.get();
    }

    public UserVector addUserVector(UserVector userVector) {
        return userVectorRepository.save(userVector);
    }

}
