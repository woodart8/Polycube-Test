package kr.co.polycube.backendtest.lotto;

import kr.co.polycube.backendtest.lotto.batch.WinnerScheduler;
import kr.co.polycube.backendtest.lotto.entity.Lotto;
import kr.co.polycube.backendtest.lotto.entity.Winner;
import kr.co.polycube.backendtest.lotto.repository.LottoRepository;
import kr.co.polycube.backendtest.lotto.repository.WinnerRepository;
import kr.co.polycube.backendtest.lotto.service.WinnerService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;

@Slf4j
@SpringBootTest
class WinnerBatchTest {

    @Autowired
    private WinnerScheduler winnerScheduler;

    @SpyBean
    private WinnerService winnerService;

    @MockBean
    private LottoRepository lottoRepository;

    @MockBean
    private WinnerRepository winnerRepository;

    @Test
    @DisplayName("당첨자 Batch에 대한 통합 테스트")
    void testRunLottoBatch() {
        // Given
        List<Lotto> testLottos = List.of(
                new Lotto(1L, 7, 28, 33, 2, 45, 19),
                new Lotto(2L, 26, 14, 41, 3, 22, 35),
                new Lotto(3L, 15, 29, 38, 6, 44, 21),
                new Lotto(4L, 31, 16, 42, 9, 23, 36),
                new Lotto(5L, 17, 30, 39, 10, 45, 24)
        );

        List<Integer> winningLotto = Arrays.asList(7, 28, 33, 2, 45, 19);

        when(lottoRepository.findAll()).thenReturn(testLottos);

        // DB에 저장하지 않고 로깅만 수행
        doAnswer(invocation -> {
            Lotto lotto = invocation.getArgument(0, Winner.class).getLotto();
            int rank = invocation.getArgument(0, Winner.class).getRank();
            log.info("당첨자 저장됨 - Lotto ID: {}, Rank: {}", lotto.getId(), rank);
            return null;
        }).when(winnerRepository).save(any(Winner.class));

        // 로또 당첨 번호를 정해진 번호로 하고 로직 수행 (기존 로직은 랜덤이었음)
        doAnswer(invocation -> {
            List<Lotto> lottos = lottoRepository.findAll();
            for (Lotto lotto : lottos) {
                List<Integer> numbers = Arrays.asList(
                        lotto.getNumber1(), lotto.getNumber2(), lotto.getNumber3(),
                        lotto.getNumber4(), lotto.getNumber5(), lotto.getNumber6()
                );
                long matchCount = numbers.stream().filter(winningLotto::contains).count();
                if (matchCount >= 2) {
                    int rank = (int) (7 - matchCount);
                    winnerRepository.save(
                            Winner.builder()
                                    .lotto(lotto)
                                    .rank(rank)
                                    .build()
                    );
                }
            }
            return null;
        }).when(winnerService).checkWinnersAndSave();

        // When
        winnerScheduler.runLottoBatch();

        // Then
        verify(winnerService, times(1)).checkWinnersAndSave(); // 배치 중 1번 수행
        verify(winnerRepository, times(1)).save(any(Winner.class)); // 1번 회원만 당첨
    }
}
