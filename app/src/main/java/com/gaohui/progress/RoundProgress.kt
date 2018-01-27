package com.gaohui.progress

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View
import com.gaohui.customprogressbar.R

/**
 * Created by gaohui on 18/1/18.
 */
class RoundProgress @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null,
                                              defStyleAttr: Int = 0) : HorizontalProgress(context, attrs, defStyleAttr)  {

    companion object {
        val DEFAULT_RADIUS = 20f
    }

    private var mProgressRadius:Float = dp2px(DEFAULT_RADIUS)

    private var mMaxStrokenWidth = 0f

    private var mRectF:RectF? = null

    init {

        mReachHeight = mUnReachHeight * 2.5f
        //获取自定义属性
        val a = getContext().obtainStyledAttributes(attrs, R.styleable.RoundProgress)

        mProgressRadius = a.getDimension(R.styleable.RoundProgress_progress_radius, mProgressRadius)
        mRectF = RectF(0f,0f,mProgressRadius * 2,mProgressRadius * 2)

        a.recycle()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        mMaxStrokenWidth = Math.max(mReachHeight,mUnReachHeight)
        val expect = (mProgressRadius * 2 + mMaxStrokenWidth + paddingLeft + paddingRight).toInt()

        val width = resolveSize(expect,widthMeasureSpec)
        val height =  View.resolveSize(expect,heightMeasureSpec)

        setMeasuredDimension(width,height)
    }

    override fun onDraw(canvas: Canvas) {

        val text = progress.toString() + "%"
        val textWidth = mPaint.measureText(text)
        val textHeight = (mPaint.descent() + mPaint.ascent())/2

        //canvas.drawCircle(mProgressRadius,mProgressRadius,mProgressRadius,mPaint)

        canvas.save()

        canvas.translate(paddingLeft.toFloat()+ mMaxStrokenWidth/2,paddingTop.toFloat() + mMaxStrokenWidth/2)
        mPaint.isAntiAlias = true
        //draw unReach
        mPaint.strokeWidth = mUnReachHeight
        mPaint.color = mUnReachColor
        mPaint.style = Paint.Style.STROKE

        canvas.drawCircle(mProgressRadius,mProgressRadius,mProgressRadius,mPaint)

        //draw Reach
        mPaint.color = mReachColor
        mPaint.strokeWidth = mReachHeight

        val sweepAngle = progress * 1.0f/max * 360
        canvas.drawArc(mRectF,0f,sweepAngle,false,mPaint)

        //canvas.drawArc()
        mPaint.style = Paint.Style.FILL
        mPaint.color = mTextColor
        canvas.drawText(text,mProgressRadius - textWidth/2,mProgressRadius - textHeight,mPaint)

        canvas.restore()
    }


}