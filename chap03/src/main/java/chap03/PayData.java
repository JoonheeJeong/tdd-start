package chap03;

import lombok.*;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PayData {
    private LocalDate firstPayDate;
    private LocalDate payDate;
    private int payAmount;
}
