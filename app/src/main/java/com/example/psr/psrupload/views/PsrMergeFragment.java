package com.example.psr.psrupload.views;

import android.app.Application;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.psr.R;
import com.example.psr.databinding.FragmentPsrMergeBinding;
import com.example.psr.psrupload.viewmodels.PSRViewModel;

import androidx.lifecycle.ViewModelProvider;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PsrMergeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PsrMergeFragment extends Fragment {

    FragmentPsrMergeBinding binding;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private Context context;
    private Application application;
    private PSRViewModel viewModel;

    public PsrMergeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PsrMergeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PsrMergeFragment newInstance(String param1, String param2) {
        PsrMergeFragment fragment = new PsrMergeFragment();
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

        PSRViewModel viewModel = new ViewModelProvider(this).get(PSRViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        this.context = getActivity();
        binding = FragmentPsrMergeBinding.inflate(inflater, container, false);
        viewModel = new PSRViewModel(application, context);
        return binding.getRoot();
    }

    void switchFragment(Fragment fragment) {
        getActivity().getSupportFragmentManager().beginTransaction().setReorderingAllowed(true).replace(R.id.fragment_container_view, fragment, fragment.getClass().getSimpleName()).addToBackStack(null).commit();
    };

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        binding.btnSubmitFinal.setOnClickListener(v -> {
            switchFragment(new PsrMergeFragment());
        });
    }
}