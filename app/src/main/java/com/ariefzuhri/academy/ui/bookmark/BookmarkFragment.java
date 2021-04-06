package com.ariefzuhri.academy.ui.bookmark;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ShareCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ariefzuhri.academy.R;
import com.ariefzuhri.academy.data.CourseEntity;
import com.ariefzuhri.academy.databinding.FragmentBookmarkBinding;
import com.ariefzuhri.academy.viewmodel.ViewModelFactory;

import java.util.List;

public class BookmarkFragment extends Fragment implements BookmarkFragmentCallback {

    private FragmentBookmarkBinding binding;

    public BookmarkFragment() {}

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentBookmarkBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (getActivity() != null) {
            ViewModelFactory factory = ViewModelFactory.getInstance(getActivity());
            BookmarkViewModel viewModel = new ViewModelProvider(this, factory).get(BookmarkViewModel.class);

            BookmarkAdapter adapter = new BookmarkAdapter(this);

            binding.progressBar.setVisibility(View.VISIBLE);
            viewModel.getBookmarks().observe(getViewLifecycleOwner(), courses -> {
                binding.progressBar.setVisibility(View.GONE);
                adapter.setCourses(courses);
                adapter.notifyDataSetChanged();
            });

            binding.rvBookmark.setLayoutManager(new LinearLayoutManager(getContext()));
            binding.rvBookmark.setHasFixedSize(true);
            binding.rvBookmark.setAdapter(adapter);
        }
    }

    @Override
    public void onShareClick(CourseEntity course) {
        if (getActivity() != null){
            String mimeType = "text/plain";
            ShareCompat.IntentBuilder
                    .from(getActivity())
                    .setText(mimeType)
                    .setChooserTitle("Bagikan aplikasi ini sekarang.")
                    .setText(getResources().getString(R.string.share_text, course.getTitle()))
                    .startChooser();
        }
    }
}