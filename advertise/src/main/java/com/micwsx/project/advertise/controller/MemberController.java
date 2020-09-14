package com.micwsx.project.advertise.controller;

import com.micwsx.project.advertise.domain.Member;
import com.micwsx.project.advertise.service.TMemberService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * @author Michael
 * @create 7/28/2020 11:15 AM
 * 会员Controller
 */
@Controller
@RequestMapping("/member")
public class MemberController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private TMemberService memberService;

    @RequestMapping("/getAll")
    public String list(Model model) {
        List<Member> list = memberService.findAll();
        model.addAttribute("list", list);
        model.addAttribute("title", "成员列表");
        return "member/list";
    }

    /**
     * 编辑用户部分数据
     *
     * @param id
     * @param model
     * @return
     */
    @RequestMapping(value = {"/show/{id}"})
    public String list(@PathVariable(value = "id") String id, Model model) {
        Member member = memberService.get(id);
        model.addAttribute("model", member);
        model.addAttribute("title", "成员信息");
        return "member/show";
    }

    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    public void edit(Member member, HttpServletResponse response) {
//        System.out.println(member);
        memberService.update(member);
        try {
            response.sendRedirect("show/" + member.getId());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
