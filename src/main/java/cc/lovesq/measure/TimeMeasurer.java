package cc.lovesq.measure;

public class TimeMeasurer implements TimeMeasurerIF {

  private CommandIF command;

  public TimeMeasurer(CommandIF command) {
    this.command = command;
  }

  public long timecost() {
    long start = System.currentTimeMillis();
    command.runTask();
    long end = System.currentTimeMillis();
    return end - start;
  }

}
