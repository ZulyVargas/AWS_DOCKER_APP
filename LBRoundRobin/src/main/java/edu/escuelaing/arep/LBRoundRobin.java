package edu.escuelaing.arep;

import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.exceptions.UnirestException;

public class LBRoundRobin {
    private int currentServer = 0;
    private String[] ports = {":35001",":35002",":35003"};
    //private String[] ports = {":4561",":4561",":4561"};
    //Para aws:
    private String url = "http://ec2-54-88-224-69.compute-1.amazonaws.com";
    //private String url = "http://host.docker.internal";

    /**
     * Performs the get request to the server connected to the database.
     * @return body of the responde.
     * @throws UnirestException - Some unirest error in the request.
     */
    public String getMessages() throws UnirestException {
        System.out.println("URL generada "+ url+ports[currentServer]+"/messages" );
        HttpResponse<String> apiResponse = Unirest.get(url+ports[currentServer]+"/messages").asString();
        System.out.println("peticion GET desde--------------- "+url+ports[currentServer]+ "Longitud   " + apiResponse.getBody());
        return apiResponse.getBody();
    }
    /**
     * Performs the post request to the server connected to the database.
     * @return body of the responde.
     * @throws UnirestException - Some unirest error in the request.
     */
    public String postMessage(String message) throws UnirestException {
        HttpResponse<String> apiResponse = Unirest.post(url+ports[currentServer]+"/messages")
                .body(message)
                .asString();
        System.out.println("peticion POST desde--------------- "+url+ports[currentServer] + "Mesagge");
        return apiResponse.getBody();
    }
    /**
     * Switches between servers.
     */
    public void changeServer(){
        currentServer=(currentServer + 1) % ports.length;
    }
}