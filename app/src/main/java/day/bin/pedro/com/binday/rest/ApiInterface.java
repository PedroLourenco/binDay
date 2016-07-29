package day.bin.pedro.com.binday.rest;

import java.util.List;

import day.bin.pedro.com.binday.model.PropertyInformation;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by pedro on 28/07/16.
 */
public interface ApiInterface {

    //https://doitonline.york.gov.uk/BinsApi/EXOR/getPropertiesForPostCode?postcode=YO24%201NB
    //https://doitonline.york.gov.uk/BinsApi/EXOR/getPropertiesForPostCode?postcode=YO24%2B1NB

    @GET("getPropertiesForPostCode")
    Call<List<PropertyInformation>> getPropertiesForPostCode(@Query("postcode") String postcode);
}