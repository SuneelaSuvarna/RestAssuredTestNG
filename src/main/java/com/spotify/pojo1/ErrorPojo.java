
package com.spotify.pojo1;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@JsonInclude(JsonInclude.Include.NON_NULL)

@Getter @Setter
public class ErrorPojo {

    @JsonProperty("error")
    private Error error;

   

}
