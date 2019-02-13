#支付插件dianfan-payment-1.0.0使用说明
当前版本为1.0.0，已集成微信、支付宝、银联支付的下单支付、账单查询、支付异步通知功能，其它功能待完善中。

## 微信支付

* 已集成微信统一下单、账单查询、支付通知验签功能
* 支持微信App支付、公众号支付、小程序支付统一下单
* 核心类
    * 支付核心类：dianfan.pay.wx.WxPayCore
    * 验签核心类：dianfan.pay.wx.WxNotify
* 核心实体类
    * 支付参数配置类：dianfan.pay.config.wx.WxPayConfigImpl

#### 1、微信统一下单示例
* 业务类：dianfan.entities.wx.UnifiedorderBean
* 代码示例

```java
{
    /*
     * 公共配置类实例化（实际参数按WxPayConfigImpl构造函数说明填入）
     */
    WXPayConfig conf = new WxPayConfigImpl(appid, macid, key);
    
    /*
     * 业务数据填充，请安实际应用场景填入
     */
    UnifiedorderBean biz = new UnifiedorderBean();
    biz.setXxx(...);
    
	/*
     * 微信支付请求,返回支付数据
     */
	Map<String, String> ret = new WxPayCore(conf).unifiedOrder(biz);
}
```
#### 2、微信订单查询示例
* 业务类：dianfan.entities.wx.WxOrderQueryBean
* 代码示例

```java
{
    /*
     * 公共配置类实例化（实际参数按WxPayConfigImpl构造函数说明填入）
     */
    WXPayConfig conf = new WxPayConfigImpl(appid, macid, key);
    
    /*
     * 业务数据填充，请安实际应用场景填入
     */
    WxOrderQueryBean biz = new WxOrderQueryBean();
    biz.setXxx(...);
    
    /*
     * 微信查询请求,返回查询数据
     */
	Map<String, String> ret = new WxPayCore(conf).orderQuery(biz);
}
```
#### 3、微信通知验签示例
* 代码示例

```java
{
    /*
     * 微信验签方法,返回验签结果（true验签成功，false验签失败）
     * respStr：接受微信响应的数据字符串
     */
    boolean ret = new WxNotify().checkSign(String respStr);
}
```

## 支付宝支付

* 已集成支付宝app支付、手机网站支付、支付宝通知数据验签功能
* 核心类
    * 支付核心类：dianfan.pay.alipay.AlipayCore
    * 验签核心类：dianfan.pay.alipay.AlipayNotify
* 核心实体类
    * 支付参数配置类：dianfan.entities.alipay.PublicPayParam

#### 1、支付宝app下单示例
* 业务类：dianfan.entities.alipay.AppPayBean
* 代码示例

```java
{
    /*
     * 支付宝app支付请求,返回支付数据
     */
	String ret = new AlipayCore(PublicPayParam base).alipayAppTrade(AppPayBean biz);
}
```
#### 2、支付宝手机网站下单示例
* 业务类：dianfan.entities.alipay.WapPayBean
* 代码示例

```java
{
    /*
     * 支付宝手机网站支付请求,返回支付数据
     */
    String ret = new AlipayCore(PublicPayParam base).alipayWapTrade(WapPayBean biz);
}
```
#### 3、支付宝订单查询示例
* 业务类：dianfan.entities.alipay.AlipayQueryBean
* 代码示例

```java
{
    /*
     * 支付宝订单查询请求,返回查询数据
     */
    String ret = new AlipayCore(PublicPayParam base).tradeQuery(AlipayQueryBean biz);
}
```
#### 4、支付宝通知验签示例
* 代码示例

```java
{
    /*
     * 支付宝验签方法,返回验签结果（true验签成功，false验签失败）
     * respStr：接受支付宝响应的数据字符串
     */
    boolean ret = new AlipayNotify(PublicPayParam base).signature(Map<String, String> respMap);
}
```

