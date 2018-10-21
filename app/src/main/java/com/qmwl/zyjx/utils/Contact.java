package com.qmwl.zyjx.utils;

/**
 * Created by Administrator on 2017/8/9.
 */

public class Contact {

    public static final String TUISONG_ALISA = "ALIAS_TYPE.ZYJX";
    //环信发消息的头像关键字
    public static final String HX_USER_IMAGE = "avatarURLPath";
    //环信发消息的id
    public static final String HX_USER_ID = "conversationID";
    //环信发消息的昵称
    public static final String HX_USER_NICKNAME = "nickname";

    public static final String wxAppid = "wx2355549c49f5cf73";

    //拍卖团购的域名
    public static String paimaiOrTuangou = "http://tgpm.zhongyaojixie.com";
    //域名
//    public static String http_yuming = "http://app.zhongyaojixie.com";
    public static String http_yuming = "http://app.qmnet.com.cn";
    //中文接口
    public static final String zh_http = http_yuming;
    //英文接口
    public static final String en_http = http_yuming.concat("/english");
    //接口转换(预防以后变动的)
    public static String httpaddress = zh_http;
    //公司注册接口
    public static String register = httpaddress.concat("/index.php/api/register");
    //验证码
    public static String register_getcode = httpaddress.concat("/index.php/api/register/send_sms");
    //验证验证码
    public static String register_yanzhengcode = httpaddress.concat("/index.php/api/register/check_code");
    //登录接口
    public static String login = httpaddress.concat("/index.php/api/login");
    //首页广告
    public static String main_guanggao = httpaddress.concat("/index.php/api/index/indexAdv");
    //首页热门帖子
    public static String main_remen_tiezi = httpaddress.concat("/index.php/api/index/postShow");
    //首页热门新闻
    public static String main_remen_news = httpaddress.concat("/index.php/api/index/shopArc");
    //首页商品分类
    public static String main_shoopings = httpaddress.concat("/index.php/api/index/category");
    //二级商品列表页
    public static String second_shops = httpaddress.concat("/index.php/api/category");
    //三级商品列表页
    public static String thread_shops = httpaddress.concat("/index.php/api/goods");
    //新闻列表页
    public static String news_list = httpaddress.concat("/index.php/api/article/articleList?id=27&{page}");
    //消息列表页
    public static String message_list = httpaddress.concat("/api/Message/mesNotice");
    //帖子列表
    public static String tiezi_list = httpaddress.concat("/index.php/api/post/index");
    //发帖
    public static String tiezi_scend = httpaddress.concat("/index.php/api/post/postAdd");
    //搜索标签
    public static String search_flag = httpaddress.concat("/index.php/api/index/searchIndex");
    //模糊搜索
    public static String search_search = httpaddress.concat("/index.php/api/index/search");
    //商家中心
    public static String shangjiazhongxin = httpaddress.concat("/index.php/api/index/advUrl");
    //商家商品列表
    public static String shangjiazhongxin_shops = httpaddress.concat("/index.php/api/index/goods_list");
    //搜索帖子
    public static String search_tiezi = httpaddress.concat("/index.php/api/post/postSearch");
    //发帖列表
    public static String fatie_list = httpaddress.concat("/index.php/api/member/postList");
    //回帖子
    public static String huitie = httpaddress.concat("/index.php/api/post/comment");
    //收货地址列表
    public static String address_list = httpaddress.concat("/index.php/api/member/memberAddress");
    //添加收货地址
    public static String address_add = httpaddress.concat("/index.php/api/member/addMemberAddress");
    //修改收货地址
    public static String address_update = httpaddress.concat("/index.php/api/member/updateMemberAddress");
    //修改默认地址
    public static String address_update_default = httpaddress.concat("/index.php/api/cart/updateAddressDefault");
    //删除收货地址
    public static String address_delete = httpaddress.concat("/index.php/api/member/memberAddressDelete");
    //获取默认收货地址
    public static String address_default_get = httpaddress.concat("/index.php/api/cart/addressDefault");
    //我的订单
    public static String wodedingdan_list = httpaddress.concat("/index.php/api/order/myOrderList");
    //商品提交订单
    public static String tijiaodingdan = httpaddress.concat("/index.php/api/order/orderCreate");
    //购物车提交订单
    public static String gouwuchetijiaodingdan = httpaddress.concat("/index.php/api/cart/orderCreate");
    //修改昵称
    public static String xiugainicheng = httpaddress.concat("/index.php/api/member/modifyNickName");
    //修改性别
    public static String xiugaixingbie = httpaddress.concat("/index.php/api/member/modifySex");
    //修改出生日期
    public static String xiugaichushengriqi = httpaddress.concat("/index.php/api/member/modifyBirthday");
    //修改密码
    public static String xiugaimima = httpaddress.concat("/index.php/api/member/modifyPassword");
    //上传头像
    public static String shangchuantouxiang = httpaddress.concat("/index.php/api/member/modifyAvatar");
    //获取用户信息
    public static String user_info = httpaddress.concat("/index.php/api/member/personaldata");
    //获取购物车列表
    public static String getgouwuche = httpaddress.concat("/index.php/api/cart/getShoppingCart?uid=");
    //添加购物车
    public static String addGouwuChe = httpaddress.concat("/index.php/api/cart/addCart");
    //更新购物车数量
    public static String updataGouWuCheNum = httpaddress.concat("/index.php/api/cart/updateCartGoodsNumber");
    //删除购物车数量
    public static String deleteGouWuCheNum = httpaddress.concat("/index.php/api/cart/cartDelete");
    //获取手机号or修改手机号
    public static String getShouJiHao = httpaddress.concat("/index.php/api/member/modifyMobile");
    //发送修改手机号的验证码
    public static String scend_shoijihao_code = httpaddress.concat("/index.php/api/Member/send_sms");
    //获取省列表
    public static String getsheng = httpaddress.concat("/index.php/api/cart/getProvince");
    //获取市接口
    public static String getshi = httpaddress.concat("/index.php/api/cart/getCity");
    //获取我的收藏列表
    public static String wodeshoucang = httpaddress.concat("/api/member/goodsCollectionList");
    //添加我的收藏列表
    public static String tianjiashoucang = httpaddress.concat("/index.php/api/goods/collectionGoodsOrShop");
    //我的浏览记录列表
    public static String wodeliulanjilu = httpaddress.concat("/index.php/api/member/histrorys");
    //获取品牌筛选列表
    public static String getpinpai_list = httpaddress.concat("/index.php/api/goods/screenBrand");
    //获取筛选列表
    public static String getshaixuan_list = httpaddress.concat("/index.php/api/goods/screen_attr");
    //黑名单
    public static String blackList_list = httpaddress.concat("/index.php/api/post/black_name");
    //物流
    public static String wuliu_list = httpaddress.concat("/index.php/api/Yunshu");
    //我的评价列表
    public static String wodepingjia_list = httpaddress.concat("/index.php/api/member/myEvaluate");


