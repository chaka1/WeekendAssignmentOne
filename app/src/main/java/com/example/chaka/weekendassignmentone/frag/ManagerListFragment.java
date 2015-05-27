package com.example.chaka.weekendassignmentone.frag;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.app.Fragment;
import android.os.Parcelable;
import android.preference.PreferenceManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.chaka.weekendassignmentone.R;
import com.example.chaka.weekendassignmentone.db.DatabaseHelper;
import com.example.chaka.weekendassignmentone.listeners.RecyclerItemClickListener;
import com.example.chaka.weekendassignmentone.obj.Employee;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ManagerListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ManagerListFragment extends Fragment {

    SwapFragmentActivityInterface swapFragmentActivityInterface;

    View mView;

    RecyclerView mEmployeeList;

    List<Employee> employeeData;

    DatabaseHelper dbh;

    LinearLayoutManager llm;

    EmployeeAdapter employeeAdapter;

    Employee employee;

    Boolean isManager;


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
     * @return A new instance of fragment ManagerListFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ManagerListFragment newInstance(Employee employee, Boolean param2) {
        ManagerListFragment fragment = new ManagerListFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_PARAM1,employee);
        args.putBoolean(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public ManagerListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            employee = getArguments().getParcelable(ARG_PARAM1);
            isManager= getArguments().getBoolean(ARG_PARAM2);
        }else{
            employee =null;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_manager_list, container, false);

        swapFragmentActivityInterface = (SwapFragmentActivityInterface) getActivity();
        dbh = new DatabaseHelper(getActivity());
        employeeData = dbh.getAllEmployees();

        mEmployeeList = (RecyclerView)mView.findViewById(R.id.rvManagerList);


        mEmployeeList.setHasFixedSize(false);

        llm = new LinearLayoutManager(getActivity());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        mEmployeeList.setLayoutManager(llm);

        employeeAdapter = new EmployeeAdapter(employeeData);


        mEmployeeList.setAdapter(employeeAdapter);

        mEmployeeList.addOnItemTouchListener(
                new RecyclerItemClickListener(getActivity(), new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        swapFragmentActivityInterface.swapFragment(R.id.details_fragment,
                                employee.get_manager(), employeeData.get(position));
                    }
                })
        );

        return mView;
    }




    public class EmployeeAdapter extends RecyclerView.Adapter<EmployeeAdapter.ButtonViewHolder> {

        private List<Employee> pinList;

        public EmployeeAdapter(List<Employee> pinList) {
            this.pinList = pinList;
        }

        @Override
        public int getItemCount() {
            return pinList.size();
        }

        @Override
        public void onBindViewHolder(final ButtonViewHolder pinViewHolder,int i) {
            final Employee cd = pinList.get(i);
            SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(getActivity());

            //Log.d(PREFS_NAME, "onBindView: " + i);

            //pinViewHolder.vCustomButton.setText(cd.get_name());
            if(cd.get_image()!=null){
                pinViewHolder.vImageView.setImageBitmap(cd.get_image());
            }
            if(cd.get_name()!=null){

                pinViewHolder.vNameText.setText(cd.get_name());
            }



        }

        @Override
        public ButtonViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {

            View itemView;

            itemView = LayoutInflater.
                    from(viewGroup.getContext()).
                    inflate(R.layout.card_employee_details, viewGroup, false);



            return new ButtonViewHolder(itemView);
        }

        public class ButtonViewHolder extends RecyclerView.ViewHolder {

            protected TextView vNameText;
            protected ImageView vImageView;

            public ButtonViewHolder(View v) {
                super(v);
                vNameText = (TextView)v.findViewById(R.id.textView);
                vImageView = (ImageView)v.findViewById(R.id.imageView);



            }
        }

    }


}
