package kr.co.polycube.backendtest.user.dto;

import lombok.*;

// 회원 ID를 전달하는 DTO
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserIdDto {

    private Long id;

}
