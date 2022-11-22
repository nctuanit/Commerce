package com.bach.Commerce.model.dto;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReviewDTO {

    private int id;
    private String review;
    private int starNumber;
    private String reviewDate;
    private UserDTO userDTO;
    private ProductDTO productDTO;

    public ReviewDTO() {
        super();
        // TODO Auto-generated constructor stub
    }

    public ReviewDTO(int id, String review, int starNumber, String reviewDate, UserDTO userDTO,
                     ProductDTO productDTO) {
        super();
        this.id = id;
        this.review = review;
        this.starNumber = starNumber;
        this.reviewDate = reviewDate;
        this.userDTO = userDTO;
        this.productDTO = productDTO;
    }


}
