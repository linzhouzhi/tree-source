package com.lzz.control;

import com.lzz.logic.KafkaLogic;
import com.lzz.model.KafkaQueryParam;
import com.lzz.model.Response;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by lzz on 2018/3/27.
 */
@Controller
@RequestMapping("/kafka")
public class KafkaControl {
    @Resource
    KafkaLogic logic;

    @RequestMapping("/content")
    public String content(Model model) {
        return "kafka/content";
    }

    @RequestMapping("/query")
    @ResponseBody
    public Response query(@RequestBody KafkaQueryParam param){
        Response response;
        List<String> resList = logic.query( param );
        response = Response.Obj(0, resList);
        return response;
    }
}
