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

import com.ariefzuhri.academy.data.CourseEntity;
import com.ariefzuhri.academy.databinding.FragmentAcademyBinding;
import com.ariefzuhri.academy.utils.DataDummy;

import java.util.List;

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
            AcademyViewModel viewModel = new ViewModelProvider(this, new ViewModelProvider.NewInstanceFactory()).get(AcademyViewModel.class);
            List<CourseEntity> courses = viewModel.getCourses();

            AcademyAdapter academyAdapter = new AcademyAdapter();
            academyAdapter.setCourses(courses);

            binding.rvAcademy.setLayoutManager(new LinearLayoutManager(getContext()));
            binding.rvAcademy.setHasFixedSize(true);
            binding.rvAcademy.setAdapter(academyAdapter);
        }
    }
}