package com.example.tvtracker.REST;

import android.content.Context;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.tvtracker.Favorites;
import com.example.tvtracker.Show;
import com.example.tvtracker.TvShow;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class RestRequests {

    public static final String QUERY_FOR_TVSHOW_NAME = "https://api.tvmaze.com/shows/";
    public static final String QUERY_FOR_TVSHOW_LIST = "https://api.tvmaze.com/search/shows?q=";
    public static final String QUERY_FOR_TVSHOW_ID = "https://api.tvmaze.com/singlesearch/shows?q=";

    String tvShowName;
    Context context;
    int id;

    public RestRequests(Context context) {
        this.context = context;
    }

    public interface VolleyResponseListener {
        void onError(String message);
        void onResponse(String tvShowName);
    }

    public interface TvShowListResponse {
        void onError(String message);
        void onResponse(ArrayList<String> result);
    }

    public interface IDResponseListener {
        void onError(String message);
        void onResponse(int id);
    }

    public void getShowName(int id, final VolleyResponseListener volleyResponseListener) {

             String url = QUERY_FOR_TVSHOW_NAME + id;

             JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                 @Override
                 public void onResponse(JSONObject response) {
                     tvShowName = "";
                     try {
                         tvShowName = response.getString("name");

                     } catch (JSONException e) {
                         e.printStackTrace();
                     }
                     volleyResponseListener.onResponse(tvShowName);
                 }

             }, new Response.ErrorListener() {
                 @Override
                 public void onErrorResponse(VolleyError error) {
                     Toast.makeText(context, "Error", Toast.LENGTH_LONG).show();
                     volleyResponseListener.onError("Error 54");
                 }
             });

             RequestSingleton.getInstance(context).addToRequestQueue(request);

         }

    public void getTvShowList(String search, TvShowListResponse tvShowListResponse){

        ArrayList<TvShow> tvShow = new ArrayList<TvShow>();

        String url = QUERY_FOR_TVSHOW_LIST + search;

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>(){
            @Override
            public void onResponse(JSONArray response){
                //Toast.makeText(context, response.toString(), Toast.LENGTH_LONG).show();
                try {

                    for (int i = 0; i < response.length(); i++) {
                        TvShow firstTvShow = new TvShow();

                        JSONObject firstTvShowAPI = (JSONObject) response.get(i); //we get the first Tv Show object from the array

                        JSONObject showObject = firstTvShowAPI.getJSONObject("show"); //we get the show object from the tv show object we received
                        Show show = new Show();
                        show.setId(showObject.getInt("id"));
                        show.setUrl(showObject.getString("url"));
                        show.setName(showObject.getString("name"));
                        show.setType(showObject.getString("type"));
                        show.setLanguage(showObject.getString("language"));


                        firstTvShow.setScore(firstTvShowAPI.getDouble("score"));
                        firstTvShow.setShow(show);

                        tvShow.add(firstTvShow); //add tvShow to the list
                        //Toast.makeText(context, firstTvShow.toString(), Toast.LENGTH_LONG).show();
                    }

                    ArrayList<String> result  = new ArrayList<String>();
                    String name = "";
                    for (int i = 0; i < tvShow.size(); i++) {
                        name = tvShow.get(i).getShow().getName();
                        result.add(name);
                    }

                        tvShowListResponse.onResponse(result);

                    } catch(JSONException e){
                        e.printStackTrace();
                    }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error){
                Toast.makeText(context, "error", Toast.LENGTH_LONG).show();
            }
        });
        RequestSingleton.getInstance(context).addToRequestQueue(request);
    }


    public void getShowId(String name, final IDResponseListener idResponseListener) {
        Toast.makeText(context, "Name: " + name, Toast.LENGTH_LONG).show();

        String url = QUERY_FOR_TVSHOW_ID + name;
        Toast.makeText(context, url, Toast.LENGTH_LONG).show();

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {
                    id = response.getInt("id");
                    Toast.makeText(context, String.valueOf(id), Toast.LENGTH_LONG).show();

                } catch (JSONException e) {
                    e.printStackTrace();
                }
               idResponseListener.onResponse(id);
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, "Error for getShowIDResponse", Toast.LENGTH_LONG).show();
            }
        });

        RequestSingleton.getInstance(context).addToRequestQueue(request);
    }
}
