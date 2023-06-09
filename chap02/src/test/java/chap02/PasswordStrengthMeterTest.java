package chap02;

import org.junit.jupiter.api.Test;

import static chap02.PasswordStrength.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class PasswordStrengthMeterTest {

    private void assertStrength(String password, PasswordStrength strength) {
        PasswordStrength result = PasswordStrengthMeter.discriminate(password);
        assertEquals(strength, result);
    }

    @Test
    void meetsAllCriteria_Then_Strong() {
        assertStrength("ab12!@AB", STRONG);
        assertStrength("abABC!@34", STRONG);
    }

    @Test
    void meetsOtherCriteria_except_for_Length_Then_Normal() {
        assertStrength("123A", NORMAL);
        assertStrength("Aabc123", NORMAL);
    }

    @Test
    void meetsOtherCriteria_except_for_Digit_Then_Normal() {
        assertStrength("abc!A@aZ", NORMAL);
        assertStrength("AabZxcvqpm",NORMAL);
    }

    @Test
    void meetsOtherCriteria_except_for_UpperCase_Then_Normal() {
        assertStrength("abc!123@a7", NORMAL);
        assertStrength("3ab9xcvqp", NORMAL);
    }

    @Test
    void meetsOnlyContainingDigitCriterion_Then_Weak() {
        assertStrength("12", WEAK);
        assertStrength("4873baa", WEAK);
        assertStrength("48!m@*z", WEAK);
    }

    @Test
    void meetsOnlyContainingUpperCaseCriterion_Then_Weak() {
        assertStrength("AaAz", WEAK);
        assertStrength("asdoBAA", WEAK);
        assertStrength("da!M@*Z", WEAK);
    }

    @Test
    void meetsOnlyLengthCriterion_Then_Weak() {
        assertStrength("asdja!@#", WEAK);
        assertStrength("asdo^!asd", WEAK);
        assertStrength("da!!@#$a", WEAK);
    }

    @Test
    void meetsNothing_Then_Weak() {
        assertStrength("asd!@#", WEAK);
        assertStrength("as^a*sd", WEAK);
        assertStrength("da@$a", WEAK);
    }

    @Test
    void nullInput_Then_Invalid() {
        assertStrength(null, INVALID);
    }

    @Test
    void emptyInput_Then_Invalid() {
        assertStrength("", INVALID);
    }

}
