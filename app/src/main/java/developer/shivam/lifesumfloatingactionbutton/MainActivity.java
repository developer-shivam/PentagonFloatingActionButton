package developer.shivam.lifesumfloatingactionbutton;

import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.graphics.Color;
import android.graphics.Point;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.Display;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    /**
     * Vertices of pentagon
     */
    Point[] pentagonVertices;

    FloatingActionButton fab;

    /**
     * Buttons to be animated
     */
    Button[] buttons;

    int height, width;

    int radius;

    int ANIMATION_DURATION = 300;

    /**
     * Coordination of button
     */
    int startPositionX = 0;
    int startPositionY = 0;

    /**
     * To check which animation is to be played
     * O for enter animation
     * 1 for exit animation
     */
    int whichAnimation = 0;

    //Polygon
    int NUM_OF_SIDES = 5;
    int POSITION_CORRECTION = 11;

    int[] enterDelay = {80, 120, 160, 40, 0};
    int[] exitDelay = {80, 40, 0, 120, 160};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        height = (int) getResources().getDimension(R.dimen.button_height);
        width = (int) getResources().getDimension(R.dimen.button_width);
        radius = (int) getResources().getDimension(R.dimen.radius);

        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(this);

        calculatePentagonVertices(radius, POSITION_CORRECTION);
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
                    (int) (radius * Math.sin(rotation + i * 2 * Math.PI / NUM_OF_SIDES)) + centerY - 100);
        }

        buttons = new Button[pentagonVertices.length];

        for (int i = 0; i < buttons.length; i++) {
            //Adding button at (0,0) coordinates and setting their visibility to zero
            buttons[i] = new Button(MainActivity.this);
            buttons[i].setLayoutParams(new RelativeLayout.LayoutParams(5, 5));
            buttons[i].setX(0);
            buttons[i].setY(0);
            buttons[i].setTag(i);
            buttons[i].setOnClickListener(this);
            buttons[i].setVisibility(View.INVISIBLE);
            buttons[i].setBackgroundResource(R.drawable.circular_background);
            buttons[i].setTextColor(Color.WHITE);
            buttons[i].setText(String.valueOf(i + 1));
            buttons[i].setTextSize(20);
            /**
             * Adding those buttons in acitvities layout
             */
            ((RelativeLayout) findViewById(R.id.activity_main)).addView(buttons[i]);
        }
    }

    @Override
    public void onClick(View view) {

        boolean isFabClicked = false;

        switch (view.getId()) {
            case R.id.fab:
                isFabClicked = true;
                if (whichAnimation == 0) {
                    /**
                     * Getting the center point of floating action button
                     *  to set start point of buttons
                     */
                    startPositionX = (int) view.getX() + 50;
                    startPositionY = (int) view.getY() + 50;

                    for (Button button : buttons) {
                        button.setX(startPositionX);
                        button.setY(startPositionY);
                        button.setVisibility(View.VISIBLE);
                    }
                    for (int i = 0; i < buttons.length; i++) {
                        playEnterAnimation(buttons[i], i);
                    }
                    whichAnimation = 1;
                } else {
                    for (int i = 0; i < buttons.length; i++) {
                        playExitAnimation(buttons[i], i);
                    }
                    whichAnimation = 0;
                }
        }

        if (!isFabClicked) {
            switch ((int) view.getTag()) {
                case 0:
                    Toast.makeText(this, "Button 1 clicked", Toast.LENGTH_SHORT).show();
                    break;
                case 1:
                    Toast.makeText(this, "Button 2 clicked", Toast.LENGTH_SHORT).show();
                    break;
                case 2:
                    Toast.makeText(this, "Button 3 clicked", Toast.LENGTH_SHORT).show();
                    break;
                case 3:
                    Toast.makeText(this, "Button 4 clicked", Toast.LENGTH_SHORT).show();
                    break;
                case 4:
                    Toast.makeText(this, "Button 5 clicked", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    }

    private void playEnterAnimation(final Button button, int position) {

        /**
         * Animator that animates buttons x and y position simultaneously with size
         */
        AnimatorSet buttonAnimator = new AnimatorSet();

        /**
         * ValueAnimator to update x position of a button
         */
        ValueAnimator buttonAnimatorX = ValueAnimator.ofFloat(startPositionX + button.getLayoutParams().width / 2,
                pentagonVertices[position].x);
        buttonAnimatorX.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                button.setX((float) animation.getAnimatedValue() - button.getLayoutParams().width / 2);
                button.requestLayout();
            }
        });
        buttonAnimatorX.setDuration(ANIMATION_DURATION);

        /**
         * ValueAnimator to update y position of a button
         */
        ValueAnimator buttonAnimatorY = ValueAnimator.ofFloat(startPositionY + 5,
                pentagonVertices[position].y);
        buttonAnimatorY.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                button.setY((float) animation.getAnimatedValue());
                button.requestLayout();
            }
        });
        buttonAnimatorY.setDuration(ANIMATION_DURATION);

        /**
         * This will increase the size of button
         */
        ValueAnimator buttonSizeAnimator = ValueAnimator.ofInt(5, width);
        buttonSizeAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                button.getLayoutParams().width = (int) animation.getAnimatedValue();
                button.getLayoutParams().height = (int) animation.getAnimatedValue();
                button.requestLayout();
            }
        });
        buttonSizeAnimator.setDuration(ANIMATION_DURATION);

        /**
         * Add both x and y position update animation in
         *  animator set
         */
        buttonAnimator.play(buttonAnimatorX).with(buttonAnimatorY).with(buttonSizeAnimator);
        buttonAnimator.setStartDelay(enterDelay[position]);
        buttonAnimator.start();
    }

    private void playExitAnimation(final Button button, int position) {

        /**
         * Animator that animates buttons x and y position simultaneously with size
         */
        AnimatorSet buttonAnimator = new AnimatorSet();

        /**
         * ValueAnimator to update x position of a button
         */
        ValueAnimator buttonAnimatorX = ValueAnimator.ofFloat(pentagonVertices[position].x - button.getLayoutParams().width / 2,
                startPositionX);
        buttonAnimatorX.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                button.setX((float) animation.getAnimatedValue());
                button.requestLayout();
            }
        });
        buttonAnimatorX.setDuration(ANIMATION_DURATION);

        /**
         * ValueAnimator to update y position of a button
         */
        ValueAnimator buttonAnimatorY = ValueAnimator.ofFloat(pentagonVertices[position].y,
                startPositionY + 5);
        buttonAnimatorY.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                button.setY((float) animation.getAnimatedValue());
                button.requestLayout();
            }
        });
        buttonAnimatorY.setDuration(ANIMATION_DURATION);

        /**
         * This will decrease the size of button
         */
        ValueAnimator buttonSizeAnimator = ValueAnimator.ofInt(width, 5);
        buttonSizeAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                button.getLayoutParams().width = (int) animation.getAnimatedValue();
                button.getLayoutParams().height = (int) animation.getAnimatedValue();
                button.requestLayout();
            }
        });
        buttonSizeAnimator.setDuration(ANIMATION_DURATION);

        /**
         * Add both x and y position update animation in
         *  animator set
         */
        buttonAnimator.play(buttonAnimatorX).with(buttonAnimatorY).with(buttonSizeAnimator);
        buttonAnimator.setStartDelay(exitDelay[position]);
        buttonAnimator.start();
    }
}
