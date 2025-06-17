package kr.co.polycube.backendtest.lotto.repository;

import kr.co.polycube.backendtest.lotto.entity.Winner;
import org.springframework.data.jpa.repository.JpaRepository;

// Winner 엔티티에 대한 CRUD 작업을 제공하는 리포지토리
public interface WinnerRepository extends JpaRepository<Winner, Long> {
}
