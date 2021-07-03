package cc.lovesq.transfer;

import cc.lovesq.model.Creative;
import cc.lovesq.model.CreativeDO;

public class CreativeDataTransfer {

    public static Creative transfer2TO(CreativeDO creativeDO) {
        if (creativeDO == null) {
            return null;
        }

        Creative creative = new Creative();
        creative.setCreativeId(creativeDO.getCreativeId());
        creative.setTitle(creativeDO.getTitle());
        creative.setContent(creativeDO.getContent());
        creative.setComment(creativeDO.getComment());
        creative.setSubmitter(creativeDO.getSubmitter());
        creative.setGmtCreate(creativeDO.getGmtCreate());
        creative.setGmtModified(creativeDO.getGmtModified());

        return creative;
    }

    public static CreativeDO transfer2DO(Creative creative) {
        if (creative == null) {
            return null;
        }

        CreativeDO creativeDO = new CreativeDO();
        creativeDO.setCreativeId(creative.getCreativeId());
        creativeDO.setTitle(creative.getTitle());
        creativeDO.setContent(creative.getContent());
        creativeDO.setComment(creative.getComment());
        creativeDO.setSubmitter(creative.getSubmitter());
        creativeDO.setGmtCreate(creative.getGmtCreate());
        creativeDO.setGmtModified(creative.getGmtModified());

        return creativeDO;
    }

}