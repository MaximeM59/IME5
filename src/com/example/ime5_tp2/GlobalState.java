package com.example.ime5_tp2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.app.Application;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.NetworkInfo.State;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.Toast;

public class GlobalState extends Application {

	private DefaultHttpClient client;  
	public boolean connecte = false;

	public void createClient() {
		this.client = new DefaultHttpClient();
	}
	
	// piste : rendre cette fonction statique ? 
	// PB : on ne peut utiliser this dans un contexte statique
   public void alerter(String s)
    {
        Toast t = Toast.makeText(this, s, Toast.LENGTH_LONG); 
        t.show();    	
    }

   public boolean verifReseau()
   {
	   // On v�rifie si le r�seau est disponible, 
	   // si oui on change le statut du bouton de connexion
        ConnectivityManager cnMngr = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE); 
        NetworkInfo netInfo = cnMngr.getActiveNetworkInfo();
        
       	String sType = "Aucun r�seau d�tect�";
        Boolean bStatut = false;
        if (netInfo != null)
        {
          
	        State netState = netInfo.getState();
	        
	        if (netState.compareTo(State.CONNECTED) == 0)
	        {
	        	bStatut = true; 
	        	int netType= netInfo.getType();   
	        	switch (netType)
	        	{
	        		case ConnectivityManager.TYPE_MOBILE : 	
					sType = "R�seau mobile d�tect�"; break;
	        		case ConnectivityManager.TYPE_WIFI :	
					sType = "R�seau wifi d�tect�"; break;
	        	}
	        	
	        }
        }
        
        alerter(sType);
        return bStatut;
        
   }

   
	
	public String requete(String qs)
    {   
		SharedPreferences preferences_recuperees = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
		String urlData = preferences_recuperees.getString("urlData", "http://localhost/");
		
    	if (qs != null)
    	{    		    		
    		HttpGet req = new HttpGet(urlData + "?" + qs);
    		HttpResponse reponse;
    		HttpEntity corpsReponse;
    		InputStream is; 
    		try {
    			RequestTask reqTask = (RequestTask) new RequestTask();
    			reqTask.execute(req);
    			
    			// On attends que la requ�te soit termin�e
    			while(!reqTask.requestDone) {}
    			
				reponse = reqTask.reponse;		// ex�cuter requ�te
				corpsReponse = reponse.getEntity();	// r�cup�re le r�sultat
				is = corpsReponse.getContent();
				String txtReponse = convertStreamToString(is);
				
				return txtReponse;				
			}
    		catch (Exception e) {
    			Log.e("Request error", e.getClass().getName());
    			e.printStackTrace();
    		}
    	}
    	
 
    	return "";
    	
    }
	
	 private String convertStreamToString(InputStream in) throws IOException {	
			try {
				BufferedReader reader = new BufferedReader(new InputStreamReader(in));
				StringBuilder sb = new StringBuilder();
				String line = null;
				while ((line = reader.readLine()) != null) {
					sb.append(line + "\n");
				}
				return sb.toString();
			}finally {
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

		}
	 
	 private class RequestTask extends AsyncTask<HttpGet, Void, String> {
		 public HttpResponse reponse = null;
		 public Boolean requestDone = false;
		 
		@Override
		protected String doInBackground(HttpGet... arg0) {
			try {
				Log.i("Request","start");
				reponse = client.execute(arg0[0]);		// ex�cuter requ�te
				Log.i("Request","end");
			}
    		catch (Exception e) {
    			Log.e("Request error", e.getClass().getName());
    			e.printStackTrace();
    		}
			requestDone = true;
			return "ok";
		}
	 }
}