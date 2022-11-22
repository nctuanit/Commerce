package com.bach.Commerce.controller.admin;

import com.bach.Commerce.service.base.ReviewService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
@AllArgsConstructor
public class AdminReviewController {


    final  ReviewService reviewService;

    @GetMapping("/admin/review")
    public String getComment(Model model) {

        model.addAttribute("reviews", reviewService.getAllReview());

        return "/admin/viewReview";
    }

    @GetMapping("/admin/review/delete/{id}")
    public String deleteReview(@PathVariable(name = "id") int id) {

        reviewService.delete(id);

        return "redirect:/admin/review";
    }
}
