package giuseppetuccilli.u5_w2_d5.exeptions;

public class BadRequestExeption extends RuntimeException {
    public BadRequestExeption(String message) {
        super(message);
    }
}
