package com.kickzHub.kickzHub.service;

import com.kickzHub.kickzHub.request.ReviewResponseDTO;
import com.kickzHub.kickzHub.model.Product;
import com.kickzHub.kickzHub.model.Review;
import com.kickzHub.kickzHub.model.User;
import com.kickzHub.kickzHub.repository.ReviewRepository;
import com.kickzHub.kickzHub.request.ReviewRequestDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReviewService {
    @Autowired
    private ReviewRepository reviewRepository;

    public ReviewResponseDTO addReview(ReviewRequestDTO reviewRequest) {
        Review review = new Review();
        Product product = new Product();
        product.setId(reviewRequest.getProductId());
        review.setProduct(product);

        User user = new User();
        user.setId(reviewRequest.getUserId());
        review.setUser(user);

        review.setComment(reviewRequest.getComment());
        review.setRating(reviewRequest.getRating());

        Review savedReview = reviewRepository.save(review);
        return convertToResponseDTO(savedReview);
    }

    public List<ReviewResponseDTO> getReviewsByProductId(Long productId) {
        return reviewRepository.findByProductId(productId).stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }

    public void deleteReview(Long id) {
        reviewRepository.deleteById(id);
    }

    private ReviewResponseDTO convertToResponseDTO(Review review) {
        ReviewResponseDTO responseDTO = new ReviewResponseDTO();
        responseDTO.setId(review.getId());
        responseDTO.setProductId(review.getProduct().getId());
        responseDTO.setUserId(review.getUser().getId());
        responseDTO.setComment(review.getComment());
        responseDTO.setRating(review.getRating());
        return responseDTO;
    }
}
