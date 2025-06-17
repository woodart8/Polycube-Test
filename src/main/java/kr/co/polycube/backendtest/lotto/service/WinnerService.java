package kr.co.polycube.backendtest.lotto.service;

import jakarta.transaction.Transactional;
import kr.co.polycube.backendtest.lotto.entity.Lotto;
import kr.co.polycube.backendtest.lotto.entity.Winner;
import kr.co.polycube.backendtest.lotto.repository.LottoRepository;
import kr.co.polycube.backendtest.lotto.repository.WinnerRepository;
import kr.co.polycube.backendtest.lotto.util.LottoNumberGenerator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

// 로또 당첨자 계산 및 저장을 담당하는 서비스 클래스
@Slf4j
@Service
public class WinnerService {

    private final LottoRepository lottoRepository;
    private final WinnerRepository winnerRepository;

    @Autowired
    public WinnerService(LottoRepository lottoRepository,
                         WinnerRepository winnerRepository) {
        this.lottoRepository = lottoRepository;
        this.winnerRepository = winnerRepository;
    }

    // 당첨 번호를 뽑고 등수 계산해서 당첨자 저장하는 메서드
    @Transactional
    public void checkWinnersAndSave() {
        List<Lotto> lottos = lottoRepository.findAll();

        List<Integer> winningNumbers = LottoNumberGenerator.generateLottoNumbers();
        log.info("당첨 번호: {}", winningNumbers);

        for (Lotto lotto : lottos) {
            List<Integer> numbers = Arrays.asList(
                    lotto.getNumber1(), lotto.getNumber2(), lotto.getNumber3(),
                    lotto.getNumber4(), lotto.getNumber5(), lotto.getNumber6()
            );

            long matchCount = numbers.stream().filter(winningNumbers::contains).count(); // 일치하는 번호 개수

            // 일치하는 번호 개수로 등수를 정할 경우 2개 이상 맞춰야 당첨
            if (matchCount >= 2) {
                int rank = (int) (7 - matchCount);
                log.info("당첨자 저장됨 - Lotto ID: {}, Rank: {}", lotto.getId(), rank);
                winnerRepository.save(
                        Winner.builder()
                              .lotto(lotto)
                              .rank(rank)
                              .build()
                );
            }
        }
    }

}
