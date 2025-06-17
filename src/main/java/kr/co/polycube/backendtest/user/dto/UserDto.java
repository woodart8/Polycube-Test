package kr.co.polycube.backendtest.user.dto;

import lombok.*;

// 회원 정보를 전달하는 DTO
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {

    private Long id;
    private String name;

}
