package zzz.explore.ruleengine.sample.helper;

import zzz.explore.ruleengine.sample.model.RuleFlowConnection;
import zzz.explore.ruleengine.sample.model.RuleFlowNode;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class RuleFlowHelper {

    private String ruleFlowType = "RuleFlow";
    private String pkgName = "ruleflow";
    private String ruleFlowName;
    private String ruleFlowId;

    private List<String> rfgNames;

    public RuleFlowHelper() {
    }

    public RuleFlowHelper(String ruleFlowName, List<String> rfgNames) {
        this.ruleFlowName = ruleFlowName;
        this.ruleFlowId = pkgName + "." + ruleFlowName;
        if (rfgNames == null || rfgNames.size() == 0) {
            rfgNames = new ArrayList<String>();
        }
        this.rfgNames = rfgNames;
    }


    public void createRFXmlFile(File toFile) {
        String s = createRFText();
        try {
            BufferedWriter bw = new BufferedWriter(
                    new FileWriter(toFile));
            try {
                bw.write(s);
            } catch (Exception e) {
                e.printStackTrace();
            }
            bw.close();
            System.out.println("Successfully Create Xml-formatted File of Rule Flow!");
            System.out.println("The text: \n" + s);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String createRFText() {
        StringBuilder sb = new StringBuilder();

        appendXMLHeader(sb);
        appendRuleFlowInfo(sb);
        appendInfoHeader(sb);
        appendNodes(sb, createNodes());
        appendConns(sb, createConns());

        sb.append("</process>");
        sb.append('\n');

        return sb.toString();

    }

    private StringBuilder appendXMLHeader(StringBuilder sb) {
        sb.append(getRFXmlHeader(new File("./rules/rfxmlheader.txt")));
        return sb;
    }

    private StringBuilder appendInfoHeader(StringBuilder sb) {
        sb.append("<header>");
        sb.append('\n');
        sb.append("</header>");
        sb.append('\n');
        sb.append('\n');
        return sb;
    }

    /*
     * ������������Ϣת��Ϊ�ַ���ʽ�����洢�ڹ������ı��ַ�����
     */
    private StringBuilder appendRuleFlowInfo(StringBuilder sb) {
        String header = "type=\"" + ruleFlowType + "\" " +
                "name=\"" + ruleFlowName + "\" " +
                "id=\"" + ruleFlowId + "\" " +
                "package-name=\"" + pkgName + "\" " + ">";
        sb.append(header);
        sb.append('\n');
        sb.append('\n');
        return sb;
    }

    /*
     * ���������ڵ㼯��ת��Ϊ�ַ���ʽ�����洢�ڹ������ı��ַ�����
     */
    private StringBuilder appendNodes(StringBuilder sb, List<RuleFlowNode> rfns) {
        sb.append("<nodes>");
        sb.append('\n');
        for (RuleFlowNode rfn : rfns) {
            sb.append(rfn);
            sb.append('\n');
        }
        sb.append("</nodes>");
        sb.append('\n');
        sb.append('\n');
        return sb;
    }

    /*
     * ���������ڵ����Ӽ���ת��Ϊ�ַ���ʽ�����洢�ڹ������ı��ַ�����
     */
    private StringBuilder appendConns(StringBuilder sb, List<RuleFlowConnection> connections) {
        sb.append("<connections>");
        sb.append('\n');
        for (RuleFlowConnection rfg : connections) {
            sb.append(rfg.desc());
            sb.append('\n');
        }
        sb.append("</connections>");
        sb.append('\n');
        sb.append('\n');
        return sb;
    }


    /*
     * ��ȡXML��ʽ�������ļ���XML��Ϣͷ���������ַ���
     */
    private String getRFXmlHeader(File file) {
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String s = null;
            StringBuilder sb = new StringBuilder();
            try {
                while ((s = br.readLine()) != null) {
                    sb.append(s);
                    sb.append('\n');
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            br.close();
            return sb.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /*
     * �����������ڵ�����Ӽ���
     */
    private List<RuleFlowConnection> createConns() {
        List<RuleFlowConnection> conns = new ArrayList<RuleFlowConnection>();
        int rfgNum = rfgNames.size();
        for (int i = 0; i <= rfgNum; i++) {
            conns.add(new RuleFlowConnection(i, i + 1));
        }
        return conns;
    }

    /*
     * �����������ڵ㼯��
     */
    private List<RuleFlowNode> createNodes() {
        List<RuleFlowNode> nodes = new ArrayList<RuleFlowNode>();

        nodes.add(new RuleFlowNode(0, "start", "Start", null));
        int i = 0;
        for (String name : rfgNames) {
            nodes.add(new RuleFlowNode(++i, "ruleSet", name, name));
        }
        nodes.add(new RuleFlowNode(++i, "end", "End", null));
        return nodes;
    }

}
