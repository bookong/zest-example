package com.github.bookong.example.zest.springboot.remark;

import com.github.bookong.example.zest.springboot.AbstractZestTest;
import com.github.bookong.example.zest.springboot.base.entity.Remark;
import com.github.bookong.example.zest.springboot.base.repository.RemarkRepository;
import de.flapdoodle.embed.mongo.MongodExecutable;
import de.flapdoodle.embed.mongo.MongodProcess;
import de.flapdoodle.embed.mongo.MongodStarter;
import de.flapdoodle.embed.mongo.config.MongodConfigBuilder;
import de.flapdoodle.embed.mongo.config.Net;
import de.flapdoodle.embed.mongo.distribution.Version;
import de.flapdoodle.embed.process.runtime.Network;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;

/**
 * @author Jiang Xu
 */
public class RemarkTest extends AbstractZestTest {

    @Autowired
    private RemarkRepository remarkRepository;

    @Test
    public void test() {
        Remark remark = new Remark();
        remark.setContent("hello world");
        remark = remarkRepository.insert(remark);
        
        System.out.println(String.format("remark.id:%s", remark.getId()));

        Remark other = remarkRepository.findById(remark.getId()).get();
        System.out.println(String.format("other.content:%s", other.getContent()));

    }
}
