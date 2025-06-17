package kr.co.polycube.backendtest.lotto.entity;

import jakarta.persistence.*;
import lombok.*;

// 로또 당첨자 정보를 저장하는 엔티티 클래스
@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Winner {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lotto_id")
    private Lotto lotto;

    @Column(name = "rank")
    private Integer rank;

}
