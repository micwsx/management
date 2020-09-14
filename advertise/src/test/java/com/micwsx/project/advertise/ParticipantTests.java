package com.micwsx.project.advertise;

import com.micwsx.project.advertise.dao.ParticipantMapper;
import com.micwsx.project.advertise.domain.Participant;
import org.apache.commons.lang.StringEscapeUtils;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


@SpringBootTest
@MapperScan("com.micwsx.project.advertise.dao")
class ParticipantTests {

    @Autowired
    private ParticipantMapper participantMapper;

    @Test
    void testT(){
        try {
            System.out.println(StringEscapeUtils.unescapeHtml("& % #"));
            System.out.println(StringEscapeUtils.escapeHtml("&#43; & % #"));

            String encode = URLEncoder.encode("order descrip-tion+Product1+|#|%|& 中国+", "utf-8");
            String result = URLDecoder.decode(encode, "utf-8");

            String plus = StringEscapeUtils.escapeHtml("+");
            System.out.println(plus);
            System.out.println(StringEscapeUtils.unescapeHtml(plus));

            System.out.println(encode);
            System.out.println(result);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    void contextLoads() {



//     addParticipant();

//        String[] ids = {"0edc213d024d45ad872e22de7beeccb7", "79d393bc1d684b39b3dbda348e573c65"};
//        participantMapper.deleteBatch(ids);

//        List<Participant> list = participantMapper.selectAll();
//        System.out.println(list.size());

    }

    private void addParticipant() {
        int conferenceId=5;
        Participant participant = new Participant();
        participant.setId(UUID.randomUUID().toString().replace("-", ""));
        participant.setConferenceId(conferenceId);
        participant.setMemberId("M0001");
        participant.setPaid(99.99F);
        participant.setCompleted(true);
        participantMapper.add(participant);

        List<Participant> list = new ArrayList<>();

        Participant p2 = new Participant();
        p2.setId(UUID.randomUUID().toString().replace("-", ""));
        p2.setConferenceId(conferenceId);
        p2.setMemberId("M0002");
        p2.setPaid(99.99F);
        p2.setCompleted(true);
        list.add(p2);

        Participant p3 = new Participant();
        p3.setId(UUID.randomUUID().toString().replace("-", ""));
        p3.setConferenceId(conferenceId);
        p3.setMemberId("M0003");
        p3.setPaid(99.99F);
        p3.setCompleted(true);
        list.add(p3);
        participantMapper.addBatch(list);


    }



}
