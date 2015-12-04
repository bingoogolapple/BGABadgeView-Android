package cn.bingoogolapple.badgeview;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

import com.nineoldandroids.animation.ValueAnimator;

/**
 * 作者:王浩 邮件:bingoogolapple@gmail.com
 * 创建时间:15/12/5 上午12:21
 * 描述:
 */
public class BGAExplosionAnimator extends ValueAnimator {
    private static final int ANIM_DURATION = 1000;
    private BGAParticle[][] mParticles;
    private Paint mPaint;
    private BGADragBadgeView mDragBadgeView;

    public BGAExplosionAnimator(BGADragBadgeView dragBadgeView, Bitmap bitmap, Rect bound) {
        setFloatValues(0.0f, 1.0f);
        setDuration(ANIM_DURATION);

        mPaint = new Paint();
        mDragBadgeView = dragBadgeView;
        mParticles = generateParticles(bitmap, bound);
    }

    private BGAParticle[][] generateParticles(Bitmap bitmap, Rect bound) {
        int partWidthHeight = BGABadgeViewUtil.dp2px(mDragBadgeView.getContext(), 2);
        int horizontalCount = bound.width() / partWidthHeight;
        int verticalCount = bound.height() / partWidthHeight;

        int bitmapPartWidth = bitmap.getWidth() / horizontalCount;
        int bitmapPartHeight = bitmap.getHeight() / verticalCount;

        BGAParticle[][] particles = new BGAParticle[verticalCount][horizontalCount];
        for (int row = 0; row < verticalCount; row++) {
            for (int column = 0; column < horizontalCount; column++) {
                int color = bitmap.getPixel(column * bitmapPartWidth, row * bitmapPartHeight);
                particles[row][column] = BGAParticle.generateParticle(partWidthHeight, color, bound, column, row);
            }
        }
        return particles;
    }

    public void draw(Canvas canvas) {
        if (!isStarted()) {
            return;
        }
        for (BGAParticle[] particleArr : mParticles) {
            for (BGAParticle particle : particleArr) {
                particle.advance((Float) getAnimatedValue());
                mPaint.setColor(particle.color);
                mPaint.setAlpha((int) (Color.alpha(particle.color) * particle.alpha));
                canvas.drawCircle(particle.centerX, particle.centerY, particle.radius, mPaint);
            }
        }
        mDragBadgeView.postInvalidate();
    }

    @Override
    public void start() {
        super.start();
        mDragBadgeView.postInvalidate();
    }
}