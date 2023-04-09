/*
 * FieldSizeTag.java
 *
 * Created on September 26, 2003, 11:13 PM
 * Copyright 2002-2005 Kevin Delargy.
 */

package com.modelgenerated.taglib;

import org.apache.commons.beanutils.PropertyUtils;
import com.modelgenerated.foundation.config.ConfigLocator;
import com.modelgenerated.foundation.dataaccess.ObjectFieldSizeConfig;
import com.modelgenerated.foundation.logging.Logger;
import com.modelgenerated.util.Assert;
import com.modelgenerated.util.StringUtil;

import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

/**
 *
 * @author  kevind
 */
public class FieldSizeTag extends TagSupport {
    String objectField;
    
    /** Creates a new instance of TextTag */
    public FieldSizeTag() {
    }
    
    public void setObjectField(String newObjectField) {
        objectField = newObjectField;
    }
    
    
    public int doStartTag() throws JspException {
	// Generate the URL to be encoded
	try {
            Logger.debug(this, "modelgenerated:TextTag Start");
            HttpServletRequest request =
                (HttpServletRequest) pageContext.getRequest();    
    
            JspWriter writer = pageContext.getOut();
            StringBuffer strBuff = new StringBuffer();
            if (objectField != null) { 
                ObjectFieldSizeConfig objectFieldSizeConfig = (ObjectFieldSizeConfig)ConfigLocator.findConfig(ObjectFieldSizeConfig.CONFIG_NAME);
                int size = objectFieldSizeConfig.getObjectFieldSize(objectField);
                
                strBuff.append(size);
            }
            Logger.debug(this, "modelgenerated:TextTag Start3");
            
	    writer.print(strBuff.toString());
            
	} catch (IOException e) {
	    throw new JspException("link.io", e);
	}
	// Evaluate the body of this tag
	return (EVAL_BODY_INCLUDE);
    }

    
    public int doEndTag() throws JspException {
	// Print the ending element to our output writer
	//JspWriter writer = pageContext.getOut();
	//try {
	    //writer.print("</text>");
	//} catch (IOException e) {
	//    throw new JspException("link.io", e);
	//}

	return (EVAL_PAGE);
    }
    
}
