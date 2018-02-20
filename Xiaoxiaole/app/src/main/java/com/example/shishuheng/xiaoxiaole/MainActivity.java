package com.example.shishuheng.xiaoxiaole;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Handler handlerDialog;
        handlerDialog = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setPositiveButton("退出游戏", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        System.exit(0);
                    }
                });
                builder.setIcon(R.drawable.mix).setTitle("Your Score: ").setMessage("" + msg.what).create().show();
            }
        };
        TextView score = (TextView) findViewById(R.id.score);
        LinearLayout container = (LinearLayout) findViewById(R.id.container);
        container.setOrientation(LinearLayout.VERTICAL);
        ArrayList<ArrayList> row = new ArrayList<>();
        for(int i = 0; i < 10; i++) {
            LinearLayout llot = new LinearLayout(getApplicationContext());
            llot.setOrientation(LinearLayout.HORIZONTAL);
            ArrayList<ViewTable> column = new ArrayList<>();
            for (int j = 0; j < 10; j++) {
                ViewTable viewTable = new ViewTable(llot, row, score, handlerDialog, i, j, getApplicationContext());
                llot.addView(viewTable.getIv());
                column.add(viewTable);
            }
            container.addView(llot);
            row.add(column);
        }
    }

    @Override
    public void onBackPressed() {
        System.exit(0);
    }
}

class ViewTable {
    public ImageView iv;
    public TextView scoreIV;
    private int color;
    private int position[] = {-1, -1};
    private boolean flag;
    public static int sum = 0;
    private static int summaryScore = 0;
    public String text = "";

    public ImageView getIv() {
        return iv;
    }

    public void setFlag(boolean tf) {
        flag = tf;
    }

    public boolean getFlag() {
        return flag;
    }

    public void setColor(int c) {
        color = c;
    }

    public void setPosition(int x, int y) {
        position[0] = x;
        position[1] = y;
    }

    public int getColor() {
        return color;
    }
    public int getSum() {
        return sum;
    }

    public void cleanIV() {
        iv.setImageResource(R.drawable.blank);
    }

    public void setIVColor(int c) {
        switch (c) {
            case 1:iv.setImageResource(R.drawable.kiwi);break;
            case 2:iv.setImageResource(R.drawable.orange);break;
            case 3:iv.setImageResource(R.drawable.tomato);break;
            case 4:iv.setImageResource(R.drawable.watermelon);break;
            default:iv.setImageResource(R.drawable.blank);break;
        }
    }

    ViewTable(LinearLayout column, final ArrayList<ArrayList> row, TextView scoreTextView, final Handler handleDialog, final int x, final int y, final Context context) {
        scoreIV = scoreTextView;
        iv = new ImageView(context);
        iv.setLayoutParams(new ActionBar.LayoutParams(108, 108));
        color = (int)(1+Math.random()*(4-1+1));
        setPosition(x, y);
        setIVColor(getColor());
        flag = true;
//        iv.setTextSize(20);
//        column.addView(iv);
//        text = "x:"+x+" y:"+y;
//        iv.setText(text);
//        iv.setImageResource(R.drawable.blank);
        iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Handler handler = new Handler(){
                    @Override
                    public void handleMessage(Message msg) {
                        upRemove(row, x, y);
                        downRemove(row, x, y);
                        leftRemove(row, x, y);
                        rightRemove(row, x, y);
                        fallDown(10, 10, row);
                        if (checkOver(row, 10, 10)) {
                            Message message = new Message();
                            message.what = summaryScore;
                            handleDialog.sendMessage(message);
                        }
                        super.handleMessage(msg);
                    }
                };
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Message message = new Message();
                        message.what = summaryScore;
                        handler.sendMessage(message);
                    }
                }).start();
