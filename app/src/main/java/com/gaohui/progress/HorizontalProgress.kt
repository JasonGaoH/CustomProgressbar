package com.gaohui.progress

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.util.Log
import android.util.TypedValue
import android.widget.ProgressBar
import com.gaohui.customprogressbar.R

/**
 * Created by gaohui on 18/1/14.
 */
open class HorizontalProgress @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null,
                                                   defStyleAttr: Int = 0) : ProgressBar(context, attrs, defStyleAttr) {
    companion object  {
        val DEFAULT_REACH_COLOR = 0xff0000
        val DEFAULT_REACH_HEIGHT = 2f //dp
        val DEFAULT_UNREACH_HEIGHT = 2f //dp
        val DEFAULT_UNREACH_COLOR = 0x00ff00
        val DEFAULT_TEXT_COLOR = 0x0000ff
        val DEFAULT_TEXT_SIZE = 10f //sp
        val DEFAULT_TEXT_OFFSET = 10f //dp

    }

    protected var mTextSize = sp2px(DEFAULT_TEXT_SIZE)
    protected var mReachColor = DEFAULT_REACH_COLOR
    protected var mReachHeight = dp2px(DEFAULT_REACH_HEIGHT)
    protected var mUnReachHeight = dp2px(DEFAULT_UNREACH_HEIGHT)
    protected var mUnReachColor = DEFAULT_UNREACH_COLOR
    protected var mTextColor = DEFAULT_TEXT_COLOR
    protected var mTextOffset = DEFAULT_TEXT_OFFSET

    private var mRealWidth = 0f

    protected val mPaint = Paint()

    init {
        //获取自定义属性
        val a = getContext().obtainStyledAttributes(attrs, R.styleable.HorizontalProgress)

        mReachColor = a.getColor(R.styleable.HorizontalProgress_progress_reach_color,DEFAULT_REACH_COLOR)
        mUnReachColor = a.getColor(R.styleable.HorizontalProgress_progress_unreach_color,DEFAULT_UNREACH_COLOR)
        mTextSize = a.getDimension(R.styleable.HorizontalProgress_progress_text_size,mTextSize)
        mReachHeight = a.getDimension(R.styleable.HorizontalProgress_progress_reach_height,mReachHeight)
        mUnReachHeight = a.getDimension(R.styleable.HorizontalProgress_progress_unreach_height,mUnReachHeight)
        mTextColor = a.getColor(R.styleable.HorizontalProgress_progress_text_color,DEFAULT_TEXT_COLOR)
        mTextOffset = a.getDimension(R.styleable.HorizontalProgress_progress_text_offset,mTextOffset)


        a.recycle()

        mPaint.textSize = mTextSize
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val widthVal = MeasureSpec.getSize(widthMeasureSpec)

        val height = measureHeight(heightMeasureSpec)

        setMeasuredDimension(widthVal,height)
        mRealWidth = (measuredWidth - paddingLeft - paddingRight).toFloat()
    }

    private fun measureHeight(heightMeasureSpec: Int): Int {
        var result: Int
        val heightMode = MeasureSpec.getMode(heightMeasureSpec)
        val heightVal = MeasureSpec.getSize(heightMeasureSpec)
        if(heightMode == MeasureSpec.EXACTLY) {
            result = heightVal
        } else {
            val textHeight = (mPaint.descent() - mPaint.ascent()).toInt()
            result = paddingTop + paddingBottom + Math.max(textHeight,Math.max(mReachHeight.toInt(),mUnReachHeight.toInt()))
            if(heightMode == MeasureSpec.AT_MOST) {
                result = Math.min(result,heightVal)
            }
        }

        return result
    }

    override fun onDraw(canvas: Canvas) {
        canvas.save()
        canvas.translate(paddingLeft.toFloat(),height.toFloat()/2)

        var isNeedUnReach = false

        val radio:Float = progress.toFloat()/max.toFloat()
        val text = progress.toString() + "%"
        val textWidth = mPaint.measureText(text)
        var progressX = mRealWidth * radio
        if(progressX + textWidth  > mRealWidth) {
            isNeedUnReach = true
            progressX = mRealWidth - textWidth
        }
        val endX = progressX - mTextOffset/2
        //if(endX > mRealWidth - maxTextWidth -mTextOffset) endX = mRealWidth - maxTextWidth -mTextOffset

        if(endX > 0) {
            mPaint.color = mReachColor
            mPaint.strokeWidth = mReachHeight
            canvas.drawLine(0f,0f,endX,0f,mPaint)
        }

        mPaint.color = mTextColor
        val y = -(mPaint.descent() + mPaint.ascent())/2
        canvas.drawText(text,progressX,y,mPaint)


        if(!isNeedUnReach) {
            val start = progressX + textWidth + mTextOffset/2
            mPaint.color = mUnReachColor
            mPaint.strokeWidth = mUnReachHeight
            canvas.drawLine(start,0f,mRealWidth,0f,mPaint)
        }
        canvas.restore()


    }

    protected fun dp2px(dpVal:Float):Float{
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,dpVal,resources.displayMetrics)
    }

    private fun sp2px(spVal:Float):Float {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,spVal,resources.displayMetrics)
    }



}