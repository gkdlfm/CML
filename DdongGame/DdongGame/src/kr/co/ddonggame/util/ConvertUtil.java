package kr.co.ddonggame.util;

import android.content.Context;
import android.content.res.Resources;
import android.util.DisplayMetrics;

public class ConvertUtil {
	public static float convertPixelToDp(float px, Context context) {
		Resources resources = context.getResources();
		DisplayMetrics metrics = resources.getDisplayMetrics();
		float dp = px / (metrics.densityDpi / 160f);
		return dp;
	}
}
