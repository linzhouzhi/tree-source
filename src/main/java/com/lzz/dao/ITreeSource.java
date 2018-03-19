package com.lzz.dao;

import com.lzz.model.Tnode;

/**
 * Created by lzz on 2018/3/18.
 */
public interface ITreeSource {
    Tnode getTnodeList(Tnode tnode) throws Exception;
}
