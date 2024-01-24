package com.testing.piggybank;
import com.testing.piggybank.account.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AccountControllerApiTest {

    @Autowired
    private TestRestTemplate restTemplate;


    @Test
    public void testGetAccount() {
        ResponseEntity<AccountResponse> responseEntity = this.restTemplate.getForEntity("/api/v1/accounts/1", AccountResponse.class);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody().getId()).isEqualTo(1L);
        assertThat(responseEntity.getBody().getName()).isEqualTo("Rekening van Melvin");
        assertThat(responseEntity.getBody().getBalance()).isEqualTo(BigDecimal.valueOf(1168.64));
    }

    @Test
    public void testGetAccounts() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("X-User-Id", "1");

        HttpEntity<String> entity = new HttpEntity<>("parameters", headers);

        ResponseEntity<GetAccountsResponse> responseEntity = restTemplate.exchange("/api/v1/accounts", HttpMethod.GET, entity, GetAccountsResponse.class);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isNotNull();
        assertThat(responseEntity.getBody().getAccounts().size()).isEqualTo(1);
    }
}
