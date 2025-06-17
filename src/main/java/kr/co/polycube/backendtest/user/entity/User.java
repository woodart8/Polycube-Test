package kr.co.polycube.backendtest.user.entity;

import jakarta.persistence.*;
import lombok.*;

// 회원 엔티티 클래스
@Entity
@Table(name = "users")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

}