    //发表评价
    public static String fabiaopingjia = httpaddress.concat("/index.php/api/member/addGoodsEvaluate");



    //意见反馈
    public static String yijianfankui = httpaddress.concat("/index.php/api/member/Feedback");
    //商品订单支付宝请求参数
    public static String zhifubaoqingqiu = httpaddress.concat("/index.php/api/pay");
    //商品订单微信请求参数
    public static String weixinqingqiu = httpaddress.concat("/index.php/api/wxpay/actionPays");


    //购物车微信支付请求
    public static String weixin_car_qingqiu = httpaddress.concat("/index.php/api/Cartpay/actionPays");
    //购物车支付宝支付请求
    public static String zhifubao_car_qingqiu = httpaddress.concat("/index.php/api/cart/pay");
    //租赁订单支付宝请求参数
    public static String zulin_zhifubaoqingqiu = httpaddress.concat("/index.php/api/pay/ZulinPay");


    //租赁订单微信请求参数
    public static String zulin_weixinqingqiu = httpaddress.concat("/index.php/api/Zupay/actionPays");


    //我要发货
    public static String woyaofahuo = httpaddress.concat("/index.php/api/transport/addHyinfo");
    //评价成功
    public static String pingjiachenggong = httpaddress.concat("/index.php/api/member/goods_recommend");
    //取消订单
    public static String quxiaodingdan = httpaddress.concat("/index.php/api/order/orderDel");
    //退款
    public static String tuikuan = httpaddress.concat("/index.php/api/order/refund");
    //确认收货
    public static String querenshouhuo = httpaddress.concat("/index.php/api/order/take_over");
    //租赁协议
    public static String zulinxieyi_url = httpaddress.concat("/index.php/api/lease/xieyi");
    //租赁合同
    public static String zulinhetong_url = httpaddress.concat("/index.php/api/lease/hetong");
    //上传租赁信息
    public static String upZulinMessage = httpaddress.concat("/index.php/api/lease/lease");
    //上传签名图片
    public static String shangchuanqianming = httpaddress.concat("/index.php/api/lease/sign");
    //租赁
    public static String zulin_list = httpaddress.concat("/index.php/api/lease/mylease");
    //退款售后
    public static String tuikuanshouhou = httpaddress.concat("/index.php/api/member/refundList");
    //全部评价接口
    public static String all_pingjia = httpaddress.concat("/index.php/api/goods/goodsEvaluate");
    //货源列表接口
    public static String huoyuanliebiao_list = httpaddress.concat("/index.php/api/transport/hylist");
    //货主认证
    public static String huozhurenzheng = httpaddress.concat("/index.php/api/transport/ownerAuthen");
    //查看租赁订单
    public static String chakanzulindingdan = httpaddress.concat("/index.php/api/lease/lookImg");
    //选择车型
    public static String xuanzechexing = httpaddress.concat("/index.php/api/transport/carModels");
    //选择车长
    public static String xuanzechechang = httpaddress.concat("/index.php/api/transport/carLength");
    //忘记密码验证验证码
    public static String wangjimimayanzheng = httpaddress.concat("/index.php/api/register/checkCode");
    //忘记密码修改密码
    public static String wangjimimaxiugaimima = httpaddress.concat("/index.php/api/register/updatePassword");

