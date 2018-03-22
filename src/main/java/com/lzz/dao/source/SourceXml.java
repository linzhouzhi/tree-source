package com.lzz.dao.source;

import com.lzz.model.SourceModel;
import com.lzz.util.XmlUtil;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by lzz on 2018/3/22.
 */
@Component
public class SourceXml implements ISourceDao {
    private static final String SOURCE_XML_FILE = "source.xml";
    @Override
    public boolean save(SourceModel sourceModel) {
        XmlUtil xmlUtil = new XmlUtil( SOURCE_XML_FILE );
        String key = sourceModel.getType() + "_" + sourceModel.getShowName();
        return  xmlUtil.add( key, sourceModel.serialize() );
    }

    @Override
    public List<SourceModel> listByGroup(String group) {
        List<SourceModel>  sourceModelList = new ArrayList<>();
        XmlUtil xmlUtil = new XmlUtil( SOURCE_XML_FILE );
        Map<String, String> allMap = xmlUtil.getAllMap();
        for(Map.Entry<String,String> entry : allMap.entrySet()){
            String value = entry.getValue();
            SourceModel sourceModel = SourceModel.unSerialize( value );
            if( sourceModel.getType().equals( group ) ){
                sourceModelList.add( sourceModel );
            }
        }
        return sourceModelList;
    }

    @Override
    public boolean remove(String target) {
        XmlUtil xmlUtil = new XmlUtil( SOURCE_XML_FILE );
        return xmlUtil.remove( target );
    }
}
