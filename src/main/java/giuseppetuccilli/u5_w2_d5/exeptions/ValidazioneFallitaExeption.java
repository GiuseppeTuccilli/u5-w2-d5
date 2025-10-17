package giuseppetuccilli.u5_w2_d5.exeptions;

import lombok.Getter;

import java.util.List;

@Getter
public class ValidazioneFallitaExeption extends RuntimeException {
    private List<String> msgList;

    public ValidazioneFallitaExeption(List<String> msgs) {
        super("ci sono stati errori nella validazione");
        this.msgList = msgs;
    }
}
