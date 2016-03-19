package zzz.study.patterns.visitor;

import java.io.File;
import java.io.IOException;
import java.util.Date;

import zzz.study.utils.DateUtil;

import static zzz.study.utils.PrintUtil.printNChars;
import static zzz.study.utils.PrintUtil.indent;

public class ActualVisitor implements Visitor {
    
	public void visitFile(File file) {
		
		if(file !=null && file.isFile()) {
			fileInfo(file);
		}
	}
	
	public void visitDir(File dir)
	{
		visitDir(dir, 1);
	}
	
	public void visitDir(File dir, int depth)
	{
		System.out.println("[D]" + dir.getAbsolutePath());
		File[] subFiles = dir.listFiles();
		adjust(subFiles);
		for (File fpath: subFiles) {
			if (fpath.getName().matches("^\\..*$")) {  //  ���  .xxx �ļ�
				continue;
			}
			if (fpath.isFile()) {
				treeIntent(depth);
				System.out.println("[F]" + fpath.getName());
			}
			else if (fpath.isDirectory()){
				treeIntent(depth);
				visitDir(fpath, depth+1);
			}
		}
	}
	
	// ���ļ����Ͻ��е���ʹ�����ļ�������Ŀ¼֮ǰ; �����ļ�����ֻ�����ļ���Ŀ¼
	// NOTE: ��ȻҲ�����ڱ����ʱ����е����ø�õ�Ч�ʣ�������ή�ʹ���������
	private void adjust(File[] files)
	{
		int i = 0, k = files.length-1;
		while (true) {
			while (i <= k && files[k].isDirectory()) {    // ֱ�������ļ�����
				k--;
			}
			while (i <= k && files[i].isFile()) {         // ֱ������Ŀ¼����
				i++;
			}
			if (i <= k) {
				swap(files, i, k);
				k--; i++;
			}
			else {
				break;
			}
		}
	}
	
	private void swap(File[] files, int i, int k)
	{
		File tmp = files[i];
		files[i] = files[k];
		files[k] = tmp;
	}
	
    // ����������ʽ
	private void treeIntent(int depth)
	{
		if (depth > 1) {
			indent(2*(depth-1));
		}
		printNChars('|', 1);
		printNChars('_', 1);
	}
	
	
	/*
	 * ��ݸ��ļ�·�����ӡ�����Ϣ
	 */
	public void fileInfo(File filePath) {
		
		try {
			System.out.println("��·����" + filePath.getPath());
			System.out.println("�Ƿ���·���� ? " + filePath.isAbsolute());
			System.out.println("���·����" + filePath.getCanonicalPath()); 
			System.out.println("��Ŀ¼·����" + new File(filePath.getCanonicalPath()).getParent()); 
			System.out.println("�Ƿ��ļ�?  " + filePath.isFile()); 
			System.out.println("�Ƿ�Ŀ¼?  " + filePath.isDirectory()); 
			System.out.println("�Ƿ�ɶ�?  " + filePath.canRead());
			System.out.println("�Ƿ��д?  " + filePath.canWrite());
			System.out.println("���һ�α��޸ĵ�ʱ��: " + DateUtil.toFormattedDate(new Date(filePath.lastModified())));
		} catch (IOException e) {
			e.printStackTrace(System.err);
		}
	}

}
