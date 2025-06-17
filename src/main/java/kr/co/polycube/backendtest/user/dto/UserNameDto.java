package kr.co.polycube.backendtest.user.dto;

import lombok.*;

// 회원 이름을 전달하는 DTO
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserNameDto {

    private String name;

}
