package um.g7.User_Service.Infrastructure.Repositories;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import um.g7.User_Service.Domain.Entities.Door;

@Repository
public interface DoorRepository extends JpaRepository<Door, UUID> {

    Optional<Door> findByName(String doorName);
    
}
