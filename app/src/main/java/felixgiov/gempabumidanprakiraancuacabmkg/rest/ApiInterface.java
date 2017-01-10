package felixgiov.gempabumidanprakiraancuacabmkg.rest;

/**
 * Created by felix on 9 Jan 2017.
 */

import felixgiov.gempabumidanprakiraancuacabmkg.model.Example;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;


public interface ApiInterface {

    String API_BMKG = "bmkg";

    @GET(API_BMKG)
    Call<Example> getGempaTerkini(@Query("view") String view, @Query("k") String k);

}