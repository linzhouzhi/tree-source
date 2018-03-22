package com.lzz.dao;

import com.lzz.model.Tnode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by lzz on 2018/3/18.
 */
public abstract class SourceBase implements ITreeSource {
    protected Map<String, Integer> chidrenMap = new HashMap<>();

    protected Tnode getTnodeList(SourceBase sourceBase, Tnode tnode) throws Exception {
        this.tnodeTemplate(this, tnode);
        this.close();
        return tnode;
    }

    @Override
    public Tnode getTnodeList(Tnode tnode) throws Exception {
        return getTnodeList(this, tnode);
    }

    protected Tnode tnodeTemplate(SourceBase sourceBase, Tnode tnode) throws Exception {
        String path = tnode.getId();
        List<Tnode> childNode = tnode.getChildren();
        List<String> childrenList = sourceBase.childrenList( path );
        for(int i = 0; i < childrenList.size(); i++){
            String childName = childrenList.get(i);
            Tnode tnode1 = new Tnode();
            tnode1.setText( childName );

            String childPath = "";
            if( "/".equals( path ) ){
                childPath = path + childName;
            }else{
                childPath = path + "/" + childName;
            }
            tnode1.setId( childPath );
            int childrenNum = sourceBase.getNumChildren( childPath );
            if( childrenNum > 0 ){
                List<Tnode> grandson = new ArrayList<>();
                Tnode ellipsisNode = new Tnode();
                ellipsisNode.setType("ellipsis");
                ellipsisNode.setId( childPath +  "/..." );
                ellipsisNode.setText("...");
                grandson.add( ellipsisNode );
                tnode1.setChildren( grandson );
            }
            String nodeType = sourceBase.nodeType(childPath, childrenNum);
            tnode1.setType( nodeType );
            childNode.add( tnode1);
            if( i > 200 ){
                childNode.add( new Tnode(path+ "/...", "...", "ellipsis") );
                break;
            }
        }
        return tnode;

    }

    // 子类覆盖后定义自己的样式
    protected String nodeType(String childPath, int childrenNum) {
        String type = "parent";
        if( childrenNum == 0 ){
            type = "leaf";
        }
        return type;
    }


    public int getNumChildren(String childPath) throws Exception {
        if( null == chidrenMap.get( childPath ) ){
            childrenList(childPath);
        }
        return chidrenMap.get( childPath );
    }


    public abstract List<String> childrenList(String path) throws Exception;

    public abstract void close();
}
