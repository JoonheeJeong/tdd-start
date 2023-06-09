package chap03;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.temporal.ChronoUnit;

public class ExpiryDateCalculator {

    private static final int FEE_FOR_A_MONTH = 10_000;

    public static LocalDate get(PayData payData) {
        LocalDate firstPayDate = payData.getFirstPayDate();
        LocalDate payDate = payData.getPayDate();
        int addedMonths = payData.getPayAmount() / FEE_FOR_A_MONTH;
        if (addedMonths >= 10)
            addedMonths += (addedMonths / 10) << 1;

        if (firstPayDate == null)
            return payDate.plusMonths(addedMonths);

        addedMonths += getDiffMonths(firstPayDate, payDate);
        return firstPayDate.plusMonths(addedMonths);
    }

    private static int getDiffMonths(LocalDate from, LocalDate to) {
        return 12 * (to.getYear() - from.getYear()) + to.getMonthValue() - from.getMonthValue();
//        return (int) ChronoUnit.MONTHS.between(
//                YearMonth.from(from),
//                YearMonth.from(to));
    }
}