    //货主认证状态
    public static String huozhurenzhengstate = httpaddress.concat("/index.php/api/transport/ownerAuthenstate");
    //承运方认证状态
    public static String chengyunfangrenzhengstate = httpaddress.concat("/index.php/api/transport/carrierAuthenstate");
    //承运人添加车辆
    public static String chengyunrentianjiacheliang = httpaddress.concat("/index.php/api/transport/carrierCaradd");
    //承运人车辆列表
    public static String chengyun_chelianglist = httpaddress.concat("/index.php/api/transport/carrierCarlist");
    //承运人车辆删除
    public static String cheliang_delete = httpaddress.concat("/index.php/api/transport/carrierCardel");
    //承运人车辆详情
    public static String cheliangxinxi = httpaddress.concat("/index.php/api/transport/carrierCarinfo");
    //修改承运人车辆信息
    public static String xiugaicheliangxinxi = httpaddress.concat("/index.php/api/transport/carrierCaredit");
    //我的运单列表
    public static String wodeyundan_huozhu = httpaddress.concat("/index.php/api/transport/hyuserlist");
    //查看报价
    public static String chakanbaojia_list = httpaddress.concat("/index.php/api/transport/offerlist");
    //选择报价
    public static String postXuanZeBaoJia = httpaddress.concat("/index.php/api/transport/offerchoice");
    //用户报价
    public static String yonghubaojia = httpaddress.concat("/index.php/api/transport/addOffer");
    //首页最新10条
    public static String yunshushouye_list = httpaddress.concat("/index.php/api/transport/newlist");

    //运输收货人地址列表(运输收货人发货人暂无编辑接口)
    public static String yunshu_address_list = httpaddress.concat("/index.php/api/transport/ownerListaddress");
    //运输收货人地址添加
    public static String yunshu_address_add = httpaddress.concat("/index.php/api/transport/ownerAddaddress");
    //运输收货人地址删除
    public static String yunshu_address_delete = httpaddress.concat("/index.php/api/transport/ownerDeladdress");
    //运输收货人地址详情
    public static String yunshu_address_details = httpaddress.concat("/index.php/api/transport/ownerinfoaddress");
    //运输收货人地址编辑
    public static String yunshu_address_edit = httpaddress.concat("/index.php/api/transport/ownerEditaddress");

