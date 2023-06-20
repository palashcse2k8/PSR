package com.example.psr.psrupload.views;

import static android.app.Activity.RESULT_OK;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.psr.R;
import com.example.psr.psrupload.imagecropper.CropImage;
import com.example.psr.psrupload.imagecropper.CropImageView;
import com.example.psr.psrupload.utils.ImageUtil;
import com.example.psr.psrupload.viewmodels.PSRViewModel;
import java.io.IOException;
import com.example.psr.databinding.FragmentPsrImageScannerBinding;
/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PsrImageScannerFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PsrImageScannerFragment extends Fragment {

    private static final int REQUEST_CODE_FIRST = 99;
    private static final int REQUEST_CODE_SECOND = 98;
    FragmentPsrImageScannerBinding binding;

    private Context context;
    private Application application;
    private PSRViewModel viewModel;

    Bitmap bitmap1;
    Bitmap bitmap2;

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
     * @return A new instance of fragment PsrScannerFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PsrImageScannerFragment newInstance(String param1, String param2) {
        PsrImageScannerFragment fragment = new PsrImageScannerFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public PsrImageScannerFragment() {
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
        this.context = getActivity();
        viewModel = new PSRViewModel(application, context);
        binding = FragmentPsrImageScannerBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init();
        binding.btnSubmit.setOnClickListener(v -> {

            viewModel.setPsrImage(ImageUtil.combineBitmapsVertically(bitmap1, bitmap2));

            if (bitmap1 == null && bitmap2 == null) {
                Toast toast = Toast.makeText(getContext(), "You didn't Select any image", Toast.LENGTH_SHORT);
                toast.getView().setBackgroundColor(Color.RED);
                toast.show();
                return;
            }
            switchFragment(new PsrMergeFragment());
        });
    }

    void switchFragment(Fragment fragment) {
        getActivity().getSupportFragmentManager().beginTransaction().setReorderingAllowed(true).replace(R.id.fragment_container_view, fragment, fragment.getClass().getSimpleName()).addToBackStack(null).commit();
    };
    private void init() {
        binding.btnCameraPart1.setOnClickListener(v -> {
            CropImage.activity()
                    .setGuidelines(CropImageView.Guidelines.ON)
                    .start(getContext(), this, CropImage.CROP_IMAGE_REQUEST_FIRST_CODE);
        });
        binding.btnCameraPart2.setOnClickListener(v -> {
            CropImage.activity()
                    .setGuidelines(CropImageView.Guidelines.ON)
                    .start(getContext(), this, CropImage.CROP_IMAGE_REQUEST_SECOND_CODE);
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
        bitmap2 = null;
        bitmap1 = null;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CropImage.CROP_IMAGE_REQUEST_FIRST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                Uri resultUri = result.getUri();
                Bitmap bitmap = null;
                try {
                    bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), resultUri);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                if (bitmap != null) {
//                    getActivity().getContentResolver().delete(resultUri, null, null);
                    bitmap1 = bitmap;
                    binding.btnCameraPart1.setVisibility(View.GONE);
                    binding.imgPart1.setImageBitmap(bitmap);
                }
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        } else if (requestCode == CropImage.CROP_IMAGE_REQUEST_SECOND_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                Uri resultUri = result.getUri();
                Bitmap bitmap = null;
                try {
                    bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), resultUri);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                if (bitmap != null) {
//                    getActivity().getContentResolver().delete(resultUri, null, null);
                    bitmap2 = bitmap;
                    binding.btnCameraPart2.setVisibility(View.GONE);
                    binding.imgPart2.setImageBitmap(bitmap);
                }
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        } else {
            Log.d("myTag", "onActivityResult else");
        }
    }
}