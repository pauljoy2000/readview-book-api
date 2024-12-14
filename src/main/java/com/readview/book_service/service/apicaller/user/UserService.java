package com.readview.book_service.service.apicaller.user;

import com.readview.book_service.constants.Messages;
import com.readview.book_service.exception.ServiceUnavailableException;
import com.readview.book_service.service.apicaller.user.dto.TokenValidationRequestDTO;
import com.readview.book_service.service.apicaller.user.dto.TokenValidationResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@Service
public class UserService {

    @Autowired
    private DiscoveryClient discoveryClient;

    @Autowired
    private RestTemplate restTemplate;

    @Value("${app.services.user-service.id}")
    private String userServiceId;

    @Value("${app.services.user-service.endpoints.token-validation}")
    private String tokenValidationEndpoint;


    private URI getBaseURI() {
        List<ServiceInstance> userServiceInstances = discoveryClient.getInstances(userServiceId);
        if(userServiceInstances == null || userServiceInstances.isEmpty())  {
            throw new ServiceUnavailableException(Messages.USER_SERVICE_UNAVAILABLE);
        }

        return discoveryClient.getInstances(userServiceId).getFirst().getUri();
    }

    private URI getTokenValidationURI() throws URISyntaxException {
        URI baseURI = getBaseURI();
        return new URI(
                baseURI.getScheme(),
                baseURI.getAuthority(),
                tokenValidationEndpoint,
                null,
                null
        );
    }

    public TokenValidationResponseDTO validateAccessToken(String accessToken) throws URISyntaxException {
        TokenValidationResponseDTO responseDTO;
        TokenValidationRequestDTO requestDTO = new TokenValidationRequestDTO();

        try {
            URI tokenValidationURI = getTokenValidationURI();

            requestDTO.setAccessToken(accessToken);
            ResponseEntity<TokenValidationResponseDTO> responseEntity = restTemplate.postForEntity(
                    tokenValidationURI,
                    requestDTO,
                    TokenValidationResponseDTO.class);
            responseDTO = responseEntity.getBody();
        } catch (Exception e) {

            throw e;
        }

        return responseDTO;
    }
}
