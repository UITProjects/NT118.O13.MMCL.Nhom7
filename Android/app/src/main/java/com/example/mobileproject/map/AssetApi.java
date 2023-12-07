package com.example.mobileproject.map;

import android.util.Log;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.ZipInputStream;

public class AssetApi {
   URL url;
   HttpURLConnection con;
   void readWhile(InputStream stream, char[] specificChars) throws IOException {
      InputStreamReader reader = new InputStreamReader(stream, StandardCharsets.UTF_8);
      char[] buffer = new char[specificChars.length];
      while (reader.read(buffer) != -1) {
         if (Arrays.equals(buffer, specificChars)) {
            // the specific chars were found
            return;
         }
      }
   }


   public AssetApi(String url, String method,String token) throws IOException
   {
      this.url = new URL(url);
      this.con = (HttpURLConnection) this.url.openConnection();
      con.setRequestProperty("Content-Type","application/json");
      con.setRequestProperty("Authorization", "Bearer ".concat(token));
      con.setRequestMethod(method);

   }

   public Map<String, String> GeData() throws IOException {
      InputStream in;
      int status = con.getResponseCode();
      if (status == 200)
         in = con.getInputStream();
      else
         in = con.getErrorStream();


      int length = con.getContentLength();
      byte[] raw_data = new byte[length];
      int byte_read = 0;
      while (byte_read < length) {
         byte_read += in.read(raw_data, byte_read, length-byte_read);
      }
      in.close();
      InputStream response_stream = new ByteArrayInputStream(raw_data);

      BufferedReader reader = new BufferedReader(new InputStreamReader(response_stream));


      StringBuilder builder  = new StringBuilder();
      while (!builder.toString().contains("sunIrradiance")) {
         char temp = (char) reader.read();
         builder.append(temp);
         builder.toString();
      }

      String line = reader.readLine();
      int stop = 0 ;




      Map<String, String> list = new HashMap<>();

      return list;

   }
}
