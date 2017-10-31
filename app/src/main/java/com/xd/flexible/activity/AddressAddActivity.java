package com.xd.flexible.activity;

import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.bigkoo.pickerview.OptionsPickerView;
import com.xd.flexible.R;
import com.xd.flexible.application.ToolBarActivity;
import com.xd.flexible.model.event.RefreshAddrEvent;
import com.xd.flexible.network.NoHttpListener;
import com.xd.flexible.network.NoHttpUtils;
import com.xd.flexible.utils.PickUtils;
import com.xd.flexible.utils.KeyboardUtils;
import com.xd.flexible.utils.NetworkUtils;
import com.xd.flexible.utils.RegularUtils;
import com.xd.flexible.utils.ToastUtil;
import com.yolanda.nohttp.RequestMethod;
import com.yolanda.nohttp.rest.Response;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Flexible on 2017/1/10 0010.
 */

public class AddressAddActivity extends ToolBarActivity {
    @BindView(R.id.tv_save)
    TextView tvSave;
    @BindView(R.id.et_person)
    TextInputEditText etPerson;
    @BindView(R.id.et_phone)
    TextInputEditText etPhone;
    @BindView(R.id.tv_addr_area)
    TextView tvAddrArea;
    @BindView(R.id.tv_addr_city)
    TextView tvAddrCity;
    @BindView(R.id.tv_addr_province)
    TextView tvAddrProvince;
    @BindView(R.id.rl_area)
    RelativeLayout rlArea;
    @BindView(R.id.et_address)
    TextInputEditText etAddress;
    private OptionsPickerView pvCity;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_address);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        intiPickerView();
    }


    /**
     * 初始化pickerView
     */
    private void intiPickerView() {

//        pvCity = PickUtils.intiPickerView(this);
//        pvCity.setOnoptionsSelectListener(new OptionsPickerView.OnOptionsSelectListener() {
//            @Override
//            public void onOptionsSelect(int options1, int option2, int options3) {
//                //返回的分别是三个级别的选中位置
//                String province = PickUtils.options1Items.get(options1).getName();
//                String city = PickUtils.options2Items.get(options1).get(option2);
//                String district = PickUtils.options3Items.get(options1).get(option2).get(options3);
//                tvAddrProvince.setText(province);
//                tvAddrCity.setText(city);
//                tvAddrArea.setText(district);
//            }
//        });

    }


    @OnClick({R.id.tv_save, R.id.rl_area})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_save:
                String phone = etPhone.getText().toString();
                String person = etPerson.getText().toString();
                String addr = etAddress.getText().toString();
                if (!RegularUtils.isMobileSimple(phone)) {
                    ToastUtil.showToast(AddressAddActivity.this, "请输入正确手机号");
                    return;
                }
                if (TextUtils.isEmpty(person)) {
                    ToastUtil.showToast(AddressAddActivity.this, "请输入联系人");
                    return;
                }
                if (TextUtils.isEmpty(addr)) {
                    ToastUtil.showToast(AddressAddActivity.this, "请输入详细地址");
                    return;
                }
                if (NetworkUtils.isAvailable(this)) {
                    addAddress(person, phone, addr);
                } else
                    ToastUtil.showToast(this, getString(R.string.no_net));

                break;
            case R.id.rl_area:
                View rootview = this.getWindow().getDecorView();
                int aaa = rootview.findFocus().getId();
                if (findViewById(aaa) instanceof EditText) {
                    KeyboardUtils.hideSoftInput(AddressAddActivity.this);
                }
                //点击弹出选项选择器
                pvCity.show();
                break;
        }
    }

    private void addAddress(String person, String phone, String addr) {
        Map<String, String> param = new HashMap<>();
        param.put("province", tvAddrProvince.getText().toString());
        param.put("city", tvAddrCity.getText().toString());
        param.put("district", tvAddrArea.getText().toString());
        param.put("name", person);
        param.put("phone", phone);
        param.put("is_default", "0");
        param.put("addr", addr);
        request(1, NoHttpUtils.fastJsonObjectRequest("", RequestMethod.POST, param), new NoHttpListener<JSONObject>() {
            @Override
            public void onStart(int what) {

            }

            @Override
            public void onSucceed(int what, Response<JSONObject> response) {
                JSONObject json = response.get();
                int code = json.getInteger("code");
                if (code != 1) {
                    String msg = json.getString("msg");
                    ToastUtil.showToast(AddressAddActivity.this, msg);
                } else {
                    ToastUtil.showToast(AddressAddActivity.this, "添加成功");
                    EventBus.getDefault().post(new RefreshAddrEvent());
                    finish();
                }

            }

            @Override
            public void onFailed(int what, Response<JSONObject> response) {
            }

            @Override
            public void onFinish(int what) {

            }
        });
    }

}
