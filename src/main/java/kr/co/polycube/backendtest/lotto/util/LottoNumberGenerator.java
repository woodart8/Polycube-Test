package kr.co.polycube.backendtest.lotto.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

// 로또 번호를 생성하는 유틸리티 클래스
public class LottoNumberGenerator {

    // 1부터 45까지의 숫자 중에서 무작위로 6개의 번호를 생성하여 반환 (정렬하라는 조건이 없어 정렬은 하지 않았음)
    public static List<Integer> generateLottoNumbers() {
        // 초기 세팅
        int minNumber = 1, maxNumber = 45;
        List<Integer> numbers = new ArrayList<>();
        for (int i=minNumber; i<=maxNumber; i++)
            numbers.add(i);

        // 번호 섞기
        Collections.shuffle(numbers);

        // 6개 뽑기
        return numbers.subList(0, 6);
    }

}
