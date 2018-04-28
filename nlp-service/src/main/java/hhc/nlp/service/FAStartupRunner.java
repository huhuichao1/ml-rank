package hhc.nlp.service;

import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * @author huhuichao
 */
@Component
@Order(value = 1)
public class FAStartupRunner implements CommandLineRunner {
    @Override
    public void run(String... args) throws Exception {}
}
