package dianfan.controller.notify;
 
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wordnik.swagger.annotations.ApiOperation;

import dianfan.annotations.LogOp;
import dianfan.annotations.UnCheckedFilter;
import dianfan.constant.ConstantIF;
import dianfan.service.RedisTokenService;
import dianfan.util.HttpClientHelper;
import dianfan.util.wx.MessageUtil;
import dianfan.util.wx.TextMessage;
 
/**
 * @ClassName WxMpNotify
 * @Description 微信公众号服务器通知接受接口
 * @author cjy
 * @date 2018年2月28日 下午6:05:50
 */
@Scope("request")
@Controller
@RequestMapping(value = "/notify")
public class WxMpNotify {
    public static Logger log = Logger.getLogger(WxMpNotify.class);
    @Autowired
    private RedisTokenService redisTokenService;
    /**
     * @Title: wxNotify
     * @Description: 接收微信通知
     * @param request
     * @return
     * @throws IOException
     * @throws:
     * @time: 2018年3月1日 上午9:13:13
     */
    @LogOp(method = "wxNotify", logtype = ConstantIF.LOG_TYPE_1, userid = "", description = "接收微信通知")
    @ApiOperation(value = "接收微信通知", httpMethod = "POST", notes = "接收微信通知", response = String.class)
    @RequestMapping(value = "wxmp")
    @UnCheckedFilter
    public @ResponseBody String wxNotify(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //服务器认证
//      response.getWriter().write(request.getParameter("echostr"));
//      return null;
        // 返回给微信服务器的消息,默认为null
        String respMessage = null;
        try {
            // xml请求解析
            Map<String, String> requestMap = MessageUtil.parseXml(request);
            // 发送方帐号(open_id)
            String fromUserName = requestMap.get("FromUserName");
            // 公众帐号
            String toUserName = requestMap.get("ToUserName");
            // 消息类型
            String msgType = requestMap.get("MsgType");
 
            // 默认回复文本消息
            TextMessage textMessage = new TextMessage();
            textMessage.setToUserName(fromUserName);
            textMessage.setFromUserName(toUserName);
            textMessage.setCreateTime(new Date().getTime());
            textMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_TEXT);
            textMessage.setFuncFlag(0);
            // 事件推送
            if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_EVENT)) {
                // 事件类型
                String eventType = requestMap.get("Event");
                // 关注或扫描带参二维码加关注
                if (eventType.equals(MessageUtil.EVENT_TYPE_SUBSCRIBE) || eventType.equals(MessageUtil.EVENT_TYPE_SCAN)) {
                    //发送客服消息
                    String salerid = requestMap.get("EventKey");
                    salerid = salerid.replace("qrscene_", "");
                     
                    StringBuffer sb = new StringBuffer();
                     
                    sb.append("欢迎关注慧鲸学堂，本学堂致力于打造中国极具实战价值的炒股在线教育平台。").append("\n\n");
                    sb.append("这里有独家的大盘解析，大数据量化精选，A股全交易时段名师直播解盘，专家在线诊股。价值投资股市易经系列，集合竞价抓涨停板绝技。").append("\n\n");
                    sb.append("新用户");
                     
                    if (salerid.isEmpty()) {
                        sb.append("<a href='http://mp.huijingschool.com/#/zhuce'>点击注册</a>");
                        //普通关注
                    } else {
                        //扫码关注
                        sb.append("<a href='http://mp.huijingschool.com/#/zhuce?from=singlemessage&salerid=" + salerid + "'>点击注册</a>");
                    }
                     
                    sb.append("大礼包免费领取！");
                     
                    String requestUrl = "https://api.weixin.qq.com/cgi-bin/message/custom/send?access_token=ACCESS_TOKEN";
                    requestUrl = requestUrl.replace("ACCESS_TOKEN", redisTokenService.getWxAccessToken());
                     
                    Map<String, Object> param = new HashMap<>();
                    param.put("touser", fromUserName);
                    param.put("msgtype", "text");
                    Map<String, Object> cont = new HashMap<>();
                    cont.put("content", sb.toString());
                    param.put("text", cont);
                     
                    ObjectMapper mapper = new ObjectMapper();
                    HttpClientHelper.sendJsonPost(requestUrl, mapper.writeValueAsString(param));
                    return null;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return respMessage;
    }
}