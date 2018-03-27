package com.lzz.control;

import com.lzz.logic.MysqlLogic;
import com.lzz.model.MysqlQueryParam;
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
@RequestMapping("/mysql")
public class MysqlControl {
    @Resource
    MysqlLogic logic;

    @RequestMapping("/content")
    public String content(Model model) {
        return "mysql/content";
    }

    @RequestMapping("/query")
    @ResponseBody
    public Response query(@RequestBody MysqlQueryParam param){
        Response response;
        try {
            String sql = param.getSql().trim();
            if( sql.toLowerCase().indexOf("select") != 0 ){
                response = Response.Obj(1, "only support select");
            }else{
                List list = logic.query(param.getAddress(), param.getDatabases(), sql);
                response = Response.Obj( 0, list );
            }

        }catch (Exception e){
            response = Response.Obj( 1, e.getMessage() );
        }
        return response;
    }
}
