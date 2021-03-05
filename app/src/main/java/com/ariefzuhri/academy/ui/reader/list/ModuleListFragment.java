package com.ariefzuhri.academy.ui.reader.list;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ariefzuhri.academy.data.ModuleEntity;
import com.ariefzuhri.academy.databinding.FragmentModuleListBinding;
import com.ariefzuhri.academy.ui.reader.CourseReaderActivity;
import com.ariefzuhri.academy.ui.reader.CourseReaderCallback;
import com.ariefzuhri.academy.ui.reader.CourseReaderViewModel;
import com.ariefzuhri.academy.utils.DataDummy;

import java.util.List;

public class ModuleListFragment extends Fragment implements MyAdapterClickListener {

    public static final String TAG = ModuleListFragment.class.getSimpleName();

    private FragmentModuleListBinding binding;
    private ModuleListAdapter adapter;
    private CourseReaderCallback courseReaderCallback;
    private CourseReaderViewModel viewModel;

    public ModuleListFragment() {}

    public static ModuleListFragment newInstance() {
        return new ModuleListFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentModuleListBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (getActivity() != null) {
            viewModel = new ViewModelProvider(requireActivity(), new ViewModelProvider.NewInstanceFactory()).get(CourseReaderViewModel.class);
            adapter = new ModuleListAdapter(this);
            populateRecyclerView(viewModel.getModules());
        }
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        courseReaderCallback = ((CourseReaderActivity) context);
    }

    @Override
    public void onItemClicked(int position, String moduleId) {
        courseReaderCallback.moveTo(position, moduleId);
        viewModel.setSelectedModule(moduleId);
    }

    private void populateRecyclerView(List<ModuleEntity> modules) {
        binding.progressBar.setVisibility(View.GONE);
        adapter.setModules(modules);
        binding.rvModule.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.rvModule.setHasFixedSize(true);
        binding.rvModule.setAdapter(adapter);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL);
        binding.rvModule.addItemDecoration(dividerItemDecoration);
    }
}