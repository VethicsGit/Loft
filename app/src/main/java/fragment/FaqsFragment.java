package fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.vethics.loft.R;

import java.util.ArrayList;

import adapter.FaqsAdapter;

public class FaqsFragment extends Fragment {
    RecyclerView rvFAQs;
    ArrayList<String> questionArrayList, answerArrayList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_faqs, container, false);

        rvFAQs = (RecyclerView) v.findViewById(R.id.rv_faqs);

        questionArrayList = new ArrayList<>();
        answerArrayList = new ArrayList<>();

        questionArrayList.add(getResources().getString(R.string.faqs_question1));
        questionArrayList.add(getResources().getString(R.string.faqs_question2));
        questionArrayList.add(getResources().getString(R.string.faqs_question3));
        questionArrayList.add(getResources().getString(R.string.faqs_question4));
        questionArrayList.add(getResources().getString(R.string.faqs_question5));
        questionArrayList.add(getResources().getString(R.string.faqs_question6));
        questionArrayList.add(getResources().getString(R.string.faqs_question7));
        questionArrayList.add(getResources().getString(R.string.faqs_question8));
        questionArrayList.add(getResources().getString(R.string.faqs_question9));
        questionArrayList.add(getResources().getString(R.string.faqs_question10));
        questionArrayList.add(getResources().getString(R.string.faqs_question11));
        questionArrayList.add(getResources().getString(R.string.faqs_question12));

        answerArrayList.add(getResources().getString(R.string.faqs_answer1));
        answerArrayList.add(getResources().getString(R.string.faqs_answer2));
        answerArrayList.add(getResources().getString(R.string.faqs_answer3));
        answerArrayList.add(getResources().getString(R.string.faqs_answer4));
        answerArrayList.add(getResources().getString(R.string.faqs_answer5));
        answerArrayList.add(getResources().getString(R.string.faqs_answer6));
        answerArrayList.add(getResources().getString(R.string.faqs_answer7));
        answerArrayList.add(getResources().getString(R.string.faqs_answer8));
        answerArrayList.add(getResources().getString(R.string.faqs_answer9));
        answerArrayList.add(getResources().getString(R.string.faqs_answer10));
        answerArrayList.add(getResources().getString(R.string.faqs_answer11));
        answerArrayList.add(getResources().getString(R.string.faqs_answer12));

        LinearLayoutManager pgridLayoutManager = new LinearLayoutManager(getActivity());
        rvFAQs.setLayoutManager(pgridLayoutManager);
        FaqsAdapter favouritesCoursesAdapter = new FaqsAdapter(getActivity(),rvFAQs, questionArrayList, answerArrayList);
        rvFAQs.setAdapter(favouritesCoursesAdapter);
        rvFAQs.setNestedScrollingEnabled(false);
        return v;
    }

}
