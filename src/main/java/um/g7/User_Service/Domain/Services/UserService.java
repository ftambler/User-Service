package um.g7.User_Service.Domain.Services;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.stereotype.Service;

import um.g7.User_Service.Domain.Entities.UserEntity;
import um.g7.User_Service.Domain.Exceptions.UserNotFoundException;
import um.g7.User_Service.Infrastructure.Repositories.UserRepository;

import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {
    
    private final UserRepository userRepository;
    
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void addUser(UserEntity user) {
        userRepository.save(user);
    }

    public void deleteUser(UUID userId) throws UserNotFoundException {
        Optional<UserEntity> optionalUser = userRepository.findById(userId);

        if (optionalUser.isEmpty())
            throw new UserNotFoundException();

        userRepository.deleteById(userId);
    }
}
