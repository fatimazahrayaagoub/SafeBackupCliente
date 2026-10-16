package domain;

import java.io.IOException;

public interface FileTransfer {
    public void sendPeticioAuth(PeticionAuth peticionAuth) throws IOException;

    public RespuestaAuth receiveRespuestaAuth() throws IOException, ClassNotFoundException;

    public void sendMetaDatos(MetadatosFichero metadatosFichero) throws IOException;

    public RespuestaMetadatos receiveRespuestaMetadatos() throws IOException, ClassNotFoundException;

    public void SendBody(MetadatosFichero metadatosFichero, String directory) throws IOException;


}