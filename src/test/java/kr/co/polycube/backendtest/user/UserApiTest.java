package kr.co.polycube.backendtest.user;

import kr.co.polycube.backendtest.common.exception.CommonException;
import kr.co.polycube.backendtest.common.exception.ErrorCode;
import kr.co.polycube.backendtest.user.dto.UserDto;
import kr.co.polycube.backendtest.user.dto.UserIdDto;
import kr.co.polycube.backendtest.user.dto.UserNameDto;
import kr.co.polycube.backendtest.user.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.nio.charset.StandardCharsets;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Slf4j
@SpringBootTest
@AutoConfigureMockMvc
class UserApiTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @DisplayName("\"POST /users\" API에 대한 통합 테스트")
    @Test
    void testCreateUserApi() throws Exception {
        // Given
        String requestBody = """
           {
             "name": "전기범"
           }
           """;

        when(userService.createUser(any(UserNameDto.class)))
                .thenAnswer(invocation ->
                        UserIdDto.builder()
                                .id(1L)
                                .build()
                );

        // When & Then
        MvcResult result = mockMvc.perform(post("/users")
                                      .contentType(MediaType.APPLICATION_JSON)
                                      .content(requestBody))
                                  .andExpect(status().isOk())
                                  .andExpect(jsonPath("$.id").exists())
                                  .andReturn();

        String content = new String(result.getResponse().getContentAsByteArray(), StandardCharsets.UTF_8);
        log.info(content);
    }

    @DisplayName("\"GET /users/{id}\" API에 대한 통합 테스트")
    @Test
    void testGetUserApi() throws Exception {
        // Given
        Long id = 1L;
        String name = "전기범";
        UserDto mockUserDto = UserDto.builder()
                                     .id(id)
                                     .name(name)
                                     .build();

        when(userService.getUser(id)).thenReturn(mockUserDto);

        // When & Then
        MvcResult result = mockMvc.perform(get("/users/{id}", id))
                                  .andExpect(status().isOk())
                                  .andExpect(jsonPath("$.id").value(id))
                                  .andExpect(jsonPath("$.name").value("전기범"))
                                  .andReturn();

        String content = new String(result.getResponse().getContentAsByteArray(), StandardCharsets.UTF_8);
        log.info(content);
    }

    @DisplayName("\"PATCH /users/{id}\" API에 대한 통합 테스트")
    @Test
    void testUpdateUserApi() throws Exception {
        // Given
        Long id = 1L;
        String requestBody = """
           {
             "name": "전수범"
           }
           """;

        when(userService.updateUser(eq(id), any(UserNameDto.class)))
                .thenAnswer(invocation -> {
                    UserNameDto userNameDto = invocation.getArgument(1);
                    return UserDto.builder()
                            .id(id)
                            .name(userNameDto.getName())
                            .build();
                });

        // When & Then
        MvcResult result = mockMvc.perform(patch("/users/{id}", id)
                                          .contentType(MediaType.APPLICATION_JSON)
                                          .content(requestBody))
                                  .andExpect(status().isOk())
                                  .andExpect(jsonPath("$.id").value(id))
                                  .andExpect(jsonPath("$.name").value("전수범"))
                                  .andReturn();

        String content = new String(result.getResponse().getContentAsByteArray(), StandardCharsets.UTF_8);
        log.info(content);
    }

    @DisplayName("\"GET /users/{id}?name=test!!\" API 호출에 대한 통합 테스트")
    @Test
    void testInvalidCharInApiUrl() throws Exception {
        // Given
        Long id = 1L;
        String name = "test";

        // When & Then
        MvcResult result = mockMvc.perform(get("/users/{id}?name=[name}!!", id, name))
                                  .andExpect(status().isBadRequest())
                                  .andExpect(jsonPath("$.reason").exists())
                                  .andReturn();

        String content = new String(result.getResponse().getContentAsByteArray(), StandardCharsets.UTF_8);
        log.info(content);
    }

    @DisplayName("유효하지 않은 이름에 대한 통합 테스트")
    @Test
    void testInvalidName() throws Exception {
        // Given
        String requestBody = """
           {
             "name": ""
           }
           """;

        when(userService.createUser(any(UserNameDto.class)))
                .thenThrow(new CommonException(ErrorCode.INVALID_USER_NAME));

        // When & Then
        MvcResult result = mockMvc.perform(post("/users")
                                          .contentType(MediaType.APPLICATION_JSON)
                                          .content(requestBody))
                                  .andExpect(status().isBadRequest())
                                  .andExpect(jsonPath("$.reason").exists())
                                  .andReturn();

        String content = new String(result.getResponse().getContentAsByteArray(), StandardCharsets.UTF_8);
        log.info(content);
    }

    @DisplayName("존재하지 않는 회원에 대한 통합 테스트")
    @Test
    void testUserNotFound() throws Exception {
        // Given
        long id = 1L;
        when(userService.getUser(id)).thenThrow(new CommonException(ErrorCode.NOT_FOUND_USER));

        // When & Then
        MvcResult result = mockMvc.perform(get("/users/{id}", id))
                                  .andExpect(status().isNotFound())
                                  .andExpect(jsonPath("$.reason").exists())
                                  .andReturn();

        String content = new String(result.getResponse().getContentAsByteArray(), StandardCharsets.UTF_8);
        log.info(content);
    }

}
