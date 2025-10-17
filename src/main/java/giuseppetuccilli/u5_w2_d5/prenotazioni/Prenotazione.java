package giuseppetuccilli.u5_w2_d5.prenotazioni;

import giuseppetuccilli.u5_w2_d5.dipendenti.Dipendente;
import giuseppetuccilli.u5_w2_d5.viaggi.Viaggio;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "prenotazioni")
@Getter
@Setter
@NoArgsConstructor
public class Prenotazione {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    private long id;
    @Column(name = "data_richiesta")
    private LocalDate dataRichiesta;
    @Column(name = "note_dipendente")
    private String noteDipendente;

    @OneToOne
    @JoinColumn(name = "id_viaggio")
    private Viaggio viaggio;

    @ManyToOne
    @JoinColumn(name = "id_dipendente")
    private Dipendente dipendente;

    public Prenotazione(Dipendente dipendente, Viaggio viaggio, String note) {
        this.dipendente = dipendente;
        this.viaggio = viaggio;
        this.dataRichiesta = LocalDate.now();
        this.noteDipendente = note;
    }


}
