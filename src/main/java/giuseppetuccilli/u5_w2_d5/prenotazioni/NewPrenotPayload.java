package giuseppetuccilli.u5_w2_d5.prenotazioni;

import jakarta.validation.constraints.NotNull;

public record NewPrenotPayload(
        @NotNull
        long idViaggio,
        String note
) {
}
