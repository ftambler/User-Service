package um.g7.User_Service.Domain.Services;

import org.springframework.stereotype.Service;

import um.g7.User_Service.Domain.Entities.UserRFID;
import um.g7.User_Service.Infrastructure.Repositories.UserRFIDRepository;

@Service
public class UserRFIDService {

    private UserRFIDRepository userRFIDRepository;
    
    public UserRFIDService(UserRFIDRepository userRFIDRepository) {
        this.userRFIDRepository = userRFIDRepository;
    }

    public void addUserRFID(UserRFID userRFID) {
        userRFIDRepository.save(userRFID);

    }
    
}
