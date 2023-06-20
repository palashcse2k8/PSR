package com.example.psr.psrupload.views;

import android.app.Application;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.psr.R;
import com.example.psr.databinding.FragmentPsrUploadBinding;
import com.example.psr.psrupload.viewmodels.PSRViewModel;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PsrUploadFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PsrUploadFragment extends Fragment {

    private Context context;
    private Application application;
    private PSRViewModel viewModel;
    FragmentPsrUploadBinding binding;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String previousTaxSession ;
    private String runningTaxSession;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public PsrUploadFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PsrUploadFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PsrUploadFragment newInstance(String param1, String param2) {
        PsrUploadFragment fragment = new PsrUploadFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
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
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        this.context = getActivity();
        binding = FragmentPsrUploadBinding.inflate(inflater, container, false);
        viewModel = new PSRViewModel(application, context);
        binding.setPsrViewModel(viewModel);
        return binding.getRoot();
    }

    void switchFragment(Fragment fragment) {
        getActivity().getSupportFragmentManager().beginTransaction().setReorderingAllowed(true).replace(R.id.fragment_container_view, fragment, fragment.getClass().getSimpleName()).addToBackStack(null).commit();
    };

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.btnUpload.setOnClickListener(v -> {
            switchFragment(new PsrImageScannerFragment());
        });

        binding.labelName.setText(viewModel.accountName.toString());
        binding.labelAcNumber.setText(viewModel.accountNumber.toString());
        binding.labelTin.setText(viewModel.tinNumber.toString());

        binding.btnUpload.setVisibility(View.GONE);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>( getContext(), R.layout.spinner_item, getAssessmentYear());

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.spinner1.setAdapter(adapter);
        binding.spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position != 0) {
                    String item = parent.getItemAtPosition(position).toString();
                    viewModel.setSelectedTaxAssessmentYear(item);
                }
                updateStatus(position);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    public void updateStatus (int position) {

        if (position == 0) {
            binding.btnUpload.setVisibility(View.GONE);
            binding.labelTaxAssessmentStatus.setText("");
        } else {

            if(position == 1) {
                Log.d("","seleted 1");
                setText(getStatus(previousTaxSession));
            } else if (position == 2) {
                setText(getStatus(runningTaxSession));
            } else {
                Log.d("","Not in the range");
            }
        }
    }

    public String getStatus (String selectedYear) {
        if(selectedYear.equalsIgnoreCase(previousTaxSession)){
            return "Updated";
        } else if (selectedYear.equalsIgnoreCase(runningTaxSession)) {
            return "Not Submitted";
        } else {
            Log.d("", "Wrong year selected!");
        }

        return null;
    }

    public void setText (String Status) {
        if(Status.equalsIgnoreCase("updated") ) {
            binding.labelTaxAssessmentStatus.setText(Status);
            binding.labelTaxAssessmentStatus.setTextColor(Color.GREEN);
            binding.btnUpload.setVisibility(View.GONE);
        } else {
            if(Status.equalsIgnoreCase("NotSubmitted")){
                Status = "Not Submitted";
            }
            binding.labelTaxAssessmentStatus.setText(Status);
            binding.labelTaxAssessmentStatus.setTextColor(Color.RED);
            binding.btnUpload.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getActivity().setTitle("PSR Upload Module");
    }
    public List<String> getAssessmentYear () {

        List<String> assessmentYears = new ArrayList<>();

        Date date = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int currentYear = calendar.get(Calendar.YEAR);
        int currentMonth = calendar.get(Calendar.MONTH);

        String currentSession;
        String previousSession;
        String separator = "-";

        if (currentMonth<7) {
            previousSession = (currentYear - 2) + separator + (currentYear-1);
            currentSession = (currentYear - 1) + separator + currentYear;
        } else {
            previousSession = (currentYear - 1) + separator + currentYear;
            currentSession = (currentYear) + separator + (currentYear + 1);
        }

        previousTaxSession = previousSession;
        runningTaxSession = currentSession;
//        Log.d("getAssessmentYear : ",previousSession+ " and " + currentSession);
        assessmentYears.add("Select Tax Assessment Year");
        assessmentYears.add(previousSession);
        assessmentYears.add(currentSession);

        return assessmentYears;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}