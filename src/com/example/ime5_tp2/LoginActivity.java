package com.example.ime5_tp2;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends Activity {
	EditText login;
	EditText password;
	CheckBox RememberMe;
	Button loginButton;
	
	GlobalState gs;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
	}
	
	@Override
	protected void onStart() {
		super.onStart();
		
		gs = (GlobalState) getApplication();
		//gs = new GlobalState();
		gs.alerter("Creation client");
		gs.createClient();
		
		RememberMe = (CheckBox) findViewById(R.id.login_cbRemember);
		login = (EditText) findViewById(R.id.login_edtLogin);
		password = (EditText) findViewById(R.id.login_edtPasse);
		if(RememberMe.isChecked()){
			SharedPreferences preferences_recuperees = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
			login.setText(preferences_recuperees.getString("login",""));
			password.setText(preferences_recuperees.getString("passe",""));
		}
	}

	@Override
	protected void onResume() { //Appelé à chaque fois que l'écran se réaffiche
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
