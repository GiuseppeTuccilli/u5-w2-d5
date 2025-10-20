package giuseppetuccilli.u5_w2_d5.security;

import giuseppetuccilli.u5_w2_d5.dipendenti.Dipendente;
import giuseppetuccilli.u5_w2_d5.dipendenti.DipendenteService;
import giuseppetuccilli.u5_w2_d5.dipendenti.NewDipPayload;
import giuseppetuccilli.u5_w2_d5.exeptions.ValidazioneFallitaExeption;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private DipendenteService dipServ;
    @Autowired
    private AuthService authServ;

    @PostMapping("login")
    public LoginResDTO login(@RequestBody LoginDTO body) {
        return new LoginResDTO(authServ.checkAndGenerate(body));
    }

    @PostMapping("register")
    @ResponseStatus(HttpStatus.CREATED)
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
