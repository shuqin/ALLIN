package cc.lovesq.pojo;

public class NcLoadData {
	
	private Long timestamp;
	
	private double ldavg_1;
	private double ldavg_5;
	private double ldavg_15;
	
	public Long getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(Long timestamp) {
		this.timestamp = timestamp;
	}
	public double getLdavg_1() {
		return ldavg_1;
	}
	public void setLdavg_1(double ldavg_1) {
		this.ldavg_1 = ldavg_1;
	}
	public double getLdavg_5() {
		return ldavg_5;
	}
	public void setLdavg_5(double ldavg_5) {
		this.ldavg_5 = ldavg_5;
	}
	public double getLdavg_15() {
		return ldavg_15;
	}
	public void setLdavg_15(double ldavg_15) {
		this.ldavg_15 = ldavg_15;
	}
	
}
