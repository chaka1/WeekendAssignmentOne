package com.example.chaka.weekendassignmentone.frag;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.chaka.weekendassignmentone.R;
import com.example.chaka.weekendassignmentone.obj.Employee;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link DetailsFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link DetailsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DetailsFragment extends Fragment {

    View mView;

    SwapFragmentActivityInterface swapFragmentActivityInterface;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    Employee employee;

    Boolean isManager;

    ImageView mPhoto;

    TextView mNameView;
    TextView mNationalInsurance;
    TextView mPassport;
    TextView mGender;
    TextView mCountry;
    TextView mDateOfBirth;
    TextView mIsManager;


    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param employee Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DetailsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DetailsFragment newInstance(Employee employee, Boolean param2) {
        DetailsFragment fragment = new DetailsFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_PARAM1, employee);
        args.putBoolean(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public DetailsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            employee = getArguments().getParcelable(ARG_PARAM1);
            isManager= getArguments().getBoolean(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        swapFragmentActivityInterface = (SwapFragmentActivityInterface) getActivity();

        mView = inflater.inflate(R.layout.fragment_details, container, false);

        mPhoto = (ImageView)mView.findViewById(R.id.imageView3);
        mPhoto.setImageBitmap(employee.get_image());

        mNameView = (TextView)mView.findViewById(R.id.textView2);
        mNameView.setText(employee.get_name());
        mNationalInsurance = (TextView)mView.findViewById(R.id.textView3);
        mNationalInsurance.setText(employee.get_ni());
        mPassport = (TextView)mView.findViewById(R.id.textView4);
        mPassport.setText(employee.get_passport());
        mGender = (TextView)mView.findViewById(R.id.textView5);
        mGender.setText(employee.get_gender());
        mCountry = (TextView)mView.findViewById(R.id.textView6);
        mCountry.setText(employee.get_country());
        mIsManager = (TextView)mView.findViewById(R.id.textView7);
        if(employee.get_manager()){
            mIsManager.setText("Manager");
        }else{
            mIsManager.setText("User");
        }



        // Inflate the layout for this fragment
        return mView;
    }





}
