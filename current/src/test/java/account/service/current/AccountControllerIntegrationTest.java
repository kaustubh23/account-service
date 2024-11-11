package account.service.current;

import account.service.current.dto.OpenAccountRequest;
import account.service.current.service.AccountService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class AccountControllerIntegrationTest {


    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private AccountService accountService;

    @Test
    void openAccount_whenAccountIsCreated() throws Exception {
        OpenAccountRequest request = new OpenAccountRequest();
        request.setCustomerId(1L);
        request.setInitialCredit(100.0);

        mockMvc.perform(post("/api/account/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.statusCode").value("201"));
    }

    @Test
    void getCustomerInfo_whenCustomerExists() throws Exception {
        Long customerId = 1L;
        mockMvc.perform(get("/api/account/{customerId}/info", customerId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("200"));
    }

    @Test
    void getCustomerInfo_whenCustomerDoesNotExist() throws Exception {
        Long nonExistentCustomerId = 999L;
        mockMvc.perform(get("/api/account/{customerId}/info", nonExistentCustomerId))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value("404"));
    }
}
