package hypass.hycon.hypass.Connections;

import android.content.ContentValues;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

public class GetConnection extends AsyncTask {
    // 1. 객체 생성 (url)
// 2. addParam 함수로 매개변수 지정
// 3. Task 실행
// 4. 결과는 resultObj에 저장됨
    String strUrl;

    String result;
    public JSONObject resultObj;

    public GetConnection(String str) {
        super();

        strUrl = str;
    }

    @Override
    protected Object doInBackground(Object[] objects) {
        String REQUEST_METHOD = "GET";
        int READ_TIMEOUT = 15000;
        int CONNECTION_TIMEOUT = 15000;
        String inputLine;
        try {
            //Create a URL object holding our url
            URL myUrl = new URL(strUrl);
            //Create a connection
            HttpURLConnection connection = (HttpURLConnection)
                    myUrl.openConnection();
            //Set methods and timeouts
            connection.setRequestMethod(REQUEST_METHOD);
            connection.setReadTimeout(READ_TIMEOUT);
            connection.setConnectTimeout(CONNECTION_TIMEOUT);

            //Connect to our url
            connection.connect();
            //Create a new InputStreamReader
            InputStreamReader streamReader = new
                    InputStreamReader(connection.getInputStream());
            //Create a new buffered reader and String Builder
            BufferedReader reader = new BufferedReader(streamReader);
            StringBuilder stringBuilder = new StringBuilder();
            //Check if the line we are reading is not null
            while ((inputLine = reader.readLine()) != null) {
                stringBuilder.append(inputLine);
            }
            //Close our InputStream and Buffered reader
            reader.close();
            streamReader.close();
            //Set our result equal to our stringBuilder
            result = stringBuilder.toString();
            resultObj = new JSONObject(result);
        } catch (Exception exc) {
            exc.printStackTrace();
        }
    return result;
    }

//
//    URL url;
//    HttpURLConnection urlConn = null;
//    StringBuffer param = null;
//    public JSONObject resultObj = new JSONObject();
//
//    public void addParam(String key, String value) {
//        if (param == null) {
//            param = new StringBuffer("?" + key + "=" + value);
//        } else {
//            param.append("&" + key + "=" + value);
//        }
//    }
//
//    public GetConnection(String url) {
//        super();
//        String result;
//        try {
//            this.url = new URL(url);
//            urlConn = (HttpURLConnection) this.url.openConnection();
//            InputStream inputStream = urlConn.getInputStream();
//            InputStreamReader isw = new InputStreamReader(inputStream);
//            int responseCode = urlConn.getResponseCode();
//            if (responseCode == HttpURLConnection.HTTP_OK){
//                result = readStream(urlConn.getInputStream());
//                resultObj =  new JSONObject(result);
//            }
//        }catch (Exception exc){
//            exc.printStackTrace();
//        }
//
//    }
//    private String readStream(InputStream in) {
//        BufferedReader reader = null;
//        StringBuffer response = new StringBuffer();
//        try {
//            reader = new BufferedReader(new InputStreamReader(in));
//            String line = "";
//            while ((line = reader.readLine()) != null) {
//                response.append(line);
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        } finally {
//            if (reader != null) {
//                try {
//                    reader.close();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//        return response.toString();
//    }
//
//    @Override
//    protected Object doInBackground(Object[] objects) {
//
//
//        return null;
//    }
}
