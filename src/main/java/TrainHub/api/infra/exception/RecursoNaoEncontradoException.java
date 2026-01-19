package TrainHub.api.infra.exception;

public class RecursoNaoEncontradoException extends RuntimeException{
    public RecursoNaoEncontradoException (String mensagem) {
        super(mensagem);
    }
}
