package cc.lovesq.repository;

import org.springframework.data.mongodb.core.MongoTemplate;

import java.io.Serializable;

/**
 * @Description mongodb 访问
 * @Date 2021/5/7 6:30 下午
 * @Created by qinshu
 */
public class MongoRepository<D> implements Serializable {
    private static final long serialVersionUID = 1L;

    MongoTemplate mongoTemplate;

    public MongoRepository(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    public void save(D entity) {
        try {
            mongoTemplate.save(entity);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

}