package com.example.xx_laphoune_xx.projetinfo.model;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.PrintWriter;
import java.net.Socket;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.TextView;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.UnknownHostException;

public class Client extends AsyncTask<Void, Void, String> {

    String dstAddress;
    int dstPort;
    String response = "";
    TextView textResponse;
    Context context;
    String path;

    private PrintWriter writer = null;
    private BufferedInputStream reader = null;

    public Client(Context context, String addr, int port, TextView textResponse) {
        dstAddress = addr;
        dstPort = port;
        this.textResponse = textResponse;
        this.context = context;
    }

    @Override
    protected String doInBackground(Void... arg0) {

        Socket socket = null;

        try {
            socket = new Socket(dstAddress, dstPort);


            writer = new PrintWriter(socket.getOutputStream(), true);
            reader = new BufferedInputStream(socket.getInputStream());
            File repertoire=new File(context.getFilesDir().getAbsolutePath());

            File[] listefichiers;
            listefichiers=repertoire.listFiles();
            int nb_fichiers_textes=0;
            for(File f:listefichiers){
                if(f.getName().endsWith(".txt")==true){
                    nb_fichiers_textes++;
                    writer.write(f.getName());
                    writer.flush();
                    read();
                    BufferedReader br = new BufferedReader(new FileReader(f));
                    String line;
                    while ((line = br.readLine()) != null) {
                        writer.write(line);
                        writer.flush();
                        read();
                    }
                    writer.write("e");
                    writer.flush();
                    read();
                    f.delete();
                    br.close();
                }
            }
            if (nb_fichiers_textes==0) {
                writer.write("s");
                writer.flush();
                response="Aucune donnee a envoyer";
            }
            else{
                writer.write("c");
                writer.flush();
                read();
                response="Donnees envoyes";
            }
            writer.close();





            /* Bloc pour lire qu'on n'utilise pas
           ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(
                    1024);
            byte[] buffer = new byte[1024];

            int bytesRead;
            InputStream inputStream = socket.getInputStream();

			*//*
             * notice: inputStream.read() will block if no data return
			 *//*
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                byteArrayOutputStream.write(buffer, 0, bytesRead);
                response += byteArrayOutputStream.toString("UTF-8");
            }*/

        } catch (UnknownHostException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            response = "UnknownHostException: " + e.toString();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            response = "IOException: " + e.toString();
        } finally {
            if (socket != null) {
                try {
                    socket.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
        return response;
    }

    private String read() throws IOException{
        String response = "";
        int stream;
        byte[] b = new byte[4096];
        stream = reader.read(b);
        response = new String(b, 0, stream);
        return response;
    }


    @Override
    protected void onPostExecute(String result) {
        textResponse.setText(response);
        super.onPostExecute(result);


    }

}