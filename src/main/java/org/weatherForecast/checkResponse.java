package org.weatherForecast;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class checkResponse {
    public static void main(String[] args) throws Exception {
        String url = "https://weather.visualcrossing.com/VisualCrossingWebServices/rest/services/weatherdata/forecast?aggregateHours=24&contentType=json&unitGroup=metric&locationMode=single&locations=Allahabad,India&key=1PYNQ6AWUDJE9AFERDCHJHSXK";

        HttpRequest request = HttpRequest.newBuilder().GET().uri(URI.create(url)).build();

        HttpClient httpClient = HttpClient.newBuilder().build();

        HttpResponse<String> httpResponse = httpClient.send(request,HttpResponse.BodyHandlers.ofString());

        System.out.println(httpResponse.statusCode());
        System.out.println(httpResponse.body());

        /*we didn't print our selected response by this method of direct
         call API.Means if we want to give location at a time
        - Then we cant does this because it is calling whole api directly.
        - which we print wether forecast for only one location by this method.
         */
    }
}
