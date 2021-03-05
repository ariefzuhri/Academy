package com.ariefzuhri.academy.ui.detail;

import android.content.Intent;
import android.os.Bundle;

import com.ariefzuhri.academy.R;
import com.ariefzuhri.academy.data.CourseEntity;
import com.ariefzuhri.academy.data.ModuleEntity;
import com.ariefzuhri.academy.databinding.ActivityDetailCourseBinding;
import com.ariefzuhri.academy.databinding.ContentDetailCourseBinding;
import com.ariefzuhri.academy.ui.reader.CourseReaderActivity;
import com.ariefzuhri.academy.utils.DataDummy;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import java.util.List;

public class DetailCourseActivity extends AppCompatActivity {

    public static final String EXTRA_COURSE = "extra_course";
    private ContentDetailCourseBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActivityDetailCourseBinding activityDetailCourseBinding = ActivityDetailCourseBinding.inflate(getLayoutInflater());
        binding = activityDetailCourseBinding.detailContent;

        setContentView(activityDetailCourseBinding.getRoot());

        setSupportActionBar(activityDetailCourseBinding.toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        DetailCourseAdapter adapter = new DetailCourseAdapter();

        DetailCourseViewModel viewModel = new ViewModelProvider(this, new ViewModelProvider.NewInstanceFactory()).get(DetailCourseViewModel.class);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String courseId = extras.getString(EXTRA_COURSE);
            if (courseId != null) {
                viewModel.setSelectedCourse(courseId);
                List<ModuleEntity> modules = viewModel.getModules();
                adapter.setModules(modules);
                populateCourse(viewModel.getCourse());
            }
        }

        binding.rvModule.setNestedScrollingEnabled(false);
        binding.rvModule.setLayoutManager(new LinearLayoutManager(this));
        binding.rvModule.setHasFixedSize(true);
        binding.rvModule.setAdapter(adapter);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(binding.rvModule.getContext(), DividerItemDecoration.VERTICAL);
        binding.rvModule.addItemDecoration(dividerItemDecoration);
    }

    private void populateCourse(CourseEntity courseEntity) {
        binding.textTitle.setText(courseEntity.getTitle());
        binding.textDescription.setText(courseEntity.getDescription());
        binding.textDate.setText(getResources().getString(R.string.deadline_date, courseEntity.getDeadline()));

        Glide.with(this)
                .load(courseEntity.getImagePath())
                .transform(new RoundedCorners(20))
                .apply(RequestOptions.placeholderOf(R.drawable.ic_loading)
                        .error(R.drawable.ic_error))
                .into(binding.imagePoster);

        binding.btnStart.setOnClickListener(v -> {
            Intent intent = new Intent(DetailCourseActivity.this, CourseReaderActivity.class);
            intent.putExtra(CourseReaderActivity.EXTRA_COURSE_ID, courseEntity.getCourseId());
            startActivity(intent);
        });
    }
}