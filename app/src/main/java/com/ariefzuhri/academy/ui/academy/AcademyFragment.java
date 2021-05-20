package com.ariefzuhri.academy.ui.academy;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.ariefzuhri.academy.databinding.FragmentAcademyBinding;
import com.ariefzuhri.academy.viewmodel.ViewModelFactory;

public class AcademyFragment extends Fragment {

    private FragmentAcademyBinding binding;

    public AcademyFragment() {}

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentAcademyBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (getActivity() != null){
            ViewModelFactory factory = ViewModelFactory.getInstance(getActivity());
            AcademyViewModel viewModel = new ViewModelProvider(this, factory).get(AcademyViewModel.class);

            AcademyAdapter academyAdapter = new AcademyAdapter();
            viewModel.getCourses().observe(getViewLifecycleOwner(), courses -> {
                if (courses != null) {
                    switch (courses.status) {
                        case LOADING:
                            binding.progressBar.setVisibility(View.VISIBLE);
                            break;
                        case SUCCESS:
                            binding.progressBar.setVisibility(View.GONE);
                            academyAdapter.submitList(courses.data);
                            break;
                        case ERROR:
                            binding.progressBar.setVisibility(View.GONE);
                            Toast.makeText(getContext(), "Terjadi kesalahan", Toast.LENGTH_SHORT).show();
                            break;
                    }
                }
            });

            binding.rvAcademy.setLayoutManager(new LinearLayoutManager(getContext()));
            binding.rvAcademy.setHasFixedSize(true);
            binding.rvAcademy.setAdapter(academyAdapter);
        }
    }
}