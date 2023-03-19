package com.example.test;

import com.google.gson.Gson;
import org.apache.commons.math3.stat.regression.SimpleRegression;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.*;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;

import okhttp3.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DataController {


    @Autowired
    DataRepository dataRepository;


    @GetMapping("add")
    public String xyz() throws IOException {
        OkHttpClient client = new OkHttpClient().newBuilder().build();

        LocalDate localDate = LocalDate.parse("2023-03-14");

        int a = 30;

        while(a>0)
        {
            Request request = new Request.Builder()
                    .url("https://api.apilayer.com/exchangerates_data/"+localDate.toString()+"?symbols=EUR%2C%20JPY%2C%20GBP%2C%20CAD%2C%20AUD%2C%20CNY%2C%20CHF%2C%20KRW%2C%20MXN%2C%20INR%2C%20RUB%2C%20TRY%2C%20BRL%2C%20ZAR%2C%20SAR%2C%20IDR%2C%20PLN%2C%20ARS&base=USD")
                    .addHeader("apikey", "Mksw9ks2iIzWsAS63TbDbjPOxwf8nHUI")
                    .method("GET", null)
                    .build();

            Response response = client.newCall(request).execute();
            String json = (response.body().string());

            Gson gson = new Gson();
            HashMap<String, Double> ratesMap = new HashMap<>();

            RatesResponse response1 = gson.fromJson(json, RatesResponse.class);
            for (String currencyCode : response1.rates.keySet()) {
                double rate = response1.rates.get(currencyCode);
                ratesMap.put(currencyCode, rate); // adding in hashmap
            }

            for(String s: ratesMap.keySet()){
                Data data = new Data();
                data.setCurrency(s);
                data.setPrice(ratesMap.get(s));
                data.setLocalDate(localDate);
                dataRepository.save(data);
            }

            a--;
            localDate = localDate.minusDays(1);

        }





    return "Done";
    }
    static class RatesResponse {
        HashMap<String, Double> rates;
    }


    @GetMapping("predict")
    public double predict(@RequestParam LocalDate date, @RequestParam String curr){
        List<String> localDateList = dataRepository.getDates();
        List<Double> rateList = dataRepository.getValues(curr);
        Double dates [] = new Double[localDateList.size()];

        for(int i=0;i<localDateList.size();i++)
        {
            dates[i] = (double) LocalDate.parse(localDateList.get(i)).toEpochDay();
        }

        Double rates [] = new Double[rateList.size()]; rateList.toArray(rates);
        SimpleRegression regression = new SimpleRegression();
        for (int i = 0; i < localDateList.size(); i++) {
            regression.addData(dates[i] , rates[i]);
        }


         return regression.predict(date.toEpochDay());

    }


















}
