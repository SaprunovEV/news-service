package by.sapra.newsservice.web.v1.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/news")
public class NewsController {

    @GetMapping
    public ResponseEntity<String> findAll() {
        return ResponseEntity.ok("""
                {
                  "news": [
                    {
                      "id": 1,
                      "title": "Test title 1",
                      "abstract": "test abstract 1",
                      "body": "test body 1",
                      "commentsCount": 1
                    },
                    {
                      "id": 2,
                      "title": "Test title 2",
                      "abstract": "test abstract 2",
                      "body": "test body 2",
                      "commentsCount": 2
                    },
                    {
                      "id": 3,
                      "title": "Test title 3",
                      "abstract": "test abstract 3",
                      "body": "test body 3",
                      "commentsCount": 3
                    }
                  ]
                }""");
    }
}
