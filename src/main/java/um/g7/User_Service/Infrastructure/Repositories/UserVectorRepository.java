package um.g7.User_Service.Infrastructure.Repositories;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import um.g7.User_Service.Domain.Entities.UserVector;

@Repository
public interface UserVectorRepository extends JpaRepository<UserVector, UUID> {

    @Query(value = """
                SELECT uv.user_id
                FROM user_vector uv
                WHERE uv.vector <-> CAST(:embedding AS vector) <= :maxDist
                ORDER BY uv.vector <-> CAST(:embedding AS vector) ASC
                LIMIT :limit
            """, nativeQuery = true)
    List<UUID> findSimilarUsersUnderThreshold(
            @Param("embedding") String embedding,
            @Param("maxDist") float maxDist,
            @Param("limit") int limit);

}
