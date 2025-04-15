package um.g7.User_Service.Domain.Services;

import java.util.Arrays;

import org.springframework.stereotype.Service;

import um.g7.User_Service.Domain.Entities.UserVector;
import um.g7.User_Service.Infrastructure.UserVectorRepository;

@Service
public class UserVectorService {

    private final UserVectorRepository userVectorRepository;

    public UserVectorService(UserVectorRepository userVectorRepository) {
        this.userVectorRepository = userVectorRepository;
    }

    public boolean hasAccess(UserVector userVector) {
        String embedding = Arrays.toString(userVector.getVector());
        return !userVectorRepository.findSimilarUsersUnderThreshold(embedding, 10, 1).isEmpty();
    }

    public UserVector addUserVector(UserVector userVector) {
        return userVectorRepository.save(userVector);
    }

}
