package giuseppetuccilli.u5_w2_d5.prenotazioni;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/prenotazioni")
public class PrenotazioneController {
    @Autowired
    private PrenotazioneService preServ;

    @GetMapping
    public Page<Prenotazione> getPrenotazioni(@RequestParam(defaultValue = "0") int numPg) {
        return preServ.findAll(numPg);
    }

    @GetMapping("/{prId}")
    public Prenotazione getPrenotazione(@PathVariable long prId) {
        return preServ.findById(prId);
    }

    @DeleteMapping("/{prId}")
    public void deletePrenotazione(@PathVariable long prId) {
        preServ.deletePrenotazione(prId);
    }


}
