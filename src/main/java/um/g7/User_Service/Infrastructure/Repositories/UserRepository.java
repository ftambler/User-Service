package um.g7.User_Service.Infrastructure.Repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import um.g7.User_Service.Domain.Entities.UserEntity;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, UUID> {
    
}
