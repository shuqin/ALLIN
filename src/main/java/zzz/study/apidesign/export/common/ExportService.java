package zzz.study.apidesign.export.common;

import cc.lovesq.result.BaseResult;

public interface ExportService {

  BaseResult<String> export(ExportParam exportParam);

}


