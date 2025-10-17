package giuseppetuccilli.u5_w2_d5.viaggi;

import giuseppetuccilli.u5_w2_d5.exeptions.BadRequestExeption;
import giuseppetuccilli.u5_w2_d5.exeptions.NotFoundExeption;
import giuseppetuccilli.u5_w2_d5.prenotazioni.Prenotazione;
import giuseppetuccilli.u5_w2_d5.prenotazioni.PrenotazioneRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;

@Service
public class ViaggioServices {
    @Autowired
    private ViaggioRepository viRepo;
    @Autowired
    private PrenotazioneRepository preRepo;

    //prende la data in stringa dal json e ritorna localdate
    private LocalDate getData(String data) {
        String dataString = "";
        if (data.length() > 10) {
            dataString = data.substring(0, 10);
        } else if (data.length() == 10) {
            dataString = data;
        } else {
            throw new BadRequestExeption("data non valida");
        }
        try {
            String[] dataArray = dataString.split("-");
            int anno = Integer.parseInt(dataArray[0]);
            int mese = Integer.parseInt(dataArray[1]);
            int giorno = Integer.parseInt(dataArray[2]);
            LocalDate dataloc = LocalDate.of(anno, mese, giorno);
            return dataloc;
        } catch (Exception ex) {
            throw new BadRequestExeption("data non valida");
        }

    }

    public Page<Viaggio> findAll(int numPg) {
        Pageable pg = PageRequest.of(numPg, 10);
        return viRepo.findAll(pg);
    }

    public Viaggio findById(long id) {
        Optional<Viaggio> found = viRepo.findById(id);
        if (found.isPresent()) {
            return found.get();
        } else {
            throw new NotFoundExeption(id);
        }
    }

    public Viaggio saveViaggio(NewViaggioPayload payload) {
        LocalDate dataLoc = getData(payload.data());
        Viaggio newViaggio = new Viaggio(dataLoc, payload.destinazione());
        viRepo.save(newViaggio);
        System.out.println("viaggio salvato");
        return newViaggio;
    }

    public void deleteViaggio(long id) {
        Viaggio found = findById(id);
        //prima di cancellare un viaggio cancello l'eventuale prenotazione associata
        Optional<Prenotazione> pre = preRepo.findByViaggio(found);
        if (pre.isPresent()) {
            preRepo.delete(pre.get());
        }
        viRepo.delete(found);
        System.out.println("viaggio eliminato");
    }

    //cambia lo stato in completato
    public Viaggio changeStatusToCompletato(long id) {
        Viaggio found = findById(id);
        if (found.getStato() == StatoViaggio.COMPLETATO) {
            throw new BadRequestExeption("il viaggio è già stato completato");
        }
        found.setStato(StatoViaggio.COMPLETATO);
        viRepo.save(found);
        return found;
    }
}
