package me.kevingo.licensekeyboard;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;

/**
 * Created by Chenyu on 2016/11/7.
 * 用来显示键盘的popwindow
 * 方便不同项目使用
 */
public class KeyBoardPopWindow extends PopupWindow{
    public static final String INPUT_LICENSE_COMPLETE = "me.kevingo.licensekeyboard.input.comp";
    public static final String INPUT_LICENSE_KEY = "LICENSE";

    private View mainView;

    private EditText inputbox1,inputbox2,
            inputbox3,inputbox4,
            inputbox5,inputbox6,inputbox7;
    private LicenseKeyboardUtil keyboardUtil;

    BroadcastReceiver receiver;
    Context mContext;
    LicenseListener mListener ;

    public KeyBoardPopWindow(final Context context , LicenseListener listener){
        super(context);
        this.mContext = context;
        this.mListener = listener;
        //窗口布局
        mainView = LayoutInflater.from(context).inflate(R.layout.keyboardlayout, null);
        setContentView(mainView);

        initView();
    }

    private void initView(){
        // 设置SelectPicPopupWindow弹出窗体可点击
        this.setFocusable(true);
        this.setOutsideTouchable(true);
        // 刷新状态
        this.update();
        // 设置SelectPicPopupWindow弹出窗体的宽
        this.setWidth(RelativeLayout.LayoutParams.MATCH_PARENT);
        // 设置SelectPicPopupWindow弹出窗体的高
        this.setHeight(RelativeLayout.LayoutParams.WRAP_CONTENT);
        //设置背景透明
        setBackgroundDrawable(new ColorDrawable(0));

        inputbox1 = (EditText) mainView.findViewById(R.id.et_car_license_inputbox1);
        inputbox2 = (EditText) mainView.findViewById(R.id.et_car_license_inputbox2);
        inputbox3 = (EditText) mainView.findViewById(R.id.et_car_license_inputbox3);
        inputbox4 = (EditText) mainView.findViewById(R.id.et_car_license_inputbox4);
        inputbox5 = (EditText) mainView.findViewById(R.id.et_car_license_inputbox5);
        inputbox6 = (EditText) mainView.findViewById(R.id.et_car_license_inputbox6);
        inputbox7 = (EditText) mainView.findViewById(R.id.et_car_license_inputbox7);
    }


    /**注册广播接受来自keyboard的车牌号 */
    public void registerReceiver(){
//        输入车牌完成后的intent过滤器
        IntentFilter finishFilter = new IntentFilter(INPUT_LICENSE_COMPLETE);
        receiver =  new  BroadcastReceiver() {
            @Override
            public   void  onReceive(Context context, Intent intent) {
                String license = intent.getStringExtra(INPUT_LICENSE_KEY);
                if(license != null && license.length() > 0){
                    if(keyboardUtil != null){
                        keyboardUtil.hideKeyboard();
                    }
                    KeyBoardPopWindow.this.dismiss();
                    mListener.setLicense(license);
                }
            }
        };
        mContext.registerReceiver(receiver, finishFilter);
    }

    /** 展示popwindow  */
    public void showKeyBoardPopWindow(View parent){
        if (!this.isShowing()) {
            registerReceiver();
            showKeyBoard();
            this.showAsDropDown(parent, 0, 0);
        } else {
            this.dismiss();
        }
    }

    @Override
    public void dismiss() {
        super.dismiss();
        mContext.unregisterReceiver(receiver);
        hideKeyBoard();
    }

    /** 展示popwindow中的键盘布局  */
    private void showKeyBoard(){
        if(keyboardUtil == null){
            keyboardUtil = new LicenseKeyboardUtil(mContext,mainView,new EditText[]{inputbox1,inputbox2,inputbox3,
                    inputbox4,inputbox5,inputbox6,inputbox7});
        }
        keyboardUtil.showKeyboard();
    }


    /** 隐藏popwindow中的键盘布局  */
    private void hideKeyBoard(){
        keyboardUtil.hideKeyboard();
        keyboardUtil = null;
    }




    /**通过接口回调将popwindow中获取的车牌号传给Activity界面 */
    public interface LicenseListener{
        void setLicense(String license);
    }
}
