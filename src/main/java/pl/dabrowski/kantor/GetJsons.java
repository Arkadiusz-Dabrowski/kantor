package pl.dabrowski.kantor;

import pl.dabrowski.kantor.entity.JsonValues;
import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.Path;

public interface GetJsons  {

    @GET("/api/exchangerates/rates/a/eur/{year}-01-01/{year}-12-31?format=json")
    Call<JsonValues> list(@Path("year") String year);

    @GET("/api/exchangerates/rates/a/eur/{year}-01-01/{today}?format=json")
    Call<JsonValues> list2(@Path("year") String year, @Path("today") String today);
}