    //运输发货人地址列表
    public static String yunshu_fahuo_address_list = httpaddress.concat("/index.php/api/transport/consignerListaddress");
    //运输发货人地址添加
    public static String yunshu_fahuo_address_add = httpaddress.concat("/index.php/api/transport/consignerAddaddress");
    //运输发货人地址删除
    public static String yunshu_fahuo_address_delete = httpaddress.concat("/index.php/api/transport/consignerDeladdress");
    //运输发货人地址详情
    public static String yunshu_fahuo_address_details = httpaddress.concat("/index.php/api/transport/consignerinfoaddress");
    //运输发货人地址编辑
    public static String yunshu_fahuo_address_edit = httpaddress.concat("/index.php/api/transport/consignerEditaddress");
    //运输首页图片地址
    public static String yunshu_main_image_url = httpaddress.concat("/index.php/api/transport/adlist");
    //运输系统推荐
    public static String yunshu_xitongtuijian = httpaddress.concat("/index.php/api/transport/flaglist");
    //运输企业认证
    public static String yunshu_chengyun_qiye_renzheng = httpaddress.concat("/index.php/api/transport/companyAuthen");
    //运输企业认证状态
    public static String yunshu_chengyun_qieye_statue = httpaddress.concat("/index.php/api/transport/companyAuthenstate");
    //承运方(我的运单)
    public static String yunshu_chengyun_chengyunfang_wodeyundan = httpaddress.concat("/index.php/api/transport/mytransportlist");
    //承运人认证提交资料
    public static String yunshu_chengyunren_renzheng = httpaddress.concat("/index.php/api/transport/carrierAuthen");
    //判断是否进行过承运人认证
    public static String yunshu_panduanchengyunren_renzheng = httpaddress.concat("/index.php/api/transport/authenstate");
    //货运状态
    public static String yunshu_yundan_caozuo = httpaddress.concat("/index.php/api/transport/cstate");
    //商家中心url(h5)
    public static String shangjiazhongxin_url = httpaddress.concat("/index.php/api/shop/center");
    //用户是否是商家
    public static String shangjiazhongxin_statue = httpaddress.concat("/index.php/api/shop/index");
    //货源详情url
    public static String huoyuanxiangqing_url = httpaddress.concat("/index.php/api/transport/transportinfo");
    //运单详情url
    public static String yundandetails_url = httpaddress.concat("/index.php/api/transport/mytransportinfo");
    //获取我要开店的状态
    public static String woyaokaidian_statue = httpaddress.concat("/index.php/api/member/Apply");
    //补充我要开店资料
    public static String woyaokaidian_chongxin_tijiao = httpaddress.concat("/index.php/api/member/againApply");
    //进入app时调用的接口
    public static String app_push = httpaddress.concat("/index.php/api/transport/push");
    //货主运单详情接口
    public static String huozhu_yundan_details = httpaddress.concat("/index.php/api/transport/hyuserinfo");
    //货主运单支付,获取订单号接口
    public static String huozhu_yundan_getorder_pay = httpaddress.concat("/index.php/api/transport/order");
    //货主运单支付,获取支付宝需要的参数
    public static String huozhu_yundan_zhifubao_pay_canshu = httpaddress.concat("/index.php/api/pay/transportPay");
    //货主运单支付,获取微信需要的参数
    public static String huozhu_yundan_weixin_pay_canshu = httpaddress.concat("/index.php/api/wxpay/transportPays");
    //对公付款信息
    public static String duigongfukuan_url = httpaddress.concat("/index.php/api/pay/businessAccount");
    //帖子数据
    public static String get_tiezi_data = httpaddress.concat("/index.php/api/post/update");
    //修改帖子的单张上传
    public static String tiezi_updata_singleimage = httpaddress.concat("/index.php/api/post/uploadPic");
    //修改帖子上传数据
    public static String tiezi_updata_updata = httpaddress.concat("/index.php/api/post/updatePost");
    //删除帖子
    public static String tiezi_delete_url = httpaddress.concat("/index.php/api/post/postDelete");
    //金融,服务，招聘培训
    public static String jinrong_fuwu_zhaopinpeixun_url = httpaddress.concat("/index.php/api/article/articleList");
    //保险
    public static String baoxian_url = httpaddress.concat("/index.php/api/Safe/safeInfo");
    //是否已经开通店铺
    public static String kaidian_url = httpaddress.concat("/index.php/api/shop/shop");
    //提交开店资料
    public static String tijiaokaidinaziliao = httpaddress.concat("/index.php/api/shop/open");
    //我要开店支付宝支付
    public static String woyaokaidianzhifubao = httpaddress.concat("/index.php/api/shop/pay");
    //我要开店微信支付
    public static String woyaokaidianweixin = httpaddress.concat("/index.php/api/shop/actionPays");
    //对公付款列表
    public static String duigongfukuanlist = httpaddress.concat("/index.php/api/shop/bank");
    //我要开店企业协议
    public static String woyaokaidian_qiyexieyi = httpaddress.concat("/index.php/api/shop/company_agree");
    //我要开店个人协议
    public static String woyaokaidian_gerenxieyi = httpaddress.concat("/index.php/api/shop/person_agree");
    //首页客服
    public static String shouyekefu = httpaddress.concat("/index.php/api/index/tel");
    //对公付款
    public static String duigongfukuan = httpaddress.concat("/index.php/api/shop/account");
    //获取示例身份证和营业执照
    public static String huoqushenfenzhengheyingyezhizhao = httpaddress.concat("/index.php/api/shop/caseImg");
    //获取省列表(商品三级筛选)
    public static String huoqushengliebiao = httpaddress.concat("/index.php/api/goods/screen_place");
    //获取城市列表(商品三级筛选)
    public static String huoquchengshiliebiao = httpaddress.concat("/index.php/api/goods/screen_city");
    //获取县列表(商品三级筛选)
    public static String huoquxianliebiao = httpaddress.concat("/index.php/api/cart/getDistrict");




