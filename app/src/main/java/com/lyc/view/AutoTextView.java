package com.lyc.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewTreeObserver;

import java.util.ArrayList;
import java.util.List;

public class AutoTextView extends View {
    //内容填充画笔  
    private Paint contentPaint ;
    //标准的字体颜色  
    private String contentNormalColor = "#737373" ;  
    //有焦点的字体颜色  
    private String contentFocuedColor = "#333333" ;  
    //控件宽度  
    private int viewWidth = 0 ;  
    //控件高度  
    private int viewHeight = 0 ;  
    //标准的字的样式  
    public static final int TEXT_TYPE_NORMAL = 1 ;  
    //控件获取焦点的时候进行的处理  
    public static final int TEXT_TYPE_FOCUED = 2 ;  
    //默认是标准的样式  
    private int currentTextType = TEXT_TYPE_NORMAL ;  
    //默认当前的颜色  
    private String currentTextColor = contentNormalColor ;  
    //字体大小  
    private int textSize = 40 ;  
    //内容  
    private String content = "测试的文字信息" ;  
    //最小view高度  
    private float minHeight = 0 ;  
    //最小view宽度  
    private float minWidth = 0 ;  
    //行间距  
    private float lineSpace ;  
    //最大行数  
    private int maxLines = 0 ;  
    //文字测量工具  
    private Paint.FontMetricsInt textFm ;  
    ///真实的行数  
    private int realLines ;  
    //当前显示的行数  
    private int line ;  
    /**省略号**/  
    private String ellipsis = "..." ;  
      
    private boolean isFirstLoad = true ;  
      
      
      
    public AutoTextView(Context context) {
        super(context);  
  
        initViews() ;  
    }  
  
    public AutoTextView(Context context, AttributeSet attrs) {
        super(context, attrs);  
  
        initViews() ;  
    }  
  
    public AutoTextView(Context context, AttributeSet attrs, int defStyleAttr) {  
        super(context, attrs, defStyleAttr);  
  
        initViews() ;  
    }  
      
