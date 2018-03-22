package com.lzz.control;

import com.lzz.logic.SourceLogic;
import com.lzz.model.Response;
import com.lzz.model.SourceModel;
import com.lzz.model.SourceType;
import com.lzz.model.Tnode;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * Created by lzz on 2018/3/18.
 */
@Controller
public class SourceControl {
    @Resource
    SourceLogic logic;

    @RequestMapping("/save-source")
    @ResponseBody
    public Response saveSource(@RequestBody SourceModel sourceModel) {
        return logic.saveSource(sourceModel);
    }

    @RequestMapping("/source-group-list")
    @ResponseBody
    public Response sourceGroupList(@RequestParam String group) {
        return logic.sourceListByGroup( group );
    }

    @RequestMapping("/remove-source")
    @ResponseBody
    public Response removeSource(@RequestParam String target) {
        return logic.removeSource(target);
    }

    @RequestMapping("/source")
    public String index(@RequestParam SourceType type, Model model) {
        String page = "welcome";
        switch (type){
            case zk:
                page = "zk/index";
                break;
            case hbase:
                page = "hbase/index";
                break;
            case kafka:
                page = "kafka/index";
                break;
            case mysql:
                page = "mysql/index";
                break;
            case redis:
                page = "redis/index";
                break;
        }
        return page;
    }

    @RequestMapping(value="/get-path-children", method = RequestMethod.GET)
    @ResponseBody
    public Tnode getPathChildren(@RequestParam String address,
                                 @RequestParam(value="path", defaultValue="/") String path,
                                 @RequestParam SourceType sourceType) throws Exception {

        if( StringUtils.isEmpty(address) || address.length() < 10 ){
            return null;
        }
        Tnode tnode = new Tnode();
        tnode.setText( path );
        tnode.setId( path );
        tnode.setType("parent");
        tnode.setSourceType( sourceType );
        tnode = logic.getList( address, tnode );
        return tnode;
    }
}
