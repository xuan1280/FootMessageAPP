package com.joanna.footmassage.modles.repositories;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.joanna.footmassage.Secret;
import com.joanna.footmassage.modles.entities.DiagnosisResult;
import com.joanna.footmassage.modles.entities.PressureData;
import com.joanna.footmassage.modles.models.DiagnosisResultModel;
import com.joanna.footmassage.modles.models.PressureDataModel;
import com.joanna.footmassage.modles.models.ResponseIntModel;
import com.joanna.footmassage.modles.models.ResponseModel;
import com.joanna.footmassage.modles.models.StartDiagnosisModel;
import com.joanna.footmassage.utils.ResponseUtils;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public class DiagnosisRetrofitRepository implements DiagnosisRepository {
    private final static String TAG = "DiagnosisRetrofitRepository";
    private DiagnosisAPI diagnosisAPI;

    public DiagnosisRetrofitRepository() {
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Secret.IP)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        diagnosisAPI = retrofit.create(DiagnosisRetrofitRepository.DiagnosisAPI.class);
    }

    @Override
    public ResponseIntModel startDiagnosis(StartDiagnosisModel startDiagnosisModel) throws IOException {
        Response<ResponseIntModel> response = diagnosisAPI.startDiagnosis(startDiagnosisModel.getId(), startDiagnosisModel.getAccount(), startDiagnosisModel.getToken()).execute();
        ResponseIntModel responseModel = response.body();
        assert responseModel != null;
        return responseModel;
    }

    @Override
    public ResponseModel<Object> sendPressureData(PressureDataModel pressureDataModel) throws IOException {
        Response<ResponseModel<Object>> response = diagnosisAPI.sendPressureData(pressureDataModel.getAccount(), pressureDataModel.getToken(),
                pressureDataModel.getRId(), pressureDataModel.getPressureData(), pressureDataModel.getPainful(), pressureDataModel.getTime()).execute();
        ResponseModel<Object> responseModel = ResponseUtils.getBody(response);
        assert responseModel != null;
        return responseModel;
    }

    @Override
    public ResponseModel<DiagnosisResult> getDiagnosisResult(DiagnosisResultModel diagnosisResultModel) throws IOException {
        Response<ResponseModel<DiagnosisResult>> response = diagnosisAPI.getDiagnosisResult(diagnosisResultModel.getAccount(), diagnosisResultModel.getToken(), diagnosisResultModel.getRId()).execute();
        ResponseModel<DiagnosisResult> responseModel = ResponseUtils.getBody(response);
        assert responseModel != null;
        return responseModel;
    }

    public interface DiagnosisAPI {

        @Headers("Content-Type:application/x-www-form-urlencoded")
        @FormUrlEncoded
        @POST("getRID.php")
        Call<ResponseIntModel> startDiagnosis(@Field("UID") int id,
                                                    @Field("account") String account,
                                                    @Field("skey") String token);

        @Headers("Content-Type:application/x-www-form-urlencoded")
        @FormUrlEncoded
        @POST("detection.php")
        Call<ResponseModel<Object>> sendPressureData(@Field("account") String account,
                                                @Field("skey") String token,
                                                @Field("RID") int rId,
                                                @Field("pressureData") int[] pressureData,
                                                @Field("painful") int painful,
                                                @Field("time") String time);

        @Headers("Content-Type:application/x-www-form-urlencoded")
        @FormUrlEncoded
        @POST("getResult.php")
        Call<ResponseModel<DiagnosisResult>> getDiagnosisResult(@Field("account") String account,
                                                                @Field("skey") String token,
                                                                @Field("RID") int rId);
    }
}
