package cn.bingoogolapple.badgeview;

import android.graphics.Rect;

import java.util.Random;

/**
 * 作者:王浩 邮件:bingoogolapple@gmail.com
 * 创建时间:15/12/5 上午12:18
 * 描述:
 */
public class BGAParticle {
    float centerX;
    float centerY;
    float radius;

    int color;
    float alpha;

    private static Random mRandom = new Random();
    private Rect mBound;

    public static BGAParticle generateParticle(int partWidthHeight, int color, Rect bound, int column, int row) {
        BGAParticle particle = new BGAParticle();
        particle.mBound = bound;
        particle.color = color;
        particle.alpha = 1f;
        particle.radius = partWidthHeight;
        particle.centerX = bound.left + partWidthHeight * column;
        particle.centerY = bound.top + partWidthHeight * row;
        return particle;
    }

    public void advance(float factor) {
        centerX = centerX + factor * mRandom.nextInt(mBound.width()) * (mRandom.nextFloat() - 0.5f);
        centerY = centerY + factor * mRandom.nextInt(mBound.height() / 2);
        radius = radius - factor * mRandom.nextInt(2);
        alpha = (1f - factor) * (1 + mRandom.nextFloat());
    }

}