package me.kevingo.licensekeyboard;

import android.content.Context;
import android.content.Intent;
import android.inputmethodservice.Keyboard;
import android.inputmethodservice.KeyboardView;
import android.inputmethodservice.KeyboardView.OnKeyboardActionListener;
import android.text.InputType;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;


/**
 * Created by xxx on 2015/9/22.
 */
public class LicenseKeyboardUtil {
    public static final String INPUT_LICENSE_COMPLETE = "me.kevingo.licensekeyboard.input.comp";
    public static final String INPUT_LICENSE_KEY = "LICENSE";

    private Context ctx;
    private KeyboardView keyboardView;
    private Keyboard k1;// 省份简称键盘
    private Keyboard k2;// 数字字母键盘

    private String provinceShort[];
    private String letterAndDigit[];

    private EditText edits[];
    private int currentEditText = 0;//默认当前光标在第一个EditText

    public LicenseKeyboardUtil(Context ctx,View view, EditText edits[]) {
        this.ctx = ctx;
        this.edits = edits;

        //禁用edits输入属性 即禁用光标
        for (int i=0; i<edits.length; i++){
            edits[i].setText("");
            edits[i].setInputType(InputType.TYPE_NULL);
        }


        k1 = new Keyboard(ctx, R.xml.province_short_keyboard);
        k2 = new Keyboard(ctx, R.xml.lettersanddigit_keyboard);

//        keyboardView = (KeyboardView) ((Activity)ctx).findViewById(R.id.keyboard_view);

        keyboardView = (KeyboardView) view.findViewById(R.id.keyboard_view);

        keyboardView.setKeyboard(k1);
        keyboardView.setEnabled(true);
        //设置为true时,当按下一个按键时会有一个popup来显示<key>元素设置的android:popupCharacters=""
        keyboardView.setPreviewEnabled(false);
        //设置键盘按键监听器
        keyboardView.setOnKeyboardActionListener(listener);
        provinceShort = new String[]{"京", "津", "冀", "鲁", "晋", "蒙", "辽", "吉", "黑"
                , "沪", "苏", "浙", "皖", "闽", "赣", "豫", "鄂", "湘"
                , "粤", "桂", "渝", "川", "贵", "云", "藏", "陕", "甘"
                , "青", "琼", "新", "港", "澳", "台", "宁"};

        letterAndDigit = new String[]{"0","1", "2", "3", "4", "5", "6", "7", "8", "9"
                , "Q", "W", "E", "R", "T", "Y", "U", "I", "O", "P"
                , "A", "S", "D", "F", "G", "H", "J", "K", "L"
                , "Z", "X", "C", "V", "B", "N", "M"};
    }

