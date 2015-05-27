package com.example.chaka.weekendassignmentone.frag;


import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.app.Fragment;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.Spinner;


import com.example.chaka.weekendassignmentone.R;
import com.example.chaka.weekendassignmentone.db.DatabaseHelper;
import com.example.chaka.weekendassignmentone.obj.Employee;
import com.iangclifton.android.floatlabel.FloatLabel;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.util.Calendar;

import de.keyboardsurfer.android.widget.crouton.Crouton;
import de.keyboardsurfer.android.widget.crouton.Style;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SignUpFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SignUpFragment extends Fragment implements DatePickerDialog.OnDateSetListener{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    static final int REQUEST_IMAGE_CAPTURE = 1;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    DatabaseHelper dbh;

    SwapFragmentActivityInterface swapFragmentActivityInterface;

    View mView;

    Button mDateOfBirthButton;
    Button mCreateButton;
    Button mCancelButton;

    FloatLabel mName;
    FloatLabel mNi;
    FloatLabel mPassport;
    FloatLabel mPassword;
    FloatLabel mConfirmPassword;
    RadioGroup mGender;
    Spinner mCountry;

    ImageView mPhoto;

    Bitmap mImage;


    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SignUpFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SignUpFragment newInstance(String param1, String param2) {
        SignUpFragment fragment = new SignUpFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public SignUpFragment() {
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


        dbh = new DatabaseHelper(getActivity());
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.fragment_sign_up, container, false);

        mName = (FloatLabel)mView.findViewById(R.id.flName);

        mNi = (FloatLabel)mView.findViewById(R.id.flNi);

        mPassport = (FloatLabel)mView.findViewById(R.id.flPassport);

        mPassword = (FloatLabel)mView.findViewById(R.id.flPassword);

        mConfirmPassword = (FloatLabel)mView.findViewById(R.id.flConfirmPassword);

        mGender = (RadioGroup)mView.findViewById(R.id.radioGender);

        mPhoto = (ImageView)mView.findViewById(R.id.imageView2);
        mPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {
                    getActivity().startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
                }
            }
        });

        mCountry = (Spinner)mView.findViewById(R.id.spinner2);

        mDateOfBirthButton = (Button)mView.findViewById(R.id.button3);
        mDateOfBirthButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar now = Calendar.getInstance();
                DatePickerDialog dpd = DatePickerDialog.newInstance(
                        SignUpFragment.this,
                        now.get(Calendar.YEAR),
                        now.get(Calendar.MONTH),
                        now.get(Calendar.DAY_OF_MONTH)
                );
                dpd.show(getActivity().getFragmentManager(), "Datepickerdialog");
            }
        });

        mCreateButton = (Button)mView.findViewById(R.id.button4);
        mCreateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createUser();
            }
        });

        mCancelButton = (Button)mView.findViewById(R.id.button5);
        mCancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                swapFragmentActivityInterface.back();
            }
        });

        mImage = null;

        if(savedInstanceState!=null) {
            mName.getEditText().setText(savedInstanceState.getString("name"));
            mNi.getEditText().setText(savedInstanceState.getString("ni"));
            mPassword.getEditText().setText(savedInstanceState.getString("pass"));
            mImage = savedInstanceState.getParcelable("bitmap");
            if (mImage != null) {
                mPhoto.setImageBitmap(mImage);
            }
            mGender.check(savedInstanceState.getInt("gender"));
        }
        return mView;
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        String date = "Date of birth: "+dayOfMonth+"/"+(monthOfYear+1)+"/"+year;
        //dateTextView.setText(date);
        mDateOfBirthButton.setText(date);
    }

    private  void createUser(){
        if(mPassword.getEditText().getText().toString().equals(mConfirmPassword.getEditText().getText().toString())) {

            String gender;
            String country;
            if(mImage == null){
                mImage = BitmapFactory.decodeResource(getActivity().getResources(),
                        R.drawable.ic_action_account_box);
            }
            if(mGender.getCheckedRadioButtonId()==R.id.radioButton){
                gender = "male";
            }else{
                gender = "female";
            }
            country = mCountry.getSelectedItem().toString();
            Employee emp = new Employee(mName.getEditText().getText().toString(),
            mNi.getEditText().getText().toString(),mPassport.getEditText().getText().toString(),
                    gender, country,
                    mPassword.getEditText().getText().toString(),mImage);
            dbh.addEmployee(emp);
            Crouton.makeText(getActivity(), "User create", Style.INFO).show();
            swapFragmentActivityInterface.back();
        }

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putString("name",mName.getEditText().getText().toString());
        outState.putString("ni", mNi.getEditText().getText().toString());
        outState.putString("pass",mPassword.getEditText().getText().toString());
        outState.putInt("gender", mGender.getCheckedRadioButtonId());
        outState.putParcelable("bitmap",mImage);
        super.onSaveInstanceState(outState);
    }





    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == Activity.RESULT_OK) {
            Bundle extras = data.getExtras();
            mImage = (Bitmap) extras.get("data");
            mPhoto.setImageBitmap(mImage);
        }
    }






}
