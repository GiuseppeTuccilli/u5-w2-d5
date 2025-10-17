package giuseppetuccilli.u5_w2_d5.dipendenti;

import giuseppetuccilli.u5_w2_d5.exeptions.ValidazioneFallitaExeption;
import giuseppetuccilli.u5_w2_d5.prenotazioni.NewPrenotPayload;
import giuseppetuccilli.u5_w2_d5.prenotazioni.Prenotazione;
import giuseppetuccilli.u5_w2_d5.prenotazioni.PrenotazioneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/dipendenti")
public class DipendenteController {
    @Autowired
    private DipendenteService dipServ;
    @Autowired
    private PrenotazioneService preServ;

    @GetMapping
    public Page<Dipendente> getDipendenti(@RequestParam(defaultValue = "0") int numPg) {
        return dipServ.findAll(numPg);
    }

    @GetMapping("/{dipId}")
    public Dipendente getDipendente(@PathVariable long dipId) {
        return dipServ.findById(dipId);
    }

    @PostMapping
    public Dipendente postDipendente(@RequestBody @Validated NewDipPayload body, BindingResult valRes) {
        if (valRes.hasErrors()) {
            List<String> errList = new ArrayList<>();
            for (int i = 0; i < valRes.getFieldErrors().size(); i++) {
                errList.add(valRes.getFieldErrors().get(i).getDefaultMessage());
            }
            throw new ValidazioneFallitaExeption(errList);
        }
        return dipServ.saveDipendente(body);

    }

    @PutMapping("/{dipId}")
    public Dipendente editDipendente(@PathVariable long dipId, @RequestBody @Validated NewDipPayload body, BindingResult valRes) {
        if (valRes.hasErrors()) {
            List<String> errList = new ArrayList<>();
            for (int i = 0; i < valRes.getFieldErrors().size(); i++) {
                errList.add(valRes.getFieldErrors().get(i).getDefaultMessage());
            }
            throw new ValidazioneFallitaExeption(errList);
        }
        return dipServ.editDipendente(dipId, body);

    }

    @DeleteMapping("/{dipId}")
    public void deleteDipendente(@PathVariable long dipId) {
        dipServ.deleteDip(dipId);
    }

    //la prenotazione la faccio nel controller dipendenti passando l'id del viaggio nel payload della prenotazione
    @PostMapping("/{dipId}/prenota")
    public Prenotazione prenota(@PathVariable long dipId, @RequestBody @Validated NewPrenotPayload body, BindingResult valRes) {
        if (valRes.hasErrors()) {
            List<String> errList = new ArrayList<>();
            for (int i = 0; i < valRes.getFieldErrors().size(); i++) {
                errList.add(valRes.getFieldErrors().get(i).getDefaultMessage());
            }
            throw new ValidazioneFallitaExeption(errList);
        }
        return preServ.savePrenotazione(body, dipId);
    }

    //cambio immagine profile che inizialmente è string vuota
    @PatchMapping("/{dipId}/avatar")
    public Dipendente changeAvatar(@PathVariable long dipId, @RequestParam("avatar") MultipartFile file) {
        System.out.println(file.getOriginalFilename());
        return dipServ.changeAvatar(dipId, file);
    }
}
