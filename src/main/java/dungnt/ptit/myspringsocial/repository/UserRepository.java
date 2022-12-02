package dungnt.ptit.myspringsocial.repository;

import dungnt.ptit.myspringsocial.pojo.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {

    Optional<User> findByEmail(String email);

    Boolean existsByEmail(String email);

    @Query(value = "SELECT * FROM spring_social.users u " +
            "WHERE (?1 is null or u.email like %?1%) AND (?2 is null or u.provider like %?2%) "
    ,nativeQuery = true)
    Page<User> getAllByCondition(String email, String type, Pageable pageable);
}
