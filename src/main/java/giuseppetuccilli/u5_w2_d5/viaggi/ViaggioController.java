package giuseppetuccilli.u5_w2_d5.viaggi;

import giuseppetuccilli.u5_w2_d5.exeptions.ValidazioneFallitaExeption;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/viaggi")
public class ViaggioController {
    @Autowired
    private ViaggioServices viserv;

    @GetMapping
    public Page<Viaggio> getViaggi(@RequestParam(defaultValue = "0") int pgNum) {
        return viserv.findAll(pgNum);
    }

    @GetMapping("/{vId}")
    public Viaggio getViaggio(@PathVariable long vId) {
        return viserv.findById(vId);
    }

    @PostMapping
    public Viaggio postViaggio(@RequestBody @Validated NewViaggioPayload body, BindingResult valRes) {
        if (valRes.hasErrors()) {
            List<String> errList = new ArrayList<>();
            for (int i = 0; i < valRes.getFieldErrors().size(); i++) {
                errList.add(valRes.getFieldErrors().get(i).getDefaultMessage());
            }
            throw new ValidazioneFallitaExeption(errList);
        }
        return viserv.saveViaggio(body);
    }

    @DeleteMapping("/{vId}")
    public void deleteViaggio(@PathVariable long vId) {
        viserv.deleteViaggio(vId);
    }
    
    //alla creazione i lo stato del viaggio è in programma, una volta cambiato a completato non è
    //più possibile riportarlo allo stato precedente
    @PatchMapping("/{vId}")
    public Viaggio setCompletato(@PathVariable long vId) {
        return viserv.changeStatusToCompletato(vId);
    }
}
