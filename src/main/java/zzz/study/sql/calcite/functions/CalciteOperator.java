package zzz.study.sql.calcite.functions;

import shared.utils.JsonPathUtil;

import java.util.regex.Pattern;

public class CalciteOperator {

	public static String regex_match(String s, String regex, int begin) {
		if (s == null) {
			return "";
		}
		if (begin >= s.length()) {
			return "";
		}
		s = s.substring(begin);
		Pattern patt = Pattern.compile(regex);
		boolean matched = patt.matcher(s).matches();
		return matched ? s : "";
	}

	public static String json_extract(String json, String path) {
		return JsonPathUtil.readValUsingJsonPath(json, path);
	}
}
