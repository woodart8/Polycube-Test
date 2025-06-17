package kr.co.polycube.backendtest.user.repository;

import kr.co.polycube.backendtest.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

// User 엔티티에 대한 CRUD 작업을 제공하는 리포지토리
public interface UserRepository extends JpaRepository<User, Long> {
}
