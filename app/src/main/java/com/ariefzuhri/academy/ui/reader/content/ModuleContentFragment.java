package com.ariefzuhri.academy.ui.reader.content;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ariefzuhri.academy.data.ModuleEntity;
import com.ariefzuhri.academy.databinding.FragmentModuleContentBinding;
import com.ariefzuhri.academy.ui.reader.CourseReaderViewModel;
import com.ariefzuhri.academy.viewmodel.ViewModelFactory;

public class ModuleContentFragment extends Fragment {

    public static final String TAG = ModuleContentFragment.class.getSimpleName();
    private FragmentModuleContentBinding binding;

    public ModuleContentFragment() {}

    public static ModuleContentFragment newInstance() {
        return new ModuleContentFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentModuleContentBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (getActivity() != null){
            ViewModelFactory factory = ViewModelFactory.getInstance(getActivity());
            CourseReaderViewModel viewModel = new ViewModelProvider(requireActivity(), factory).get(CourseReaderViewModel.class);

            binding.progressBar.setVisibility(View.VISIBLE);
            viewModel.getSelectedModule().observe(getViewLifecycleOwner(), module -> {
                binding.progressBar.setVisibility(View.GONE);
                if (module != null) {
                    populateWebView(module);
                }
            });
        }
    }

    private void populateWebView(ModuleEntity module) {
        binding.webView.loadData(module.contentEntity.getContent(), "text/html", "UTF-8");
    }
}