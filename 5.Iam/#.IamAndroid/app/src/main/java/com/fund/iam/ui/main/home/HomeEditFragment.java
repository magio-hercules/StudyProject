package com.fund.iam.ui.main.home;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.fund.iam.BR;
import com.fund.iam.R;
import com.fund.iam.data.DataManager;
import com.fund.iam.data.model.Portfolio;
import com.fund.iam.data.model.User;
import com.fund.iam.databinding.FragmentHomeEditBinding;
import com.fund.iam.di.ViewModelProviderFactory;
import com.fund.iam.ui.base.BaseFragment;
import com.fund.iam.ui.letter.LetterActivity;
import com.orhanobut.logger.Logger;

import java.util.List;

import javax.inject.Inject;

public class HomeEditFragment extends BaseFragment<FragmentHomeEditBinding, HomeViewModel> implements HomeNavigator, View.OnClickListener {

    public static final String TAG = HomeEditFragment.class.getSimpleName();

    private final int PORTFOLIPO_TITLE_ID  = 0x3000;
    private final int PORTFOLIPO_EDIT_ID  = 0x4000;
    private final int PORTFOLIPO_DELETE_ID  = 0x5000;
    private final int PORTFOLIPO_IMAGE_ID  = 0x6000;


    @Inject
    ViewModelProviderFactory viewModelProviderFactory;

    @Inject
    DataManager dataManager;


    //

    private int portfolidIndex = 1;

//    Spinner spinner;
    String[] spinnerValueLocation = {
            "서울",
            "수원",
            "인천",
            "하남"
    };

    String[] spinnerValueJob = {
            "개발자",
            "디자이너",
            "기획자",
    };

    //


    public static HomeEditFragment newInstance() {
        Bundle args = new Bundle();
        HomeEditFragment fragment = new HomeEditFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_home_edit;
    }

    @Override
    public HomeViewModel getViewModel() {
        return new ViewModelProvider(getViewModelStore(), viewModelProviderFactory).get(HomeViewModel.class);
    }

    @Override
    public void goBack() {
        getBaseActivity().onFragmentDetached(TAG);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        Logger.i("onCreate");
        getViewModel().setNavigator(this);
        setHasOptionsMenu(true);
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initSpinner();
    }

    @Override
    public void onClick(View view) {
        goBack();
    }

    @Override
    public void startLetterActivity() {
        LetterActivity.start(getActivity());
        getActivity().finish();
    }

    @Override
    public void showToast(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void handleError(Throwable throwable) {
        Logger.e("handleError " + throwable.getMessage());
        Toast.makeText(getContext(), throwable.getMessage(), Toast.LENGTH_LONG).show();
    }


    /////////////////////////////


    public void initSpinner() {
        SpinnerAdapter adapter = new SpinnerAdapter(getContext(), R.layout.spinner_item);
//        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        adapter.addAll(spinnerValueLocation);
        adapter.add("거주지");


        getViewDataBinding().profileEditLocation.setAdapter(adapter);
        getViewDataBinding().profileEditLocation.setSelection(adapter.getCount());

        getViewDataBinding().profileEditLocation.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onNothingSelected(AdapterView<?> parent) { }

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // TODO Auto-generated method stub

//                if (getViewDataBinding().profileEditLocation.getSelectedItem() == "거주지") {
//                    //Do nothing.
//                    Toast.makeText(getContext(), "이거슨 힌트", Toast.LENGTH_LONG).show();
//                } else {
//                    Toast.makeText(getContext(), getViewDataBinding().profileEditLocation.getSelectedItem().toString(), Toast.LENGTH_LONG).show();
//                }
            }
        });

        // for job
        SpinnerAdapter adapterJob = new SpinnerAdapter(getContext(), R.layout.spinner_item);
        adapterJob.clear();
        adapterJob.addAll(spinnerValueJob);
        adapterJob.add("직종");

        getViewDataBinding().profileEditJob.setAdapter(adapterJob);
        getViewDataBinding().profileEditJob.setSelection(adapterJob.getCount());

        getViewDataBinding().profileEditJob.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onNothingSelected(AdapterView<?> parent) { }

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // TODO Auto-generated method stub
//                if (getViewDataBinding().profileEditLocation.getSelectedItem() == "거주지") {
//                    //Do nothing.
//                    Toast.makeText(getContext(), "이거슨 힌트", Toast.LENGTH_LONG).show();
//                } else {
//                    Toast.makeText(getContext(), getViewDataBinding().profileEditLocation.getSelectedItem().toString(), Toast.LENGTH_LONG).show();
//                }
            }
        });
    }

    public void updateUser() {
        Log.d(TAG, "updateUI");

    }

    public void updatePortfolio() {
        Log.d(TAG, "updatePortfolio");

    }

}
