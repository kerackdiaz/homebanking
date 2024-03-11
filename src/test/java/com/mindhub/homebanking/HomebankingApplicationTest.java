package com.mindhub.homebanking;


import com.mindhub.homebanking.utilitis.RandomUtil;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashSet;
import java.util.Set;

import static org.hamcrest.Matchers.*;
import static org.hamcrest.MatcherAssert.assertThat;



@SpringBootTest
public class HomebankingApplicationTest {

    @Test
    public void contextLoads() {
    }

    @RepeatedTest(5000)
    public void testRandomNumberCards() {
        Set<String> accountNumbers = new HashSet<>();
        for (int i = 0; i < 100; i++) {
            accountNumbers.add(RandomUtil.generateAccountNumber(16));
        }
        assertThat(accountNumbers, hasSize(greaterThan(90)));
    }

    @RepeatedTest(5000)
    public void testRandomNumberCvv() {
        Set<String> accountNumbers = new HashSet<>();
        for (int i = 0; i < 200; i++) {
            accountNumbers.add(RandomUtil.generateAccountNumber(3));
        }
        assertThat(accountNumbers, hasSize(greaterThan(90)));
    }

    @RepeatedTest(5000)
    public void testRandomUtil2() {
        Set<Long> numbers = new HashSet<>();
        for (int i = 0; i < 100; i++) {
            numbers.add(RandomUtil.generateNumber(10));
        }
        assertThat(numbers, hasSize(greaterThan(90)));
    }
}
