/*
package com.qmwl.zyjx.activity;

import android.Manifest;
import android.app.Dialog;
import android.content.pm.PackageManager;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.BitmapDescriptor;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.qmwl.zyjx.R;
import com.qmwl.zyjx.base.BaseActivity;
import com.qmwl.zyjx.bean.MapBean;
import com.qmwl.zyjx.utils.GlideUtils;
import com.qmwl.zyjx.utils.JsonUtils;
import com.qmwl.zyjx.view.CommomDialog;

import java.util.ArrayList;
import java.util.List;

*/
/**
 * Created by Administrator on 2017/8/1.
 * 地图
 *//*


public class MapActivity extends BaseActivity implements AMap.OnMarkerClickListener {
    private MapView mapView;
    private AMap aMap;
    private View rootContainer;
    public static final String DATA = "com.gh.map.data";
    private double latitude;
    private double longitude;

    private MapBean bean = null;
    private static final int WRITE_COARSE_LOCATION_REQUEST_CODE = 2222;

    @Override
    protected void setLayout() {

    }

    @Override
    protected void setLayoutBundle(Bundle savedInstanceState) {
        setContentLayout(R.layout.map_activity_layout);

        rootContainer = findViewById(R.id.map_layout_container);
        mapView = (MapView) findViewById(R.id.map_activity_map);
        mapView.onCreate(savedInstanceState);// 此方法必须重写
        if (mapView != null) {
            aMap = mapView.getMap();
        }
        aMap.setOnMarkerClickListener(this);// 设置点击marker事件监听器
//        getMark(40.081528, 116.278506);
        getData();
    }


    private void getData() {
        String stringExtra = getIntent().getStringExtra(DATA);
        if ("".equals(stringExtra) || stringExtra == null) {
            Toast.makeText(this, getString(R.string.xinxibuwanzheng), Toast.LENGTH_SHORT).show();
            finish();
            return;
        }
        List<MapBean> mapDatas = JsonUtils.parseMapData(stringExtra);
        for (MapBean bean : mapDatas) {
            this.bean = bean;
        }
        if (bean != null) {
            getMark(bean.getLat_x(), bean.getLay_y());
        }

    }

    private MarkerOptions getMark(double j, double w) {
        LatLng l = new LatLng(j, w);
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(l);
        BitmapDescriptor bitmapDescriptor = BitmapDescriptorFactory.fromResource(R.mipmap.fangzi);
        markerOptions.icon(bitmapDescriptor);
        aMap.addMarker(markerOptions);
        aMap.moveCamera(CameraUpdateFactory.newLatLng(l));
        float maxZoomLevel = aMap.getMaxZoomLevel();
        aMap.moveCamera(CameraUpdateFactory.zoomTo(maxZoomLevel - 5));
        return markerOptions;
    }


    private void addMarks(ArrayList<MarkerOptions> markList) {

    }


    @Override
    protected void initView() {
        setTitleContent(R.string.map);
        //这里以ACCESS_COARSE_LOCATION为例
        jiancequanxian();

    }
//SDK在Android 6.0下需要进行运行检测的权限如下：
//    Manifest.permission.ACCESS_COARSE_LOCATION,
//    Manifest.permission.ACCESS_FINE_LOCATION,
//    Manifest.permission.WRITE_EXTERNAL_STORAGE,
//    Manifest.permission.READ_EXTERNAL_STORAGE,
//    Manifest.permission.READ_PHONE_STATE

    private void jiancequanxian() {
        if (isPermission()) {
            //申请WRITE_EXTERNAL_STORAGE权限
            openpermission();
        } else {
            dingwei();
        }

    }

    //开权限
    private void openpermission() {
        ActivityCompat.requestPermissions(this, new String[]{
                        Manifest.permission.ACCESS_COARSE_LOCATION,
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.READ_PHONE_STATE},
                WRITE_COARSE_LOCATION_REQUEST_CODE);//自定义的code
    }

    private boolean isPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE)
                        != PackageManager.PERMISSION_GRANTED
                ) {
            return false;
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        //可在此继续其他操作。
        if (requestCode == WRITE_COARSE_LOCATION_REQUEST_CODE) {
            if (isPermission()) {
                dingwei();
            } else {
                new CommomDialog(this, R.style.dialog, getString(R.string.weihuoqudaoquanxian), new CommomDialog.OnCloseListener() {
                    @Override
                    public void onClick(Dialog dialog, boolean confirm) {
                        if (confirm) {
                            openpermission();
                        }
                        dialog.dismiss();
                    }
                }).show();
            }

        }
    }

    @Override
    protected void onListener() {

    }

    @Override
    protected void getInterNetData() {

    }

    @Override
    protected void onMyClick(View v) {
        switch (v.getId()) {
            case R.id.map_popu_iv:
                if (latitude == 0) {
                    return;
                }

                break;
            case R.id.map_popu_quzheli:
//
//                if (bean == null || latitude == 0 || longitude == 0) {
//                    return;
//                }
//                Intent intent = new Intent(MapActivity.this, DaoHangActivity.class);
//                String[] jingweidus = new String[]{String.valueOf(latitude), String.valueOf(longitude), String.valueOf(bean.getLat_x()), String.valueOf(bean.getLay_y())};
//                intent.putExtra("jingweidu", jingweidus);
//                startActivity(intent);
                break;
        }
    }

    private void showPopuView() {
        View popuView = getLayoutInflater().inflate(R.layout.map_popuwindow_layout, null);

        PopupWindow popupWindow = new PopupWindow(popuView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        popupWindow.setContentView(popuView);
        initPopuView(popuView);
        popupWindow.setOutsideTouchable(true);
        View barview = findViewById(R.id.map_bar_view);
        popupWindow.setBackgroundDrawable(new ColorDrawable());
//        popupWindow.showAsDropDown(barview);
        popupWindow.showAtLocation(rootContainer, Gravity.BOTTOM, 0, 0);
    }

    private void initPopuView(View popuView) {
        TextView popuName = (TextView) popuView.findViewById(R.id.map_popu_name);
        TextView address = (TextView) popuView.findViewById(R.id.map_popu_address);
        TextView tell = (TextView) popuView.findViewById(R.id.map_popu_tell);
        ImageView iv = (ImageView) popuView.findViewById(R.id.map_popu_iv);
        popuView.findViewById(R.id.map_popu_quzheli).setOnClickListener(this);
        if (bean == null) {
            return;
        }
        popuName.setText(bean.getTitle());
        address.setText(bean.getAddress());
        tell.setText(bean.getPhone());
        iv.setOnClickListener(this);
//        Glide.with(this).load(bean.getLogo()).into(iv);
        GlideUtils.openImage(this, bean.getLogo(), iv);
    }

    */
