package com.micwsx.project.advertise.controller;

import com.micwsx.project.advertise.common.WechatContext;
import com.micwsx.project.advertise.domain.Conference;
import com.micwsx.project.advertise.domain.ConferenceItem;
import com.micwsx.project.advertise.domain.Participant;
import com.micwsx.project.advertise.service.TConferenceService;
import com.micwsx.project.advertise.service.TMemberService;
import com.micwsx.project.advertise.service.TParticipantService;
import com.micwsx.project.advertise.utility.Util;
import com.micwsx.project.advertise.viewmodel.PersonalCenter;
import com.micwsx.project.advertise.viewmodel.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Controller
@RequestMapping("/menu")
public class InitialServerController extends BaseController {

    @Autowired
    private TParticipantService participantService;

    @Autowired
    private TMemberService memberService;

    @Autowired
    private TConferenceService conferenceService;

    @Autowired
    private WechatContext wechatContext;


    /**
     * 创建菜单
     * 　个人明细菜单：查看个人分享人数（直属人员）
     * 分享会议活动码：点击后显示本人的邀请关注动态码，
     * 或者直接显示入会支付二维码，支付后中根据用户ID,与支付订单关联（支付订单中包含用户ID,主题会议，支付成功后成为会议成员），此会议目前参加人员。
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/create")
    public String createMenu(HttpServletRequest request, HttpServletResponse response) {
        String host = request.getScheme() + "://" + request.getRemoteHost();
        // 创建二个菜单：个人明细菜单和会议活动主界面入口
        boolean flag = wechatContext.initalMenu(host);
        return flag ? "初始化菜单成功！" : "初始化菜单失败！";
    }

    /**
     * 响应点击菜单个人中心：展示个人数据（直属人员列表，服务器关注推荐码，参与过的会议记录（如果有））
     *
     * @param request
     * @return
     */
    @RequestMapping("/personal")
    public String clickPersonalMenu(HttpServletRequest request, Model model) {
        //获取用户的OpenId
        String openId=(String) request.getSession().getAttribute("id");
        openId="M0001";
        PersonalCenter personalCenter = memberService.getPersonalDate(openId);
        // 获取参与的会议列表
        List<Conference> conferences = conferenceService.getConferencesByMemberId(openId);
        personalCenter.setRecentActivities(conferences);
        model.addAttribute("person", personalCenter);
        model.addAttribute("title", "个人中心");
        return "personCenter";
    }

    /**
     * 响应点击菜单会议入口: 展示活动主页面（最近2场活动）
     *
     * @return
     */
    @RequestMapping("/activities")
    public String clickActivitiesMenu(HttpServletRequest request,Model model) {
        //获取点击菜单用户id
        List<ConferenceItem> conferenceItems = conferenceService.findOnline();
        model.addAttribute("list", conferenceItems);
        model.addAttribute("title", "活动列表");
        return "activities";
    }

    /**
     * 会议详情页面
     * @param id
     * @param model
     * @return
     */
    @RequestMapping("/activity/{id}")
    public String detail(@PathVariable("id") Integer id, Model model) {
        // 会议id号
        Conference conference = conferenceService.findById(id);
        model.addAttribute("conference",conference);
        model.addAttribute("qrcode",wechatContext.getOfficeQRCode());
        model.addAttribute("title", "活动详情");
        return "activity";
    }

    /**
     * 响应微信callback操作，获取用户openId
     *
     * @param request
     * @param response
     */
    @RequestMapping("/getOpenId")
    public void getOpenIdWithCode(HttpServletRequest request, HttpServletResponse response) {
        try {
            String code = request.getParameter("code");
            String requestRelativePath = request.getParameter("state");
            // 根据code获取用户openId
            String openId = wechatContext.getOpenIdByCode(code);

            //保存到Session中，避免后续操作再次操作用户openId操作。
            HttpSession session = request.getSession();
            session.setAttribute("id", openId);

            // 获取openId后重定向到用户请求页面去
            request.getRequestDispatcher(requestRelativePath).forward(request, response);

        } catch (ServletException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @RequestMapping("/jsapi/")
    public String jsapi(Product product, HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
        // 获取用户openId
        HttpSession session = request.getSession();
        String id = (String) session.getAttribute("id");
        // 用户的客户端IP
        String ipAddr = Util.getIp(request);
        // participant id(即订单id)
        String pid = UUID.randomUUID().toString().replace("-", "");
        Participant participant = new Participant();
        participant.setId(pid);
        participant.setConferenceId(product.getConferenceId());
        participant.setMemberId(id);
        participant.setCompleted(false);
        participant.setPaid(product.getAmount());
        participant.setTicketCodeNum(product.getTicketCode());
        participant.setIpAddr(ipAddr);
        
        // 创建会议人员（订单）
        int result = participantService.add(participant);
        if (result > 0) {
            // 支付参数
            String notifyUrl = request.getScheme() + "://" + request.getRemoteHost() + "/wechat/notify";
            Map<String, String> map = new HashMap<>();
            map.put("id", pid);//participant id(订单号)
            map.put("conferenceId", String.valueOf(product.getConferenceId()));//会议id
            map.put("description", product.getDescription());//会议编号
            map.put("memberId", id);//会议编号
            map.put("amount", String.valueOf(product.getAmount()));//入场费用
            map.put("ticketCode", product.getTicketCode());//客户ip地址
            map.put("ip", ipAddr);//客户ip地址
            map.put("notifyUrl", notifyUrl);//支付通知地址
            //map.put("remark",product.getRemark());//会议备注
            String jsonParam = wechatContext.jsapiPay(map);
            model.addAttribute("jsonParam", jsonParam);
            model.addAttribute("participantId", pid);
        }
        model.addAttribute("title", "支付");
        return "pay";
    }

}
