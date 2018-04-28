package hhc.nlp.service.controller;

import com.ansj.vec.domain.WordEntry;
import hhc.nlp.service.service.Word2VectorRepository;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

/**
 * Created by huhuichao on 2018/4/26.
 */

@RestController
@RequestMapping("/v1/nlp/word2vector")
public class Word2vecController {

    @Autowired
    private Word2VectorRepository repository;
    private static final Logger LOGGER = LoggerFactory.getLogger(Word2vecController.class);


    @RequestMapping(value = "/distance", method = { RequestMethod.GET }, produces = { "application/json; charset=UTF-8" })
    @ApiOperation(value = "返回相似词", notes = "GET")
    public Set<WordEntry> distance(String word) throws Exception {
        return  repository.distance(word);
    }


}
