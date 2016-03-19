package zzz.study.foundations.exception;

public class TaleOfCrazyForSalt {

	public void nuclearExperiment() throws ProbablySlinkingNuclearExperiment
	{
			throw new ProbablySlinkingNuclearExperiment("偷偷摸摸核试验 *):(*");
	}
	
	public void bigEarthquakeWOW() throws BigEarthquake
	{
		try {
			nuclearExperiment();
		} catch (Exception e) {
			BigEarthquake be = new BigEarthquake("大地震，大海啸侵袭！");
			be.initCause(e);
			throw be;
		}
	}
	
	public void nuclearEquipmentAccident() throws DestroyedNuclearEquipment 
	{
		try {
			bigEarthquakeWOW();
		} catch (Exception e) {
			DestroyedNuclearEquipment dne = new DestroyedNuclearEquipment("核设施毁坏……");
			dne.initCause(e);
			throw dne;
		}
	}
	
	
	
	public void nuclearAccident() throws TooMuchNuclearRadiationException 
	{
		try {
			nuclearEquipmentAccident();
		} catch (Exception e) {
			TooMuchNuclearRadiationException tmnre = new TooMuchNuclearRadiationException("核辐射过量！");
			tmnre.initCause(e);
			throw tmnre;
		}
	}
	
	public void crazyForSalt() throws CrazyForSaltException 
	{
		try {
			nuclearAccident();
		} catch (Exception e) {
			CrazyForSaltException cfse = new CrazyForSaltException("抢盐风波……");
			cfse.initCause(e);
			throw cfse;
		}
		
	}
	
	public static void main(String[] args) {
		
		System.out.println("There had been a harmonious society long long before, however until one day : ");
		
		try {
			new TaleOfCrazyForSalt().crazyForSalt();
		} catch (CrazyForSaltException e) {		
			e.printStackTrace(System.out);
		} finally {
			System.out.println("Should we say that the Japanese deserves what they get or something else ?");
		}
	}
	
}


class CrazyForSaltException extends Exception {
	
	public CrazyForSaltException(String message) {
		super(message);
	}
}

class TooMuchNuclearRadiationException extends Exception {

	public TooMuchNuclearRadiationException(String message) {
		super(message);
	}
}

class DestroyedNuclearEquipment extends Exception {
	
	public DestroyedNuclearEquipment(String message) {
		super(message);
	}
}

class BigEarthquake extends Exception {
	public BigEarthquake(String message) {
		super(message);
	}
}

class ProbablySlinkingNuclearExperiment extends Exception {
	public ProbablySlinkingNuclearExperiment(String message) {
		super(message);
	}
}