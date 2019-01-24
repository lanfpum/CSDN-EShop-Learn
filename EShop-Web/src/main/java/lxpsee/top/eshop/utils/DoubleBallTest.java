package lxpsee.top.eshop.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * The world always makes way for the dreamer
 * Created by 努力常态化 on 2018/12/25 12:19.
 */
public class DoubleBallTest {
    public static void main(String[] args) {
        genDoubleBallNO();
    }

    public static void genDoubleBallNO() {
        Set<Integer> redBalls = new HashSet<Integer>();

        for (int i = 0; i < 6; i++) {
            int red = (int) (Math.random() * 33 + 1);
            redBalls.add(red);
        }

        if (redBalls.size() < 6) {
            int redBall = (int) (Math.random() * 33 + 1);
            redBalls.add(redBall);
        } else {
            int blueBall = (int) (Math.random() * 16 + 1);
            ArrayList<Integer> redBallList = new ArrayList<Integer>(redBalls);
            Collections.sort(redBallList);
            int[] arrBei = {2, 3, 4, 5, 10};
            int bei = arrBei[(int) (Math.random() * 5)];
            System.out.println("红球：" + redBallList + " 篮球" + blueBall + " + " + bei + " 倍");
        }

    }
}
