package com.example.chaka.weekendassignmentone;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.SharedPreferences;
import android.database.CrossProcessCursorWrapper;
import android.os.Parcelable;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Spinner;

import com.example.chaka.weekendassignmentone.db.DatabaseHelper;
import com.example.chaka.weekendassignmentone.frag.DetailsFragment;
import com.example.chaka.weekendassignmentone.frag.LoginFragment;
import com.example.chaka.weekendassignmentone.frag.ManagerListFragment;
import com.example.chaka.weekendassignmentone.frag.SignUpFragment;
import com.example.chaka.weekendassignmentone.frag.SwapFragmentActivityInterface;
import com.example.chaka.weekendassignmentone.obj.Employee;
import com.iangclifton.android.floatlabel.FloatLabel;

import de.keyboardsurfer.android.widget.crouton.Crouton;
import de.keyboardsurfer.android.widget.crouton.Style;


public class MainActivity extends AppCompatActivity implements SwapFragmentActivityInterface {





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        LoginFragment loginFragment = new LoginFragment();
        ft.add(R.id.fragmentContainer,loginFragment);

        ft.commit();

    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void swapFragment(int fragment, boolean manager, Employee parceable){
        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        Log.d("logdebug","swap fragment");
        switch (fragment){
            case R.id.sign_up_fragment:
                SignUpFragment signUpFragment = new SignUpFragment();
                ft.replace(R.id.fragmentContainer,signUpFragment);
                ft.addToBackStack(null);
                ft.commit();
                break;
            case R.id.login_fragment:
                LoginFragment loginFragment = new LoginFragment();
                ft.replace(R.id.fragmentContainer,loginFragment);
                ft.addToBackStack(null);
                ft.commit();
                break;
            case R.id.manager_list_fragment:
                ManagerListFragment managerListFragment = ManagerListFragment.newInstance(parceable, manager);
                ft.replace(R.id.fragmentContainer,managerListFragment);
                ft.addToBackStack(null);
                ft.commit();
                break;
            case R.id.details_fragment:
                DetailsFragment detailsFragment = DetailsFragment.newInstance(parceable, manager);
                ft.replace(R.id.fragmentContainer,detailsFragment);
                ft.addToBackStack(null);
                ft.commit();

                break;
        }



    }

    public void back(){
        FragmentManager fm = getFragmentManager();
        fm.popBackStack();


    }


}
