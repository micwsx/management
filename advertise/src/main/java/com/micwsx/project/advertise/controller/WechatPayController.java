package com.micwsx.project.advertise.controller;

import com.micwsx.project.advertise.common.WechatContext;
import com.micwsx.project.advertise.domain.Participant;
import com.micwsx.project.advertise.service.TParticipantService;
import com.micwsx.project.advertise.utility.Dom4JUtil;
import com.micwsx.project.advertise.utility.HttpUtility;
import com.micwsx.project.advertise.viewmodel.BusinessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@Controller
@RequestMapping("/wechat")
public class WechatPayController extends BaseController {

    @Autowired
    private TParticipantService participantService;

    @Autowired
    private WechatContext wechatContext;

    @RequestMapping(value="/refresh",method = RequestMethod.GET)
    public String refresh(@RequestParam String pid, Model model) throws BusinessException {
        Participant participant = participantService.selectById(pid);
        if (participant == null) {
            throw new BusinessException("订单[" + pid + "]不存在");
        } else {
            String paymentStatus = participant.isCompleted() ? "支付成功" : "等待支付";
            model.addAttribute("status", paymentStatus);
            model.addAttribute("conferenceId", participant.getConferenceId());
        }
        model.addAttribute("title", "支付结果");
        return "paymentStatus";
    }


    /**
     * 接收微信支付通知
     * @param request
     * @param response
     */
    @RequestMapping("/notify")
    public void notify(HttpServletRequest request, HttpServletResponse response) {
        // 获取微信通知结果
        String xmlContent = HttpUtility.getStreamContent(request);
        logger.info("微信返回通知报文：" + xmlContent);
        if (!StringUtils.isEmpty(xmlContent)) {
            Map<String, String> map = Dom4JUtil.parseXML2Map(xmlContent);
            String participantId = map.get("attach");
            Participant participant = participantService.selectById(participantId);
            if (participant == null) {
                logger.error("订单[" + participantId + "]不存在！");
            } else if (participant.isCompleted()) {
                logger.warn("订单[" + participantId + "]支付完成已收到通知！");
            }else {
                Participant updateRecord=new Participant();
                updateRecord.setId(participantId);
                updateRecord.setCompleted(true);
                updateRecord.setData(xmlContent);
                participantService.update(updateRecord);
            }
        }
    }

    /**
     * 展示服务号二维码
     * @param model
     * @return
     */
    @RequestMapping("/qrcode")
    public String qrcode(Model model) {
        String qrcodeSrc=wechatContext.getOfficeQRCode();
        model.addAttribute("qrcodeSrc",qrcodeSrc);
        model.addAttribute("title","公众号二维码");
        return "qrcode";
    }


    @RequestMapping("/test")
    public String testError() throws BusinessException{
        return "testPay";
    }

    @RequestMapping(value = "/test1",method = RequestMethod.GET)
    public void testError1(@RequestParam String pid) throws BusinessException{
        System.out.println("pid: "+pid);
    }

}
