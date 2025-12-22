/* ShowLookupTag.java
 *
 * Created on September 26, 2003, 11:13 PM
 * Copyright 2002-2005 Kevin Delargy.
 */

package com.modelgenerated.taglib;

import org.apache.commons.beanutils.PropertyUtils;
import com.modelgenerated.lookup.LookupData;
import com.modelgenerated.lookup.LookupDataList;
import com.modelgenerated.util.Assert;
import com.modelgenerated.util.StringUtil;

import java.io.IOException;
import java.util.Iterator;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

/**
 *
 * @author  kevind
 */
public class ShowLookupTag extends TagSupport {
    // from struts globals
    protected static String EOL = "\n";
    protected String name;
    protected String lookupList;
    protected String property;

    
    /** Creates a new instance of TextTag */
    public ShowLookupTag() {
    }

    public void setName(String newName) {
        name = newName;
    }
    
    public void setProperty(String newProperty) {
        property = newProperty;
    }
    
    public void setLookupList(String newLookupList) {
        lookupList = newLookupList;
    }

    public int doStartTag() throws JspException {
        // Generate the URL to be encoded
        try {
                JspWriter writer = pageContext.getOut();
                StringBuffer strBuff = new StringBuffer();

                String value = getValue();

                LookupDataList lookupDataList = getLookupDataList();
                Assert.check(lookupDataList != null, "lookupDataList != null");

                String display = value;
                String style = null;

                Iterator i = lookupDataList.iterator();
                while (i.hasNext()) {
                    LookupData lookupData = (LookupData)i.next();

                    String optionValue = lookupData.getCode();
                    if (valuesEqual(optionValue, value)) {
                        display = lookupData.getDisplay();
                        style = lookupData.getDisplayStyle();
                        break;
                    }
                }
                if (display == null) {
                    display = "";
                }

                strBuff.append("<div ");
                if (!StringUtil.isEmpty(style)) {
                    strBuff.append("class='");
                    strBuff.append(style);
                    strBuff.append("' ");
                }
                strBuff.append(">");
                strBuff.append(display);
                strBuff.append("</div>");

            writer.print(strBuff.toString());

        } catch (IOException e) {
            throw new JspException("link.io", e);
        }
        // Evaluate the body of this tag
        return (EVAL_BODY_INCLUDE);
    }

    private void writeOption(StringBuffer strBuff, String value, String display, String style, boolean selected) {
        strBuff.append("<option ");

        // option value 
        strBuff.append("value='");            
        strBuff.append(value);            
        strBuff.append("' ");   

        //Logger.debug(this, "writeOption - style:" + style);
        // select class
        if (style != null && style.length() > 0) { 
            strBuff.append("class='");
            strBuff.append(style);            
            strBuff.append("' ");            
        }
        if (selected) {
            strBuff.append("selected='selected'");
        }
        strBuff.append(">");
        strBuff.append(display);            
        strBuff.append("</option>" + EOL);
    }

    private boolean valuesEqual(String value1, String value2) {
        if (value1 == null) {
            value1 = "";
        }
        if (value2 == null) {
            value2 = "";
        }
        return value1.equals(value2);        
    }
    
    

    private LookupDataList getLookupDataList(){
        //Logger.debug(this, "getLookupDataList- name: " + name + " - lookupList: " + lookupList + "*");
        LookupDataList lookupDataList = null;
        if (lookupDataList == null) { 
            // find attribute checks all scopes
            lookupDataList = (LookupDataList)this.pageContext.findAttribute(lookupList);
        }            

        return lookupDataList;
    }
    
    private String getValue(){
        try {
            String value = null;
            if (name != null && property != null) {
                Object bean = this.pageContext.findAttribute(name);                
                if (bean != null) {
                    value = (String)PropertyUtils.getProperty(bean, property);
                }
            } else {      
                value = (String)this.pageContext.getAttribute(property);
            }
            return value;
        } catch (java.lang.IllegalAccessException e) {
            throw new RuntimeException("Error getting value", e);
        } catch (java.lang.reflect.InvocationTargetException e) {
            throw new RuntimeException("Error getting value", e);
        } catch (java.lang.NoSuchMethodException e) {
            throw new RuntimeException("Error getting value", e);
        }
    }
    
    public int doEndTag() throws JspException {
        return (EVAL_PAGE);
    }
    
}
