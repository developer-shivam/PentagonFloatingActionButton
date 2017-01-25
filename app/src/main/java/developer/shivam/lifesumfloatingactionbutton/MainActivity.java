package developer.shivam.lifesumfloatingactionbutton;

import android.graphics.Color;
import android.graphics.Point;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Display;
import android.widget.Button;
import android.widget.RelativeLayout;

public class MainActivity extends AppCompatActivity {

    Point[] pentagonVertices = new Point[5];
    Button[] buttons;
    int POSITION_CORRECTION = 11;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        calculatePentagonVertices(200, POSITION_CORRECTION);
    }

    private void calculatePentagonVertices(int radius, int rotation) {

        /**
         * Calculating the center of pentagon
         */
        Display display = getWindowManager().getDefaultDisplay();
        int centerX = display.getWidth() / 2;
        int centerY = display.getHeight() / 2;

        /**
         * Calculating the coordinates of vertices of pentagon
         */
        for (int i = 0; i < 5; i++) {
            pentagonVertices[i] = new Point((int) (radius * Math.cos(rotation + i * 2 * Math.PI / 5)) + centerX,
                    (int) (radius * Math.sin(rotation + i * 2 * Math.PI / 5)) + centerY);
        }

        buttons = new Button[pentagonVertices.length];

        for (int i = 0; i < buttons.length ; i++) {
            buttons[i] = new Button(MainActivity.this);
            buttons[i].setLayoutParams(new RelativeLayout.LayoutParams(100, 100));
            buttons[i].setX(pentagonVertices[i].x - buttons[i].getWidth()/2);
            buttons[i].setY(pentagonVertices[i].y - buttons[i].getHeight()/2);
            buttons[i].setBackgroundResource(R.drawable.circular_background);
            buttons[i].setTextColor(Color.WHITE);
            buttons[i].setText(String.valueOf(i + 1));
            buttons[i].setTextSize(20);
            ((RelativeLayout) findViewById(R.id.activity_main)).addView(buttons[i]);
        }
    }
}
