import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.net.*;
import java.util.List;
import java.io.*;

public class processingWeather {
    public static String getWeather(String message) throws IOException {
        String apiId = "&appid=286d37efd2f53154bbe32653bfac5a00";
        String units = "&units=metric";
        String lang = "&lang=ru";
        String country = message;
        String weather = "https://api.openweathermap.org/data/2.5/weather?q=" + country + "" + units + "" + lang + "" + apiId;
        HttpURLConnection connection = null;
        Gson GSON = new GsonBuilder().setPrettyPrinting().create();
        String result = "";
        String result1 = "";
        try {
            connection = (HttpURLConnection) new URL(weather).openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(250);
            connection.setReadTimeout(250);
            connection.connect();
            StringBuilder sb = new StringBuilder();
            if (HttpURLConnection.HTTP_OK == connection.getResponseCode()) {
                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String line;
                while ((line = in.readLine()) != null) {
                    sb.append(line);
                    sb.append("\n");
                }
                String resConnect = sb.toString();
                OpenWeather openweather = GSON.fromJson(resConnect, OpenWeather.class);
                result1 = resConnect;
                result ="Город:"+openweather.name + "\n" +
                        "Температура:"+openweather.main.temp + "\n" +
                        "Скорость Ветра: "+String.valueOf(openweather.wind.speed) + "\n" +
                        "Влажность: " + String.valueOf(openweather.main.humidity) + "\n";
                System.out.print(result1);
            } else {
                System.out.print("fail:" + connection.getResponseCode() + ", " + connection.getResponseMessage());
                result = "Город не найден";
            }
        } catch (Throwable cause) {
            cause.printStackTrace();
        } finally {
            if (connection != null)
                connection.disconnect();
        }
        return result;
    }
}

class OpenWeather{
    public Coord coord;
    public List<Weather> weather;
    public String base;
    public Main main;
    public double visibility;
    public Wind wind;
    public Clouds clouds;
    public double dt;
    public Sys sys;
    public double id;
    public String name;
    public double cod;
}
class Coord{
    public double lon;
    public double lat;
}
class Weather{
    public double id;
    public String main;
    public String description;
    public String icon;
}
class Main {
    public double temp;
    public double pressure;
    public double humidity;
    public double temp_min;
    public double temp_max;
}
class Wind {
    public double speed;
    public double deg;
}
class Clouds{
    public double all;
}
class Sys{
    public double type;
    public double id;
    //public double massage;
    public String Country;
    public double sunrise;
    public double sunset;
}
