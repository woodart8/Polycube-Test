package kr.co.polycube.backendtest.lotto;

import kr.co.polycube.backendtest.lotto.dto.LottoDto;
import kr.co.polycube.backendtest.lotto.service.LottoService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Slf4j
@SpringBootTest
@AutoConfigureMockMvc
class LottoApiTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private LottoService lottoService;

    @DisplayName("\"POST /lottos\" API에 대한 통합 테스트")
    @Test
    void testCreateLottoApi() throws Exception {
        // Given
        List<Integer> lotto = Arrays.asList(1,2,3,4,5,6);

        when(lottoService.createLotto()).thenReturn(
                LottoDto.builder()
                        .numbers(lotto)
                        .build()
        );

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