//                fallDown(10, 10, row);
            }
        });
    }

    public boolean remove(ViewTable vt) {
        if(vt != null) {
            if(vt.getColor() == getColor()) {
                sum++;
                cleanIV();
                vt.cleanIV();
                setFlag(false);
                vt.setFlag(false);
                return true;
            }
        }
        return false;
    }
    public void upRemove(ArrayList<ArrayList> aa, int x, int y) {
        if(x >= 0 && y >= 0 && x < 10 && y < 10) {
            ViewTable viewTable = (ViewTable) aa.get(x).get(y);
            if(viewTable.getColor() == getColor() && viewTable.getFlag() == true) {
                sum++;
                cleanIV();
                viewTable.cleanIV();
                setFlag(false);
                viewTable.setFlag(false);

                upRemove(aa, x-1, y);
                downRemove(aa, x+1, y);
                leftRemove(aa, x, y-1);
                rightRemove(aa, x, y+1);
            }
        }
    }
    public void downRemove(ArrayList<ArrayList> aa, int x, int y) {
        if(x >= 0 && y >= 0 && x < 10 && y < 10) {
            ViewTable viewTable = (ViewTable) aa.get(x).get(y);
            if(viewTable.getColor() == getColor() && viewTable.getFlag() == true) {
                sum++;
                cleanIV();
                viewTable.cleanIV();
                setFlag(false);
                viewTable.setFlag(false);

                upRemove(aa, x-1, y);
                downRemove(aa, x+1, y);
                leftRemove(aa, x, y-1);
                rightRemove(aa, x, y+1);
            }
        }
    }
    public void leftRemove(ArrayList<ArrayList> aa, int x, int y) {
        if(x >= 0 && y >= 0 && x < 10 && y < 10) {
            ViewTable viewTable = (ViewTable) aa.get(x).get(y);
            if(viewTable.getColor() == getColor() && viewTable.getFlag() == true) {
                sum++;
                cleanIV();
                viewTable.cleanIV();
                setFlag(false);
                viewTable.setFlag(false);

                upRemove(aa, x-1, y);
                downRemove(aa, x+1, y);
                leftRemove(aa, x, y-1);
                rightRemove(aa, x, y+1);
            }
        }
    }
    public void rightRemove(ArrayList<ArrayList> aa, int x, int y) {
        if(x >= 0 && y >= 0 && x < 10 && y < 10) {
            ViewTable viewTable = (ViewTable) aa.get(x).get(y);
            if(viewTable.getColor() == getColor() && viewTable.getFlag() == true) {
                sum++;
                cleanIV();
                viewTable.cleanIV();
                setFlag(false);
                viewTable.setFlag(false);

                upRemove(aa, x-1, y);
                downRemove(aa, x+1, y);
                leftRemove(aa, x, y-1);
                rightRemove(aa, x, y+1);
            }
        }
    }

    public void fallColumn(ArrayList<ArrayList> aa, int row, int column) {
        int bakrow = row;
        ArrayList<Integer> recColor = new ArrayList<>();
        while (row >= 0) {
            ViewTable vt = (ViewTable) aa.get(row).get(column);
            if (vt.getFlag() == true) {
                recColor.add(vt.getColor());
            }
            row--;
        }
        for (int j = 0; j <= bakrow; j++) {
            ViewTable viewTable = (ViewTable) aa.get(j).get(column);
            viewTable.setColor(5);
            viewTable.setFlag(false);
            viewTable.setIVColor(viewTable.getColor());
        }
        for (int i = 0; i < recColor.size(); i++) {
            ViewTable viewTable = (ViewTable) aa.get(bakrow - i).get(column);
            viewTable.setColor(recColor.get(i));
            viewTable.setFlag(true);
            viewTable.setIVColor(viewTable.getColor());
        }
    }

    public void fallRow(ArrayList<ArrayList> aa, int row, int column) {
        int bakcolumn = column;
        boolean flag = true;
        ArrayList<Integer> recColor = new ArrayList<>();
        while (column >= 0) {
            ViewTable vt = (ViewTable) aa.get(row).get(column);
            if (vt.getFlag() == true) {
                recColor.add(vt.getColor());
            }
            column--;
        }
        for (int j = 0; j <= bakcolumn; j++) {
            ViewTable viewTable = (ViewTable) aa.get(row).get(j);
            viewTable.setColor(5);
            viewTable.setFlag(false);
            viewTable.setIVColor(viewTable.getColor());
        }
        for (int i = 0; i < recColor.size(); i++) {
            ViewTable viewTable = (ViewTable) aa.get(row).get(bakcolumn - i);
            viewTable.setColor(recColor.get(i));
            viewTable.setFlag(true);
            viewTable.setIVColor(viewTable.getColor());
        }
    }

    public void fallDown(int row, int column, ArrayList<ArrayList> aa) {
        for (int j = 0; j < column; j++) {
            fallColumn(aa, row - 1, j);
        }
        for (int i = 0; i < row; i++) {
            fallRow(aa, i, column - 1);
        }
        summaryScore += getCurrentRoundScore();
        scoreIV.setText("score: " + summaryScore);
    }

    public int getCurrentRoundScore() {
//        int score = (int)Math.log10((float)sum) * sum;
        int score = sum * sum;
        sum = 0;
        return score;
    }

    public boolean checkOver(ArrayList<ArrayList> aa, int row, int column) {
        for(int i = 0; i < row; i++) {
            for (int j = 0; j < column; j++) {
                if (((ViewTable) aa.get(i).get(j)).getFlag() == true)
                    return false;
            }
        }
        return true;
    }
}
