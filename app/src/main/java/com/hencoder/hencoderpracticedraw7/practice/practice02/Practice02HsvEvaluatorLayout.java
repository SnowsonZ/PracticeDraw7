package com.hencoder.hencoderpracticedraw7.practice.practice02;

import android.animation.ObjectAnimator;
import android.animation.TypeEvaluator;
import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.hencoder.hencoderpracticedraw7.R;

public class Practice02HsvEvaluatorLayout extends RelativeLayout {
    Practice02HsvEvaluatorView view;
    Button animateBt;

    public Practice02HsvEvaluatorLayout(Context context) {
        super(context);
    }

    public Practice02HsvEvaluatorLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public Practice02HsvEvaluatorLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();

        view = (Practice02HsvEvaluatorView) findViewById(R.id.objectAnimatorView);
        animateBt = (Button) findViewById(R.id.animateBt);

        animateBt.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                ObjectAnimator animator = ObjectAnimator.ofInt(view, "color", 0xffff0000, 0xff00ff00);
                animator.setEvaluator(new HsvEvaluator()); // 使用自定义的 HsvEvaluator
                animator.setInterpolator(new LinearInterpolator());
                animator.setDuration(2000);
                animator.start();
            }
        });
    }

    private class HsvEvaluator implements TypeEvaluator<Integer> {

        // 重写 evaluate() 方法，让颜色按照 HSV 来变化
        @Override
        public Integer evaluate(float fraction, Integer startValue, Integer endValue) {
            float[] startHSV = new float[3];
            float[] endHSV = new float[3];
            float[] outHSV = new float[3];
            Color.colorToHSV(startValue, startHSV);
            Color.colorToHSV(endValue, endHSV);

            if (endHSV[0] - startHSV[0] > 180) {
                endHSV[0] -= 360;
            } else if (endHSV[0] - startHSV[0] < -180) {
                endHSV[0] += 360;
            }
            outHSV[0] = startHSV[0] + (endHSV[0] - startHSV[0]) * fraction;
            if (outHSV[0] > 360) {
                outHSV[0] -= 360;
            } else if (outHSV[0] < 0) {
                outHSV[0] += 360;
            }
            outHSV[1] = startHSV[1] + (endHSV[1] - startHSV[1]) * fraction;
            outHSV[2] = startHSV[2] + (endHSV[2] - startHSV[2]) * fraction;
            return Color.HSVToColor(outHSV);
        }
    }
}
