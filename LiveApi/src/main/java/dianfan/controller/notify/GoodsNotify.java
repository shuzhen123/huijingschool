package dianfan.controller.notify;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alipay.api.AlipayApiException;
import com.github.wxpay.sdk.WXPayUtil;
import com.wordnik.swagger.annotations.ApiOperation;

import common.propertymanager.PropertyUtil;
import dianfan.annotations.LogOp;
import dianfan.annotations.UnCheckedFilter;
import dianfan.constant.ConstantIF;
import dianfan.dao.mapper.app.GoodsNotifyMapper;
import dianfan.entities.GoodsOrder;
import dianfan.entities.alipay.PublicPayParam;
import dianfan.exception.SQLExecutorException;
import dianfan.pay.alipay.AlipayNotify;
import dianfan.service.GoodsNotifyService;
import dianfan.util.wx.WeixinResult;

/**
 * @ClassName GoodsNotify
 * @Description 支付通知接口
 * @author cjy
 * @date 2018年4月16日 下午2:21:59
 */
@Scope("request")
@Controller
@RequestMapping(value = "/notify")
public class GoodsNotify {
	/**
	 * 注入：GoodsNotifyService
	 */
	@Autowired
	private GoodsNotifyService goodsNotifyService;
	/**
	 * 注入：GoodsNotifyMapper
	 */
	@Autowired
	private GoodsNotifyMapper goodsNotifyMapper;
	/**
	 * log日志
	 */
	public static Logger log = Logger.getLogger(GoodsNotify.class);

	/**
	 * @Title: wxNotify
	 * @Description: 接收微信通知
	 * @param request
	 *            request
	 * @return WeixinResult 返回值
	 * @throws IOException
	 * @throws:
	 * @time: 2018年3月1日 上午9:13:13
	 */
	@RequestMapping(value = "/goods/wxpay")
	@UnCheckedFilter
	public @ResponseBody WeixinResult wxNotify(HttpServletRequest request, HttpServletResponse response) {
		BufferedReader br;
		String buffer = null;
		StringBuffer resp_data = null;
		try {
			br = new BufferedReader(new InputStreamReader(request.getInputStream(), "UTF-8"));
			// 存放响应数据
			resp_data = new StringBuffer();
			while ((buffer = br.readLine()) != null) {
				resp_data.append(buffer);
			}
		} catch (IOException e1) {
			return new WeixinResult("FAIL", "io exception");
		}

		WeixinResult wr = null;
		try {
			// xml解析为map格式的
			Map<String, String> data = WXPayUtil.xmlToMap(resp_data.toString());
			// 签名验证
			boolean sign_valid = WXPayUtil.isSignatureValid(data, PropertyUtil.getProperty("goods_key"));
			if (!sign_valid) {
				// 验签失败
				log.error("验签失败");
				wr = new WeixinResult("FAIL", "验签失败");
			} else {
				// 验证支付金额
				BigDecimal money = goodsNotifyMapper.findGoodsOrderDataById(data.get("out_trade_no"));
				if (money == null) {
					log.error("订单不存在或已支付成功");
					wr = new WeixinResult("FAIL", "订单不存在或已支付成功");
				} else {
					// 微信支付订单总金额
					if (Integer.parseInt(data.get("total_fee")) != money.multiply(new BigDecimal(100)).intValue()) {
						// 金额不匹配
						log.error("订单金额检测异常");
						wr = new WeixinResult("FAIL", "订单金额检测异常");
					} else {
						// 验证通过，更改订单状态
						goodsNotifyService.updateGoodsOrder(data.get("out_trade_no"));
						log.error("订单更新成功");
						wr = new WeixinResult("SUCCESS");
					}
				}
			}
		} catch (Exception e) {
			wr = new WeixinResult("FAIL", "内部服务器错误");
		}
		return wr;
	}

	@LogOp(method = "alipayNotify", logtype = ConstantIF.LOG_TYPE_1, userid = "", description = "接收支付宝通知")
    @ApiOperation(value = "接收支付宝通知", httpMethod = "POST", notes = "接收支付宝通知", response = String.class)
    @RequestMapping(value = "alipay/pay")
    @UnCheckedFilter
    public void alipayNotify(HttpServletRequest request, HttpServletResponse response) throws IOException, AlipayApiException, SQLExecutorException {
		Map<String, String> params = new HashMap<String, String>();
		Map<String, String[]> requestParams = request.getParameterMap();
		for (Iterator<String> iter = requestParams.keySet().iterator(); iter.hasNext();) {
			String name = iter.next();
			String[] values = requestParams.get(name);
			String valueStr = "";
			for (int i = 0; i < values.length; i++) {
				valueStr = (i == values.length - 1) ? valueStr + values[i] : valueStr + values[i] + ",";
			}
			params.put(name, valueStr);
		}
		
		PublicPayParam base = new PublicPayParam();
		base.setAlipay_public_key(PropertyUtil.getProperty("goods_alipay_public_key"));
		
		boolean b = new AlipayNotify(base).signature(params);
		if(!b) {
			//验签失败
			response.getWriter().write("failed");
		}else {
			//验签成功
			if("TRADE_SUCCESS".equals(params.get("trade_status"))) {
				//支付成功
				//1、商户需要验证该通知数据中的out_trade_no是否为商户系统中创建的订单号，
				GoodsOrder order = goodsNotifyMapper.getGoodsOrderInfo(params.get("out_trade_no"));
				if(order == null || order.getPaystatus() == 1) {
					response.getWriter().write("success");
				}else {
					//2、判断total_amount是否确实为该订单的实际金额（即商户订单创建时的金额），
					if(!params.get("total_amount").equals(order.getMoney().toString())) {
						//此处金额不匹配，做日志处理
						response.getWriter().write("failed");
					}else {
						//3、校验通知中的seller_id（或者seller_email) 是否为out_trade_no这笔单据的对应的操作方（有的时候，一个商户可能有多个seller_id/seller_email），
//						if(!pid.equals(params.get("seller_id"))) {
//							return FAILED;
//						}
						
						//4、验证app_id是否为该商户本身
						if(!PropertyUtil.getProperty("goods_alipay_appid").equals(params.get("app_id"))) {
							response.getWriter().write("failed");
						}else {
							//校验通过
							goodsNotifyService.updateGoodsOrder(params.get("out_trade_no"));
							response.getWriter().write("success");
						}
					}
				}
			}
		}
		response.flushBuffer();
    }
	
}
