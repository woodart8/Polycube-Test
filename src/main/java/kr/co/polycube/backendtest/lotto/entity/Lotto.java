package kr.co.polycube.backendtest.lotto.entity;

import jakarta.persistence.*;
import lombok.*;

// 로또 번호를 저장하는 엔티티 클래스
@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Lotto {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id")
    private Long id;

    @Column(name = "number_1")
    private Integer number1;

    @Column(name = "number_2")
    private Integer number2;

    @Column(name = "number_3")
    private Integer number3;

    @Column(name = "number_4")
    private Integer number4;

    @Column(name = "number_5")
    private Integer number5;

    @Column(name = "number_6")
    private Integer number6;

}
