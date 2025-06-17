package kr.co.polycube.backendtest.lotto;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.transaction.annotation.Transactional;

import java.nio.charset.StandardCharsets;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Slf4j
@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class LottoApiTest {

    @Autowired
    private MockMvc mockMvc;

    @DisplayName("\"POST /lottos\" API에 대한 통합 테스트")
    @Test
    void testCreateLottoApi() throws Exception {
        // When & Then
        MvcResult result = mockMvc.perform(post("/lottos"))
                                  .andExpect(status().isOk())
                                  .andExpect(jsonPath("$.numbers").isArray())
                                  .andExpect(jsonPath("$.numbers", hasSize(6)))
                                  .andReturn();

        String content = new String(result.getResponse().getContentAsByteArray(), StandardCharsets.UTF_8);
        log.info(content);
    }

}
