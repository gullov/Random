package ru.gulov.random;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.transition.Fade;
import androidx.transition.Transition;
import androidx.transition.TransitionManager;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class NumActivity extends AppCompatActivity {

    FloatingActionButton v_trigger_fabs;
    CardView v_result_card;
    private AnimatorSet mSetRightOut;
    private AnimatorSet mSetLeftIn;
    private boolean mIsBackVisible = false;
    private boolean still = false;
    private boolean still2 = false;
    private View mCardFrontLayout;
    private View mCardBackLayout;
    View background, backgrounds;
    EditText f_num_from_et, f_num_to;
    TextView first, second, v_param_no_repeat_counter;
    int is = 0;
    int globalIndex = 0;
    AsyncTask<?, ?, ?> shuffle;
    ArrayList<Integer> number = new ArrayList<Integer>();
    public void hideKeyboard(View view) {
        InputMethodManager inputMethodManager =(InputMethodManager)getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_num);

        findViews();
        loadAnimations();
        changeCameraDistance();
        first = findViewById(R.id.first);
        second = findViewById(R.id.second);
        f_num_from_et = findViewById(R.id.f_num_from_et);
        f_num_to = findViewById(R.id.f_num_to);
        v_param_no_repeat_counter = findViewById(R.id.v_param_no_repeat_counter);
        v_param_no_repeat_counter.setText("0/"+(Integer.parseInt(f_num_to.getText().toString())+1-Integer.parseInt(f_num_from_et.getText().toString())));

        f_num_from_et.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {

                switch (actionId) {

                    case EditorInfo.IME_ACTION_DONE:
                        Log.d("plaplapl", "onEditorAction: "+"plaplap");
                        f_num_from_et.clearFocus();
                        hideKeyboard(v);
                        shuffle = new Shuffle();
                        shuffle.execute();
                        v_param_no_repeat_counter.setText("0/"+(Integer.parseInt(f_num_to.getText().toString())+1-Integer.parseInt(f_num_from_et.getText().toString())));
                        return true;
                    default:
                        return false;
                }

            }
        });

        f_num_to.setOnEditorActionListener((v, actionId, event) -> {

            switch (actionId) {

                case EditorInfo.IME_ACTION_DONE:
                    Log.d("plaplapl", "onEditorAction: "+"plaplap");
                    f_num_to.clearFocus();
                    hideKeyboard(v);
                    shuffle = new Shuffle().execute();
                    v_param_no_repeat_counter.setText("0/"+(Integer.parseInt(f_num_to.getText().toString())+1-Integer.parseInt(f_num_from_et.getText().toString())));
                    return true;
                default:
                    return false;
            }

        });



        backgrounds = findViewById(R.id.backgrounds);
        backgrounds.setOnClickListener(v -> {
            circularReActivity();
            still2 = false;
        });
        mSetRightOut.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                still = false;
            }

            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
                still = true;
            }
        });
        background = findViewById(R.id.v_result_root);
        v_trigger_fabs = findViewById(R.id.v_trigger_fabs);
        v_trigger_fabs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!still2) {
                    circularRevealActivity();
                    still2 = true;
                    RotateAnimation rotate = new RotateAnimation(360, 0, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
                    rotate.setDuration(500);
                    rotate.setInterpolator(new AccelerateDecelerateInterpolator());
                    v_trigger_fabs.startAnimation(rotate);
                    flipCard(v_trigger_fabs);
                    first.setText(number.get(globalIndex)+"");
                    globalIndex++;

                }
                else if (!still){
                    RotateAnimation rotate = new RotateAnimation(360, 0, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
                    rotate.setDuration(500);
                    rotate.setInterpolator(new AccelerateDecelerateInterpolator());
                    v_trigger_fabs.startAnimation(rotate);
                    flipCard(v_trigger_fabs);
                    if (globalIndex%2==0){
                        first.setText(number.get(globalIndex)+"");
                        globalIndex++;
                    }
                    else{
                        second.setText(number.get(globalIndex)+"");
                        globalIndex++;
                    }
                }
            }
        });


    }

    private void circularRevealActivity() {
        int cx = background.getRight() - getDips(50);
        int cy = background.getBottom() - getDips(100);

        float finalRadius = Math.max(background.getWidth(), background.getHeight());

        Animator circularReveal = ViewAnimationUtils.createCircularReveal(
                background,
                cx,
                cy,
                0,
                finalRadius);

        circularReveal.setDuration(200);
        circularReveal.setInterpolator(new AccelerateDecelerateInterpolator());
        background.setVisibility(View.VISIBLE);
        circularReveal.start();

    }
    private void circularReActivity() {

        int cx = background.getRight() - getDips(50);
        int cy = background.getBottom() - getDips(100);

        float finalRadius = Math.max(background.getWidth(), background.getHeight());
        Animator circularReveal = ViewAnimationUtils.createCircularReveal(background, cx, cy, finalRadius, 0);

        circularReveal.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {
                background.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });
        circularReveal.setDuration(200);
        circularReveal.setInterpolator(new AccelerateDecelerateInterpolator());
        circularReveal.start();

    }

    private int getDips(int dps) {
        Resources resources = getResources();
        return (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                dps,
                resources.getDisplayMetrics());
    }
    private void changeCameraDistance() {
        int distance = 1100;
        float scale = getResources().getDisplayMetrics().density * distance;
        mCardFrontLayout.setCameraDistance(scale);
        mCardBackLayout.setCameraDistance(scale);
    }

    private void loadAnimations() {
        mSetRightOut = (AnimatorSet) AnimatorInflater.loadAnimator(this, R.animator.in_animation);
        mSetLeftIn = (AnimatorSet) AnimatorInflater.loadAnimator(this, R.animator.out_animation);
        mSetRightOut.setInterpolator(new AccelerateDecelerateInterpolator());
        mSetLeftIn.setInterpolator(new AccelerateDecelerateInterpolator());

    }

    private void findViews() {
        mCardBackLayout = findViewById(R.id.card_back);
        mCardFrontLayout = findViewById(R.id.card_front);
    }

    public void flipCard(View view) {
        if (!still) {
            if (!mIsBackVisible) {
                mSetRightOut.setTarget(mCardFrontLayout);
                mSetLeftIn.setTarget(mCardBackLayout);
                mSetRightOut.start();
                mSetLeftIn.start();

                mIsBackVisible = true;
            } else {
                mSetRightOut.setTarget(mCardBackLayout);
                mSetLeftIn.setTarget(mCardFrontLayout);
                mSetRightOut.start();
                mSetLeftIn.start();
                mIsBackVisible = false;
            }
        }
    }
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(NumActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
        overridePendingTransition(R.anim.design_bottom_sheet_slide_in1, R.anim.design_bottom_sheet_slide_out1);
    }
    private final class Shuffle extends AsyncTask<Void, Void, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            number = new ArrayList<Integer>();
            }

        @Override
        protected String doInBackground(Void... params) {
            for (int i = Integer.parseInt(f_num_from_et.getText().toString())-1;
                 i < Integer.parseInt(f_num_to.getText().toString()); i++) {
                number.add(i+1);
                Log.d("TAGssss", "onCreate: "+number.get(i));
            }
            Collections.shuffle(number);
            for (int i = Integer.parseInt(f_num_from_et.getText().toString())-1;
                 i < Integer.parseInt(f_num_to.getText().toString()); i++) {
                Log.d("TAGssss", "onCreate: "+number.get(i));
            }
            return "Executed";
        }

        @Override
        protected void onPostExecute(String result) {
            Log.d("TAGssss", "onPostExecute: ");
        }
    }
}