package giuseppetuccilli.u5_w2_d5.viaggi;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "viaggi")
@NoArgsConstructor
@Getter
@Setter
public class Viaggio {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    private long id;
    private StatoViaggio stato;
    private LocalDate data;
    private String destinazione;

    public Viaggio(LocalDate data, String destinazione) {
        this.data = data;
        this.destinazione = destinazione;
        this.stato = StatoViaggio.IN_PROGRAMMA;
    }


}
