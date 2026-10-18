package data;

import domain.model.MetadatosFichero;
import domain.model.PeticionAuth;
import domain.model.RespuestaAuth;
import domain.model.RespuestaMetadatos;
import domain.repository.FileTransfer;

import java.io.*;
import java.net.Socket;

public class TransferSockets implements FileTransfer {

    private ObjectOutputStream objectOutputStream;

    private ObjectInputStream objectInputStream;

    public TransferSockets(Socket socket) throws IOException {
        objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
        objectOutputStream.flush();
        objectInputStream = new ObjectInputStream(socket.getInputStream());

    }

    @Override
    public void sendPeticioAuth(PeticionAuth peticionAuth) throws IOException {
        objectOutputStream.writeObject(peticionAuth);
        objectOutputStream.flush();
    }

    @Override
    public RespuestaAuth receiveRespuestaAuth() throws IOException, ClassNotFoundException {
        return (RespuestaAuth) objectInputStream.readObject();
    }


    @Override
    public void sendMetaDatos(MetadatosFichero metadatosFichero) throws IOException {
        objectOutputStream.writeObject(metadatosFichero);
        objectOutputStream.flush();
    }

    @Override
    public RespuestaMetadatos receiveRespuestaMetadatos() throws IOException, ClassNotFoundException {
        return (RespuestaMetadatos) objectInputStream.readObject();
    }

    @Override
    public void SendBody(MetadatosFichero metadatosFichero, String directory) throws IOException {
        byte[] buffer = new byte[8 * 1024];
        File file = new File(directory+File.separator+ metadatosFichero.getNombre());
        int longitud = 0;
        try (FileInputStream fileInputStream = new FileInputStream(file)) {
            while ((longitud = fileInputStream.read(buffer, 0, buffer.length)) != -1) {
                objectOutputStream.write(buffer, 0, longitud);
            }
            objectOutputStream.flush();
        }
    }


}