    private void initViews(){  
        contentPaint = new Paint();    
        contentPaint.setTextSize(textSize);    
        contentPaint.setColor(Color.parseColor(currentTextColor));
        contentPaint.setTextAlign(Paint.Align.LEFT);  
          
        textFm = contentPaint.getFontMetricsInt() ;  
          
          
        getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener(){
  
            @Override  
            public boolean onPreDraw() {  
                if(isFirstLoad){  
                    minHeight = measureTextHeight();  
                    invalidate();  
                    isFirstLoad = false ;  
                }  
                  
                return true;  
            }  
              
        });  
    }  
  
    /** 
     * 设置字体样式 
     * @param type 
     */  
    public void setTextStyle(int type){  
        this.currentTextType = type ;  
  
        changeStyle(currentTextType);  
    }  
  
    //切换字体样式（颜色、大小等）  
    private void changeStyle(int type){  
        switch (type){  
            case TEXT_TYPE_NORMAL:  
                currentTextColor = contentNormalColor ;  
                break;  
            case TEXT_TYPE_FOCUED:  
                currentTextColor = contentFocuedColor ;  
                break;  
        }  
    }  
  
    /** 
     * 设置字体大小 
     * @param textSize 
     */  
    public void setTextSize(int textSize){  
        this.textSize = textSize ;  
        contentPaint.setTextSize(this.textSize);  
        //重新设置了字体大小后需要重新获取FontMetricsInt（必须重置）  
        textFm = contentPaint.getFontMetricsInt() ;  
    }  
      
    /** 
     * 设置文字内容 
     * @param text 
     */  
    public void setText(String text){  
        this.content = text ;  
    }  
      
    /** 
     * 设置行间距 
     * @param lineSpace 
     */  
    public void setLineSpace(float lineSpace){  
        this.lineSpace = lineSpace ;  
    }  
      
    /** 
     * 设置最大行数 
     * @param maxLines 
     */  
    public void setMaxLines(int maxLines){  
        this.maxLines = maxLines ;  
        invalidate();  
    }  
  
    private void drawText(Canvas canvas, String text, int baseline){
        canvas.drawText(text, 0, baseline, contentPaint);  
    }  
      
    //循环绘制每行文字  
    private void drawText(Canvas canvas){  
        List<String> array = getTexts(content) ;
        //line是当前要绘制的行数，当指定最大行数的时候，此时line就是最大行数  
        for(int i=0;i<line;i++){  
            String text = array.get(i) ;  
            int textHeight = (int)Math.ceil(textFm.descent - textFm.ascent) ;  
            int baseline = textHeight/2 - textFm.descent + (textFm.descent - textFm.ascent) / 2;  
              
            if(realLines>line){  
                //此时需要显示省略号  
                if(i==line-1){  
                    if(getTextWidth(contentPaint, text)>viewWidth-getEllipsisWidth()){  
                        //开始截取字符串，让省略号加入进去刚好凑一行  
                        for(int j=0;j<text.length();j++){  
                            int width = getTextWidth(contentPaint, text.substring(0, j)) ;  
                            //计算到下一位字符的字符串的长度  
                            int widthNext = getTextWidth(contentPaint, text.substring(0, j+1)) ;  
                              
                            if(width<=viewWidth-getEllipsisWidth() && widthNext>viewWidth-getEllipsisWidth()){  
                                text = text.substring(0, j)+ellipsis ;  
                                break ;  
                            }  
                        }  
                    }else{  
                        text = text + ellipsis ;  
                    }  
                }  
            }  
              
            drawText(canvas, text, (int) (lineSpace*i)+(int)Math.ceil(textFm.descent - textFm.ascent)*i + baseline);  
        }  
    }  
      
    //把每行文字加载到列表中  
    private List<String> getTexts(String allText){  
        List<String> result = new ArrayList<String>() ;
          
        int off = 0 ;  
        for(int i=0;i<allText.length();i++){  
            //计算剩下的字符串长度  
            int lastWidth = getTextWidth(contentPaint, allText.substring(off, allText.length())) ;  
            //计算指定坐标的字符串的长度  
            int width = getTextWidth(contentPaint, allText.substring(off, i)) ;  
            //计算到下一位字符的字符串的长度  
            int widthNext = getTextWidth(contentPaint, allText.substring(off, i+1)) ;  
              
            if(lastWidth>viewWidth){  
                if(width<=viewWidth && widthNext>viewWidth){  
                    result.add(allText.substring(off, i)) ;  
                    off = i ;  
                }  
            }else{  
                result.add(allText.substring(off, allText.length())) ;  
            }  
        }  
          
          
        return result;  
    }  
  
    @Override  
    protected void onDraw(Canvas canvas) {  
        super.onDraw(canvas);  
        //开始绘制文字  
        drawText(canvas);  
    }  
      
    /** 
     * 计算省略号的长度 
     * @return 
     */  
    private int getEllipsisWidth(){  
        return getTextWidth(contentPaint, ellipsis) ;  
    }  
      
    /** 
     * 计算文本区域高度（含行间距） 
     * @return 
     */  
    private float measureTextHeight(){  
        float result = 0 ;  
        //计算文字总长度  
        float textWidth = getTextWidth(contentPaint, content) ;  
        //计算应该有的行数  
        line = (int) (textWidth%getMeasuredWidth()>0?textWidth/getMeasuredWidth()+1:textWidth/getMeasuredWidth()) ;  
        //设置真实的行数  
        realLines = line ;  
        //如果设置了最大行数，那么就只显示最大行数  
        if(line>maxLines && maxLines>0) line = maxLines ;   
        //计算文字高度  
        float lineHeight = (float)Math.ceil(textFm.descent - textFm.ascent);  
        //计算最终文本区的高度  
        result = line*lineHeight + lineSpace*(line-1) ;  
          
        return result ;  
    }  
      
    /** 
     * 计算文字总长度 
     * @param paint 
     * @param str 
     * @return 
     */  
    private int getTextWidth(Paint paint, String str) {  
        int iRet = 0;    
        if (str != null && str.length() > 0) {    
            int len = str.length();    
            float[] widths = new float[len];    
            paint.getTextWidths(str, widths);    
            for (int j = 0; j < len; j++) {    
                iRet += (int) Math.ceil(widths[j]);    
            }    
        }    
        return iRet;    
    }  
      
  
    @Override  
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {  
        viewWidth  = measureDimension((int)minWidth, widthMeasureSpec);  
        viewHeight = measureDimension((int)minHeight, heightMeasureSpec);  
          
        setMeasuredDimension(viewWidth, viewHeight);                  
    }  
      
    protected int measureDimension(int defaultSize, int measureSpec ) {  
          
        int result = defaultSize;  
          
        int specMode = MeasureSpec.getMode(measureSpec);  
        int specSize = MeasureSpec.getSize(measureSpec);  
                  
        //1. layout给出了确定的值，比如：100dp  
        //2. layout使用的是match_parent，但父控件的size已经可以确定了，比如设置的是具体的值或者match_parent  
        if (specMode == MeasureSpec.EXACTLY) {        
            result = specSize; //建议：result直接使用确定值  
        }   
        //1. layout使用的是wrap_content  
        //2. layout使用的是match_parent,但父控件使用的是确定的值或者wrap_content  
        else if (specMode == MeasureSpec.AT_MOST) {              
            result = Math.min(defaultSize, specSize); //建议：result不能大于specSize  
        }   
        //UNSPECIFIED,没有任何限制，所以可以设置任何大小  
        //多半出现在自定义的父控件的情况下，期望由自控件自行决定大小  
        else {        
            result = defaultSize;  
        }  
          
        return result;  
    }  
      
}  