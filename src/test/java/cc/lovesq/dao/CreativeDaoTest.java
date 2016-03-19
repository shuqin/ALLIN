/*
package cc.lovesq.dao;

import org.jtester.unitils.dbfit.DbFit;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.unitils.spring.annotation.SpringBeanByName;

import cc.lovesq.model.CreativeDO;

public class CreativeDaoTest extends BaseDAOJTester {

    @SpringBeanByName
    private CreativeDAO creativeDAO;

    @Test
    @DbFit(
            when = {baseWikiUrl + "CreativeDaoTest.testInsertCreative.initBlank.wiki"},
            then = baseWikiUrl + "CreativeDaoTest.testInsertCreative.then.wiki")
    public void testInsertCreative() {
        CreativeDO creativeDO = new CreativeDO();
        creativeDO.setTitle("Good");
        creativeDO.setContent("Good idea for education");
        creativeDO.setComment("oh");
        creativeDO.setSubmitter("qinshu");
        Integer key = creativeDAO.save(creativeDO);
        Assert.assertNotNull(key);
    }

    @Test
    @DbFit(
            when = {baseWikiUrl + "CreativeDaoTest.testQueryCreative.initOneRecord.wiki"})
    public void testQueryCreativeById() {
        CreativeDO creativeDO = creativeDAO.findByCreativeId(111L);
        Assert.assertNotNull(creativeDO);
        Assert.assertEquals(creativeDO.getTitle(), "Good");
        Assert.assertEquals(creativeDO.getContent(), "Good idea for education");
        Assert.assertEquals(creativeDO.getComment(), "oh");
        Assert.assertEquals(creativeDO.getSubmitter(), "qinshu");
    }

    @Test
    @DbFit(
            when = {baseWikiUrl + "CreativeDaoTest.testDeleteCreativeById.initOneRecord.wiki"})
    public void testDeleteClusterConfigById() {
        Long creativeId = 111L;
        CreativeDO creativeDO = creativeDAO.findByCreativeId(creativeId);
        Assert.assertEquals(creativeDO.getCreativeId(), creativeId);
        Integer rows = creativeDAO.delete(creativeId);
        Assert.assertEquals(rows.intValue(), 1);
        CreativeDO deleted = creativeDAO.findByCreativeId(creativeId);
        Assert.assertNull(deleted);
    }

    @Test
    @DbFit(
            when = {baseWikiUrl + "CreativeDaoTest.testUpdateCreative.initOneRecord.wiki"},
            then = baseWikiUrl + "CreativeDaoTest.testUpdateCreative.then.wiki")
    public void testUpdateClusterConfig() {
        Long creativeId = 111L;
        CreativeDO creativeDO = creativeDAO.findByCreativeId(creativeId);
        Assert.assertEquals(creativeDO.getCreativeId(), creativeId);
        creativeDO.setTitle("Better");
        creativeDO.setContent("Good idea for education and science");
        creativeDO.setComment("oh very good");
        Integer rows = creativeDAO.update(creativeDO);
        Assert.assertEquals(rows.intValue(), 1);
    }


}
*/
