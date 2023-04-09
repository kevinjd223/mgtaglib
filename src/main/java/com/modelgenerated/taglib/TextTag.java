/*
 * TextTag.java
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
public class TextTag extends TagSupport {
    String name;
    String objectField;
    String styleClass;
    String property;
    String style;
    String required;
    
    /** Creates a new instance of TextTag */
    public TextTag() {
    }
    
    public void setObjectField(String newObjectField) {
        objectField = newObjectField;
    }
    
    public void setName(String newName) {
        //Logger.debug(this, "setName: " + newName);
        name = newName;
    }
    
    public void setProperty(String newProperty) {
        //Logger.debug(this, "setProperty: " + newProperty);
        property = newProperty;
    }
    
    public void setStyleClass(String newStyleClass) {
        styleClass = newStyleClass;
    }
    
    public void setStyle(String newStyle) {
        style = newStyle;
    }
    
    public void setRequired(String newRequired) {
        required = newRequired;
    }
    
    public int doStartTag() throws JspException {
	// Generate the URL to be encoded
	try {
            Logger.debug(this, "modelgenerated:TextTag Start");
            HttpServletRequest request =
                (HttpServletRequest) pageContext.getRequest();    
    
            JspWriter writer = pageContext.getOut();
            StringBuffer strBuff = new StringBuffer();
            strBuff.append("<input type='text' ");
            Logger.debug(this, "modelgenerated:TextTag Start2");
            strBuff.append("name='");            
            strBuff.append(property);            
            strBuff.append("' ");            
            Logger.debug(this, "modelgenerated:TextTag Start2.1");
            strBuff.append("id='");            
            strBuff.append(property);            
            strBuff.append("' ");            
            String value = getValue();
            if (value != null) {
                strBuff.append("value='");
                strBuff.append(value);
                strBuff.append("' ");
            }            
            if (objectField != null) { 
                ObjectFieldSizeConfig objectFieldSizeConfig = (ObjectFieldSizeConfig)ConfigLocator.findConfig(ObjectFieldSizeConfig.CONFIG_NAME);
                int size = objectFieldSizeConfig.getObjectFieldSize(objectField);
                
                strBuff.append("maxlength='");
                strBuff.append(size);
                strBuff.append("' ");
            }
            if (styleClass != null) { 
                strBuff.append("class='");
                strBuff.append(styleClass);            
                strBuff.append("' ");            
            }
            if (style != null) { 
                strBuff.append("style='");
                strBuff.append(style);            
                strBuff.append("' ");            
            }
            if (required != null) { 
                strBuff.append("required='");
                strBuff.append(required);            
                strBuff.append("' ");            
            }
            Logger.debug(this, "modelgenerated:TextTag Start3");
            strBuff.append("/>");
            
	    writer.print(strBuff.toString());
            
	} catch (IOException e) {
	    throw new JspException("link.io", e);
	}
	// Evaluate the body of this tag
	return (EVAL_BODY_INCLUDE);
    }

    private String getValue(){
        try {
            String value = null;
            if (name != null && property != null) {
                Object bean = this.pageContext.findAttribute(name);                
                // UNDONE: throw a modelgeneratedtag exception
                Assert.check(bean != null, "Could not find form: " + name);
                if (bean != null) {
                    value = (String)PropertyUtils.getProperty(bean, property);
                }
            } else {                
                value = (String)this.pageContext.getAttribute(property);
            }
            if (value != null) {
                value = StringUtil.httpEncode(value);
            }
            
            return value;
        } catch (java.lang.IllegalAccessException e) {
        } catch (java.lang.reflect.InvocationTargetException e) {
        } catch (java.lang.NoSuchMethodException e) {
        }
        return null;
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
