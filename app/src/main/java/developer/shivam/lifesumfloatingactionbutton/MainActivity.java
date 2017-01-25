package developer.shivam.lifesumfloatingactionbutton;

import android.graphics.Point;
import android.support.annotation.MainThread;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.Window;
import android.widget.Button;
import android.widget.RelativeLayout;

public class MainActivity extends AppCompatActivity {

    Point[] pentagonVertices = new Point[5];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        calculatePentagonVertices(200);

        Button[] buttons = new Button[pentagonVertices.length];

        for (int i = 0; i < buttons.length ; i++) {
            buttons[i] = new Button(MainActivity.this);
            buttons[i].setX(pentagonVertices[i].x - buttons[i].getWidth()/2);
            buttons[i].setY(pentagonVertices[i].y - buttons[i].getHeight()/2);
            buttons[i].setLayoutParams(new RelativeLayout.LayoutParams(50, 50));
            buttons[i].setBackgroundResource(R.drawable.circular_background);
            ((RelativeLayout) findViewById(R.id.activity_main)).addView(buttons[i]);
        }
    }

    private void calculatePentagonVertices(int radius) {

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
            pentagonVertices[i] = new Point((int) (radius * Math.cos(i * 2 * Math.PI / 5)) + centerX / 2, (int) (radius * Math.sin(i * 2 * Math.PI / 5) + centerY / 2));
            System.out.println("X : " + pentagonVertices[i].x);
            System.out.println("Y : " + pentagonVertices[i].y);
        }
    }
}
