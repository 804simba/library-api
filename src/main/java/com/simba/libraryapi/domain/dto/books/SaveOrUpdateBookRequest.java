package com.simba.libraryapi.domain.dto.books;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SaveOrUpdateBookRequest {

    @JsonProperty("book_id")
    private Long bookId;

    @JsonProperty("book_title")
    @NotEmpty(message = "book_title cannot be empty")
    private String title;

    @JsonProperty("book_author")
    @NotEmpty(message = "book_title cannot be empty")
    private String author;

    @JsonProperty("book_isbn")
    @NotEmpty(message = "book_title cannot be empty")
    private String isbn;

    @JsonProperty("book_publication_year")
    @NotEmpty(message = "book_title cannot be empty")
    @Pattern(regexp = "^\\d{4}$", message = "Year must be in YYYY format")
    private String publicationYear;
}
