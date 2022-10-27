package com.example.himanshjosh.network;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import retrofit2.Response;

public abstract  class SafeApiRequest {

        public <T> Object apiRequest(Response<T>  call) throws Exception {

            Response<T> response= (Response) call.body();

            if (call.isSuccessful())
            {
                return response.body();

            } else

            {
                String  error = response.errorBody().toString();
                StringBuilder  massage = new StringBuilder() ;
                try{
                    massage.append(new JSONObject(error).getString("message"));

                }catch (JSONException jsonException)
                {
                    massage.append("\n"+jsonException);
                }
                massage.append("Error code :" + (response.code())+"\n"+response.body());
                throw new Exception(massage.toString());
            }




        }


    }

