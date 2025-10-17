package giuseppetuccilli.u5_w2_d5.prenotazioni;

import giuseppetuccilli.u5_w2_d5.dipendenti.Dipendente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PrenotazioneRepository extends JpaRepository<Prenotazione, Long> {
    List<Prenotazione> findByDipendente(Dipendente dipendente);
}
