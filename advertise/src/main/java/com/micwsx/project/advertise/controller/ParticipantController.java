package com.micwsx.project.advertise.controller;

import com.micwsx.project.advertise.domain.*;
import com.micwsx.project.advertise.service.imp.ConferenceService;
import com.micwsx.project.advertise.service.imp.ParticipantService;
import com.micwsx.project.advertise.utility.Util;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

/**
 * @author Michael
 * @create 7/28/2020 3:51 PM
 */
@Controller
@RequestMapping("/participant")
public class ParticipantController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ParticipantService participantService;

    @Autowired
    private ConferenceService conferenceService;

    @RequestMapping("/getAll")
    public String list(Model model) {
        List<ParticipantItem> participantItems = participantService.selectAllSum();
        model.addAttribute("list", participantItems);
        model.addAttribute("title", "会议人员管理");
        return "participant/list";
    }

    /**
     * 显示会议成员数据
     * @param currentPage
     * @param pageSize
     * @param model
     * @return
     */
    @RequestMapping("/show")
    public String show(@RequestParam Integer id,
                       @RequestParam(required = false, defaultValue = "1") Integer currentPage,
                       @RequestParam(required = false, defaultValue = "2") Integer pageSize, Model model) {
        Conference conference = conferenceService.findById(id);
//        PageHelper.startPage(currentPage, pageSize);
        // 过滤条件查询
//        List<ParticipantInfo> participantInfos =conference.getParticipants();
//        PageInfo<ParticipantInfo> pageInfo=new PageInfo<>(participantInfos);
        model.addAttribute("conference", conference);
//        model.addAttribute("pageInfo", pageInfo);
//        model.addAttribute("condition",new MemberCondition());
        model.addAttribute("title", "会议人员");
        return "participant/show";
    }

    @RequestMapping("/query")
    public String show(MemberCondition memberCondition,
                       @RequestParam(required = false, defaultValue = "1") Integer currentPage,
                       @RequestParam(required = false, defaultValue = "2") Integer pageSize, Model model) {
        System.out.println(memberCondition);
        PageHelper.startPage(currentPage, pageSize);
        List<ParticipantInfo> list = participantService.selectCriteria(memberCondition);
        // 过滤条件查询
        PageInfo<ParticipantInfo> pageInfo=new PageInfo<>(list);
        model.addAttribute("pageInfo", pageInfo);
        model.addAttribute("condition",memberCondition);
        return "participant/dataset";
    }

    /**
     * 向会议添加成员
     * @return
     */
    @RequestMapping(value = "/delete/{id}",method = RequestMethod.POST)
    @ResponseBody
    public boolean delete(@PathVariable("id") String id) {
        return participantService.delete(id)>0;
    }

    /**
     * 显示添加页面
     * @return
     */
    @RequestMapping(value = "/add/{id}",method = RequestMethod.GET)
    public String showAddView(@PathVariable("id") Integer id,HttpServletRequest request,Model model) {
        Participant participant=new Participant();
        // 设置默认值
        participant.setId(UUID.randomUUID().toString().replace("-", ""));
        participant.setConferenceId(id);
        participant.setPaid(0);
        participant.setCompleted(true);
        String ipAddr = Util.getIp(request);
        participant.setIpAddr(ipAddr);

        model.addAttribute("model", participant);
        model.addAttribute("title", "添加会议人员");
        return "participant/add";
    }

    @RequestMapping(value = "/add",method = RequestMethod.POST)
    public void add(Participant participant, HttpServletResponse response) {
        System.out.println(participant);

        participantService.add(participant);

        try {
            response.sendRedirect("query?id="+participant.getConferenceId());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }




}
