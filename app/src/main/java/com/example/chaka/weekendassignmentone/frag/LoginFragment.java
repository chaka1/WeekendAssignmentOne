package com.example.chaka.weekendassignmentone.frag;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.app.Fragment;
import android.preference.PreferenceManager;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Spinner;


import com.example.chaka.weekendassignmentone.R;
import com.example.chaka.weekendassignmentone.db.DatabaseHelper;
import com.example.chaka.weekendassignmentone.obj.Employee;
import com.iangclifton.android.floatlabel.FloatLabel;

import de.keyboardsurfer.android.widget.crouton.Crouton;
import de.keyboardsurfer.android.widget.crouton.Style;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link LoginFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LoginFragment extends Fragment {

    SwapFragmentActivityInterface swapFragmentActivityInterface;

    static final String DEBUG_TAG = "First week assignemnt";

    static final String REMEMBER_ME = "Remember";
    static final String LOGIN = "Login";
    static final String PASSWORD = "Password";
    static final String SHOW_PASSWORD = "ShowPassword";
    static final String LOGIN_TYPE= "LoginType";

    View mView;

    FloatLabel mLogin;
    FloatLabel mPassword;
    Spinner mLoginType;
    CheckBox mShowPassword;
    CheckBox mRememberMe;
    Button mLoginButton;
    Button mSignUpButton;

    DatabaseHelper dbh;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment LoginFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static LoginFragment newInstance(String param1, String param2) {
        LoginFragment fragment = new LoginFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public LoginFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
swapFragmentActivityInterface = (SwapFragmentActivityInterface) getActivity();
    mView = inflater.inflate(R.layout.fragment_login, container, false);
        setHasOptionsMenu(true);
        dbh = new DatabaseHelper(getActivity());

        mLogin = (FloatLabel)mView.findViewById(R.id.flLogin);
        mPassword = (FloatLabel)mView.findViewById(R.id.flPassword);
        mPassword.getEditText().setInputType(InputType.TYPE_CLASS_TEXT |
                InputType.TYPE_TEXT_VARIATION_PASSWORD);
        mLoginType = (Spinner)mView.findViewById(R.id.spinner);
        mShowPassword = (CheckBox)mView.findViewById(R.id.showPasswordCheckBox);
        mShowPassword.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    mPassword.getEditText().setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);

                    mPassword.getEditText().setSelection(mPassword.getEditText().getText().length());
                }else{
                    mPassword.getEditText().setInputType(InputType.TYPE_CLASS_TEXT |
                            InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    mPassword.getEditText().setSelection(mPassword.getEditText().getText().length());
                }
            }
        });
        mRememberMe = (CheckBox)mView.findViewById(R.id.checkBox2);

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        if(sharedPreferences.getBoolean(REMEMBER_ME,false)){
            mLogin.getEditText().setText(sharedPreferences.getString(LOGIN,""));
            mPassword.getEditText().setText(sharedPreferences.getString(PASSWORD,""));
            mRememberMe.setChecked(true);
            mShowPassword.setChecked(sharedPreferences.getBoolean(SHOW_PASSWORD, false));
            mLoginType.setSelection(sharedPreferences.getInt(LOGIN_TYPE,0));

        }

        mLoginButton = (Button)mView.findViewById(R.id.button);
        mLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("Debug button2","SIgnup on click");
                saveForm();
                tryLogin();
            }
        });


        mSignUpButton = (Button)mView.findViewById(R.id.button2);
        mSignUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("Debug button2","SIgnup on click");
                swapFragmentActivityInterface.swapFragment(R.id.sign_up_fragment,false,null);

            }
        });
        // Inflate the layout for this fragment
        return mView;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_login_fragment, menu);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_create_manager:
                Bitmap mImage = BitmapFactory.decodeResource(getActivity().getResources(),
                        R.drawable.ic_action_account_box);
                Employee emp = new Employee("manager","manNI","manPassport","male","UK","manPassword",true,mImage);
                emp.set_manager(true);
                dbh.addEmployee(emp);
                return true;
        }


        return super.onOptionsItemSelected(item);
    }

    private void saveForm() {

        if(mRememberMe.isChecked()){
            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString(LOGIN,mLogin.getEditText().getText().toString());
            editor.putString(PASSWORD,mPassword.getEditText().getText().toString());
            editor.putBoolean(REMEMBER_ME, mRememberMe.isChecked());
            editor.putBoolean(SHOW_PASSWORD, mShowPassword.isChecked());

        }
    }

    private void tryLogin() {

        Employee employee = dbh.tryLogin(mLogin.getEditText().getText().toString(),
                mPassword.getEditText().getText().toString());

        if(employee != null){
            if(employee.get_manager()){
                swapFragmentActivityInterface.swapFragment(R.id.manager_list_fragment,employee.get_manager(),employee);
            }else{
                //TODO show details
                swapFragmentActivityInterface.swapFragment(R.id.details_fragment,employee.get_manager(),employee);
            }

        }else{
            Crouton.makeText(getActivity(), "Failed to login", Style.ALERT).show();
        }

    }


}
