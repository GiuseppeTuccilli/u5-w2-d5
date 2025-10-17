package giuseppetuccilli.u5_w2_d5.dipendenti;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import giuseppetuccilli.u5_w2_d5.exeptions.BadRequestExeption;
import giuseppetuccilli.u5_w2_d5.exeptions.NotFoundExeption;
import giuseppetuccilli.u5_w2_d5.prenotazioni.Prenotazione;
import giuseppetuccilli.u5_w2_d5.prenotazioni.PrenotazioneRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class DipendenteService {
    private static final long MAX_SIZE = 10 * 1024 * 1024; //10 Mb ;
    private static final List<String> ALLOWED_TYPES = List.of("image/jpg", "image/jpeg", "image/png");
    @Autowired
    private DipendenteRepository dipRepo;
    @Autowired
    private PrenotazioneRepository preRepo;
    @Autowired
    private Cloudinary imgUploader;

    public Page<Dipendente> findAll(int numPg) {
        Pageable pg = PageRequest.of(numPg, 10);
        return dipRepo.findAll(pg);
    }

    public Dipendente findById(long id) {
        Optional<Dipendente> found = dipRepo.findById(id);
        if (found.isPresent()) {
            return found.get();
        } else {
            throw new NotFoundExeption(id);
        }
    }

    public Dipendente saveDipendente(NewDipPayload payload) {
        if (dipRepo.existsByEmail(payload.email())) {
            throw new BadRequestExeption("l'email " + payload.email() + " è già in uso");
        }
        Dipendente newDip = new Dipendente(payload.nome(), payload.cognome(), payload.email());
        dipRepo.save(newDip);
        System.out.println("dipendente salvato");
        return newDip;
    }

    //modifica nome, cognome e mail, non l'avatar
    public Dipendente editDipendente(long id, NewDipPayload payload) {
        Dipendente found = findById(id);
        found.setNome(payload.nome());
        found.setCognome(payload.cognome());
        found.setUsername();
        if (!payload.email().equals(found.getEmail())) {
            if (dipRepo.existsByEmail(payload.email())) {
                throw new BadRequestExeption("email già esistente nel database");
            }
        }
        found.setEmail(payload.email());
        dipRepo.save(found);
        return found;
    }

    public void deleteDip(long id) {
        Dipendente found = findById(id);
        //prima di eliminare un dipendente elimino tutte l sue prenotazioni
        List<Prenotazione> prenotazioni = preRepo.findByDipendente(found);
        if (!prenotazioni.isEmpty()) {
            for (int i = 0; i < prenotazioni.size(); i++) {
                preRepo.delete(prenotazioni.get(i));
            }
        }
        dipRepo.delete(found);
        System.out.println("dipendente eliminato");
    }

    public Dipendente changeAvatar(long id, MultipartFile file) {
        Dipendente found = findById(id);
        //controllo che il file non sia vuoto
        if (file.isEmpty()) {
            throw new BadRequestExeption("il file è vuoto");
        }
        //non superi i 10 MB
        if (file.getSize() > MAX_SIZE) {
            throw new BadRequestExeption("il file supera la dimensione massima consentita");
        }
        //sia di tipo jpg, jpeg o png
        if (!ALLOWED_TYPES.contains(file.getContentType())) {
            throw new BadRequestExeption("formato del file non supportato");
        }

        try {
            Map res = imgUploader.uploader().upload(file.getBytes(), ObjectUtils.emptyMap());
            String imgUrl = (String) res.get("url");
            found.setAvatar(imgUrl);
            dipRepo.save(found);
            return found;
        } catch (IOException ex) {
            throw new BadRequestExeption("errore nell'upload");
        }
    }
}
