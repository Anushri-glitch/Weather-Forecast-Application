package org.weatherForecast;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Scanner;

public class weather {
    public static void main(String[] args) {
        //System.out.println("Hello world!");

        try {
            callWeatherForecastApp();
        }catch(Exception e){
            throw new RuntimeException(e);
        }

    }

    /* This Method will call weather API and show the data */

    public static void callWeatherForecastApp() throws Exception {

        //Taking input for location to be forecasted
        System.out.println("Enter the location for which you want the weather forcast information : ");
        Scanner sc = new Scanner(System.in);
        String location = sc.nextLine();

        URIBuilder builder = new URIBuilder("https://weather.visualcrossing.com/VisualCrossingWebServices/rest/services/weatherdata/forecast");
        builder.setParameter("aggregateHours", "24");
        builder.setParameter("contentType", "json");
        builder.setParameter("unitGroup", "metric");
        builder.setParameter("locationMode", "single");
        builder.setParameter("key", "1PYNQ6AWUDJE9AFERDCHJHSXK");
        builder.setParameter("location", location);

        HttpGet getData = new HttpGet(builder.build());

        CloseableHttpClient httpClient = HttpClients.createDefault();

        CloseableHttpResponse response = httpClient.execute(getData);
        //System.out.println(response);

        if(response.getStatusLine().getStatusCode() == 200){
            HttpEntity entityResponse = response.getEntity();
            String result = EntityUtils.toString(entityResponse);

            //Below code is new code...After this , we see only some attributes data from weatherForecast...

            /* 1. JSON Response formatting for atributes 1. mint 2. maxt 3. datetimeStr 4. visibility 5. humidity
               2. Use JSONObject class to make response as object
            */
            JSONObject responseObject = new JSONObject(result);

            JSONObject locationObject = responseObject.getJSONObject("location");

            JSONArray valueObject = locationObject.getJSONArray("values");

            System.out.println("datetimeStr            \t     mint     \t  maxt   \t visibility \t humidity");
            for(int i=0; i<valueObject.length(); i++){

                JSONObject value = valueObject.getJSONObject(i);

                String dateTime = value.getString("datetimeStr");
                Double minTemp = value.getDouble("mint");
                Double maxTemp = value.getDouble("maxt");
                Double visi = value.getDouble("visibility");
                Double humi = value.getDouble("humidity");

                System.out.printf("%s \t %f \t %f \t %f \t   %f \n",dateTime,minTemp,maxTemp,visi,humi);
            }
            System.out.println(result);
        }
        else{
            System.out.println("Something went wrong !");
            return;
        }
        //After Formatting JSON , we see the clearify version of our JSON where only valuable output is shown.



        //If we print this below statement then it shows the called API
        //System.out.println(entityResponse);

        httpClient.close();

    }
}

//How to beautify json to see the real answer then go to Postman with copy of this output.....