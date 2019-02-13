package dianfan.pay.unionpay;

import java.util.Map;

import dianfan.pay.unionpay.sdk.AcpService;
import dianfan.pay.unionpay.sdk.UnionPayBase;

/**
 * 重要：联调测试时请仔细阅读注释！
 * 
 * 产品：手机控件支付产品<br>
 * 功能：后台通知接收处理示例 <br>
 * 日期： 2015-09<br>
 * 版本： 1.0.0 版权： 中国银联<br>
 * 声明：以下代码只是为了方便商户测试而提供的样例代码，商户可以根据自己需要，按照技术文档编写。该代码仅供参考，不提供编码，性能，规范性等方面的保障<br>
 * 交易说明： 前台类交易成功才会发送后台通知。后台类交易（有后台通知的接口）交易结束之后成功失败都会发通知。
 * 为保证安全，涉及资金类的交易，收到通知后请再发起查询接口确认交易成功。不涉及资金的交易可以以通知接口respCode=00判断成功。
 * 未收到通知时，查询接口调用时间点请参照此FAQ：https://open.unionpay.com/ajweb/help/faq/list?id=77&level=0&from=0
 */

public class UnionPayNotify {

	/**
	 * @Title: validate
	 * @Description: 重要！验证签名前不要修改reqParam中的键值对的内容，否则会验签不过
	 * @param reqParam
	 *            参数
	 * @return 验证
	 * @throws:
	 * @time: 2018年6月4日 下午4:16:43
	 */
	public boolean validate(Map<String, String> reqParam) {
		return AcpService.validate(reqParam, UnionPayBase.encoding);
	}

}
