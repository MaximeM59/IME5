package com.example.ime5_tp2;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class LoginActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
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
				// Ouverture de l'activit� Pr�f�rences
				Intent afficherPreferences = // On veut changer d'activit�
					new Intent(this,PreferenceActivity.class);
					startActivity(afficherPreferences);
				break;
			case R.id.action_compte:
				Toast t = Toast.makeText(this,"non implement�",Toast.LENGTH_SHORT); // toat = message temporaire
				t.show();
				break;
		}
		return true;
	}

}
