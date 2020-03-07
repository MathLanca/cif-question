package com.mackenzie.cif.question.domain.service;

import com.mackenzie.cif.question.domain.domain.Question;
import com.mackenzie.cif.question.domain.dto.QuestionDTO;
import com.mackenzie.cif.question.domain.repository.QuestionRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class QuestionService {
    @Autowired
    private QuestionRepository rep;
    public List<QuestionDTO> listAllQuestions(){
        log.info("Service list all questions >>>>>");
        return rep.findAll().stream().map(QuestionDTO::create).collect(Collectors.toList());
    }

    public QuestionDTO findByCode(String code){
        log.info("Service find by code >>>>>");
        Question question = rep.findByCode(code);
        if(question == null){
            return null;
        }
        QuestionDTO questionDTO = QuestionDTO.create(question);
        return questionDTO;
    }

    public QuestionDTO findById(String id){
        log.info("Service find by code >>>>>");
        Optional<Question> question = rep.findById(id);
        if(question == null){
            return null;
        }
        QuestionDTO questionDTO = QuestionDTO.create(question.get());
        return questionDTO;
    }

    public QuestionDTO insert(Question question) {
        return QuestionDTO.create(rep.save(question));
    }

    public QuestionDTO update(Question question, String id){
        Optional<Question> optional = rep.findById(id);

        if(optional.isPresent()){
            Question db = optional.get();
            db.setCode(question.getCode());
            db.setDescription(question.getDescription());
            rep.save(db);
            return QuestionDTO.create(db);
        }else{
            throw new RuntimeException("Could not update registry");
        }
    }

    public boolean delete(String id){
        if(rep.findById(id).map(QuestionDTO::create).isPresent()){
            rep.deleteById(id);
            return true;
        } return false;
    }
}
