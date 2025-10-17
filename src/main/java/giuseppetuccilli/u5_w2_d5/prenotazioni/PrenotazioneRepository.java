package giuseppetuccilli.u5_w2_d5.prenotazioni;

import giuseppetuccilli.u5_w2_d5.dipendenti.Dipendente;
import giuseppetuccilli.u5_w2_d5.viaggi.Viaggio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PrenotazioneRepository extends JpaRepository<Prenotazione, Long> {
    List<Prenotazione> findByDipendente(Dipendente dipendente);

    Optional<Prenotazione> findByViaggio(Viaggio viaggio);

    boolean existsByViaggio(Viaggio viaggui);


}