    private OnKeyboardActionListener listener = new OnKeyboardActionListener() {
        @Override
        public void swipeUp() {
        }

        @Override
        public void swipeRight() {
        }

        @Override
        public void swipeLeft() {
        }

        @Override
        public void swipeDown() {
        }

        @Override
        public void onText(CharSequence text) {
        }

        @Override
        public void onRelease(int primaryCode) {

        }

        @Override
        public void onPress(int primaryCode) {

        }

        @Override
        public void onKey(int primaryCode, int[] keyCodes) {
//            if(primaryCode == 112){ //xml中定义的删除键值为112
//                edits[currentEditText].setText("");//将当前EditText置为""并currentEditText-1
//                currentEditText--;
//                if(currentEditText < 1){
//                    //切换为省份简称键盘
//                    keyboardView.setKeyboard(k1);
//                    edits[currentEditText].setText("");
//                }
//                if(currentEditText < 0){
//                    currentEditText = 0;
//                }
//            }else if(primaryCode == 66){ //xml中定义的完成键值为66
//                Intent intent = new Intent();
//                String license = "";
//                for (int i=0;i<edits.length;i++){
//                    license += edits[i].getText().toString();
//                }
//                intent.putExtra(MainActivity.INPUT_LICENSE_KEY, license);
//                intent.setAction(MainActivity.INPUT_LICENSE_COMPLETE);
//                ctx.sendBroadcast(intent);
//            }else if(primaryCode == 113){
//               return;
//            } else { //其它字符按键
//                if (currentEditText == 0) {
//                    //如果currentEditText==0代表当前为省份键盘,
//                    // 按下一个按键后,设置相应的EditText的值
//                    // 然后切换为字母数字键盘
//                    //currentEditText+1
//                    edits[0].setText(provinceShort[primaryCode]);
//                    currentEditText = 1;
//                    //切换为字母数字键盘
//                    keyboardView.setKeyboard(k2);
//                }else{
//                    //第二位必须大写字母
//                    if(currentEditText == 1 && !letterAndDigit[primaryCode].matches("[A-Z]{1}")){
//                        return ;
//                    }
//                    edits[currentEditText].setText(letterAndDigit[primaryCode]);
//                    currentEditText++;
//                    if (currentEditText > edits.length-1) {
//                        currentEditText = edits.length-1;
//                    }
//                }
//            }
            if (currentEditText == 0){
                if (primaryCode == 113){

                    edits[currentEditText].setText("");
                }else{
                    if (edits[currentEditText].getText().toString().equals("")){
                        edits[currentEditText].setText(provinceShort[primaryCode]);
                        currentEditText++;
                        keyboardView.setKeyboard(k2);
                    }else {

                    }
                }
            }else if (currentEditText == 1 ){
                if (primaryCode == 112){
                    if ( !edits[currentEditText].getText().toString().equals("")){
                        edits[currentEditText].setText("");
                        currentEditText--;
                        keyboardView.setKeyboard(k1);
                    }else {
                        edits[currentEditText-1].setText("");
                        currentEditText--;
                        keyboardView.setKeyboard(k1);
                    }
                }else if(primaryCode == 66){
                    Toast.makeText(ctx,"请输入完整车牌号",Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    if (edits[currentEditText].getText().toString().equals("")){
                        edits[currentEditText].setText(letterAndDigit[primaryCode]);
                        currentEditText++;
                    }else {
                        edits[currentEditText+1].setText(letterAndDigit[primaryCode]);
                        currentEditText++;
                    }
                }
            }else if (currentEditText>1 && currentEditText<edits.length-1){
                if (primaryCode == 112){
                    if ( !edits[currentEditText].getText().toString().equals("")){
                        edits[currentEditText].setText("");
                        currentEditText--;
                    }else {
                        edits[currentEditText-1].setText("");
                        currentEditText--;
                    }

                }else if(primaryCode == 66){
                    Toast.makeText(ctx,"请输入完整车牌号",Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    if (edits[currentEditText].getText().toString().equals("")){
                        edits[currentEditText].setText(letterAndDigit[primaryCode]);
                        currentEditText++;
                    }else {
                        edits[currentEditText+1].setText(letterAndDigit[primaryCode]);
                        currentEditText++;
                    }
                }
            }else if (currentEditText == edits.length-1){
                if (primaryCode == 112){
                    if ( !edits[currentEditText].getText().toString().equals("")){
                        edits[currentEditText].setText("");
                        currentEditText--;
                    }else {
                        edits[currentEditText-1].setText("");
                        currentEditText--;
                    }
                }else if (primaryCode == 66){
                    Intent intent = new Intent();
                    String license = "";
                    for (int i=0;i<edits.length;i++){
                        license += edits[i].getText().toString();
                    }
                    intent.putExtra(INPUT_LICENSE_KEY, license);
                    intent.setAction(INPUT_LICENSE_COMPLETE);
                    ctx.sendBroadcast(intent);
                }else {
                    if (edits[currentEditText].getText().toString().equals("")){
                        edits[currentEditText].setText(letterAndDigit[primaryCode]);
                        currentEditText++;
                    }else {
                    }
                    currentEditText = edits.length-1;
                }
            }
        }
    };

    /**
     * 显示键盘
     */
    public void showKeyboard() {
        currentEditText = 0;
        keyboardView.setKeyboard(k1);
        int visibility = keyboardView.getVisibility();
        if (visibility == View.GONE || visibility == View.INVISIBLE) {
            keyboardView.setVisibility(View.VISIBLE);
        }
    }

    /**
     * 隐藏键盘
     */
    public void hideKeyboard() {
       for (int i=0; i<=edits.length-1; i++){
           edits[i].setText("");
       }
        int visibility = keyboardView.getVisibility();
        if (visibility == View.VISIBLE) {
            keyboardView.setVisibility(View.INVISIBLE);
        }
    }
}