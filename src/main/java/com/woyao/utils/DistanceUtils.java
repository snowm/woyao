package com.woyao.utils;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.woyao.GlobalConfig;

@Component("distanceUtils")
public class DistanceUtils {

	@Resource(name = "globalConfig")
	private GlobalConfig globalConfig;

	public double distance(double lat1, double long1, double lat2, double long2) {
		double a, b;
		lat1 = lat1 * Math.PI / 180.0;
		lat2 = lat2 * Math.PI / 180.0;
		a = lat1 - lat2;
		b = (long1 - long2) * Math.PI / 180.0;
		double d;
		double sa2, sb2;
		sa2 = Math.sin(a / 2.0);
		sb2 = Math.sin(b / 2.0);
		d = 2 * globalConfig.getEarthRadius() * Math.asin(Math.sqrt(sa2 * sa2 + Math.cos(lat1) * Math.cos(lat2) * sb2 * sb2));
		return d;
	}

	/**
	 * 计算经纬度点对应正方形4个点的坐标
	 * 
	 * @param latitude
	 * @param longitude
	 * @param distance
	 *            单位米
	 * @return
	 */
	public SquareItudes returnLLSquarePoint(double latitude, double longitude, double distance) {
		// 计算经度弧度,从弧度转换为角度
		double dLongitude = 2
				* (Math.asin(Math.sin(distance / (2 * globalConfig.getLongitudeRadius())) / Math.cos(Math.toRadians(latitude))));

		dLongitude = Math.toDegrees(dLongitude);

		// 计算纬度角度
		double dLatitude = distance / globalConfig.getLatitudeRadius();
		dLatitude = Math.toDegrees(dLatitude);

		// 正方形
		double[] leftTopPoint = { latitude + dLatitude, longitude - dLongitude };
		double[] rightTopPoint = { latitude + dLatitude, longitude + dLongitude };
		double[] leftBottomPoint = { latitude - dLatitude, longitude - dLongitude };
		double[] rightBottomPoint = { latitude - dLatitude, longitude + dLongitude };

		SquareItudes square = new SquareItudes();
		square.leftTop = leftTopPoint;
		square.rightTop = rightTopPoint;
		square.rightBottom = rightBottomPoint;
		square.leftBottom = leftBottomPoint;

		return square;
	}

	public class SquareItudes {
		private double[] leftTop;
		private double[] rightTop;
		private double[] rightBottom;
		private double[] leftBottom;

		public double[] getLeftTop() {
			return leftTop;
		}

		public double[] getRightTop() {
			return rightTop;
		}

		public double[] getRightBottom() {
			return rightBottom;
		}

		public double[] getLeftBottom() {
			return leftBottom;
		}
	}

	// @Before
	// public void init(){
	// GlobalConfig cfg = new GlobalConfig();
	// cfg.setEarthRadius(6371004);
	// cfg.setLatitudeRadius(6378140);
	// cfg.setLongitudeRadius(6356755);
	// this.globalConfig = cfg;
	// }
	//
	// @Test
	// public void testD() {
	//
	// SquareItudes itudes = this.returnLLSquarePoint(30.775154, 103.932918,
	// 2000);
	// System.out.println("leftTop: lat:" + itudes.leftTop[0] + ", lng:" +
	// itudes.leftTop[1]);
	// System.out.println("rightTop: lat:" + itudes.rightTop[0] + ", lng:" +
	// itudes.rightTop[1]);
	// System.out.println("rightBottom: lat:" + itudes.rightBottom[0] + ", lng:"
	// + itudes.rightBottom[1]);
	// System.out.println("leftBottom: lat:" + itudes.leftBottom[0] + ", lng:" +
	// itudes.leftBottom[1]);
	// }
	//
	// @Test
	// public void testDistance(){
	// double rs = this.distance(30.79312029723182, 103.91193671631343,
	// 30.775154, 103.932918);
	// System.out.println(rs);
	// }
}
