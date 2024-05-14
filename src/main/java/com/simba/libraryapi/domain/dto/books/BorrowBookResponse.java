package com.simba.libraryapi.domain.dto.books;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BorrowBookResponse {

    @JsonProperty("return_date")
    private String returnDate;

    @JsonProperty("book_data")
    private BookData bookData;

    @Data
    @EqualsAndHashCode(onlyExplicitlyIncluded = true)
    @JsonIgnoreProperties(ignoreUnknown = true)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class BookData {

        @JsonProperty("book_name")
        private String name;
    }
}
