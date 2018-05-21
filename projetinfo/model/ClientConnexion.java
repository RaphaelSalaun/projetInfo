package com.example.xx_laphoune_xx.projetinfo.model;

/**
 * Created by Xx_LaPhoune_xX on 21/05/2018.
 */
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;


public class ClientConnexion implements Runnable{

    private Socket connexion = null;
    private PrintWriter writer = null;
    private BufferedInputStream reader = null;

    public ClientConnexion(String host, int port){

        try {
            connexion = new Socket(host, port);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void run(){

            try {


                writer = new PrintWriter(connexion.getOutputStream(), true);
                reader = new BufferedInputStream(connexion.getInputStream());
                //On envoie les donnÃ©es au serveur

                String donnees = "Nikita Belluci Femme données";
                writer.write(donnees);
                writer.flush();
               // System.out.println("Données envoyées au serveur");
                read();
            } catch (IOException e1) {
                e1.printStackTrace();
            }



        writer.write("CLOSE");
        writer.flush();
        writer.close();
    }

    //MÃ©thode pour lire les rÃ©ponses du serveur
    private String read() throws IOException{
        String response = "";
        int stream;
        byte[] b = new byte[4096];
        stream = reader.read(b);
        response = new String(b, 0, stream);
        return response;
    }
}
