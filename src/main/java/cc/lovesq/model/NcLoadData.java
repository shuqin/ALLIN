package cc.lovesq.model;

import lombok.Data;

@Data
public class NcLoadData {

    private Long timestamp;

    private double ldavg_1;
    private double ldavg_5;
    private double ldavg_15;

}
