package developer.shivam.lifesumfloatingactionbutton;

import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.graphics.Color;
import android.graphics.Point;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.animation.FastOutSlowInInterpolator;
import android.support.v7.app.AppCompatActivity;
import android.view.Display;
import android.view.View;
import android.view.animation.AnimationSet;
import android.widget.Button;
import android.widget.RelativeLayout;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Point[] pentagonVertices;
    Button[] buttons;
    FloatingActionButton fab;

    int startPositionX = 0;
    int startPositionY = 0;

    //Polygon
    int NUM_OF_SIDES = 5;
    int POSITION_CORRECTION = 11;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(this);

        calculatePentagonVertices(200, POSITION_CORRECTION);
    }

    private void calculatePentagonVertices(int radius, int rotation) {

        pentagonVertices = new Point[NUM_OF_SIDES];

        /**
         * Calculating the center of pentagon
         */
        Display display = getWindowManager().getDefaultDisplay();
        int centerX = display.getWidth() / 2;
        int centerY = display.getHeight() / 2;

        /**
         * Calculating the coordinates of vertices of pentagon
         */
        for (int i = 0; i < NUM_OF_SIDES; i++) {
            pentagonVertices[i] = new Point((int) (radius * Math.cos(rotation + i * 2 * Math.PI / NUM_OF_SIDES)) + centerX,
                    (int) (radius * Math.sin(rotation + i * 2 * Math.PI / NUM_OF_SIDES)) + centerY);
            System.out.println("X : " + pentagonVertices[i].x);
            System.out.println("Y : " + pentagonVertices[i].y);
        }

        buttons = new Button[pentagonVertices.length];

        for (int i = 0; i < buttons.length; i++) {
            buttons[i] = new Button(MainActivity.this);
            buttons[i].setLayoutParams(new RelativeLayout.LayoutParams(100, 100));
            buttons[i].setX(500);
            buttons[i].setY(500);
            buttons[i].setBackgroundResource(R.drawable.circular_background);
            buttons[i].setTextColor(Color.WHITE);
            buttons[i].setText(String.valueOf(i + 1));
            buttons[i].setTextSize(20);
            ((RelativeLayout) findViewById(R.id.activity_main)).addView(buttons[i]);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.fab:
                startPositionX = (int) view.getX();
                startPositionY = (int) view.getY();

                AnimatorSet animationSet = new AnimatorSet();

                for (int i = 0; i < buttons.length; i++) {
                    buttons[i].setX(startPositionX);
                    buttons[i].setY(startPositionY);
                }

                ValueAnimator buttonOneAnimator = ValueAnimator.ofFloat(startPositionX + buttons[0].getLayoutParams().width/2, pentagonVertices[0].x);
                buttonOneAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator valueAnimator) {
                        buttons[0].setX((float) valueAnimator.getAnimatedValue() - buttons[0].getLayoutParams().width / 2);
                        buttons[0].requestLayout();
                    }
                });
                buttonOneAnimator.setInterpolator(new FastOutSlowInInterpolator());
                buttonOneAnimator.setDuration(500);

                ValueAnimator buttonTwoAnimator = ValueAnimator.ofFloat(startPositionX + buttons[0].getLayoutParams().width/2, pentagonVertices[1].x);
                buttonTwoAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator valueAnimator) {
                        buttons[1].setX((float) valueAnimator.getAnimatedValue() - buttons[0].getLayoutParams().width / 2);
                        buttons[1].requestLayout();
                    }
                });
                buttonTwoAnimator.setInterpolator(new FastOutSlowInInterpolator());
                buttonTwoAnimator.setDuration(500);
                buttonTwoAnimator.setStartDelay(10);

                ValueAnimator buttonThreeAnimator = ValueAnimator.ofFloat(startPositionX + buttons[0].getLayoutParams().width/2, pentagonVertices[2].x);
                buttonThreeAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator valueAnimator) {
                        buttons[2].setX((float) valueAnimator.getAnimatedValue() - buttons[0].getLayoutParams().width / 2);
                        buttons[2].requestLayout();
                    }
                });
                buttonThreeAnimator.setInterpolator(new FastOutSlowInInterpolator());
                buttonThreeAnimator.setDuration(500);
                buttonThreeAnimator.setStartDelay(20);

                ValueAnimator buttonFourAnimator = ValueAnimator.ofFloat(startPositionX + buttons[0].getLayoutParams().width/2, pentagonVertices[3].x);
                buttonFourAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator valueAnimator) {
                        buttons[3].setX((float) valueAnimator.getAnimatedValue() - buttons[0].getLayoutParams().width / 2);
                        buttons[3].requestLayout();
                    }
                });
                buttonFourAnimator.setInterpolator(new FastOutSlowInInterpolator());
                buttonFourAnimator.setDuration(500);
                buttonFourAnimator.setStartDelay(30);

                ValueAnimator buttonFiveAnimator = ValueAnimator.ofFloat(startPositionX + buttons[0].getLayoutParams().width/2, pentagonVertices[4].x);
                buttonFiveAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator valueAnimator) {
                        buttons[4].setX((float) valueAnimator.getAnimatedValue() - buttons[0].getLayoutParams().width / 2);
                        buttons[4].requestLayout();
                    }
                });
                buttonFiveAnimator.setInterpolator(new FastOutSlowInInterpolator());
                buttonFiveAnimator.setDuration(500);
                buttonFiveAnimator.setStartDelay(40);

                /////////////////////////////////////////////////////////////////////

                ValueAnimator buttonOneAnimatorY = ValueAnimator.ofFloat(startPositionY , pentagonVertices[0].y);
                buttonOneAnimatorY.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator valueAnimator) {
                        buttons[0].setY((float) valueAnimator.getAnimatedValue());
                        buttons[0].requestLayout();
                    }
                });
                buttonOneAnimatorY.setInterpolator(new FastOutSlowInInterpolator());
                buttonOneAnimatorY.setDuration(500);

                ValueAnimator buttonTwoAnimatorY = ValueAnimator.ofFloat(startPositionY, pentagonVertices[1].y);
                buttonTwoAnimatorY.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator valueAnimator) {
                        buttons[1].setY((float) valueAnimator.getAnimatedValue());
                        buttons[1].requestLayout();
                    }
                });
                buttonTwoAnimatorY.setInterpolator(new FastOutSlowInInterpolator());
                buttonTwoAnimatorY.setDuration(500);

                ValueAnimator buttonThreeAnimatorY = ValueAnimator.ofFloat(startPositionY, pentagonVertices[2].y);
                buttonThreeAnimatorY.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator valueAnimator) {
                        buttons[2].setY((float) valueAnimator.getAnimatedValue());
                        buttons[2].requestLayout();
                    }
                });
                buttonThreeAnimatorY.setInterpolator(new FastOutSlowInInterpolator());
                buttonThreeAnimatorY.setDuration(500);

                ValueAnimator buttonFourAnimatorY = ValueAnimator.ofFloat(startPositionY, pentagonVertices[3].y);
                buttonFourAnimatorY.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator valueAnimator) {
                        buttons[3].setY((float) valueAnimator.getAnimatedValue());
                        buttons[3].requestLayout();
                    }
                });
                buttonFourAnimatorY.setInterpolator(new FastOutSlowInInterpolator());
                buttonFourAnimatorY.setDuration(500);

                ValueAnimator buttonFiveAnimatorY = ValueAnimator.ofFloat(startPositionY, pentagonVertices[4].y);
                buttonFiveAnimatorY.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator valueAnimator) {
                        buttons[4].setY((float) valueAnimator.getAnimatedValue());
                        buttons[4].requestLayout();
                    }
                });
                buttonFiveAnimatorY.setInterpolator(new FastOutSlowInInterpolator());
                buttonFiveAnimatorY.setDuration(500);

                animationSet.playTogether(buttonOneAnimator, buttonTwoAnimator, buttonThreeAnimator, buttonFourAnimator, buttonFiveAnimator,
                        buttonOneAnimatorY, buttonTwoAnimatorY, buttonThreeAnimatorY, buttonFourAnimatorY, buttonFiveAnimatorY);
                animationSet.start();
        }
    }
}
