package zzz.study.apidesign.export.refund;

import cc.lovesq.result.BaseResult;

public interface RefundExportService {

  BaseResult<String> export(RefundExportParam refundExportParam);

}


