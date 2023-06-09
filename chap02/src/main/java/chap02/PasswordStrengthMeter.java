package chap02;

import static chap02.PasswordStrength.*;

public class PasswordStrengthMeter {

    private static final PasswordStrength[] resultCode = { WEAK, WEAK, NORMAL, STRONG };

    public static PasswordStrength discriminate(String s) {
        if (s == null || s.isEmpty())
            return INVALID;

        int cntMetCriteria = countMetCriteria(s);
        return resultCode[cntMetCriteria];
    }

    private static int countMetCriteria(String s) {
        int cntMetCriteria = 0;
        if (isLengthEnough(s))
            cntMetCriteria++;
        if (containsDigit(s))
            cntMetCriteria++;
        if (containsUpperCase(s))
            cntMetCriteria++;
        return cntMetCriteria;
    }

    private static boolean isLengthEnough(String s) {
        return s.length() >= 8;
    }

    private static boolean containsDigit(String s) {
        for (char ch : s.toCharArray())
            if (Character.isDigit(ch))
                return true;
        return false;
    }

    private static boolean containsUpperCase(String s) {
        for (char ch : s.toCharArray())
            if (Character.isUpperCase(ch))
                return true;
        return false;
    }
}
