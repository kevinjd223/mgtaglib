/*
 * DateFormatTag.java
 *
 * Created on September 26, 2003, 11:13 PM
 * Copyright 2002-2005 Kevin Delargy.
 */

package com.modelgenerated.taglib;

import org.apache.commons.beanutils.PropertyUtils;
import com.modelgenerated.util.Assert;
import com.modelgenerated.util.NumberUtil;

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
public class NumberFormatTag extends TagSupport {
    String name;
    String property;
    String minPrecision;
    String maxPrecision;
    
    /** Creates a new instance of TextTag */
    public NumberFormatTag() {
    }
    
    public void setName(String newName) {
        name = newName;
    }
    
    public void setProperty(String newProperty) {
        property = newProperty;
    }
    
    public void setMinPrecision(String newMinPrecision) {
        minPrecision = newMinPrecision;
    }
    
    public void setMaxPrecision(String newMaxPrecision) {
        maxPrecision = newMaxPrecision;
    }
    
    public int doStartTag() throws JspException {
    	// Generate the URL to be encoded
    	try {
            JspWriter writer = pageContext.getOut();
            StringBuffer strBuff = new StringBuffer();
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
                    Double doubleValue = (Double)PropertyUtils.getProperty(bean, property);

                    Integer min = Integer.valueOf(minPrecision);
                    Integer max = Integer.valueOf(maxPrecision);
                    
                    if (doubleValue == null || doubleValue.isNaN()) {
                        // TODO: allow the user to set a string to use if value is null or NaN 
                        return ""; 
                    } else if (min != null && max != null) {
                        value = NumberUtil.format(doubleValue, min.intValue(), max.intValue());                        
                    } else if (max != null) {
                        value = NumberUtil.format(doubleValue, max.intValue());                        
                    } else {
                        value = "" + doubleValue;
                    }
                }
            } else {                
                value = (String)this.pageContext.getAttribute(property);
            }
        } catch (java.lang.IllegalAccessException e) {
        } catch (java.lang.reflect.InvocationTargetException e) {
        } catch (java.lang.NoSuchMethodException e) {
        } catch (java.lang.NumberFormatException e) {
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
