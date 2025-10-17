package giuseppetuccilli.u5_w2_d5.exeptions;

import java.time.LocalDate;

public record ErrorsPayload(String message, LocalDate data) {
}
