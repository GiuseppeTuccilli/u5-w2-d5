package giuseppetuccilli.u5_w2_d5.prenotazioni;

import giuseppetuccilli.u5_w2_d5.dipendenti.Dipendente;
import giuseppetuccilli.u5_w2_d5.dipendenti.DipendenteService;
import giuseppetuccilli.u5_w2_d5.exeptions.BadRequestExeption;
import giuseppetuccilli.u5_w2_d5.exeptions.NotFoundExeption;
import giuseppetuccilli.u5_w2_d5.viaggi.StatoViaggio;
import giuseppetuccilli.u5_w2_d5.viaggi.Viaggio;
import giuseppetuccilli.u5_w2_d5.viaggi.ViaggioServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class PrenotazioneService {
    @Autowired
    private PrenotazioneRepository preRepo;
    @Autowired
    private DipendenteService dipServ;
    @Autowired
    private ViaggioServices viServ;

    public Page<Prenotazione> findAll(int numPg) {
        Pageable pg = PageRequest.of(numPg, 10);
        return preRepo.findAll(pg);
    }

    public Prenotazione findById(long id) {
        Optional<Prenotazione> found = preRepo.findById(id);
        if (found.isPresent()) {
            return found.get();
        } else {
            throw new NotFoundExeption(id);
        }
    }

    public void deletePrenotazione(long id) {
        Prenotazione found = this.findById(id);
        preRepo.delete(found);
    }

    public Prenotazione savePrenotazione(NewPrenotPayload payload, long idDip) {
        Dipendente dipFound = dipServ.findById(idDip);
        List<Prenotazione> preList = preRepo.findByDipendente(dipFound);
        Viaggio viaggioPren = viServ.findById(payload.idViaggio());
        //controllo che il viaggio non sia prenotato
        if (preRepo.existsByViaggio(viaggioPren)) {
            throw new BadRequestExeption("il viaggio è già prenotato");
        }
        //controllo che il viaggio non sia completato o in data passata
        if (viaggioPren.getStato() == StatoViaggio.COMPLETATO || viaggioPren.getData().isBefore(LocalDate.now())) {
            throw new BadRequestExeption("il viaggio è gia stato completato");
        }

        //controllo che le non ci siano viaggi prenotati per la data
        if (!preList.isEmpty()) {
            for (int i = 0; i < preList.size(); i++) {
                if (preList.get(i).getViaggio().getData().equals(viaggioPren.getData())) {
                    throw new BadRequestExeption("non puoi prenotare più viaggi per la stessa data");
                }
            }
        }
        Prenotazione newPren = new Prenotazione(dipFound, viaggioPren, payload.note());
        preRepo.save(newPren);
        System.out.println("prenotazione salvata");
        return newPren;
    }
}
