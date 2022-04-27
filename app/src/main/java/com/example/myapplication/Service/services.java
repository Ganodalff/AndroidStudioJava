package com.example.myapplication.Service;

import android.os.AsyncTask;

import com.example.myapplication.Model.CEP;
import com.google.gson.Gson;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URI;
import java.net.URL;
import java.util.Scanner;

public class services extends AsyncTask<Void, Void, CEP>{
    private final String cep;
    public services(String cep){
        this.cep = cep;
    }

    @Override
    protected CEP doInBackground(Void... voids) {
        StringBuilder resposta = new StringBuilder();
        try {
            URL url = new URL("https://viacep.com.br/ws/"+ this.cep +"/json/");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Accept","application/json");
            connection.setConnectTimeout(6000);
            connection.connect();


            Scanner scanner =  new Scanner(url.openStream());
            while (scanner.hasNext()){
                resposta.append(scanner.nextLine());
            }
        }catch (MalformedURLException e){
            e.printStackTrace();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new Gson().fromJson(resposta.toString(), CEP.class);
    }
}
