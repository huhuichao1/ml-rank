package hhc.nlp.service.service;

import com.ansj.vec.Word2VEC;
import com.ansj.vec.domain.WordEntry;
import hhc.common.utils.stringUtils.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

@Repository
@PropertySource("classpath:/nlp-service.properties")
public class Word2VectorRepository {
    private static final Logger LOGGER = LoggerFactory.getLogger(Word2VectorRepository.class);


    @Value("${word2vec.path}")
    private String path;
    private Word2VEC vec=null;

//    private static final double scoreThreshold=0.5;

    private int size=10;

    @PostConstruct
    public void init(){
         vec = new Word2VEC();
        try {
            LOGGER.info("正在初始化word2vec..{}",path);
            Long time=System.currentTimeMillis();
            vec.loadJavaModel(path);
            LOGGER.info("初始化完毕，耗时：{}",(System.currentTimeMillis()-time));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Set<WordEntry> distance(String word){
        Set<WordEntry> wordEntries=null;
        if(StringUtil.isEmpty(word)){
            return wordEntries;
        }
        if(word.contains(",")){
            List<String> words= Arrays.asList(word.split(","));
            wordEntries=vec.distance(words);
        }else{
            wordEntries=vec.distance(word);
        }
       return wordEntries;
    }


//    /**
//     * 默认返回前10条
//     *
//     */
//    private Set<WordEntry>  adjustRecord(Set<WordEntry> wordEntries){
//        if(CollectionUtils.isEmpty(wordEntries)){
//            return null;
//        }
//        int length =wordEntries.size();
//        if(length<size){
//            return null;
//        }
//        TreeSet<WordEntry> result = new TreeSet<WordEntry>();
//        int i=0;
//        for(WordEntry entry:wordEntries){
//            result.add(entry);
//            i++;
//            if(i==size){
//                break;
//            }
//        }
//        return result;
//    }
//




}
