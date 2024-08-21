package hsn.restful.sandbox.model.requests;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class UpdateContactRequest {
    @NotBlank
    @JsonIgnore
    private String id;

    @NotBlank
    @Size(max = 100)
    private String firstName;

    @Size(max = 100)
    private String lastName;

    @Size(max = 100)
    @Email
    private String email;

    @Size(max = 100)
    // ToDo
    // Uncomment this to use custom regex, search regex for phone number in Google
    // @Pattern
    private String phone;
}
