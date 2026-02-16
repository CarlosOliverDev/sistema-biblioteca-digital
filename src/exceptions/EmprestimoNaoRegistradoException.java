package exceptions;

public class EmprestimoNaoRegistradoException extends RuntimeException {
    public EmprestimoNaoRegistradoException(String message) {
        super(message);
    }
}
