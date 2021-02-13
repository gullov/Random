package ru.gulov.random;


import android.content.Context;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Toast;

public class CustomEditText extends androidx.appcompat.widget.AppCompatEditText {

    public MyAdapterListener onClickListener;
    View v = this;
    public CustomEditText(Context context) {
        super(context);
    }

    public CustomEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean onKeyPreIme(int keyCode, KeyEvent event) {
        if (event.getAction() == KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_BACK) {
            onClickListener.backPressed(v, true);
            return true;
        }
        return super.onKeyPreIme(keyCode, event);
    }
    public void setCustomListener(MyAdapterListener listener){
        onClickListener = listener;
    }

    public interface MyAdapterListener {
        void backPressed(View v, boolean a);
    }


}
