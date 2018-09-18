package fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.vethics.loft.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class StudentCourseHistoryFragment extends Fragment {
    ListView lvStudentCourseHistory;

    public StudentCourseHistoryFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_student_course_history, container, false);
        lvStudentCourseHistory = (ListView) v.findViewById(R.id.lv_student_course_history);
        return v;
    }

}
