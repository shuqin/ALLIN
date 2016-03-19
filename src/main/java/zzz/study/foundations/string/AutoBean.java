package zzz.study.foundations.string;

import java.io.PrintStream; 
import java.math.BigDecimal; 

public class AutoBean { 

	private Integer id;

	public void setId(Integer id) { this.id = id; } 

	public Integer getId() {  return id; } 

	private String name;

	public void setName(String name) { this.name = name; } 

	public String getName() {  return name; } 

	private BigDecimal bigNum;

	public void setBigNum(BigDecimal bigNum) { this.bigNum = bigNum; } 

	public BigDecimal getBigNum() {  return bigNum; } 

	private PrintStream printStream;

	public void setPrintStream(PrintStream printStream) { this.printStream = printStream; } 

	public PrintStream getPrintStream() {  return printStream; } 


 }