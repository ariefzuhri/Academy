package com.ariefzuhri.academy.ui.bookmark;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.ariefzuhri.academy.R;
import com.ariefzuhri.academy.data.source.local.entity.CourseEntity;
import com.ariefzuhri.academy.databinding.ItemsBookmarkBinding;
import com.ariefzuhri.academy.ui.detail.DetailCourseActivity;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

public class BookmarkAdapter extends PagedListAdapter<CourseEntity, BookmarkAdapter.CourseViewHolder> {
    private final BookmarkFragmentCallback callback;

    BookmarkAdapter(BookmarkFragmentCallback callback) {
        super(DIFF_CALLBACK);
        this.callback = callback;
    }

    private static DiffUtil.ItemCallback<CourseEntity> DIFF_CALLBACK =
            new DiffUtil.ItemCallback<CourseEntity>() {
                @Override
                public boolean areItemsTheSame(@NonNull CourseEntity oldItem, @NonNull CourseEntity newItem) {
                    return oldItem.getCourseId().equals(newItem.getCourseId());
                }

                @SuppressLint("DiffUtilEquals")
                @Override
                public boolean areContentsTheSame(@NonNull CourseEntity oldItem, @NonNull CourseEntity newItem) {
                    return oldItem.equals(newItem);
                }
            };

    @NonNull
    @Override
    public CourseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemsBookmarkBinding binding = ItemsBookmarkBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new CourseViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull final CourseViewHolder holder, int position) {
        CourseEntity course = getItem(position);
        if (course != null) {
            holder.bind(course);
        }
    }

    public CourseEntity getSwipedData(int swipedPosition) {
        return getItem(swipedPosition);
    }

    class CourseViewHolder extends RecyclerView.ViewHolder {

        private final ItemsBookmarkBinding binding;

        CourseViewHolder(ItemsBookmarkBinding binding) {
            super(binding.getRoot());

            this.binding = binding;
        }

        void bind(CourseEntity course) {
            binding.tvItemTitle.setText(course.getTitle());
            binding.tvItemDate.setText(itemView.getResources().getString(R.string.deadline_date, course.getDeadline()));
            itemView.setOnClickListener(v -> {
                Intent intent = new Intent(itemView.getContext(), DetailCourseActivity.class);
                intent.putExtra(DetailCourseActivity.EXTRA_COURSE, course.getCourseId());
                itemView.getContext().startActivity(intent);
            });
            binding.imgShare.setOnClickListener(v -> callback.onShareClick(course));
            Glide.with(itemView.getContext())
                    .load(course.getImagePath())
                    .apply(RequestOptions.placeholderOf(R.drawable.ic_loading).error(R.drawable.ic_error))
                    .into(binding.imgPoster);
        }
    }
}
