package com.gc.materialdesign.utils;

import android.content.res.Resources;
import android.content.res.TypedArray;
import android.util.AttributeSet;

import com.gc.materialdesign.R;

/**
 * Created by Navas on 7/11/16.
 */

public class AttributesUtils {

    public static int[] attrs = {
            android.R.attr.background,
            android.R.attr.padding,
            android.R.attr.paddingLeft,
            android.R.attr.paddingTop,
            android.R.attr.paddingRight,
            android.R.attr.paddingBottom,
            android.R.attr.text,
            android.R.attr.textColor,
            android.R.attr.textSize};

    public static final int BACKGROUNDCOLOR = 0;
    public static final int PADDING = 1;
    public static final int PADDINGLEFT = 2;
    public static final int PADDINGTOP = 3;
    public static final int PADDINGRIGT = 4;
    public static final int PADDINGBOTTOM = 5;
    public static final int TEXT = 6;
    public static final int TEXTCOLOR = 7;
    public static final int TEXTSIZE = 8;



    final static String MATERIALDESIGNXML = "http://schemas.android.com/apk/res-auto";
    final static String ANDROIDXML = "http://schemas.android.com/apk/res/android";

    public static int getBackgroundColor(Resources resources, AttributeSet attrs, TypedArray typedArray){
        int bacgroundColor = attrs.getAttributeResourceValue(ANDROIDXML, "background", -1);
        if (bacgroundColor != -1) {
            return (resources.getColor(bacgroundColor));
        } else {
            // Color by hexadecimal
            // Color by hexadecimal
            bacgroundColor = attrs.getAttributeIntValue(ANDROIDXML, "background", -1);
            if (bacgroundColor != -1)
                return bacgroundColor;
            else if(typedArray != null){
                bacgroundColor = typedArray.getResourceId(BACKGROUNDCOLOR,-1);
                if(bacgroundColor != -1) {
                    return (resources.getColor(bacgroundColor));
                }else {
                    // Color by hexadecimal
                    // Color by hexadecimal
                    bacgroundColor = typedArray.getInt(BACKGROUNDCOLOR, -1);
                    if (bacgroundColor != -1)
                        return bacgroundColor;
                    }
            }
        }
        return -1;
    }

    public static int[] getPadding(Resources resources, AttributeSet attrs, TypedArray typedArray, int[] paddings){
        String value = attrs.getAttributeValue(ANDROIDXML, "padding");
        if (value != null) {
            float padding = Float.parseFloat(value.replace("dip", ""));
            paddings[0] = Utils.dpToPx(padding, resources);
            paddings[1] = paddings[0];
            paddings[2] = paddings[0];
            paddings[3] = paddings[0];
        } else if(typedArray != null){
            value = typedArray.getString(PADDING);
            if (value != null) {
                float padding = Float.parseFloat(value.replace("dip", ""));
                paddings[0] = Utils.dpToPx(padding, resources);
                paddings[1] = paddings[0];
                paddings[2] = paddings[0];
                paddings[3] = paddings[0];
            }else {
                value = attrs.getAttributeValue(ANDROIDXML, "paddingLeft");
                if(value == null){
                    value = typedArray.getString(PADDINGLEFT);
                    if(value != null) paddings[0] = Utils.dpToPx(Float.parseFloat(value.replace("dip","")), resources);
                }else paddings[0] = Utils.dpToPx(Float.parseFloat(value.replace("dip","")), resources);
                value = attrs.getAttributeValue(ANDROIDXML, "paddingTop");
                if(value == null){
                    value = typedArray.getString(PADDINGTOP);
                    if(value != null) paddings[1] = Utils.dpToPx(Float.parseFloat(value.replace("dip","")), resources);
                }else paddings[1] = Utils.dpToPx(Float.parseFloat(value.replace("dip","")), resources);
                value = attrs.getAttributeValue(ANDROIDXML, "paddingRight");
                if(value == null){
                    value = typedArray.getString(PADDINGRIGT);
                    if(value != null) paddings[2] = Utils.dpToPx(Float.parseFloat(value.replace("dip","")), resources);
                }else paddings[2] = Utils.dpToPx(Float.parseFloat(value.replace("dip","")), resources);
                value = attrs.getAttributeValue(ANDROIDXML, "paddingBottom");
                if(value == null){
                    value = typedArray.getString(PADDINGBOTTOM);
                    if(value != null) paddings[3] = Utils.dpToPx(Float.parseFloat(value.replace("dip","")), resources);
                }else paddings[3] = Utils.dpToPx(Float.parseFloat(value.replace("dip","")), resources);
            }
        }
        return paddings;
    }

    public static String getText(Resources resources, AttributeSet attrs, TypedArray typedArray){
        String text = null;
        int textResource = attrs.getAttributeResourceValue(ANDROIDXML, "text", -1);
        if (textResource != -1) {
            return resources.getString(textResource);
        } else if(attrs.getAttributeValue(ANDROIDXML, "text") != null){
           return attrs.getAttributeValue(ANDROIDXML, "text");
        }else if(typedArray != null){
            textResource = typedArray.getResourceId(TEXT,-1);
            if (textResource != -1) {
                return resources.getString(textResource);
            } else if(typedArray.getString(TEXT) != null){
                return typedArray.getString(TEXT);
            }
        }
        return null;
    }

    public static int getTextColor(Resources resources, AttributeSet attrs, TypedArray typedArray){
        int bacgroundColor = attrs.getAttributeResourceValue(ANDROIDXML, "textColor", -1);
        if (bacgroundColor != -1) {
            return (resources.getColor(bacgroundColor));
        } else {
            // Color by hexadecimal
            // Color by hexadecimal
            bacgroundColor = attrs.getAttributeIntValue(ANDROIDXML, "textColor", -1);
            if (bacgroundColor != -1)
                return bacgroundColor;
            else if(typedArray != null){
                bacgroundColor = typedArray.getResourceId(TEXTCOLOR,-1);
                if(bacgroundColor != -1) {
                    return (resources.getColor(bacgroundColor));
                }else {
                    // Color by hexadecimal
                    // Color by hexadecimal
                    bacgroundColor = typedArray.getInt(TEXTCOLOR, -1);
                    if (bacgroundColor != -1)
                        return bacgroundColor;
                }
            }
        }
        return -1;
    }

    public static float getTextSize(Resources resources, TypedArray attrs, TypedArray style){
        float textSize = attrs.getDimension(0, -1);
        attrs.recycle();
        if (textSize != -1)
            return textSize;
        else if (style != null && style.getDimension(TEXTSIZE,-1) != -1)
                return style.getDimension(TEXTSIZE,-1);
        return -1;
    }

    public static float getRippleSpeed(Resources resources, AttributeSet attrs, TypedArray style){
        float rippleSpeed = attrs.getAttributeFloatValue(MATERIALDESIGNXML,
                "rippleSpeed", -1);
        if(rippleSpeed != -1){
            return rippleSpeed;
        }else if(style != null){

        }
        return rippleSpeed
    }

}
