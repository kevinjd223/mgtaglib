/*
 * DateFormatTag.java
 *
 * Created on September 26, 2003, 11:13 PM
 * Copyright 2002-2005 Kevin Delargy.
 */

package com.modelgenerated.taglib;

import org.apache.commons.beanutils.PropertyUtils;
import com.modelgenerated.util.Assert;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

/**
 *
 * @author  kevind
 */
public class DateFormatTag extends TagSupport {
    String name;
    String property;
    
    /** Creates a new instance of TextTag */
    public DateFormatTag() {
    }
    
    public void setName(String newName) {
        //Logger.debug(this, "setName: " + newName);
        name = newName;
    }
    
    public void setProperty(String newProperty) {
        //Logger.debug(this, "setProperty: " + newProperty);
        property = newProperty;
    }
    
    public int doStartTag() throws JspException {
        // Generate the URL to be encoded
        try {
                //Logger.debug(this, "modelgenerated:TextTag Start");
                JspWriter writer = pageContext.getOut();
                StringBuffer strBuff = new StringBuffer();
                //Logger.debug(this, "modelgenerated:TextTag Start2");
                String value = getValue();
                strBuff.append(value);

            writer.print(strBuff.toString());

        } catch (IOException e) {
            throw new JspException("link.io", e);
        }
        // Evaluate the body of this tag
        return (EVAL_BODY_INCLUDE);
    }

    private String getValue(){
        String value = null;
        try {
            if (name != null && property != null) {
                Object bean = this.pageContext.findAttribute(name);                
                // UNDONE: throw a modelgeneratedtag exception
                Assert.check(bean != null, "Could not find form: " + name);
                if (bean != null) {
                    Date date = (Date)PropertyUtils.getProperty(bean, property);
                    value = formatDate(date, "MM/dd/yyyy");
                }
            } else {                
                value = (String)this.pageContext.getAttribute(property);
            }
        } catch (java.lang.IllegalAccessException e) {
        } catch (java.lang.reflect.InvocationTargetException e) {
        } catch (java.lang.NoSuchMethodException e) {
        }
        if (value == null) {
            value = "";
        }
        return value;
    }
    
    public int doEndTag() throws JspException {
	    return (EVAL_PAGE);
    }

    public String formatDate(Date date, String format) {
        String formattedDate = null;
        if (date != null) {                                                      
            SimpleDateFormat  SimpleDateFormat = new SimpleDateFormat(format);
            formattedDate = SimpleDateFormat.format(date);
        }
        return formattedDate;
    }
    
    
}
