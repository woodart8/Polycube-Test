package kr.co.polycube.backendtest.common;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.nio.charset.StandardCharsets;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Slf4j
@SpringBootTest
@AutoConfigureMockMvc
class ApiFailureTest {

    @Autowired
    private MockMvc mockMvc;

    @DisplayName("존재하지 않는 API 호출 통합 테스트")
    @Test
    void testApiNotFound() throws Exception {
        // Given
        String text = "not-found";

        // When & Then
        MvcResult result = mockMvc.perform(get("/{text}", text))
                                  .andExpect(status().isNotFound())
                                  .andExpect(jsonPath("$.reason").exists())
                                  .andReturn();

        String content = new String(result.getResponse().getContentAsByteArray(), StandardCharsets.UTF_8);
        log.info(content);
    }

}
