package domain;

import java.io.IOException;

public interface FileTransfer {
    public  MetadatosFichero receiveMeta() throws IOException;
    public void receiveBody(MetadatosFichero fichero,String directory) throws IOException;
}
