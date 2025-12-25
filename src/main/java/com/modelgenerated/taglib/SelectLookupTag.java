/* SelectLookupTag.java
 * Created on September 26, 2003, 11:13 PM
 * Copyright 2002-2005 Kevin Delargy.
 */

package com.modelgenerated.taglib;

import org.apache.commons.beanutils.PropertyUtils;
import com.modelgenerated.foundation.logging.Logger;
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
 * todo: clean this up. Iterface should be minimal/complete/consistent.
 * @author  kevind
 */
public class SelectLookupTag extends TagSupport {
    // from struts globals
    protected static final String LOCALE_KEY = "org.apache.struts.action.LOCALE";
    
    protected static String EOL = "\n";
    protected String name;
    protected String originalValue;
    protected String lookupList;
    protected String styleClass;
    protected String property;
    protected String propertyPrefix;
    protected String propertyId;
    protected String emptyOptionKey;
    protected String localeKey = LOCALE_KEY;

    
    /** Creates a new instance of TextTag */
    public SelectLookupTag() {
    }

    public void setName(String newName) {
        name = newName;
    }
    
    public void setOriginalValue(String newOriginalValue) {
        originalValue = newOriginalValue;
    }
    
    public void setProperty(String newProperty) {
        property = newProperty;
    }
    
    public void setPropertyPrefix(String newPropertyPrefix) {
        propertyPrefix = newPropertyPrefix;
    }
    
    public void setPropertyId(String newPropertyId) {
        propertyId = newPropertyId;
    }
    
    public void setLookupList(String newLookupList) {
        lookupList = newLookupList;
    }
    
    public void setStyleClass(String newStyleClass) {
        styleClass = newStyleClass;
    }
    
    public void setEmptyOption(String newEmptyOption) {
        emptyOptionKey = newEmptyOption;
    }
    
    public String getLocale() {
        return localeKey;
    }

    public void setLocale(String newLocaleKey) {
        localeKey = newLocaleKey;
    }

    public int doStartTag() throws JspException {
        // Generate the URL to be encoded
        try {
                JspWriter writer = pageContext.getOut();
                StringBuilder strBuff = new StringBuilder();
                strBuff.append("<select ");

                // select name
                strBuff.append("name='");
                if (!StringUtil.isEmpty(property)) {
                    strBuff.append(property);
                } else {
                    strBuff.append(propertyPrefix);
                    strBuff.append(getIdValue());
                 }
                strBuff.append("' ");

                // select class
                if (styleClass != null) {
                    strBuff.append("class='");
                    strBuff.append(styleClass);
                    strBuff.append("' ");
                }
                strBuff.append(">" + EOL);

                if (emptyOptionKey != null && emptyOptionKey.length() > 0) {
                    strBuff.append("<option value=''>");
                    strBuff.append(emptyOptionKey);
                    strBuff.append("</option>" + EOL);
                }

                String value = getSelectedValue();

                LookupDataList lookupDataList = getLookupDataList();
                Assert.check(lookupDataList != null, "lookupDataList != null");

                Iterator i = lookupDataList.iterator();
                while (i.hasNext()) {
                    LookupData lookupData = (LookupData)i.next();

                    String optionValue = lookupData.getCode();
                    String display = lookupData.getDisplay();
                    String status = lookupData.getStatus();
                    String style = lookupData.getDisplayStyle();
                    //Logger.debug(this, "doStartTag - style:" + style);

                    if (valuesEqual(optionValue, value)) {
                        writeOption(strBuff, optionValue, display, style, true);
                    } else {
                        if (!"disabled".equals(status)) {
                            writeOption(strBuff, optionValue, display, style, false);
                        }
                    }

                }

                strBuff.append("<select/>" + EOL);

            writer.print(strBuff.toString());

        } catch (IOException e) {
            throw new JspException("link.io", e);
        }
        // Evaluate the body of this tag
        return (EVAL_BODY_INCLUDE);
    }

    private void writeOption(StringBuilder strBuff, String value, String display, String style, boolean selected) {
        strBuff.append("<option ");

        // option value 
        strBuff.append("value='");            
        strBuff.append(value);            
        strBuff.append("' ");   

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
    
    private String getSelectedValue(){
        try {
            String value = null;
            if (name != null && originalValue != null) {
                Logger.debug(this, "getSelectedValue - name - " + name);
                Logger.debug(this, "getSelectedValue - originalValue - " + originalValue);
                Object bean = this.pageContext.findAttribute(name);
                Logger.debug(this, "getSelectedValue - bean.toString() - " + bean.toString());
                             
                if (bean != null) {
                    value = (String)PropertyUtils.getProperty(bean, originalValue);
                }
            } else {      
                value = (String)this.pageContext.getAttribute(originalValue);
            }
            return value;
        } catch (java.lang.IllegalAccessException e) {
            throw new RuntimeException("Error getting value", e);
        } catch (java.lang.reflect.InvocationTargetException e) {
            throw new RuntimeException("Error getting value", e);
        } catch (java.lang.NoSuchMethodException e) {
            Logger.debug(this, e);
            throw new RuntimeException("Error getting value", e);
        }
    }
    
    private String getIdValue(){
        try {
            Object value = null;
            if (name != null && propertyId != null) {
                Object bean = this.pageContext.findAttribute(name);                
                if (bean != null) {
                    value = PropertyUtils.getProperty(bean, propertyId);
                }
            } else {      
                value = this.pageContext.getAttribute(propertyId);
            }
            return value == null ? null : value.toString();
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