/**
     * 方法必须重写
     *//*

    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
    }

    */
/**
     * 方法必须重写
     *//*

    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();
    }

    */
/**
     * 方法必须重写
     *//*

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    */
/**
     * 方法必须重写
     *//*

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        showPopuView();
        return false;
    }

    //声明AMapLocationClient类对象
    public AMapLocationClient mLocationClient = null;
    //声明定位回调监听器
    public AMapLocationListener mLocationListener = new AMapLocationListener() {
        @Override
        public void onLocationChanged(AMapLocation aMapLocation) {
            if (aMapLocation != null) {
                if (aMapLocation.getErrorCode() == 0) {
                    //可在其中解析amapLocation获取相应内容。

                    latitude = aMapLocation.getLatitude();//获取纬度
                    longitude = aMapLocation.getLongitude();//获取经度
                    String address = aMapLocation.getAddress();
//                    Toast.makeText(MapActivity.this, "定位成功:" + address, Toast.LENGTH_SHORT).show();
                } else {
                    //定位失败时，可通过ErrCode（错误码）信息来确定失败的原因，errInfo是错误信息，详见错误码表。
//                    Toast.makeText(MapActivity.this, "定位失败:" + aMapLocation.getErrorCode(), Toast.LENGTH_SHORT).show();
                }
            }
        }
    };
    //声明AMapLocationClientOption对象
    public AMapLocationClientOption mLocationOption = null;

    private void dingwei() {
        //初始化定位
        mLocationClient = new AMapLocationClient(this);
        //设置定位回调监听
        mLocationClient.setLocationListener(mLocationListener);
        //初始化AMapLocationClientOption对象
        mLocationOption = new AMapLocationClientOption();
        //设置定位模式为AMapLocationMode.Hight_Accuracy，高精度模式。
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        //获取一次定位结果：
        //该方法默认为false。
        mLocationOption.setOnceLocation(true);
        //设置是否允许模拟位置,默认为true，允许模拟位置
        mLocationOption.setMockEnable(true);
        //给定位客户端对象设置定位参数
        mLocationClient.setLocationOption(mLocationOption);
        //启动定位
        mLocationClient.startLocation();
    }

    private void startDaoHang() {
        //获取AMapNavi实例
    }
}
*/
