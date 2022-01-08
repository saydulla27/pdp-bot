package uz.pdp.pdpbot.valyutaModel;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.time.LocalDate;

public class CurrencyUtil {
    public static Currency getOneOfInfo(String currency) throws IOException {
        Gson gson = new Gson();
        LocalDate localDate = LocalDate.now();
        URL url = new URL("https://cbu.uz/oz/arkhiv-kursov-valyut/json/" + currency + "/" + localDate + "/");

        URLConnection connection = url.openConnection();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));

        Currency[] currencies = gson.fromJson(bufferedReader, Currency[].class);

        //OneCurrencyItem
        return currencies[0];
    }
}
