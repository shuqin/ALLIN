package zzz.study.patterns.builder;

public class ForgivingBuilder extends ReservationBuilder {
	
	public Reservation build() throws ReservationException {
		
		// 如果时间或地点为指定，则视为无效请求。
		if (date == null) {
			throw new ReservationException("没有指定日期，无效的预定申请！\n");
		}
		if (city==null) {
			throw new ReservationException("没有指定地点，无效的预定申请！\n");
		}
		
		if (headcount == 0) {
			// 如果未指定总人数，但指定单人费用，则根据最小总费用计算总人数
			if (yuanPerHead != 0) {
			     headcount = (int)(MIN_TOTAL_FEE / yuanPerHead);
			}
			// 如果总人数和单人费用均未指定，则分别置为默认总人数和默认单人费用
			else {
				headcount = MIN_PERSONS;
				yuanPerHead = MIN_TOTAL_FEE / headcount;
			}
		}
		else {
			// 如果指定了总人数，但单人费用为指定，则根据最小总费用计算单人费用
		    if (yuanPerHead == 0) {
		    	yuanPerHead = MIN_TOTAL_FEE / headcount;
		    }
		    // 如果均指定了，但未达到最小总费用，视为错误请求。
		    else if (headcount * yuanPerHead < MIN_TOTAL_FEE){
		    	throw new ReservationException("订购总费用太少，至少应达 " + MIN_TOTAL_FEE + " RMB.\n");
		    }
		}
		
		return new Reservation(date, headcount, city, yuanPerHead, hasSite);
	}

}
