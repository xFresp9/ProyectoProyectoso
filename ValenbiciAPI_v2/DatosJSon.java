/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package es.gva.edu.iesjuandegaray.valenbiciv2;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author Eva G.
 */
public class DatosJSon {
    private static String API_URL;
    private String datos = "";  //para mostar en el jTextArea los datos de las estaciones
     
    private String [] values; //para añadir los datos de las estaciones Valenbici a la BDD
    private int numEst;
    
    public DatosJSon(int nE){
          numEst = nE;
          datos = "";
          API_URL = "https://valencia.opendatasoft.com/api/explore/v2.1/catalog/datasets/valenbisi-disponibilitat-valenbisi-dsiponibilidad/records?f=json&location=39.46447,-0.39308&distance=10&limit=" + nE;
          
          values = new String [numEst];
          
          for (int i = 0; i < numEst; i++ )
              values[i] = "";
    }
    
      
    
    public void mostrarDatos(int nE){
        
        numEst = nE;
        datos="";
        API_URL ="https://valencia.opendatasoft.com/api/explore/v2.1/catalog/datasets/valenbisi-disponibilitat-valenbisi-dsiponibilidad/records?f=json&location=39.46447,-0.39308&distance=10&limit=" + nE;
        
        values = new String [numEst];
        for (int i = 0; i < numEst; i++ )
              values[i] = "";

        double lon,lat;
      
        if (API_URL.isEmpty()) {
            //System.err.println("La URL de la API no está especificada.");
            setDatos(getDatos().concat("La URL de la API no está especificada."));
            return;
        }

        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpGet request = new HttpGet(API_URL);
            HttpResponse response = httpClient.execute(request);

            HttpEntity entity = response.getEntity();
            if (entity != null) {
                String result = EntityUtils.toString(entity);
             

                // Intentamos procesar la respuesta como JSON
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    JSONArray resultsArray = jsonObject.getJSONArray("results");

                    // Añade aquí el Código para recorrer el vector de objetos JSON, con los datos de
                     //  las estaciones y preparar el vector de valores (atributo values de esta clase).
                    for (int i = 0; i < resultsArray.length(); i++) {
                        JSONObject record = resultsArray.getJSONObject(i);

                        String address = record.optString("address");
                        int number = record.getInt("number");
                        String open = record.getString("open");
                        int available = record.getInt("available");
                        int free = record.getInt("free");
                        String updatedAt = record.getString("updated_at");

                        JSONObject geoPoint = record.getJSONObject("geo_point_2d");
                        lon = geoPoint.getDouble("lon");
                        lat = geoPoint.getDouble("lat");
                        
                        // Guardamos todos los datos en formato String
                        values[i] = "Dirección: " + address + ", Nº: " + number +
                                    ", Abierta: " + open + ", Bicis disponibles: " + available +
                                    ", Anclajes libres: " + free + ", Actualizado: " + updatedAt +
                                    ", Coords: (" + lat + "<" + lon + ")";
                    
                    }
                    datos = String.join("\n", values);

                } catch (org.json.JSONException e) {
                    // Si la respuesta no es un array JSON, imprimimos el mensaje de error
                    setDatos(getDatos().concat("Error al procesar los datos JSON: " + e.getMessage()));

                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return;
}
    
    /**
     * @return the datos
     */
    public String getDatos() {
        return datos;
    }

    /**
     * @param datos the datos to set
     */
    public void setDatos(String datos) {
        this.datos = datos;
    }

    /**
     * @return the values
     */
    public String[] getValues() {
        return values;
    }

    /**
     * @param values the values to set
     */
    public void setValues(String[] values) {
        this.values = values;
    }

    /**
     * @return the numEst
     */
    public int getNumEst() {
        return numEst;
    }

    /**
     * @param numEst the numEst to set
     */
    public void setNumEst(int numEst) {
        this.numEst = numEst;
    }
}


