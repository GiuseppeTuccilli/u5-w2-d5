package giuseppetuccilli.u5_w2_d5.viaggi;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public record NewViaggioPayload(
        @NotEmpty(message = "la destinazione è obbligatoria")
        @Size(min = 5, max = 30, message = "la destinazione deve avere un numero di caratteri compreso fra 5 e 30")
        String destinazione,
        @NotBlank(message = "la data deve essere nel formato YYYY/MM/DD/")
        @Size(min = 10, message = "la data deve essere nel formato YYYY/MM/DD/")
        String data
) {
}
