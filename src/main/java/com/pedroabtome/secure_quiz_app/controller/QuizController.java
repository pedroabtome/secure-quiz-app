package com.pedroabtome.secure_quiz_app.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

import com.pedroabtome.secure_quiz_app.model.Question;
import com.pedroabtome.secure_quiz_app.model.User;
import com.pedroabtome.secure_quiz_app.service.QuestionsService;
import com.pedroabtome.secure_quiz_app.service.QuizUserDetailsService;

@Controller
public class QuizController {

    private final QuestionsService questionsService;
    private final QuizUserDetailsService quizUserDetailsService;

    public QuizController(QuestionsService questionsService,
                          QuizUserDetailsService quizUserDetailsService) {
        this.questionsService = questionsService;
        this.quizUserDetailsService = quizUserDetailsService;
    }

    // GET request for retrieving the login page
    @GetMapping("/login")
    public String showLoginPage() {
        return "login";
    }

    // GET request for retrieving the registration page
   @GetMapping("/registration")
    public String showRegistrationPage(Model model) {
        model.addAttribute("user", new User());
        return "registration";
}

    // POST request for registering user
    @PostMapping("/register")
    public String registerUser(@ModelAttribute("user") User user) {
    quizUserDetailsService.registerUser(
        user.getUsername(),
        user.getPassword(),
        user.getEmail(),
        user.getRole()
);
        return "redirect:/login";
    }

    @GetMapping("/quiz")
public String showQuizPage(Model model) {
    model.addAttribute("quizzes", questionsService.loadQuizzes());
    return "quiz";
}

    // GET request for retrieving quiz questions (home page)
    @GetMapping({"/", "/home"})
    public String showHomePage(Model model) {
        List<Question> quizzes = questionsService.loadQuizzes();
        model.addAttribute("quizzes", quizzes);
        return "home";
    }

    // GET mapping for retrieving the addQuiz page
    @GetMapping("/addQuiz")
    public String showAddQuizPage(Model model) {
        model.addAttribute("question", new Question());
        return "addQuiz";
    }

    // POST request for adding quiz questions
    @PostMapping("/addQuiz")
    public String addQuiz(@ModelAttribute Question question) {
        questionsService.addQuiz(question);
        return "redirect:/home";
    }

    // GET mapping for retrieving the editQuiz page
    @GetMapping("/editQuiz/{id}")
    public String showEditQuizPage(@PathVariable int id, Model model) {
        List<Question> quizzes = questionsService.loadQuizzes();

        Question questionToEdit = quizzes.stream()
                .filter(q -> q.getId() == id)
                .findFirst()
                .orElse(null);

        if (questionToEdit == null) {
            return "redirect:/home";
        }

        model.addAttribute("question", questionToEdit);
        return "editQuiz";
    }

    // PUT request for editing quiz questions
    @PutMapping("/editQuiz/{id}")
    public String editQuiz(@PathVariable int id, @ModelAttribute Question question) {
        question.setId(id);
        questionsService.editQuiz(question);
        return "redirect:/home";
    }

    // DELETE request for removing quiz questions
    @DeleteMapping("/deleteQuiz/{id}")
    public String deleteQuiz(@PathVariable int id) {
        questionsService.deleteQuiz(id);
        return "redirect:/home";
    }

    // POST request for submitting answers
    @PostMapping("/submitAnswers")
    public String submitAnswers() {
        // TODO: Implement answer submission logic when result service/model is ready
        return "redirect:/result";
    }

    // GET request for retrieving the result page
    @GetMapping("/result")
    public String showResultPage() {
        return "result";
    }
}