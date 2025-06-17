package kr.co.polycube.backendtest.user.controller;

import kr.co.polycube.backendtest.user.dto.UserDto;
import kr.co.polycube.backendtest.user.dto.UserIdDto;
import kr.co.polycube.backendtest.user.dto.UserNameDto;
import kr.co.polycube.backendtest.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

// 회원 관련 요청을 처리하는 컨트롤러
@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    // user 등록
    @PostMapping()
    public ResponseEntity<UserIdDto> createUser(@RequestBody UserNameDto userNameDto) {
        UserIdDto userIdDto = userService.createUser(userNameDto);
        return ResponseEntity.ok(userIdDto);
    }

    // user 조회
    @GetMapping("/{id:[0-9]+}")
    public ResponseEntity<UserDto> getUser(@PathVariable(name = "id") long id) {
        UserDto userDto = userService.getUser(id);
        return ResponseEntity.ok(userDto);
    }

    // user 수정
    @PatchMapping("/{id:[0-9]+}")
    public ResponseEntity<UserDto> updateUser(@PathVariable(name = "id") long id,
                                              @RequestBody UserNameDto userNameDto) {
        UserDto userDto = userService.updateUser(id, userNameDto);
        return ResponseEntity.ok(userDto);
    }

}
