package kr.co.polycube.backendtest.lotto.batch;

import kr.co.polycube.backendtest.lotto.service.WinnerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

// 매주 일요일 자정에 실행되는 당첨자 배치 작업 스케줄러
@Slf4j
@Component
public class WinnerScheduler {

    private final WinnerService winnerService;

    @Autowired
    public WinnerScheduler(WinnerService winnerService) {
        this.winnerService = winnerService;
    }

    // 매주 일요일 00:00:00에 실행되는 배치 작업
    // 당첨자를 확인하고 저장하는 로직을 수행
    @Scheduled(cron = "0 0 0 * * SUN") // (초 분 시 일 월 요일)
    public void runLottoBatch() {
        log.info("배치 실행 시작 시간 - {}", LocalDateTime.now());
        winnerService.checkWinnersAndSave();
        log.info("배치 실행 종료 시간 - {}", LocalDateTime.now());
    }

}
