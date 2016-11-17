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
            android.R.attr.textSize,
            R.attr.MLrippleSpeed,
            R.attr.MLclickAfterRipple,
            R.attr.MLrippleColor,
            R.attr.MLanimated,
            R.attr.MLshowNumberIndicator,
            R.attr.MLmax,
            R.attr.MLmin,
            R.attr.MLvalue,
            R.attr.MLprogress,
            R.attr.MLringWidth,
            R.attr.MLchecked,
            R.attr.MLiconDrawable,
            R.attr.MLrippleBorderRadius};

    public static final int BACKGROUNDCOLOR = 0;
    public static final int PADDING = 1;
    public static final int PADDING_LEFT = 2;
    public static final int PADDING_TOP = 3;
    public static final int PADDING_RIGT = 4;
    public static final int PADDING_BOTTOM = 5;
    public static final int TEXT = 6;
    public static final int TEXT_COLOR = 7;
    public static final int TEXT_SIZE = 8;
    public static final int RIPPLE_SPEED = 9;
    public static final int CLICK_AFTER_RIPPLE = 10;
    public static final int RIPPLE_COLOR = 11;
    public static final int ANIMATED = 12;
    public static final int SHOW_NUMBER_INDICATOR = 13;
    public static final int MAX = 14;
    public static final int MIN = 15;
    public static final int VALUE = 16;
    public static final int PROGRESS = 17;
    public static final int RING_WIDTH = 18;
    public static final int CHECKED = 19;
    public static final int ICON_DRAWABLE = 20;
    public static final int RIPPLE_BORDER_RADIUS = 21;


    final static String ML_RIPPLE_SPEED = "MLrippleSpeed";
    final static String ML_CLICK_AFTER_RIPPLE = "MLclickAfterRipple";
    final static String ML_RIPPLE_COLOR = "MLrippleColor";
    final static String ML_ANIMATED = "MLanimated";
    final static String ML_SHOW_NUMBER_INDICATOR = "MLshowNumberIndicator";
    final static String ML_MAX = "MLmax";
    final static String ML_MIN = "MLmin";
    final static String ML_VALUE = "MLvalue";
    final static String ML_PROGRESS = "MLprogress";
    final static String ML_RING_WIDTH = "MLringWidth";
    final static String ML_CHECKED = "MLchecked";
    final static String ML_CHECKBOX_SIZE = "MLcheckBoxSize";
    final static String ML_THUMB_SIZE = "MLthumbSize";
    final static String ML_ICON_DRAWABLE = "MLiconDrawable";
    final static String ML_ICON_SIZE = "MLiconSize";
    final static String ML_RIPPLE_BORDER_RADIUS = "MLrippleBorderRadius";



    final static String MATERIALDESIGNXML = "http://schemas.android.com/apk/res-auto";
    final static String ANDROIDXML = "http://schemas.android.com/apk/res/android";

    public static int getBackgroundColor(Resources resources, AttributeSet attrs, TypedArray typedArray){
        return getColor(resources, attrs, typedArray, "background", BACKGROUNDCOLOR);
    }

    public static int getTextColor(Resources resources, AttributeSet attrs, TypedArray typedArray){
        return getColor(resources, attrs, typedArray, "textColor", TEXT_COLOR);
    }

    private static int getColor(Resources resources, AttributeSet attrs, TypedArray typedArray, String name, int position){
        return getColor(resources, attrs, typedArray, ANDROIDXML, name, position);
    }

    private static int getColor(Resources resources, AttributeSet attrs, TypedArray typedArray, String xml, String name, int position){
        int bacgroundColor = attrs.getAttributeResourceValue(xml, name, -1);
        if (bacgroundColor != -1) {
            return (resources.getColor(bacgroundColor));
        } else {
            // Color by hexadecimal
            // Color by hexadecimal
            bacgroundColor = attrs.getAttributeIntValue(xml, name, -1);
            if (bacgroundColor != -1)
                return bacgroundColor;
            else if(typedArray != null){
                bacgroundColor = typedArray.getResourceId(position,-1);
                if(bacgroundColor != -1) {
                    return (resources.getColor(bacgroundColor));
                }else {
                    // Color by hexadecimal
                    // Color by hexadecimal
                    bacgroundColor = typedArray.getInt(position, -1);
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
                    value = typedArray.getString(PADDING_LEFT);
                    if(value != null) paddings[0] = Utils.dpToPx(Float.parseFloat(value.replace("dip","")), resources);
                }else paddings[0] = Utils.dpToPx(Float.parseFloat(value.replace("dip","")), resources);
                value = attrs.getAttributeValue(ANDROIDXML, "paddingTop");
                if(value == null){
                    value = typedArray.getString(PADDING_TOP);
                    if(value != null) paddings[1] = Utils.dpToPx(Float.parseFloat(value.replace("dip","")), resources);
                }else paddings[1] = Utils.dpToPx(Float.parseFloat(value.replace("dip","")), resources);
                value = attrs.getAttributeValue(ANDROIDXML, "paddingRight");
                if(value == null){
                    value = typedArray.getString(PADDING_RIGT);
                    if(value != null) paddings[2] = Utils.dpToPx(Float.parseFloat(value.replace("dip","")), resources);
                }else paddings[2] = Utils.dpToPx(Float.parseFloat(value.replace("dip","")), resources);
                value = attrs.getAttributeValue(ANDROIDXML, "paddingBottom");
                if(value == null){
                    value = typedArray.getString(PADDING_BOTTOM);
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

    public static float getTextSize(Resources resources, TypedArray attrs, TypedArray style){
        float textSize = attrs.getDimension(0, -1);
        attrs.recycle();
        if (textSize != -1)
            return textSize;
        else if (style != null && style.getDimension(TEXT_SIZE,-1) != -1)
            return style.getDimension(TEXT_SIZE,-1);
        return -1;
    }

    public static float getRippleSpeed(Resources resources, AttributeSet attrs, TypedArray style){
        float rippleSpeed = attrs.getAttributeFloatValue(MATERIALDESIGNXML,
                ML_RIPPLE_SPEED, -1);
        if(rippleSpeed != -1){
            return rippleSpeed;
        }else if(style != null){
            rippleSpeed = style.getFloat(RIPPLE_SPEED, -1);
        }
        return rippleSpeed;
    }

    public static boolean getClickAfterRipple(Resources resources, AttributeSet attrs, TypedArray style, boolean defaaultValue){
        return getBoolean(resources, attrs, style, ML_CLICK_AFTER_RIPPLE, CLICK_AFTER_RIPPLE, defaaultValue);
    }

    public static int getRippleColor(Resources resources, AttributeSet attrs, TypedArray typedArray){
        return getColor(resources, attrs, typedArray,MATERIALDESIGNXML, ML_RIPPLE_COLOR, RIPPLE_COLOR);
    }

    public static boolean getAnimated(Resources resources, AttributeSet attrs, TypedArray style, boolean defaaultValue){
        return getBoolean(resources, attrs, style, ML_ANIMATED, ANIMATED, defaaultValue);
    }

    public static boolean getShowNumberIndicator(Resources resources, AttributeSet attrs, TypedArray style, boolean defaaultValue){
        return getBoolean(resources, attrs, style, ML_SHOW_NUMBER_INDICATOR, SHOW_NUMBER_INDICATOR, defaaultValue);
    }

    public static float getRipleBorderRadius(Resources resources, AttributeSet attrs, TypedArray style){
        return getFloat(resources, attrs, style, ML_RIPPLE_BORDER_RADIUS, RIPPLE_BORDER_RADIUS);
    }

    public static int getMax(Resources resources, AttributeSet attrs, TypedArray style, int defaultValue){
        return getInt(resources,attrs, style, ML_MAX, MAX,defaultValue);
    }

    public static int getMin(Resources resources, AttributeSet attrs, TypedArray style, int defaultValue){
        return getInt(resources,attrs, style, ML_MIN, MIN,defaultValue);
    }

    public static int getValue(Resources resources, AttributeSet attrs, TypedArray style, int defaultValue){
        return getInt(resources,attrs, style, ML_VALUE, VALUE,defaultValue);
    }

    public static int getProgress(Resources resources, AttributeSet attrs, TypedArray style, int defaultValue){
        return getInt(resources,attrs, style, ML_PROGRESS, PROGRESS,defaultValue);
    }

    public static boolean getChecked(Resources resources, AttributeSet attrs, TypedArray style, boolean defaultValue){
        return getBoolean(resources, attrs, style, ML_CHECKED, CHECKED,defaultValue);
    }

    public static int getIconDrawable(Resources resources, AttributeSet attrs, TypedArray style){
        int iconDrawable = attrs.getAttributeResourceValue(MATERIALDESIGNXML,
                ML_ICON_DRAWABLE, -1);
        if(iconDrawable != -1){
            return iconDrawable;
        }else if(style != null){
            iconDrawable = style.getResourceId(ICON_DRAWABLE, -1);
        }
        return iconDrawable;
    }

    public static boolean getBoolean(Resources resources, AttributeSet attrs, TypedArray style, String name, int position, boolean defaultValue){
        boolean bool = attrs.getAttributeBooleanValue(MATERIALDESIGNXML,
                name, defaultValue);
        return style.getBoolean(position,bool);
    }

    public static int getInt(Resources resources, AttributeSet attrs, TypedArray style, String name, int position, int defaultValue){
        int integer = attrs.getAttributeIntValue(MATERIALDESIGNXML,
                name, defaultValue);
        if(integer != defaultValue){
            return integer;
        }else if(style != null){
            integer = style.getInt(position, defaultValue);
        }
        return integer;
    }

    public static float getFloat(Resources resources, AttributeSet attrs, TypedArray style, String name, int position){
        float _float = attrs.getAttributeFloatValue(MATERIALDESIGNXML,
                name, -1);
        if(_float != -1){
            return _float;
        }else if(style != null){
            _float = style.getFloat(position, -1);
        }
        return _float;
    }



}
