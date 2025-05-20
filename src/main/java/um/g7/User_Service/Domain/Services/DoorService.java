package um.g7.User_Service.Domain.Services;

import org.springframework.stereotype.Service;

import um.g7.User_Service.Domain.Entities.Door;
import um.g7.User_Service.Infrastructure.Repositories.DoorRepository;

@Service
public class DoorService {

    private final DoorRepository doorRepository;

    public DoorService(DoorRepository doorRepository) {
        this.doorRepository = doorRepository;
    }

    public void addDoor(Door door) {
        doorRepository.save(door);
    }

}
