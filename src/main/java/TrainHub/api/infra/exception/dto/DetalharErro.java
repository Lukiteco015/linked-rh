package TrainHub.api.infra.exception.dto;

public record DetalharErro(

   @com.fasterxml.jackson.annotation.JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
   java.time.LocalDateTime timeStamp,
   Integer status,
   String erro,
   String mensagem,
   String caminho
) {}
