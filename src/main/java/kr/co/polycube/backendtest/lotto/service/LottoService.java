package kr.co.polycube.backendtest.lotto.service;

import kr.co.polycube.backendtest.lotto.dto.LottoDto;
import kr.co.polycube.backendtest.lotto.entity.Lotto;
import kr.co.polycube.backendtest.lotto.repository.LottoRepository;
import kr.co.polycube.backendtest.lotto.util.LottoNumberGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

// 로또 관련 비즈니스 로직을 처리하는 서비스 클래스
@Service
public class LottoService {

    private final LottoRepository lottoRepository;

    @Autowired
    public LottoService(LottoRepository lottoRepository) {
        this.lottoRepository = lottoRepository;
    }

    // 로또 번호 생성
    @Transactional
    public LottoDto createLotto() {
        List<Integer> winNumbers = LottoNumberGenerator.generateLottoNumbers();
        lottoRepository.save(
                Lotto.builder()
                     .number1(winNumbers.get(0))
                     .number2(winNumbers.get(1))
                     .number3(winNumbers.get(2))
                     .number4(winNumbers.get(3))
                     .number5(winNumbers.get(4))
                     .number6(winNumbers.get(5))
                     .build()
        );

        return LottoDto.builder()
                       .numbers(winNumbers)
                       .build();
    }

}
