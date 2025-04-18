package um.g7.User_Service.Domain.Services;

import org.springframework.stereotype.Service;

import um.g7.User_Service.Domain.Entities.UserEntity;
import um.g7.User_Service.Infrastructure.Repositories.UserRepository;

@Service
public class UserService {
    
    private UserRepository userRepository;
    
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void addUser(UserEntity user) {
        userRepository.save(user);
    }

}
