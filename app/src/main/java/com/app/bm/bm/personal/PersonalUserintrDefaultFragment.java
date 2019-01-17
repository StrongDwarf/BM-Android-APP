package com.app.bm.bm.personal;

import android.provider.Contacts;
import android.provider.ContactsContract;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.telephony.PhoneNumberFormattingTextWatcher;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;


import com.app.bm.bm.R;
import com.app.bm.bm.common.apiUrls.ApiUrl;
import com.app.bm.bm.common.datas.AjaxReceiveData;
import com.app.bm.bm.common.extend.VerificationCodeInput;
import com.app.bm.bm.common.tools.Ajax;

import java.util.Timer;
import java.util.TimerTask;

public class PersonalUserintrDefaultFragment extends Fragment {
    private ImageView userPic;              //头像图片
    private LinearLayout userPicRight;      //头像图片右边的元素
    private Button btnLoginOrVeri;          //底部"登录/注册"按钮

    private PopupWindow popupWindow;        //弹出窗口

    private String PhoneNumber = "";         //电话号码

    private TextView tvDefault;         //登录弹窗中的文字
    private TextView tvPwd;             //登录弹窗中的密码登录
    private TextView tvUserTxt;         //登录弹窗中的用户协议

    private int phoneStatus = 0;            //用户输入的手机号的状态，4:手机号未注册  5:手机号已注册且已设置密码  6:手机号已注册未设置登录密码

    private int time = 60;          //验证码限制发送的事件,

