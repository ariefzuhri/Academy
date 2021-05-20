package com.ariefzuhri.academy.ui.bookmark;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ShareCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ariefzuhri.academy.R;
import com.ariefzuhri.academy.data.source.local.entity.CourseEntity;
import com.ariefzuhri.academy.databinding.FragmentBookmarkBinding;
import com.ariefzuhri.academy.viewmodel.ViewModelFactory;
import com.google.android.material.snackbar.Snackbar;

public class BookmarkFragment extends Fragment implements BookmarkFragmentCallback {

    private FragmentBookmarkBinding binding;

    private BookmarkViewModel viewModel;
    private BookmarkAdapter adapter;

    public BookmarkFragment() {}

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentBookmarkBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        itemTouchHelper.attachToRecyclerView(binding.rvBookmark);

        if (getActivity() != null) {
            ViewModelFactory factory = ViewModelFactory.getInstance(getActivity());
            viewModel = new ViewModelProvider(this, factory).get(BookmarkViewModel.class);

            adapter = new BookmarkAdapter(this);

            binding.progressBar.setVisibility(View.VISIBLE);
            viewModel.getBookmarks().observe(getViewLifecycleOwner(), courses -> {
                binding.progressBar.setVisibility(View.GONE);
                adapter.submitList(courses);
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

    private ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new ItemTouchHelper.Callback() {

        @Override
        public int getMovementFlags(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
            // Aksi di bawah digunakan untuk melakukan swap ke kanan dan ke kiri
            return makeMovementFlags(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT);
        }

        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return true;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            if (getView() != null) {
                // Sebelum melakukan penghapusan, course harus mendapatkan posisi dari item yang di swipe
                int swipedPosition = viewHolder.getAdapterPosition();

                // Kemudian memanggil CourseEntity sesuai posisi ketika diswipe
                CourseEntity courseEntity = adapter.getSwipedData(swipedPosition);

                // Melakukan setBookmark untuk menghapus bookmark dari list course
                viewModel.setBookmark(courseEntity);

                // Memanggil Snackbar untuk melakukan pengecekan, apakah benar melakukan penghapusan bookmark
                Snackbar snackbar = Snackbar.make(getView(), R.string.message_undo, Snackbar.LENGTH_LONG);

                // Mengembalikan item yang terhapus
                snackbar.setAction(R.string.message_ok, v -> viewModel.setBookmark(courseEntity));

                // Menampilkan snackbar
                snackbar.show();
            }
        }
    });
}