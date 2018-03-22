package com.lzz.dao.source;

import com.lzz.model.SourceModel;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by lzz on 2018/3/22.
 */
@Component
public interface ISourceDao {
    boolean save(SourceModel sourceModel);
    List<SourceModel> listByGroup(String group);
    boolean remove(String target);
}
