package com.micwsx.project.advertise.controller;

import com.alibaba.fastjson.JSONObject;
import com.micwsx.project.advertise.domain.Conference;
import com.micwsx.project.advertise.service.TConferenceService;
import com.micwsx.project.advertise.utility.DateUtil;
import com.micwsx.project.advertise.utility.FileUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/conference")
public class ConferenceController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private TConferenceService conferenceService;

    /**
     * 添加/修改
     */
    @RequestMapping(value = "/submit", method = RequestMethod.POST)
    public void submit(MultipartHttpServletRequest request, HttpServletResponse response) {

        MultipartFile bannerImg = request.getFile("bannerImg");
        MultipartFile hosterImg = request.getFile("hosterImg");
        MultipartFile guestImg = request.getFile("guestImg");
        // 字段值
        String id = request.getParameter("id");
        String brief = request.getParameter("brief");
        String serialNumber = request.getParameter("serialNumber");
        String topic = request.getParameter("topic");
        String introduction = request.getParameter("introduction");
        String location = request.getParameter("location");
        String seatNum = request.getParameter("seatNum");
        String fee = request.getParameter("fee");
        String released = request.getParameter("released");
        String startTime = request.getParameter("startTime");
        String endTime = request.getParameter("endTime");
        String remark = request.getParameter("remark");
        String hoster = request.getParameter("hoster");
        String hosterBrief = request.getParameter("hosterBrief");
        String guest = request.getParameter("guest");
        String guestBrief = request.getParameter("guestBrief");

        Conference conference = new Conference();
        conference.setId((StringUtils.isEmpty(id) ? 0 : Integer.parseInt(id)));
        conference.setBrief(brief);
        conference.setSerialNumber(serialNumber);
        conference.setTopic(topic);
        conference.setIntroduction(introduction);
        conference.setLocation(location);
        conference.setSeatNum(Integer.parseInt(seatNum));
        conference.setFee(Float.parseFloat(fee));
        conference.setStartTime(DateUtil.getDate(startTime, "yyyy-MM-dd hh:mm:ss"));
        conference.setEndTime(DateUtil.getDate(endTime, "yyyy-MM-dd hh:mm:ss"));
        conference.setRemark(remark);
        conference.setHoster(hoster);
        conference.setHosterBrief(hosterBrief);
        conference.setGuest(guest);
        conference.setGuestBrief(guestBrief);
        conference.setReleased(released.equals("on") ? true : false);

        // 上传图片文件
        List<MultipartFile> list = Arrays.asList(bannerImg, hosterImg, guestImg);
        list.parallelStream().forEach(file -> {
            String fileName = file.getOriginalFilename();
            if (!StringUtils.isEmpty(fileName)) {
                File dest = FileUtil.getFileInUpload(file.getOriginalFilename());
                try {
                    file.transferTo(dest);
                } catch (IOException e) {
                    logger.error("[" + dest.getPath() + "] 上传失败");
                }
            }
        });
        String uploadRelativePath = "/" + FileUtil.UPLOAD + "/";
        if (!StringUtils.isEmpty(bannerImg.getOriginalFilename()))
            conference.setBanner(uploadRelativePath + bannerImg.getOriginalFilename());
        if (!StringUtils.isEmpty(hosterImg.getOriginalFilename()))
            conference.setHosterLink(uploadRelativePath + hosterImg.getOriginalFilename());
        if (!StringUtils.isEmpty(guestImg.getOriginalFilename()))
            conference.setGuestLink(uploadRelativePath + guestImg.getOriginalFilename());

        // 赋值对象
        System.out.println(conference);
        if (conference.getId() == 0) {
            //添加
            conferenceService.add(conference);
        } else {
            //修改
            conferenceService.update(conference);
        }
        try {
            response.sendRedirect("getAll");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @RequestMapping(value = {"/show", "/show/{id}"}, method = RequestMethod.GET)
    public String show(@PathVariable(value = "id", required = false) Integer id, Model model) {
        Conference conference = null;
        if (id == null)
            conference = new Conference();
        else
            conference = conferenceService.findById(id);
        model.addAttribute("model", conference);
        model.addAttribute("title", "会议详情");
        return "conference/show";
    }

    @RequestMapping("/test")
    public String testImg(Model model) {
        model.addAttribute("title", "测试页面");
        return "test";
    }


    @RequestMapping("/hide")
    @ResponseBody
    public boolean hide(@RequestBody JSONObject jsonObject) {
        System.out.println(jsonObject);
        int id = jsonObject.getInteger("id");
        boolean isReleased = jsonObject.getBoolean("val");
        System.out.println(id + " - " + isReleased);
        Conference conference = new Conference();
        conference.setId(id);
        conference.setReleased(isReleased);

        int result = conferenceService.update(conference);
        return result > 0;
    }

    @RequestMapping("/getAll")
    public String getAll(@RequestParam Map<String, Object> queryMap,
                         @RequestParam(required = false, defaultValue = "1") Integer currentPage,
                         @RequestParam(required = false, defaultValue = "2") Integer pageSize, Model model) {
        PageHelper.startPage(currentPage, pageSize);
        List<Conference> list = conferenceService.findCriteria(queryMap);
        PageInfo<Conference> pageInfo = new PageInfo<>(list);
        model.addAttribute("pageInfo", pageInfo);
        model.addAttribute("title", "会议管理");
        model.addAllAttributes(queryMap);
        return "conference/list";
    }
}
