package kr.co.polycube.backendtest.lotto.controller;

import kr.co.polycube.backendtest.lotto.dto.LottoDto;
import kr.co.polycube.backendtest.lotto.service.LottoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

// 로또 관련 API를 처리하는 컨트롤러
@RestController
@RequestMapping("/lottos")
public class LottoController {

    private final LottoService lottoService;

    @Autowired
    public LottoController(LottoService lottoService) {
        this.lottoService = lottoService;
    }

    // 로또 번호 생성
    @PostMapping()
    public ResponseEntity<LottoDto> createLotto() {
        LottoDto lottoDto = lottoService.createLotto();
        return ResponseEntity.ok(lottoDto);
    }

}
