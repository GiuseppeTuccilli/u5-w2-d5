package giuseppetuccilli.u5_w2_d5.dipendenti;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record NewDipPayload(
        @NotBlank(message = "il nome è obbligatorio")
        @Size(min = 2, max = 30, message = "il nome deve avere dai 2 ai 30 caratteri")
        String nome,
        @NotBlank(message = "il cognome è obbligatorio")
        @Size(min = 2, max = 30, message = "il cognome deve avere dai 2 ai 30 caratteri")
        String cognome,
        @NotBlank(message = "l'email è obbligatoria")
        @Email(message = "l'email inserita non è nel formato corretto")
        String email
) {
}
