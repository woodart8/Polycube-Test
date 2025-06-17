package kr.co.polycube.backendtest.lotto.dto;

import lombok.*;

import java.util.List;

// 로또 번호 정보를 담는 DTO
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LottoDto {

    private List<Integer> numbers; // 로또 번호 리스트

}
