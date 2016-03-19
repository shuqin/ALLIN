package zzz.study.patterns.builder;

public class UnforgivingBuilder extends ReservationBuilder {
	
	public Reservation build() throws ReservationException {
		
		// 若任一为空，则视为无效请求
		if (date==null) {
			throw new ReservationException("没有指定日期，无效的预定申请！\n");
		}	
		if (headcount==0) {
			throw new ReservationException("没有指定总人数，无效的预定申请！\n");
		}
		if (city==null) {
			throw new ReservationException("没有指定地点，无效的预定申请！\n");
		}
		if (yuanPerHead==0) {
			throw new ReservationException("没有指定单人费用，无效的预定申请！\n");
		}
		// 若总人数未达到最小总人数，视为错误请求
		if (headcount < MIN_PERSONS) {
			throw new ReservationException("订购人数太少, 至少应有 " + MIN_PERSONS + " 人.\n");
		}
		// 若总费用未达到最小总费用，视为错误请求
		if (headcount * yuanPerHead < MIN_TOTAL_FEE) {
			throw new ReservationException("订购总费用太少，至少应达 " + MIN_TOTAL_FEE + " RMB.\n");
		}
		
		return new Reservation(date, headcount, city, yuanPerHead, hasSite);
	}

}
