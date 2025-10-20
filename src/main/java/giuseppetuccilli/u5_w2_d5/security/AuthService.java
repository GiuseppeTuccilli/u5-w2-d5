package giuseppetuccilli.u5_w2_d5.security;

import giuseppetuccilli.u5_w2_d5.dipendenti.Dipendente;
import giuseppetuccilli.u5_w2_d5.dipendenti.DipendenteService;
import giuseppetuccilli.u5_w2_d5.exeptions.UnauthorizedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    @Autowired
    private DipendenteService dipServ;
    @Autowired
    private JWTTools jwtTools;

    public String checkAndGenerate(LoginDTO body) {
        Dipendente found = dipServ.findByEmail(body.email());
        if (body.nome().equals(found.getNome())) {
            return jwtTools.creaToken(found);
        } else {
            throw new UnauthorizedException("credenziali non corrette");
        }
    }
}
