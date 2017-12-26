package hhc.common.doc;


import hhc.common.utils.IdUtil;

import java.io.Serializable;


/**
 * Created by liusenhua on 2017/7/13.
 */
public class Entity implements Serializable{
	
    /**
	 * 
	 */
	private static final long serialVersionUID = 3199020823481181490L;
	
	private String id =  String.valueOf(IdUtil.nextEntityId());

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
