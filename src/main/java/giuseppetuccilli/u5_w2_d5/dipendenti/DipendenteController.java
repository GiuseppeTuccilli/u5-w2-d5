package giuseppetuccilli.u5_w2_d5.dipendenti;

import giuseppetuccilli.u5_w2_d5.exeptions.ValidazioneFallitaExeption;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/dipendenti")
public class DipendenteController {
    @Autowired
    private DipendenteService dipServ;

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
}
