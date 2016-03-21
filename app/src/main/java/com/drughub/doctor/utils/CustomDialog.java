package com.drughub.doctor.utils;


import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.drughub.doctor.BaseActivity;
import com.drughub.doctor.R;

public class CustomDialog
{

    final static int COLOR_TRANSPARENT = 0xDD000000;

    public static Dialog showCustomDialog(BaseActivity activity, int layoutId, int gravity,
                                          boolean isTranslucent, boolean wrapContent, boolean showActionbar)
    {
        Dialog dialog = new Dialog(activity);//, R.style.AppTheme
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(layoutId);

        dialog.getWindow().setGravity(gravity);

        if(isTranslucent)
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(COLOR_TRANSPARENT));

        if(showActionbar || wrapContent) {
            Rect rect = new Rect();
            activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(rect);
            int activity_height = rect.height();
            int actionbar_height = activity.getActionBarHeight();
            if(wrapContent)
                dialog.getWindow().setLayout(rect.width(), WindowManager.LayoutParams.WRAP_CONTENT);
            else
                dialog.getWindow().setLayout(rect.width(), activity_height-actionbar_height);

            dialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
            dialog.getWindow().setGravity(Gravity.BOTTOM);
        }

        //dialog.setCanceledOnTouchOutside(false);
        dialog.show();

        return dialog;
    }

    public static Dialog showMessageDialog(BaseActivity activity, String messageTxt, String okBtnTxt)
    {
        Dialog dialog = showCustomDialog(activity, R.layout.dialog_message, Gravity.CENTER, true, false, true);

        TextView textView = (TextView)dialog.findViewById(R.id.dialogMessage);
        textView.setText(messageTxt);
        Button okBtn = (Button)dialog.findViewById(R.id.dialogOkBtn);
        okBtn.setText(okBtnTxt);

        return dialog;
    }

    public static Dialog showQuestionDialog(BaseActivity activity, String questionTxt, String noBtnTxt, String yesBtnTxt)
    {
        Dialog dialog = showCustomDialog(activity, R.layout.dialog_question, Gravity.CENTER, true, false, true);

        TextView textView = (TextView)dialog.findViewById(R.id.dialogQuestion);
        textView.setText(questionTxt);
        Button noBtn = (Button)dialog.findViewById(R.id.dialogNoBtn);
        noBtn.setText(noBtnTxt);
        Button yesBtn = (Button)dialog.findViewById(R.id.dialogYesBtn);
        yesBtn.setText(yesBtnTxt);

        return dialog;
    }
}
