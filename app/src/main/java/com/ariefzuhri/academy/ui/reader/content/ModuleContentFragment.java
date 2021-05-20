package com.ariefzuhri.academy.ui.reader.content;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.ariefzuhri.academy.data.source.local.entity.ModuleEntity;
import com.ariefzuhri.academy.databinding.FragmentModuleContentBinding;
import com.ariefzuhri.academy.ui.reader.CourseReaderViewModel;
import com.ariefzuhri.academy.viewmodel.ViewModelFactory;

public class ModuleContentFragment extends Fragment {

    public static final String TAG = ModuleContentFragment.class.getSimpleName();
    private CourseReaderViewModel viewModel;
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
            viewModel = new ViewModelProvider(requireActivity(), factory).get(CourseReaderViewModel.class);

            viewModel.selectedModule.observe(getViewLifecycleOwner(), moduleEntity -> {
                if (moduleEntity != null) {
                    switch (moduleEntity.status) {
                        case LOADING:
                            binding.progressBar.setVisibility(View.VISIBLE);
                            break;

                        case SUCCESS:
                            if (moduleEntity.data != null) {
                                binding.progressBar.setVisibility(View.GONE);
                                if (moduleEntity.data.contentEntity != null) {
                                    populateWebView(moduleEntity.data);
                                }
                                setButtonNextPrevState(moduleEntity.data);
                                if (!moduleEntity.data.isRead()) {
                                    viewModel.readContent(moduleEntity.data);
                                }
                            }
                            break;

                        case ERROR:
                            binding.progressBar.setVisibility(View.GONE);
                            Toast.makeText(getContext(), "Terjadi kesalahan", Toast.LENGTH_SHORT).show();
                            break;
                    }
                    binding.btnNext.setOnClickListener(v -> viewModel.setNextPage());
                    binding.btnPrev.setOnClickListener(v -> viewModel.setPrevPage());
                }
            });
        }
    }

    private void populateWebView(ModuleEntity module) {
        binding.webView.loadData(module.contentEntity.getContent(), "text/html", "UTF-8");
    }

    private void setButtonNextPrevState(ModuleEntity module) {
        if (getActivity() != null) {
            if (module.getPosition() == 0) {
                binding.btnPrev.setEnabled(false);
                binding.btnNext.setEnabled(true);
            } else if (module.getPosition() == viewModel.getModuleSize() - 1) {
                binding.btnPrev.setEnabled(true);
                binding.btnNext.setEnabled(false);
            } else
                binding.btnPrev.setEnabled(true);
            binding.btnNext.setEnabled(true);
        }
    }
}