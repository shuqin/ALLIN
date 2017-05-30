package cc.lovesq.controller;


import org.junit.Assert;
import org.junit.Test;

import java.util.List;
import java.util.Map;

import cc.lovesq.query.CreativeQuery;

/**
 * Created by shuqin on 17/5/29.
 */
public class CreativeControllerTest {

  CreativeController controller = new CreativeController();

  @Test
  public void testSearchForSelect2() {
    CreativeQuery creativeQuery = controller.buildCreativeQuery("haha", 1, 20);
    Map<String, Object> result = controller.searchForSelect2(creativeQuery,
                                                            (q) -> null , (q)-> 0);
    Assert.assertEquals(0, ((List)result.get("rows")).size());
    Assert.assertEquals(0, ((Integer)result.get("total")).intValue());

  }

}
