package chap02;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum PasswordStrength {
    STRONG("강함"),
    NORMAL("보통"),
    WEAK("약함"),
    INVALID("유효하지 않음")
    ;

    private final String description;
}
