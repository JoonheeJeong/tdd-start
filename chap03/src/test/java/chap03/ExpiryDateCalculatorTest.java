package chap03;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ExpiryDateCalculatorTest {

    private void assertExpiryDate(PayData payData, LocalDate expectedExpiryDate) {
        LocalDate actualExpiryDate = ExpiryDateCalculator.get(payData);
        assertEquals(expectedExpiryDate, actualExpiryDate);
    }

    @Test
    void payForOneMonth_Then_ExpiryDateShouldBeNextMonth() {
        int payAmount = 10_000;
        PayData payData;

        payData = PayData.builder()
                .payDate(LocalDate.of(2023, 4, 3))
                .payAmount(payAmount)
                .build();
        assertExpiryDate(
                payData,
                LocalDate.of(2023, 5, 3));

        payData = PayData.builder()
                .payDate(LocalDate.of(2023, 5, 30))
                .payAmount(payAmount)
                .build();
        assertExpiryDate(
                payData,
                LocalDate.of(2023, 6, 30));
    }

    @Test
    void dayOfMonthsOfPayDateAndNextMonthOfItAreNotEqual() {
        int payAmount = 10_000;

        assertExpiryDate(
                PayData.builder()
                        .payDate(LocalDate.of(2023, 1, 31))
                        .payAmount(payAmount)
                        .build(),
                LocalDate.of(2023, 2, 28));

        assertExpiryDate(
                PayData.builder()
                        .payDate(LocalDate.of(2023, 5, 31))
                        .payAmount(payAmount)
                        .build(),
                LocalDate.of(2023, 6, 30));

        assertExpiryDate(
                PayData.builder()
                        .payDate(LocalDate.of(2020, 1, 31))
                        .payAmount(payAmount)
                        .build(),
                LocalDate.of(2020, 2, 29));
    }

    @Test
    void whenDayOfMonthsOfPayDateAndOfFirstPayDateAreDifferent_PayForOneMonth_Then_CriterionIsFirstPayDate() {
        final int payAmount = 10_000;
        assertExpiryDate(
                PayData.builder()
                        .firstPayDate(LocalDate.of(2020, 1, 31))
                        .payDate(LocalDate.of(2020, 2, 29))
                        .payAmount(payAmount)
                        .build(),
                LocalDate.of(2020, 3, 31));

        assertExpiryDate(
                PayData.builder()
                        .firstPayDate(LocalDate.of(2020, 1, 30))
                        .payDate(LocalDate.of(2020, 2, 29))
                        .payAmount(payAmount)
                        .build(),
                LocalDate.of(2020, 3, 30));

        assertExpiryDate(
                PayData.builder()
                        .firstPayDate(LocalDate.of(2020, 5, 31))
                        .payDate(LocalDate.of(2020, 6, 30))
                        .payAmount(payAmount)
                        .build(),
                LocalDate.of(2020, 7, 31));
    }

    @Test
    void payForMoreThanTwoMonths_Then_AddedMonths() {
        assertExpiryDate(
                PayData.builder()
                        .payDate(LocalDate.of(2020, 6, 30))
                        .payAmount(20_000)
                        .build(),
                LocalDate.of(2020, 8, 30));

        assertExpiryDate(
                PayData.builder()
                        .payDate(LocalDate.of(2020, 1, 31))
                        .payAmount(30_000)
                        .build(),
                LocalDate.of(2020, 4, 30));

        assertExpiryDate(
                PayData.builder()
                        .payDate(LocalDate.of(2020, 7, 30))
                        .payAmount(70_000)
                        .build(),
                LocalDate.of(2021, 2, 28));
    }

    @Test
    void 첫납부일과_새납부일의_일자가_다를때_두달이상_납부() {
        assertExpiryDate(
                PayData.builder()
                        .firstPayDate(LocalDate.of(2020, 1, 31))
                        .payDate(LocalDate.of(2020, 2, 29))
                        .payAmount(20_000)
                        .build(),
                LocalDate.of(2020, 4, 30));

        assertExpiryDate(
                PayData.builder()
                        .firstPayDate(LocalDate.of(2018, 10, 31))
                        .payDate(LocalDate.of(2018, 11, 30))
                        .payAmount(30_000)
                        .build(),
                LocalDate.of(2019, 2, 28));

        assertExpiryDate(
                PayData.builder()
                        .firstPayDate(LocalDate.of(2019, 1, 31))
                        .payDate(LocalDate.of(2019, 2, 28))
                        .payAmount(40_000)
                        .build(),
                LocalDate.of(2019, 6, 30));

        assertExpiryDate(
                PayData.builder()
                        .firstPayDate(LocalDate.of(2019, 3, 31))
                        .payDate(LocalDate.of(2019, 4, 30))
                        .payAmount(30_000)
                        .build(),
                LocalDate.of(2019, 7, 31));
    }

    @Test
    void ifPayForTenMonths_Then_ExpiryDateShouldBePayDatePlusOneYear() {
        final int PAY_AMOUNT = 100_000;

        assertExpiryDate(
                PayData.builder()
                        .payDate(LocalDate.of(2019, 1, 1))
                        .payAmount(PAY_AMOUNT)
                        .build(),
                LocalDate.of(2020, 1, 1));

        assertExpiryDate(
                PayData.builder()
                        .payDate(LocalDate.of(2019, 12, 31))
                        .payAmount(PAY_AMOUNT)
                        .build(),
                LocalDate.of(2020, 12, 31));
    }

    @Test
    void ifPayForTenMonths_WhenLeapYearLeapMonth() {
        final int PAY_AMOUNT = 100_000;

        assertExpiryDate(
                PayData.builder()
                        .payDate(LocalDate.of(2020, 2, 29))
                        .payAmount(PAY_AMOUNT)
                        .build(),
                LocalDate.of(2021, 2, 28));
    }

    @Test
    void ifPayForMoreThanTenMonths() {
        assertExpiryDate(
                PayData.builder()
                        .payDate(LocalDate.of(2019, 12, 31))
                        .payAmount(110_000)
                        .build(),
                LocalDate.of(2021, 1, 31));

        assertExpiryDate(
                PayData.builder()
                        .payDate(LocalDate.of(2019, 12, 31))
                        .payAmount(120_000)
                        .build(),
                LocalDate.of(2021, 2, 28));
    }

    @Test
    void 첫납부일과_납부일이_다를때_열달이상_납부() {
        assertExpiryDate(
                PayData.builder()
                        .firstPayDate(LocalDate.of(2018, 12, 31))
                        .payDate(LocalDate.of(2019, 2, 28))
                        .payAmount(100_000)
                        .build(),
                LocalDate.of(2020, 2, 29));

        assertExpiryDate(
                PayData.builder()
                        .firstPayDate(LocalDate.of(2018, 12, 31))
                        .payDate(LocalDate.of(2019, 2, 28))
                        .payAmount(100_000)
                        .build(),
                LocalDate.of(2020, 2, 29));

        assertExpiryDate(
                PayData.builder()
                        .firstPayDate(LocalDate.of(2020, 2, 29))
                        .payDate(LocalDate.of(2020, 5, 31))
                        .payAmount(190_000)
                        .build(),
                LocalDate.of(2022, 2, 28));
    }

    @Test
    void 추가_테스트() {
        /*
        1. payDate 가 null
        2. payAmount 가 한달 요금보다 작거나, 한달 요금으로 나누어 떨어지지 않을 때
         */
    }
}
