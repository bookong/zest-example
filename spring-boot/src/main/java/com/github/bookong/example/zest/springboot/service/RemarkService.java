package com.github.bookong.example.zest.springboot.service;

import com.github.bookong.example.zest.springboot.base.api.param.remark.RemarkParam;
import com.github.bookong.example.zest.springboot.base.entity.Remark;
import com.github.bookong.example.zest.springboot.base.enums.ApiStatus;
import com.github.bookong.example.zest.springboot.base.repository.RemarkRepository;
import com.github.bookong.example.zest.springboot.exception.ApiException;
import com.github.bookong.example.zest.springboot.support.xkcd.Xkcd;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * @author Jiang Xu
 */
@Service
public class RemarkService {

    private Logger           logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private RemarkRepository remarkRepository;

    @Autowired
    private RestTemplate     restTemplate;

    public Remark add(RemarkParam param) {
        logger.info("add user {} remark", param.getUserId());

        Remark remark = new Remark();
        BeanUtils.copyProperties(param, remark);
        remark = remarkRepository.insert(remark);

        return remark;
    }

    public Pair<Remark, Xkcd> getRemarkWithComic(String remarkId) {
        Remark remark = remarkRepository.findById(remarkId).get();
        if (remark == null) {
            throw new ApiException(ApiStatus.PARAM_ERROR, "remark does not exist");
        }
        Xkcd xkcd = httpGetXkcd(remark.getXkcdId());
        return new ImmutablePair<>(remark, xkcd);
    }

    public List<Remark> findUserOneDay(Long userId) {
        return remarkRepository.findOneDayRemarks(userId);
    }

    /** 根据漫画网站 xkcd.com 的接口查询漫画信息 */
    public Xkcd httpGetXkcd(Long id) {
        String url = String.format("https://xkcd.com/%d/info.0.json", id);
        ResponseEntity<Xkcd> entity = restTemplate.getForEntity(url, Xkcd.class);
        if (HttpStatus.OK.equals(entity.getStatusCode())) {
            return entity.getBody();
        }

        logger.error("http call {} fail. http status:{} ({})", url, entity.getStatusCode().value(), entity.getStatusCode().getReasonPhrase());
        throw new ApiException(ApiStatus.CALL_OTHER_SYSTEM);
    }
}
