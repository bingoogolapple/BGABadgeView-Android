/**
 * Copyright 2015 bingoogolapple
 * <p/>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p/>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p/>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package cn.bingoogolapple.badgeview;

import android.animation.ValueAnimator;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Interpolator;

import java.util.Random;

/**
 * 作者:王浩 邮件:bingoogolapple@gmail.com
 * 创建时间:15/12/5 上午12:21
 * 描述:参考https://github.com/tyrantgit/ExplosionField改成了只有一个View的情况,只刷新徽章附近的区域
 */
public class BGAExplosionAnimator extends ValueAnimator {
    public static final int ANIM_DURATION = 300;
    private static final Interpolator DEFAULT_INTERPOLATOR = new AccelerateInterpolator(0.6f);
    private static final float END_VALUE = 1.4f;
    private static final int REFRESH_RATIO = 3;
    private static float X;
    private static float Y;
    private static float V;
    private static float W;

    private Particle[] mParticles;
    private Paint mPaint;
    private BGADragBadgeView mDragBadgeView;
    private Rect mRect;
    private Rect mInvalidateRect;

    public BGAExplosionAnimator(BGADragBadgeView dragBadgeView, Rect rect, Bitmap bitmap) {
        setFloatValues(0.0f, END_VALUE);
        setDuration(ANIM_DURATION);
        setInterpolator(DEFAULT_INTERPOLATOR);

        X = BGABadgeViewUtil.dp2px(dragBadgeView.getContext(), 5);
        Y = BGABadgeViewUtil.dp2px(dragBadgeView.getContext(), 20);
        V = BGABadgeViewUtil.dp2px(dragBadgeView.getContext(), 2);
        W = BGABadgeViewUtil.dp2px(dragBadgeView.getContext(), 1);

        mPaint = new Paint();
        mDragBadgeView = dragBadgeView;
        mRect = rect;
        mInvalidateRect = new Rect(mRect.left - mRect.width() * REFRESH_RATIO, mRect.top - mRect.height() * REFRESH_RATIO, mRect.right + mRect.width() * REFRESH_RATIO, mRect.bottom + mRect.height() * REFRESH_RATIO);

        int partLen = 15;
        mParticles = new Particle[partLen * partLen];
        Random random = new Random(System.currentTimeMillis());
        int w = bitmap.getWidth() / (partLen + 2);
        int h = bitmap.getHeight() / (partLen + 2);
        for (int i = 0; i < partLen; i++) {
            for (int j = 0; j < partLen; j++) {
                mParticles[(i * partLen) + j] = generateParticle(bitmap.getPixel((j + 1) * w, (i + 1) * h), random);
            }
        }
    }

    private Particle generateParticle(int color, Random random) {
        Particle particle = new Particle();
        particle.color = color;
        particle.radius = V;
        if (random.nextFloat() < 0.2f) {
            particle.baseRadius = V + ((X - V) * random.nextFloat());
        } else {
            particle.baseRadius = W + ((V - W) * random.nextFloat());
        }
        float nextFloat = random.nextFloat();
        particle.top = mRect.height() * ((0.18f * random.nextFloat()) + 0.2f);
        particle.top = nextFloat < 0.2f ? particle.top : particle.top + ((particle.top * 0.2f) * random.nextFloat());
        particle.bottom = (mRect.height() * (random.nextFloat() - 0.5f)) * 1.8f;
        float f = nextFloat < 0.2f ? particle.bottom : nextFloat < 0.8f ? particle.bottom * 0.6f : particle.bottom * 0.3f;
        particle.bottom = f;
        particle.mag = 4.0f * particle.top / particle.bottom;
        particle.neg = (-particle.mag) / particle.bottom;
        f = mRect.centerX() + (Y * (random.nextFloat() - 0.5f));
        particle.baseCx = f + mRect.width() / 2;
        particle.cx = particle.baseCx;
        f = mRect.centerY() + (Y * (random.nextFloat() - 0.5f));
        particle.baseCy = f;
        particle.cy = f;
        particle.life = END_VALUE / 10 * random.nextFloat();
        particle.overflow = 0.4f * random.nextFloat();
        particle.alpha = 1f;
        return particle;
    }

    public void draw(Canvas canvas) {
        if (!isStarted()) {
            return;
        }
        for (Particle particle : mParticles) {
            particle.advance((float) getAnimatedValue());
            if (particle.alpha > 0f) {
                mPaint.setColor(particle.color);
                mPaint.setAlpha((int) (Color.alpha(particle.color) * particle.alpha));
                canvas.drawCircle(particle.cx, particle.cy, particle.radius, mPaint);
            }
        }
        postInvalidate();
    }

    @Override
    public void start() {
        super.start();
        postInvalidate();
    }

    /**
     * 只刷新徽章附近的区域
     */
    private void postInvalidate() {
        mDragBadgeView.postInvalidate(mInvalidateRect.left, mInvalidateRect.top, mInvalidateRect.right, mInvalidateRect.bottom);
    }

    private class Particle {
        float alpha;
        int color;
        float cx;
        float cy;
        float radius;
        float baseCx;
        float baseCy;
        float baseRadius;
        float top;
        float bottom;
        float mag;
        float neg;
        float life;
        float overflow;

        public void advance(float factor) {
            float f = 0f;
            float normalization = factor / END_VALUE;
            if (normalization < life || normalization > 1f - overflow) {
                alpha = 0f;
                return;
            }
            normalization = (normalization - life) / (1f - life - overflow);
            float f2 = normalization * END_VALUE;
            if (normalization >= 0.7f) {
                f = (normalization - 0.7f) / 0.3f;
            }
            alpha = 1f - f;
            f = bottom * f2;
            cx = baseCx + f;
            cy = (float) (baseCy - this.neg * Math.pow(f, 2.0)) - f * mag;
            radius = V + (baseRadius - V) * f2;
        }
    }
}