    //获取取消订单的理由
    public static String get_cancel_order_reson = httpaddress.concat("/index.php/api/order/reson");

    //获取退款订单的理由
    public static String get_tuikuan_order_reson = httpaddress.concat("/index.php/api/order/tuiReson");

    //获取退货订单的理由
    public static String get_tuihuo_order_reson = httpaddress.concat("/index.php/api/order/tuiHuoReson");
















    public static void resetHttpAddress() {
        //公司注册接口
        register = httpaddress.concat("/index.php/api/register");
        //验证码
        register_getcode = httpaddress.concat("/index.php/api/register/send_sms");
        //验证验证码
        register_yanzhengcode = httpaddress.concat("/index.php/api/register/check_code");
        //登录接口
        login = httpaddress.concat("/index.php/api/login");
        //首页广告
        main_guanggao = httpaddress.concat("/index.php/api/index/indexAdv");
        //首页热门帖子
        main_remen_tiezi = httpaddress.concat("/index.php/api/index/postShow");
        //首页热门新闻
        main_remen_news = httpaddress.concat("/index.php/api/index/shopArc");
        //首页商品分类
        main_shoopings = httpaddress.concat("/index.php/api/index/category");
        //二级商品列表页
        second_shops = httpaddress.concat("/index.php/api/category");
        //三级商品列表页
        thread_shops = httpaddress.concat("/index.php/api/goods");
        //新闻列表页
        news_list = httpaddress.concat("/index.php/api/article/articleList?id=27&{page}");
        //消息列表页
        message_list = httpaddress.concat("/api/Message/mesNotice");
        //帖子列表
        tiezi_list = httpaddress.concat("/index.php/api/post/index");
        //发帖
        tiezi_scend = httpaddress.concat("/index.php/api/post/postAdd");
        //搜索标签
        search_flag = httpaddress.concat("/index.php/api/index/searchIndex");
        //模糊搜索
        search_search = httpaddress.concat("/index.php/api/index/search");
        //商家中心
        shangjiazhongxin = httpaddress.concat("/index.php/api/index/advUrl");
        //商家商品列表
        shangjiazhongxin_shops = httpaddress.concat("/index.php/api/index/goods_list");
        //搜索帖子
        search_tiezi = httpaddress.concat("/index.php/api/post/postSearch");
        //发帖列表
        fatie_list = httpaddress.concat("/index.php/api/member/postList");
        //回帖子
        huitie = httpaddress.concat("/index.php/api/post/comment");
        //收货地址列表
        address_list = httpaddress.concat("/index.php/api/member/memberAddress");
        //添加收货地址
        address_add = httpaddress.concat("/index.php/api/member/addMemberAddress");
        //修改收货地址
        address_update = httpaddress.concat("/index.php/api/member/updateMemberAddress");
        //修改默认地址
        address_update_default = httpaddress.concat("/index.php/api/cart/updateAddressDefault");
        //删除收货地址
        address_delete = httpaddress.concat("/index.php/api/member/memberAddressDelete");
        //获取默认收货地址
        address_default_get = httpaddress.concat("/index.php/api/cart/addressDefault");
        //我的订单
        wodedingdan_list = httpaddress.concat("/index.php/api/order/myOrderList");
        //商品提交订单
        tijiaodingdan = httpaddress.concat("/index.php/api/order/orderCreate");
        //购物车提交订单
        gouwuchetijiaodingdan = httpaddress.concat("/index.php/api/cart/orderCreate");
        //修改昵称
        xiugainicheng = httpaddress.concat("/index.php/api/member/modifyNickName");
        //修改性别
        xiugaixingbie = httpaddress.concat("/index.php/api/member/modifySex");
        //修改出生日期
        xiugaichushengriqi = httpaddress.concat("/index.php/api/member/modifyBirthday");
        //修改密码
        xiugaimima = httpaddress.concat("/index.php/api/member/modifyPassword");
        //上传头像
        shangchuantouxiang = httpaddress.concat("/index.php/api/member/modifyAvatar");
        //获取用户信息
        user_info = httpaddress.concat("/index.php/api/member/personaldata");
        //获取购物车列表
        getgouwuche = httpaddress.concat("/index.php/api/cart/getShoppingCart?uid=");
        //添加购物车
        addGouwuChe = httpaddress.concat("/index.php/api/cart/addCart");
        //更新购物车数量
        updataGouWuCheNum = httpaddress.concat("/index.php/api/cart/updateCartGoodsNumber");
        //删除购物车数量
        deleteGouWuCheNum = httpaddress.concat("/index.php/api/cart/cartDelete");
        //获取手机号or修改手机号
        getShouJiHao = httpaddress.concat("/index.php/api/member/modifyMobile");
        //发送修改手机号的验证码
        scend_shoijihao_code = httpaddress.concat("/index.php/api/Member/send_sms");
        //获取省列表
        getsheng = httpaddress.concat("/index.php/api/cart/getProvince");
        //获取市接口
        getshi = httpaddress.concat("/index.php/api/cart/getCity");
        //获取我的收藏列表
        wodeshoucang = httpaddress.concat("/api/member/goodsCollectionList");
        //添加我的收藏列表
        tianjiashoucang = httpaddress.concat("/index.php/api/goods/collectionGoodsOrShop");
        //我的浏览记录列表
        wodeliulanjilu = httpaddress.concat("/index.php/api/member/histrorys");
        //获取品牌筛选列表
        getpinpai_list = httpaddress.concat("/index.php/api/goods/screenBrand");
        //获取筛选列表
        getshaixuan_list = httpaddress.concat("/index.php/api/goods/screen_attr");
        //黑名单
        blackList_list = httpaddress.concat("/index.php/api/post/black_name");
        //物流
        wuliu_list = httpaddress.concat("/index.php/api/Yunshu");
        //我的评价列表
        wodepingjia_list = httpaddress.concat("/index.php/api/member/myEvaluate");
        //发表评价
        fabiaopingjia = httpaddress.concat("/index.php/api/member/addGoodsEvaluate");
        //意见反馈
        yijianfankui = httpaddress.concat("/index.php/api/member/Feedback");
        //商品订单支付宝请求参数
        zhifubaoqingqiu = httpaddress.concat("/index.php/api/pay");
        //商品订单微信请求参数
        weixinqingqiu = httpaddress.concat("/index.php/api/wxpay/actionPays");
        //租赁订单支付宝请求参数
        zulin_zhifubaoqingqiu = httpaddress.concat("/index.php/api/pay/ZulinPay");
        //租赁订单微信请求参数
        zulin_weixinqingqiu = httpaddress.concat("/index.php/api/Zupay/actionPays");
        //我要发货
        woyaofahuo = httpaddress.concat("/index.php/api/transport/addHyinfo");
        //评价成功
        pingjiachenggong = httpaddress.concat("/index.php/api/member/goods_recommend");
        //取消订单
        quxiaodingdan = httpaddress.concat("/index.php/api/order/orderDel");
        //退款
        tuikuan = httpaddress.concat("/index.php/api/order/refund");
        //确认收货
        querenshouhuo = httpaddress.concat("/index.php/api/order/take_over");
        //租赁协议
        zulinxieyi_url = httpaddress.concat("/index.php/api/lease/xieyi");
        //租赁合同
        zulinhetong_url = httpaddress.concat("/index.php/api/lease/hetong");
        //上传租赁信息
        upZulinMessage = httpaddress.concat("/index.php/api/lease/lease");
        //上传签名图片
        shangchuanqianming = httpaddress.concat("/index.php/api/lease/sign");
        //租赁
        zulin_list = httpaddress.concat("/index.php/api/lease/mylease");
        //退款售后
        tuikuanshouhou = httpaddress.concat("/index.php/api/member/refundList");
        //全部评价接口
        all_pingjia = httpaddress.concat("/index.php/api/goods/goodsEvaluate");
        //货源列表接口
        huoyuanliebiao_list = httpaddress.concat("/index.php/api/transport/hylist");
        //货主认证
        huozhurenzheng = httpaddress.concat("/index.php/api/transport/ownerAuthen");
        //查看租赁订单
        chakanzulindingdan = httpaddress.concat("/index.php/api/lease/lookImg");
        //选择车型
        xuanzechexing = httpaddress.concat("/index.php/api/transport/carModels");
        //忘记密码验证验证码
        wangjimimayanzheng = httpaddress.concat("/index.php/api/register/checkCode");
        //忘记密码修改密码
        wangjimimaxiugaimima = httpaddress.concat("/index.php/api/register/updatePassword");
        //货主认证状态
        huozhurenzhengstate = httpaddress.concat("/index.php/api/transport/ownerAuthenstate");
        //承运方认证状态
        chengyunfangrenzhengstate = httpaddress.concat("/index.php/api/transport/carrierAuthenstate");
        //承运人添加车辆
        chengyunrentianjiacheliang = httpaddress.concat("/index.php/api/transport/carrierCaradd");
        //承运人车辆删除
        cheliang_delete = httpaddress.concat("/index.php/api/transport/carrierCardel");
        //承运人车辆详情
        cheliangxinxi = httpaddress.concat("/index.php/api/transport/carrierCarinfo");
        //修改承运人车辆信息
        xiugaicheliangxinxi = httpaddress.concat("/index.php/api/transport/carrierCaredit");
        //我的运单列表
        wodeyundan_huozhu = httpaddress.concat("/index.php/api/transport/hyuserlist");
        //查看报价
        chakanbaojia_list = httpaddress.concat("/index.php/api/transport/offerlist");
        //选择报价
        postXuanZeBaoJia = httpaddress.concat("/index.php/api/transport/offerchoice");
        //用户报价
        yonghubaojia = httpaddress.concat("/index.php/api/transport/addOffer");
        //首页最新10条
        yunshushouye_list = httpaddress.concat("/index.php/api/transport/newlist");

        //运输收货人地址列表(运输收货人发货人暂无编辑接口)
        yunshu_address_list = httpaddress.concat("/index.php/api/transport/ownerListaddress");
        //运输收货人地址添加
        yunshu_address_add = httpaddress.concat("/index.php/api/transport/ownerAddaddress");
        //运输收货人地址删除
        yunshu_address_delete = httpaddress.concat("/index.php/api/transport/ownerDeladdress");
        //运输收货人地址详情
        yunshu_address_details = httpaddress.concat("/index.php/api/transport/ownerinfoaddress");
        //运输收货人地址编辑
        yunshu_address_edit = httpaddress.concat("/index.php/api/transport/ownerEditaddress");

        //运输发货人地址列表
        yunshu_fahuo_address_list = httpaddress.concat("/index.php/api/transport/consignerListaddress");
        //运输发货人地址添加
        yunshu_fahuo_address_add = httpaddress.concat("/index.php/api/transport/consignerAddaddress");
        //运输发货人地址删除
        yunshu_fahuo_address_delete = httpaddress.concat("/index.php/api/transport/consignerDeladdress");
        //运输发货人地址详情
        yunshu_fahuo_address_details = httpaddress.concat("/index.php/api/transport/consignerinfoaddress");
        //运输发货人地址编辑
        yunshu_fahuo_address_edit = httpaddress.concat("/index.php/api/transport/consignerEditaddress");
        //运输首页图片地址
        yunshu_main_image_url = httpaddress.concat("/index.php/api/transport/adlist");
        //运输系统推荐
        yunshu_xitongtuijian = httpaddress.concat("/index.php/api/transport/flaglist");
        //运输企业认证
        yunshu_chengyun_qiye_renzheng = httpaddress.concat("/index.php/api/transport/companyAuthen");
        //运输企业认证状态
        yunshu_chengyun_qieye_statue = httpaddress.concat("/index.php/api/transport/companyAuthenstate");
        //承运方(我的运单)
        yunshu_chengyun_chengyunfang_wodeyundan = httpaddress.concat("/index.php/api/transport/mytransportlist");
        //承运人认证提交资料
        yunshu_chengyunren_renzheng = httpaddress.concat("/index.php/api/transport/carrierAuthen");
        //判断是否进行过承运人认证
        yunshu_panduanchengyunren_renzheng = httpaddress.concat("/index.php/api/transport/authenstate");
        //货运状态
        yunshu_yundan_caozuo = httpaddress.concat("/index.php/api/transport/cstate");
        //商家中心url(h5)
        shangjiazhongxin_url = httpaddress.concat("/index.php/api/shop/center");
        //用户是否是商家
        shangjiazhongxin_statue = httpaddress.concat("/index.php/api/shop/index");
        //货源详情url
        huoyuanxiangqing_url = httpaddress.concat("/index.php/api/transport/transportinfo");
        //运单详情url
        yundandetails_url = httpaddress.concat("/index.php/api/transport/mytransportinfo");
        //获取我要开店的状态
        woyaokaidian_statue = httpaddress.concat("/index.php/api/member/Apply");
        //补充我要开店资料
        woyaokaidian_chongxin_tijiao = httpaddress.concat("/index.php/api/member/againApply");
        //进入app时调用的接口
        app_push = httpaddress.concat("/index.php/api/transport/push");
        //货主运单详情接口
        huozhu_yundan_details = httpaddress.concat("/index.php/api/transport/hyuserinfo");
        //对公付款
        duigongfukuan_url = httpaddress.concat("/index.php/api/pay/businessAccount");
        //帖子数据
        get_tiezi_data = httpaddress.concat("/index.php/api/post/update");
        //修改帖子的单张上传
        tiezi_updata_singleimage = httpaddress.concat("/index.php/api/post/uploadPic");
        //修改帖子上传数据
        tiezi_updata_updata = httpaddress.concat("/index.php/api/post/updatePost");
        //删除帖子
        tiezi_delete_url = httpaddress.concat("/index.php/api/post/postDelete");
        //金融,服务，招聘培训
        jinrong_fuwu_zhaopinpeixun_url = httpaddress.concat("/index.php/api/article/articleList");
        //是否已经开通店铺
        kaidian_url = httpaddress.concat("/index.php/api/shop/shop");
        //提交开店资料
        tijiaokaidinaziliao = httpaddress.concat("/index.php/api/shop/open");
        //我要开店支付宝支付
        woyaokaidianzhifubao = httpaddress.concat("/index.php/api/shop/pay");
        //我要开店微信支付
        woyaokaidianweixin = httpaddress.concat("/index.php/api/shop/actionPays");
        //对公付款列表
        duigongfukuanlist = httpaddress.concat("/index.php/api/shop/bank");
        //我要开店企业协议
        woyaokaidian_qiyexieyi = httpaddress.concat("/index.php/api/shop/company_agree");
        //我要开店个人协议
        woyaokaidian_gerenxieyi = httpaddress.concat("/index.php/api/shop/person_agree");
        //首页客服
        shouyekefu = httpaddress.concat("/index.php/api/index/tel");
        //对公付款
        duigongfukuan = httpaddress.concat("/index.php/api/shop/account");
        //获取示例身份证和营业执照
        huoqushenfenzhengheyingyezhizhao = httpaddress.concat("/index.php/api/shop/caseImg");
        //获取省列表(商品三级筛选)
        huoqushengliebiao = httpaddress.concat("/index.php/api/goods/screen_place");
        //获取城市列表
        huoquchengshiliebiao = httpaddress.concat("/index.php/api/goods/screen_city");
        //获取县列表(商品三级筛选)
        huoquxianliebiao = httpaddress.concat("/index.php/api/cart/getDistrict");

    }
}
