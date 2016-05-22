package simple.ticket;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.widget.LinearLayout;

/**
 * Created by fenghao on 2016/5/22.
 */
public class Ticket extends LinearLayout {

    private Paint mPaint;
    /**
     * 横向三角形的个数
     */
    private int widthCountNum;
    /**
     * 竖直方向三角形的个数
     */
    private int heightCountNum;
    /**
     * 三角形底边一半的长度
     */
    private float halfLine;
    /**
     * 三角形的高度
     */
    private float triHeight;
    /**
     * 画完水平三角形后，剩余不足以画一个完整三角形的宽度的一半
     */
    private float halfRemainWidth;
    /**
     * 画完竖直三角形后，剩余不足以画一个完整三角形的高度的一半
     */
    private float halfRemainHeight;

    private Context context;


    //四个边的三角形是否画出来
    private boolean isDrawDown;
    private boolean isDrawUp;
    private boolean isDrawLeft;
    private boolean isDrawRight;

    public Ticket(Context context) {
        this(context, null);
    }

    public Ticket(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public Ticket(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.Ticket);

        isDrawUp = ta.getBoolean(R.styleable.Ticket_draw_up, true);
        isDrawRight = ta.getBoolean(R.styleable.Ticket_draw_right, true);
        isDrawDown = ta.getBoolean(R.styleable.Ticket_draw_down, true);
        isDrawLeft = ta.getBoolean(R.styleable.Ticket_draw_left, true);
        halfLine = ta.getDimension(R.styleable.Ticket_half_line, 40);
        triHeight = ta.getDimension(R.styleable.Ticket_tri_height, 30);
        init();
    }

    private void init(){
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        TypedArray array =context.getTheme().obtainStyledAttributes(new int[]{
                android.R.attr.colorBackground,
                android.R.attr.textColorPrimary,
        });
        int backgroundColor = array.getColor(0, 0xFF00FF);
        mPaint.setColor(backgroundColor);
        mPaint.setStyle(Paint.Style.FILL);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        halfRemainWidth = (int)(w % (halfLine * 2) / 2);
        halfRemainHeight = (int)(h % (halfLine * 2) / 2);
        widthCountNum = (int) (w / (halfLine * 2));
        heightCountNum = (int) (h / halfLine * 2);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        float x = halfRemainWidth;
        Path path = new Path();
        //画横向三角形
        for (int i = 0 ; i < widthCountNum ; i++){
            //画上三角
            if (isDrawUp){
                path.moveTo(x, 0);
                path.lineTo(x + halfLine, triHeight);
                path.lineTo(x + 2 * halfLine, 0);
            }

            //画下三角
            if (isDrawDown){
                path.moveTo(x, getHeight());
                path.lineTo(x + halfLine, getHeight() - triHeight);
                path.lineTo(x + 2 * halfLine, getHeight());
            }

            canvas.drawPath(path, mPaint);
            path.reset();

            x += halfLine * 2;
        }

        float y = halfRemainHeight;
        for (int i = 0; i < heightCountNum ; i++){
            //画左三角

            if (isDrawLeft){
                path.moveTo(0, y);
                path.lineTo(triHeight, y + halfLine);
                path.lineTo(0, y + halfLine * 2);
            }

            //画右三角
            if (isDrawRight){
                path.moveTo(getWidth(), y);
                path.lineTo(getWidth() - triHeight, y + halfLine);
                path.lineTo(getWidth(), y + 2 * halfLine);
            }

            canvas.drawPath(path, mPaint);
            path.reset();

            y += halfLine * 2;
        }
        path.close();
    }
}
