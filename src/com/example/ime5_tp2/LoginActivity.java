package com.example.ime5_tp2;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends Activity {
	EditText login;
	EditText password;
	CheckBox rememberMe;
	Button loginButton;
	
	GlobalState gs;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		loginButton = (Button) findViewById(R.id.login_btnOK); // ajout d'un listener = ajout d'une action lors du clic sur le bouton
		loginButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				rememberMe = (CheckBox) findViewById(R.id.login_cbRemember);
				login = (EditText) findViewById(R.id.login_edtLogin);
				password = (EditText) findViewById(R.id.login_edtPasse);
				
				// Sauvegarde des préférences
				SharedPreferences preferences_recuperees = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
				if(rememberMe.isChecked()){
					SharedPreferences.Editor prefEditor = preferences_recuperees.edit();
					
					prefEditor.putString("login", login.getText().toString());
					prefEditor.putString("passe", password.getText().toString());
					prefEditor.putBoolean("remember", rememberMe.isChecked());
					
					prefEditor.apply();
				}
				
				// Requête
				String loginText = login.getText().toString();
				String loginPass = password.getText().toString();
				
				if(!loginText.equals(""))
				{
					String requestResult = gs.requete("login=" + loginText + "&passe=" + loginPass);
					Toast t = Toast.makeText(getApplicationContext(),"Request: " + requestResult,Toast.LENGTH_SHORT); // toat = message temporaire
					t.show();
				}
			}
		});
	}
	
	@Override
	protected void onStart() {
		super.onStart();
		
		gs = (GlobalState) getApplication();
		gs.alerter("Creation client");
		gs.createClient();
		
		rememberMe = (CheckBox) findViewById(R.id.login_cbRemember);
		login = (EditText) findViewById(R.id.login_edtLogin);
		password = (EditText) findViewById(R.id.login_edtPasse);
		
		SharedPreferences preferences_recuperees = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
		Boolean autoLoad = preferences_recuperees.getBoolean("autoload", false);
		
		if(autoLoad){
			login.setText(preferences_recuperees.getString("login",""));
			password.setText(preferences_recuperees.getString("passe",""));
			rememberMe.setChecked(preferences_recuperees.getBoolean("remember", false));
		}
	}

	@Override
	protected void onResume() { //Appelé à chaque fois que l'écran se réaffiche
		super.onResume();
		//Tester la dispo du réseau et activer le bouton si il est dispo.
		loginButton = (Button) findViewById(R.id.login_btnOK);
		loginButton.setEnabled(gs.verifReseau());
		
		
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.menu_principal, menu);
		return true;
	}
	
	@Override // Ecrire par dessus
	public boolean onOptionsItemSelected(MenuItem item){
		switch(item.getItemId())
		{
			case R.id.action_settings:
				// Ouverture de l'activité Préférences
				Intent afficherPreferences = // On veut changer d'activité
					new Intent(this,PreferenceActivity.class);
					startActivity(afficherPreferences);
				break;
			case R.id.action_compte:
				Toast t = Toast.makeText(this,"non implementé",Toast.LENGTH_SHORT); // toat = message temporaire
				t.show();
				break;
		}
		return true;
	}

}
