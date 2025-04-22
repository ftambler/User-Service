package um.g7.User_Service.Infrastructure.Repositories;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import um.g7.User_Service.Domain.Entities.UserRFID;

@Repository
public interface UserRFIDRepository extends JpaRepository<UserRFID, UUID>{

    Optional<UserRFID> findByRfid(String rfid);
}