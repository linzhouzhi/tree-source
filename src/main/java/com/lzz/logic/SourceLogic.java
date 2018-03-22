package com.lzz.logic;

import com.lzz.dao.ITreeSource;
import com.lzz.dao.hbase.HbaseClient;
import com.lzz.dao.kafka.KafkaClient;
import com.lzz.dao.redis.RedisManager;
import com.lzz.dao.source.ISourceDao;
import com.lzz.dao.sqldb.Mysql;
import com.lzz.dao.zk.CuratorClient;
import com.lzz.model.Response;
import com.lzz.model.SourceModel;
import com.lzz.model.SourceType;
import com.lzz.model.Tnode;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by lzz on 2018/3/18.
 */
@Component
public class SourceLogic {
    @Resource
    private ISourceDao sourceXml;

    public SourceLogic(){

    }

    public Response saveSource(SourceModel sourceModel) {
        boolean res = sourceXml.save( sourceModel );
        return res ? Response.OK() : Response.Fail();
    }

    public Response sourceListByGroup(String group) {
        List<SourceModel> res = sourceXml.listByGroup( group );
        return Response.Obj(0, res);
    }

    public Response removeSource(String target) {
        boolean res = sourceXml.remove( target );
        return res ? Response.OK() : Response.Fail();
    }

    public Tnode getList(String address, Tnode tnode) throws Exception {
        SourceType sourceType = tnode.getSourceType();
        ITreeSource s = null;
        Tnode resNode = null;
        switch ( sourceType ){
            case zk:
                s = new CuratorClient( address );
                resNode = s.getTnodeList( tnode );
                break;
            case kafka:
                s = new KafkaClient( address );
                resNode = s.getTnodeList( tnode );
                break;
            case mysql:
                s = new Mysql( address );
                resNode = s.getTnodeList( tnode );
                break;
            case redis:
                s = (new RedisManager()).factory(address);
                resNode = s.getTnodeList( tnode );
                break;
            case hbase:
                s = new HbaseClient( address );
                resNode = s.getTnodeList( tnode );
                break;
        }

        return resNode;
    }
}
