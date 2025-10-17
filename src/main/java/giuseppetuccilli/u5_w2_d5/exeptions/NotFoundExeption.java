package giuseppetuccilli.u5_w2_d5.exeptions;

public class NotFoundExeption extends RuntimeException {
    public NotFoundExeption(long id) {
        super("la risorsa con id " + id + " non è presente nel database");
    }
}