    private VerificationCodeInput verificationCodeInput;        //验证码输入框

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstancestate){
        View rootView = inflater.inflate(R.layout.personal_seg_userlntr_default,null);

        //获取页面中的指定元素
        userPic = (ImageView) rootView.findViewById(R.id.user_pic);
        userPicRight = (LinearLayout) rootView.findViewById(R.id.user_pic_right);
        btnLoginOrVeri = (Button) rootView.findViewById(R.id.btn_login_or_veri);

        //给页面中的元素添加点击事件监听
        userPic.setOnClickListener(onClickListener);
        userPicRight.setOnClickListener(onClickListener);
        btnLoginOrVeri.setOnClickListener(onClickListener);

        return rootView;
    }

    public View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            //处理点击事件
            switch (v.getId()){
                case R.id.user_pic:
                    Log.i("xiaobaicai","点击了头像图片");
                    break;
                case R.id.user_pic_right:
                    Log.i("xiaobaicai","点击了头像左边");
                    break;
                case R.id.btn_login_or_veri:
                    Log.i("xiaobaicai","点击了登录按钮");
                    showLoginPopupWindow(v);// 显示PopupWindow
                    break;
            }
        }
    };

    private void showLoginPopupWindow(View v) {

        LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
        View view = layoutInflater.inflate(R.layout.loginandveri_pw_login, null);

        popupWindow = new PopupWindow(view,WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT, true);
        // 如果不设置PopupWindow的背景，无论是点击外部区域还是Back键都无法dismiss弹框
        popupWindow.setBackgroundDrawable(getResources().getDrawable(R.drawable.popupwindow_center_background));
        popupWindow.setOutsideTouchable(true);
        popupWindow.setAnimationStyle(R.style.popupwindow_center_anim_style);

        tvDefault = view.findViewById(R.id.tv_default);
        tvPwd = view.findViewById(R.id.tv_pwd_login);
        tvUserTxt = view.findViewById(R.id.tv_user_txt);

        final EditText etPhone = (EditText) view.findViewById(R.id.et_phone);
        final Button btnNextStep = (Button) view.findViewById(R.id.btn_next_step);
        btnNextStep.setBackgroundColor(getResources().getColor(R.color.button_primary_disable));

        etPhone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                //Log.i("xiaobaicai","改变内容之前:"+s);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //Log.i("xiaobaicai","内容改变时:"+s);
                tvDefault.setText("");
                tvPwd.setText("密码登录");
                tvUserTxt.setText("");
                PhoneNumber = "";
                if(s.length() == 11){
                    btnNextStep.setBackgroundColor(getResources().getColor(R.color.button_primary_basic));
                    PhoneNumber = s.toString();
                    new GetLoginStatusThread().start();

                }else{
                    btnNextStep.setBackgroundColor(getResources().getColor(R.color.button_primary_disable));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                //Log.i("xiaobaicai","内容改变之后:"+s);

            }

        });

        //为popupWindow中的元素添加事件监听
        View.OnClickListener onClickListener1 = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch(v.getId()){
                    case R.id.btn_close:
                        //点击了关闭按钮
                        popupWindow.dismiss();
                        break;
                    case R.id.iv_input_clear:
                        //点击了清除文字按钮
                        etPhone.setText("");
                        break;
                    case R.id.btn_next_step:
                        //点击了下一步按钮
                        Log.i("xiaobaicai","点击了下一步");
                        //判断此时手机号是不是11位,如果是11位,且用户手机号状态为未注册
                        if(PhoneNumber.length() == 11){
                            if(phoneStatus == 4){
                                Log.i("xiaobaicai","phoneNumber"+PhoneNumber);
                                //开启新线程发送验证码
                                new GetRegisterCodeThread(PhoneNumber+"",v,popupWindow).start();
                                //popupWindow.dismiss();
                                //showVeriPopupWindow(v);
                            }
                        }
                        break;
                    case R.id.tv_pwd_login:
                        //点击了密码登录按钮
                        Log.i("xiaobaicai","点击了密码登录");
                        break;
                    case R.id.tv_user_txt:
                        Log.i("xiaobaicai","点击了用户协议");
                        break;
                }
            }
        };


        view.findViewById(R.id.btn_close).setOnClickListener(onClickListener1);
        view.findViewById(R.id.iv_input_clear).setOnClickListener(onClickListener1);
        view.findViewById(R.id.btn_next_step).setOnClickListener(onClickListener1);
        view.findViewById(R.id.tv_pwd_login).setOnClickListener(onClickListener1);
        view.findViewById(R.id.tv_user_txt).setOnClickListener(onClickListener1);

        // PopupWindow弹出位置
        popupWindow.showAtLocation(v, Gravity.CENTER, 0, 0);

        backgroundAlpha(0.5f);
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {

            @Override
            public void onDismiss() {
                backgroundAlpha(1f);
            }
        });
    }

    private void showVeriPopupWindow(View v){
        LayoutInflater layoutInflaterVeri = LayoutInflater.from(getActivity());
        final View viewVeri = layoutInflaterVeri.inflate(R.layout.loginandveri_pw_veri, null);


        popupWindow = new PopupWindow(viewVeri,WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT, true);
        // 如果不设置PopupWindow的背景，无论是点击外部区域还是Back键都无法dismiss弹框
        popupWindow.setBackgroundDrawable(getResources().getDrawable(R.drawable.popupwindow_center_background));
        popupWindow.setOutsideTouchable(true);
        popupWindow.setAnimationStyle(R.style.popupwindow_center_anim_style);

        //初始化弹出中的文字

        TextView tvPhone = viewVeri.findViewById(R.id.tv_phone);
        tvPhone.setText(PhoneNumber+"");
        final TextView tvTime = viewVeri.findViewById(R.id.tv_time);
        time = 60;
        tvTime.setText(time+"s");
        tvTime.setBackgroundResource(R.drawable.elem_disable_small);
        tvTime.setTextColor(getResources().getColor(R.color.small_disable));

        //设置一个定时器,
        final Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        time--;
                        if(time <= 0){
                            tvTime.setText("再次获取");
                            tvTime.setBackgroundResource(R.drawable.elem_basic_small);
                            tvTime.setTextColor(getResources().getColor(R.color.small_basic));
                            timer.cancel();
                        }else{
                            tvTime.setText(time+"s");
                        }
                    }
                });
            }
        },0,1000);

        //获取验证码输入框
        verificationCodeInput = viewVeri.findViewById(R.id.verificationCodeInput);
        //添加输入验证码完成后的事件
        verificationCodeInput.setOnCompleteListener(new VerificationCodeInput.Listener() {
            @Override
            public void onComplete(String content) {
                makeToast("验证码输入完成,验证码:"+content);

            }
        });

        View.OnClickListener onClickListener2 = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch(v.getId()){
                    case R.id.btn_back:
                        //点击了返回
                        Log.i("xiaobaicai","点击了返回按钮");
                        popupWindow.dismiss();
                        showLoginPopupWindow(v);
                        break;
                    case R.id.btn_veri_close:
                        //点击了关闭按钮
                        popupWindow.dismiss();
                        break;
                    case R.id.tv_get_voice_veri:
                        //点击了接收语音验证
                        Log.i("xiaobaicai","点击了语音验证");
                        break;
                    case R.id.tv_time:
                        Log.i("xiaobaicai","点击了再次获取");
                        //判断此时时间是否小于0，小于0,再次获取,大于0,点击无效
                        if(time <= 0){
                            //再次获取
                            new GetRegisterCodeThread(PhoneNumber+"",v,popupWindow).start();
                        }
                        break;
                }
            }
        };
        viewVeri.findViewById(R.id.btn_back).setOnClickListener(onClickListener2);
        viewVeri.findViewById(R.id.btn_veri_close).setOnClickListener(onClickListener2);
        viewVeri.findViewById(R.id.tv_get_voice_veri).setOnClickListener(onClickListener2);
        viewVeri.findViewById(R.id.tv_time).setOnClickListener(onClickListener2);
        // PopupWindow弹出位置
        popupWindow.showAtLocation(v, Gravity.CENTER, 0, 0);

        backgroundAlpha(0.5f);
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {

            @Override
            public void onDismiss() {
                backgroundAlpha(1f);
            }
        });
    }

    // 设置屏幕透明度
    public void backgroundAlpha(float bgAlpha) {
        WindowManager.LayoutParams lp = getActivity().getWindow().getAttributes();
        lp.alpha = bgAlpha; // 0.0~1.0
        getActivity().getWindow().setAttributes(lp);
    }

    //内部类,封装登录状态数据
    private class loginStatusbeforeData{
        private String phone;

        public loginStatusbeforeData(){}
        public loginStatusbeforeData(String phone){
            this.phone = phone;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }
    }

    //内部类,获取验证码数据
    private class registerCodeBeforeData{
        private String phone;

        public registerCodeBeforeData(String phone){
            this.phone = phone;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }
    }

    //内部类,忘记密码数据
    private class forgetPwdData{
        private String phone;
        private String code;
        private String pwd;

        public forgetPwdData(String phone,String code,String pwd){
            this.phone = phone;
            this.code = code;
            this.pwd = pwd;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public void setPwd(String pwd) {
            this.pwd = pwd;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getPhone() {
            return phone;
        }

        public String getPwd() {
            return pwd;
        }

        public String getCode() {
            return code;
        }
    }

    //内部类,获取后台手机号状态数据线程
    private class GetLoginStatusThread extends Thread{
        @Override
        public void run(){
            Log.i("xiaobaicai","PhoneNumber"+PhoneNumber+"");
            Object loginSData = Ajax.get(new loginStatusbeforeData(PhoneNumber+""),ApiUrl.getLoginStatus());
            if(loginSData != null){
                phoneStatus = ((AjaxReceiveData) loginSData).getCode();
                Log.i("xiaobaicai",phoneStatus+"");
                //文字
                if(phoneStatus == 4){
                    //用户未注册
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if(tvDefault != null){
                                tvDefault.setText("该手机号未注册,登录即代表你同意");
                            }
                            if(tvPwd != null){
                                tvPwd.setText("");
                                tvUserTxt.setText("用户协议");
                            }
                        }
                    });
                }else if(phoneStatus == 5){
                    //手机号已注册,且已设置密码
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            makeToast("手机号已注册,且已设置密码");

                        }
                    });

                }else if(phoneStatus == 6){
                    //手机号已注册,未设置密码
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            makeToast("手机号已注册,且未设置密码");
                        }
                    });
                }

            }
        }
    }

    //内部类,忘记密码线程
    private class forgetPwdThread extends  Thread{
        @Override
        public void run(){
            Object forgetPwdData = Ajax.post(new forgetPwdData("18861855098","1234","234546"),ApiUrl.getLoginForgetPwd());
            if(forgetPwdData != null){
                Log.i("xiaobaicai",((AjaxReceiveData) forgetPwdData).getCode()+"");
            }
        }
    }

    //内部类,获取验证码数据线程
    private class GetRegisterCodeThread extends Thread{
        private View v;
        private int code;
        private PopupWindow popupWindow;
        private String PhoneNumber;

        public GetRegisterCodeThread(String PhoneNumber,View v,PopupWindow popupWindow){
            this.v = v;
            this.popupWindow = popupWindow;
            this.PhoneNumber = PhoneNumber;
        }

        @Override
        public void run(){
            Object registerCodeData = Ajax.post(new registerCodeBeforeData(PhoneNumber + ""),ApiUrl.getRegisterCode());
            if(registerCodeData != null){
                code = ((AjaxReceiveData) registerCodeData).getCode();
                switch(code){
                    case 0:
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                popupWindow.dismiss();
                                showVeriPopupWindow(v);
                                makeToast("成功");
                            }
                        });
                        //登录成功,刷新
                        break;
                    case 3:
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                makeToast("随机码输入有误");
                            }
                        });
                        break;
                    case 33:
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                makeToast("请输入验证码");
                            }
                        });
                        break;
                    case 700:
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                makeToast("你已经是会员了");
                            }
                        });
                        break;
                    case 999:
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                makeToast("短信验证码发送失败,请输入正确的手机号格式");
                            }
                        });

                        break;
                    case 995:
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                makeToast("一分钟最多发一条短信验证码");
                            }
                        });
                        break;
                    case 996:
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                makeToast("一小时最多发送五条验证码");
                            }
                        });
                        break;
                    case 997:
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                makeToast("一天最多发送10条验证码");
                            }
                        });
                        break;
                }
            }
        }
    }

    //make toast
    private void makeToast(String toast){
        Toast.makeText(getActivity(),toast,Toast.LENGTH_LONG).show();
    }
}