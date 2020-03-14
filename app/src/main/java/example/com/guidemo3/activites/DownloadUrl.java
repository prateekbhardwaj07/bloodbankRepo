package example.com.guidemo3.activites;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by prateekb on 2/22/2018.
 */
public class DownloadUrl {
    public String readUrl(String strurl) throws IOException{
        String data="";
        InputStream inputStream = null;
        HttpURLConnection urlConnection = null;

        try
        {
            URL url = new URL(strurl);
            //Creating an http connection to communicate with url
            urlConnection = (HttpURLConnection)url.openConnection();
            urlConnection.connect();

            //Reading inputstream data from urlConnection
            inputStream=urlConnection.getInputStream();

            BufferedReader reader=new BufferedReader(new InputStreamReader(inputStream));

            StringBuffer stringBuffer = new StringBuffer();
            String line="";
            while((line=reader.readLine())!=null)
            {
                stringBuffer.append(line);
            }
            data=stringBuffer.toString();
            Log.d("DownloadUrl",data);
            reader.close();

        }
        catch (IOException exception)
        {
            Log.d("Error",exception.toString());
        }
        finally {
            inputStream.close();
            urlConnection.disconnect();
        }
        return data;
    }

}
